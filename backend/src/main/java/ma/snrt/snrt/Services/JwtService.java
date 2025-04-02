package ma.snrt.snrt.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    private static final String SECRET = "!@#$FDGSDFGSGSGSGSHSHSHSSHGFFDSGSFGSSGHSDFSDFSFSFSFSDFSFSFSF";

    // Generates an access token with a 30-minute expiration time
    public String generateToken(String userName) {
        // Create an empty map to store claims
        Map<String, Object> claims = new HashMap<>();
        // Call the createToken method with the claims, username, and expiration time of 30 minutes
        return createToken(claims, userName, 1000 * 60 * 30); // 30 minutes
    }

    // Generates a refresh token with a 7-day expiration time
    public String generateRefreshToken(String userName) {
        // Create an empty map to store claims
        Map<String, Object> claims = new HashMap<>();
        // Call the createToken method with the claims, username, and expiration time of 7 days
        return createToken(claims, userName, 1000 * 60 * 60 * 24 * 7); // 7 days
    }

    // Creates a token with the specified claims, subject, and expiration time
    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        // Build the JWT with the provided claims, subject, issued date, expiration date, and signing key
        return Jwts.builder()
                .setClaims(claims) // Set the claims for the token
                .setSubject(subject) // Set the subject (username) for the token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued date to the current time
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Set the expiration date
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Sign the token with the signing key and HS256 algorithm
                .compact(); // Compact the JWT into a string
    }

    // Retrieves the signing key for the JWT
    private Key getSignKey() {
        // Decode the secret key from Base64 format
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        // Generate the signing key using the decoded bytes
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extracts the username from the token
    public String extractUserName(String token) {
        // Extract the subject (username) claim from the token
        return extractClaim(token, Claims::getSubject);
    }

    // Extracts the expiration date from the token
    public Date extractExpiration(String token) {
        // Extract the expiration date claim from the token
        return extractClaim(token, Claims::getExpiration);
    }

    // Extracts a specific claim from the token
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // Extract all claims from the token
        final Claims claims = extractAllClaims(token);
        // Apply the claim resolver function to the claims and return the result
        return claimResolver.apply(claims);
    }

    // Extracts all claims from the token
    private Claims extractAllClaims(String token) {
        // Parse the token and extract the claims using the signing key
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // Set the signing key
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the claims from the token
    }

    // Checks if the token is expired
    private Boolean isTokenExpired(String token) {
        // Check if the expiration date of the token is before the current date
        return extractExpiration(token).before(new Date());
    }

    // Validates the token against the user details
    public Boolean validateToken(String token, UserDetails userDetails) {
        // Extract the username from the token
        final String userName = extractUserName(token);
        // Check if the username matches the user details and the token is not expired
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}