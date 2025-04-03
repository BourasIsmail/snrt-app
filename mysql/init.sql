-- Create the roles table
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);



-- Insert the SUPER_ADMIN_ROLES role
INSERT INTO roles (name) VALUES ('SUPER_ADMIN_ROLES');


