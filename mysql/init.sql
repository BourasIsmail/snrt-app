-- Create the unite table
CREATE TABLE unite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description VARCHAR(200)
);

-- Create the roles table
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(50) NOT NULL
);




-- Insert the SUPER_ADMIN_ROLES role
INSERT INTO roles (role) VALUES ('SUPER_ADMIN_ROLES');

INSERT INTO unite (nom,description) VALUES ('HD3', 'Description unite HD3')

