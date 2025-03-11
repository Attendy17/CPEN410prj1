-- Crear el esquema y seleccionarlo
CREATE SCHEMA `proyecto`;
USE `proyecto`;

------------------------------------------------------------
-- Tabla para las páginas (webPageGood)
------------------------------------------------------------
CREATE TABLE webPageGood (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pageURL VARCHAR(255) NOT NULL,
    Title VARCHAR(100),
    Description VARCHAR(255),
    MenuID BIGINT,
    previous_page_id BIGINT NULL,
    CONSTRAINT fk_previous_page
        FOREIGN KEY (previous_page_id)
        REFERENCES webPageGood(id)
        ON DELETE SET NULL
);

------------------------------------------------------------
-- Tabla de Usuarios (con campo para la última página visitada)
------------------------------------------------------------
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Contraseña hasheada
    birth_date DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    profile_picture VARCHAR(255),    -- URL de la imagen de perfil
    last_page_id BIGINT NULL,         -- Última página visitada (referencia a webPageGood)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_last_page
        FOREIGN KEY (last_page_id)
        REFERENCES webPageGood(id)
        ON DELETE SET NULL
);

------------------------------------------------------------
-- Tabla de Direcciones
------------------------------------------------------------
CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    street VARCHAR(255),
    town VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

------------------------------------------------------------
-- Tabla de Educación
------------------------------------------------------------
CREATE TABLE education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    degree VARCHAR(100),
    school VARCHAR(150),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

------------------------------------------------------------
-- Tabla de Amistades
------------------------------------------------------------
CREATE TABLE friendships (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user1_id BIGINT NOT NULL,
    user2_id BIGINT NOT NULL,
    status ENUM('pending', 'accepted', 'rejected') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (user1_id, user2_id)
);

------------------------------------------------------------
-- Tabla de Roles
------------------------------------------------------------
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name ENUM('USER', 'ADMIN') NOT NULL UNIQUE
);

------------------------------------------------------------
-- Tabla de relación Usuario - Rol
------------------------------------------------------------
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    UNIQUE (user_id, role_id)
);

