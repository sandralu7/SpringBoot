insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (1, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (2, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (3, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (4, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (5, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (6, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (7, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (8, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (9, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (10, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (11, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (12, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (13, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (14, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (15, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (16, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (17, 'Sandra','Rodriguez', 'ing.sandralu@gmail.com', '2017-08-28', '')
insert into usua_usuarios (USUA_ID, USUA_NOMBRE, USUA_APELLIDO, USUA_EMAIL, USUA_CREATE_AT, USUA_FOTO) VALUES (18, 'Andres','Rodriguez', 'ing.andreslu@gmail.com', '2017-07-28', '')


INSERT INTO pani_albumes (album_id, album_cantidad, album_fecha, album_descripcion, album_nombre) VALUES (1, 200, '2020/01/01', 'Tony Stark y Pepper Pods', 'Pepperoni')


INSERT INTO pan_albumes_usuarios (album_usuario_id, album_usuario_nivel, album_id, usua_id) VALUES (1, 'Principiante', 1, 1);
INSERT INTO pan_albumes_usuarios (album_usuario_id, album_usuario_nivel, album_id, usua_id) VALUES (2, 'Intermedio', 1, 2);
INSERT INTO pan_albumes_usuarios (album_usuario_id, album_usuario_nivel, album_id, usua_id) VALUES (3, 'Avanzado', 1, 3);


INSERT INTO pani_seccion (seccion_id, seccion_descripcion, seccion_nombre, album_album_id) VALUES (1, 'Seccion 1', 'Iron Man 1', 1);
INSERT INTO pani_seccion (seccion_id, seccion_descripcion, seccion_nombre, album_album_id) VALUES (2, 'Seccion 2', 'Iron Man 2', 1);

INSERT INTO pani_ficha (ficha_id, ficha_descripcion, ficha_dificultad, ficha_nombre, seccion_seccion_id) VALUES (1, 'Armadura', 'F', 'Armadura', 1);
INSERT INTO pani_ficha (ficha_id, ficha_descripcion, ficha_dificultad, ficha_nombre, seccion_seccion_id) VALUES (2, 'Tony Stark', 'D', 'Tony Stark', 1);
INSERT INTO pani_ficha (ficha_id, ficha_descripcion, ficha_dificultad, ficha_nombre, seccion_seccion_id) VALUES (3, 'Pepper Pods', 'F', 'Pepper Pods', 2);
INSERT INTO pani_ficha (ficha_id, ficha_descripcion, ficha_dificultad, ficha_nombre, seccion_seccion_id) VALUES (4, 'Obadain', 'F', 'Obadain', 2);


INSERT INTO USUA_USER (USUA_USERNAME, USUA_PASSWORD, USUA_ENABLED) VALUES ('Sandra', '$2a$10$IhtYHnn40L6VnY4qHNsdiOmHi5QGw3oe4qfAmggpXT2r6u1PFkERG', 1);
INSERT INTO USUA_USER (USUA_USERNAME, USUA_PASSWORD, USUA_ENABLED) VALUES ('admin', '$2a$10$gaEajWEdOJq/xHS0pkgne.nkumouvu3gBAJQaNpXj/zkPe3GuMzQy', 1);


INSERT INTO USUA_AUTHORITIES (USUA_ID, AUTH_AUTHORITY) VALUES (1, 'ROLE_USER');
INSERT INTO USUA_AUTHORITIES (USUA_ID, AUTH_AUTHORITY) VALUES (2, 'ROLE_USER');
INSERT INTO USUA_AUTHORITIES (USUA_ID, AUTH_AUTHORITY) VALUES (2, 'ROLE_ADMIN');

