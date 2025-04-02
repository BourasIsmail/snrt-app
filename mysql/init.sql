-- Create the unite table
CREATE TABLE unite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create the roles table
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Create the user table
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    unite_id INT,
    FOREIGN KEY (unite_id) REFERENCES unite(id) ON DELETE SET NULL
);

-- Create the user_roles join table
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Insert the SUPER_ADMIN_ROLES role
INSERT INTO roles (name) VALUES ('SUPER_ADMIN_ROLES');

-- Insert the user
INSERT INTO user (id, name, email, password, unite_id) VALUES
(1, 'oussama', 'email2@email.com', '$2a$10$rbYFQ/0dE5vUwVgom3Wsz.RluWIQMU7/7sP4oFbhSxteiVVeKIQKu', NULL);

-- Assign the SUPER_ADMIN_ROLES role to the user
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1); -- Assuming the role ID for SUPER_ADMIN_ROLES is 1