package ma.snrt.snrt.Services;


import ma.snrt.snrt.Entities.Roles;
import ma.snrt.snrt.Entities.Type;
import ma.snrt.snrt.Entities.Unite;
import ma.snrt.snrt.Entities.User;
import ma.snrt.snrt.Repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
public class UsersService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesService rolesService;
    private final UniteService uniteService;

    public UsersService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, RolesService rolesService, @Lazy UniteService uniteService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesService = rolesService;
        this.uniteService = uniteService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UsersDetails(user);
    }

    public User addUser(User user) {
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public String deleteUser(Long id) {
        try {
            User user = getUser(id);
            userRepository.delete(user);
            return "User deleted";
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }

    }

    /**
     * Updates an existing user with new information.
     * @param id the ID of the user to update
     * @param user the user object containing updated information
     * @return the updated user
     */
    public User updateUser(Long id, User user) {
        User existingUser = getUser(id);
        // Only update roles if they are provided
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            List<Roles> newRoles = user.getRoles().stream()
                    .map(role -> rolesService.getRole(role.getId()))
                    .collect(Collectors.toList());
            existingUser.setRoles(newRoles);
        }
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        if(user.getUnite() != null) {
            Unite unite1 = uniteService.getUnite(user.getUnite().getId());
            existingUser.setUnite(unite1);
        }
        else {
            existingUser.setUnite(null);
        }

        // Only update password if it's provided and not empty
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(existingUser);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void removeUniteFromUsers(Long uniteId) {
        List<User> users = userRepository.findByUniteId(uniteId);
        for (User user : users) {
            user.setUnite(null);
            userRepository.save(user);
        }
    }
}
