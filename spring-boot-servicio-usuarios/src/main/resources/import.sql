INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('sandralu7', '$2a$10$sVIqfHicdmjW5EBFTPutVu25rPbocnMqwviIM8tW3DGDAj8iJoHpe', true, 'Sandra', 'Rodriguez', 'ing.sandralu@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('sandralu8', '$2a$10$11BVkdcQHzjT4FOiOY0w2ujIi8d94XJsYPxmMVznOeXUDSpCsR3LG', true, 'Lucia', 'Torres', 'ing.lucia@gmail.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,1);