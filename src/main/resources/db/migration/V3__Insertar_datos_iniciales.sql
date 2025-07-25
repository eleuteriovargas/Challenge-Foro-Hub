-- Insertar usuario administrador
INSERT INTO usuarios (nombre, email, clave, rol)
VALUES ('Admin Principal', 'admin@forohub.com', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP2', 'ADMINISTRADOR');

-- Insertar usuario regular
INSERT INTO usuarios (nombre, email, clave)
VALUES ('Usuario Demo', 'usuario@forohub.com', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP2');

-- Insertar usuario regular 123456
INSERT INTO usuarios (nombre, email, clave)
VALUES ('Usuario Prueba', 'usuarioPrueba@forohub.com', '$2a$12$jgK1eURt3pOQSxOfzV8zAuvFdPqnmUFhoz.UT6E0TuSF7rox/C73q');

-- Insertar algunos tópicos de ejemplo
INSERT INTO topicos (titulo, contenido, autor_id)
VALUES
('¿Cómo usar Spring Boot?', 'Estoy comenzando con Spring Boot y necesito ayuda para configurar mi primer proyecto.', 2),
('Problema con autenticación JWT', 'No puedo hacer que funcione la autenticación con JWT en mi aplicación.', 2),
('Mejores prácticas para APIs REST', '¿Cuáles son las mejores prácticas actuales para diseñar APIs RESTful?', 1);