-- Create the roles table
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(50) NOT NULL
);



-- Insert the SUPER_ADMIN_ROLES role
INSERT INTO roles (role) VALUES ('SUPER_ADMIN_ROLES');


