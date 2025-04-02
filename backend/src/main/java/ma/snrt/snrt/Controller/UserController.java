package ma.snrt.snrt.Controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.snrt.snrt.Entities.AuthRequest;
import ma.snrt.snrt.Entities.Roles;
import ma.snrt.snrt.Entities.Unite;
import ma.snrt.snrt.Entities.User;
import ma.snrt.snrt.Services.JwtService;
import ma.snrt.snrt.Services.RolesService;
import ma.snrt.snrt.Services.UniteService;
import ma.snrt.snrt.Services.UsersService;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Tag(name = "User",
        description = "This API provides the capability to search User from a User Repository")
public class UserController {
    private final UsersService userService;
    private final UniteService uniteService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RolesService rolesService;
    // Endpoint to test if the API is working
    @GetMapping("/test")
    @Operation(summary = "Test the API", description = "This endpoint is used to test if the API is working")
    public String welcome() {
        return "API is working";
    }


    // Endpoint to add a new user, accessible only to users with ADMIN_ROLES authority
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        try {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
    
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                Roles role = rolesService.getRole(user.getRoles().get(0).getId());
                newUser.setRoles(List.of(role));
            }
    
            if (user.getUnite() != null && user.getUnite().getId() != 0) {
                Unite unite = uniteService.getUnite(user.getUnite().getId());
                newUser.setUnite(unite);
            }
    
            User savedUser = userService.addUser(newUser);
    
            return new ResponseEntity<>("User added successfully with ID: " + savedUser.getId(), HttpStatus.CREATED);
    
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to login a user and generate JWT tokens
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest authRequest, HttpServletResponse response) {
        // Authenticate the user using the authenticationManager
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        // If authentication is successful
        if (authenticate.isAuthenticated()) {
            // Generate an access token and a refresh token
            String token = jwtService.generateToken(authRequest.getEmail());
            String refreshToken = jwtService.generateRefreshToken(authRequest.getEmail());

            // Create a cookie to store the refresh token
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true); // Set the cookie as HttpOnly
            refreshTokenCookie.setPath("/user/refresh-token"); // Set the path for the cookie
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // Set the cookie expiration time to 7 days
            response.addCookie(refreshTokenCookie); // Add the cookie to the response

            // Return the access token in the response
            return ResponseEntity.ok(token);
        } else {
            // If authentication fails, throw an exception
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    // Endpoint to refresh the access token using the refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@CookieValue("refreshToken") String refreshToken) {
        // Extract the username from the refresh token
        String username = jwtService.extractUserName(refreshToken);
        // If the username is valid and the token is valid
        if (username != null && jwtService.validateToken(refreshToken, userService.loadUserByUsername(username))) {
            // Generate a new access token
            String newToken = jwtService.generateToken(username);
            // Return the new access token in the response
            return ResponseEntity.ok(newToken);
        } else {
            // If the refresh token is invalid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    // Endpoint to get all users, accessible only to users with ADMIN_ROLES authority
    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        // Get the list of all users using the userService
        List<User> users = userService.getUsers();
        // Return the list of users in the response
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint to get a user by ID, accessible only to users with USER_ROLES authority
    @GetMapping("/getUsers/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Get the user by ID using the userService
        User user = userService.getUser(id);
        // Return the user in the response
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Endpoint to get a user by email, accessible only to users with ADMIN_ROLES authority
    @GetMapping("/getUsersByEmail/{email}")
    public ResponseEntity<User> getUsersByEmail(@PathVariable String email) {
        // Get the user by email using the userService
        User users = userService.getUserByEmail(email);
        // Return the user in the response
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint to delete a user by ID, accessible only to users with ADMIN_ROLES authority
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            // Delete the user by ID using the userService
            userService.deleteUser(id);
            // Return a response indicating the user was deleted successfully
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Return a response indicating an error occurred
            return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to update a user by ID, accessible to users with ADMIN_ROLES or USER_ROLES authority
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        // Update the user by ID using the userService
        User updatedUser = userService.updateUser(id, user);
        // Return the updated user in the response
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}