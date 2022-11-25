insert into clientes(id, nombre, apellido, email, create_at, foto) values (10, 'Jose','Ruiz','Jo@gmail.com','2022-08-28','')

insert into clientes(id, nombre, apellido, email, create_at, foto) values (11, 'Carlos','Ruiz','Carlos@gmail.com','2022-06-22','')
insert into clientes(id, nombre, apellido, email, create_at, foto) values (12, 'Jose','Ruiz','Jo@gmail.com','2022-08-28','')

insert into clientes(id, nombre, apellido, email, create_at, foto) values (13, 'Carlos','Ruiz','Carlos@gmail.com','2022-06-22','')
insert into clientes(id, nombre, apellido, email, create_at, foto) values (14, 'Jose','Ruiz','Jo@gmail.com','2022-08-28','')

insert into clientes(id, nombre, apellido, email, create_at, foto) values (15, 'Carlos','Ruiz','Carlos@gmail.com','2022-06-22','')
insert into clientes(id, nombre, apellido, email, create_at, foto) values (16, 'Jose','Ruiz','Jo@gmail.com','2022-08-28','')

insert into clientes(id, nombre, apellido, email, create_at, foto) values (17, 'Carlos','Ruiz','Carlos@gmail.com','2022-06-22','')
insert into clientes(id, nombre, apellido, email, create_at, foto) values (18, 'Jose','Ruiz','Jo@gmail.com','2022-08-28','')

insert into clientes(id, nombre, apellido, email, create_at, foto) values (19, 'Carlos','Ruiz','Carlos@gmail.com','2022-06-22','')
insert into clientes(id, nombre, apellido, email, create_at, foto) values (20, 'Jose','Ruiz','Jo@gmail.com','2022-08-28','')

insert into clientes(id, nombre, apellido, email, create_at, foto) values (21, 'Carlos','Ruiz','Carlos@gmail.com','2022-06-22','')
insert into clientes(id, nombre, apellido, email, create_at, foto) values (22, 'Jose','Ruiz','Jo@gmail.com','2022-08-28','')

insert into clientes(id, nombre, apellido, email, create_at, foto) values (23, 'Carlos','Ruiz','Carlos@gmail.com','2022-06-22','')
insert into clientes(id, nombre, apellido, email, create_at, foto) values (24, 'Jose','Ruiz','Jo@gmail.com','2022-08-28','')

insert into clientes(id, nombre, apellido, email, create_at, foto) values (25, 'Carlos','Ruiz','Carlos@gmail.com','2022-06-22','')

insert into productos (nombre, precio, create_at) values ('Panasonic LCD 75"',250000,NOW());
insert into productos (nombre, precio, create_at) values ('Samsung Galaxy S22','160000',NOW());
insert into productos (nombre, precio, create_at) values ('LG smatr TV 75"',220000,NOW());
insert into productos (nombre, precio, create_at) values ('Samsung Galaxy S20','140000',NOW());

insert into facturas (descripcion, observacion,cliente_id, create_at, total) values ('Factura equipos de oficina ', null,10, NOW(),0);
insert into facturas_items (cantidad, factura_id, producto_id) values(1,1,1);
insert into facturas_items (cantidad, factura_id, producto_id) values(2,1,3);

insert into users (username, password, enable) Values ('jose','$2a$10$WEBYuDCc5S4iNmJvdcH11OKrj23ZOA6p8YjXUN0uJH3fIySHPWkDq',1)
insert into users (username, password, enable) Values ('admin','$2a$10$1SLRV/aUjkwxLRAHv2KIBeOUn5tmFz5OWVLQwXQ4HDC77Peckkvj6',1)


insert into authorities (user_id, authority) VALUES (1, 'ROLE_USER');
insert into authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');
insert into authorities (user_id, authority) VALUES (2, 'ROLE_USER');