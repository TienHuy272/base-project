-- CREATE DATABASE `base-db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

--
-- Creating table users
--
CREATE TABLE IF NOT EXISTS `users`
(
    `id`           INT NOT NULL AUTO_INCREMENT,
    `username`     VARCHAR(50) NOT NULL,
    `role_id`      INT         NOT NULL,
    `email`        VARCHAR(255) DEFAULT NULL,
    `password`     VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
DEFAULT CHARSET = utf8mb4
COLLATE `utf8mb4_unicode_ci`;

--
-- Dumping data for table users
--
INSERT INTO users
VALUES ( 1, 'admin', 1, 'admin@localhost', '$2a$10$NQRfkTyx71hD3XyFHEcXjeHQ7uPwKqCvBZz.zA3YQUZoFIOR5B5O.');


--
-- Create table roles
--
CREATE TABLE IF NOT EXISTS `roles`
(
    `id` INT  NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(16) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
DEFAULT CHARSET = utf8mb4
COLLATE `utf8mb4_unicode_ci`;

INSERT INTO roles VALUES (1, 'ADMIN');
