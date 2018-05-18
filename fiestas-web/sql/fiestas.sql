delete from TEMATICAENTITY_PRODUCTOENTITY;
delete from CONTRATOENTITY_PRODUCTOENTITY;
delete from ValoracionEntity;
delete from BonoEntity;
delete from BlogEntity;
delete from PagoEntity;
delete from ProductoEntity;
delete from ServicioEntity;
delete from UsuarioEntity;
delete from ContratoEntity;
delete from EventoEntity;
delete from ClienteEntity;
delete from HorarioEntity;
delete from EventoEntity;
delete from TematicaEntity;
delete from ProveedorEntity;



insert into ProveedorEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena, penalizado) values(10011, 'Hamburguesería Mc Donalds','https://pbs.twimg.com/profile_images/646210794535956481/UXp3jGpm_400x400.png' , '1012345678', 3212121212, 'mcdonalds@mcdonalds.com', 'cll.2 #20-32','mcdonalds','asd123', 0);
insert into ProveedorEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena, penalizado) values(10012, 'Iluminación de Diana','http://i2.wp.com/recursosaempresas.com/wp-content/uploads/2015/11/salon-fiestas.jpg?resize=600%2C270' ,'1012345679', 3212121213, 'dianagalan@yahoo.es', 'cll.2 #20-33','dianas','asd123', 0);
insert into ProveedorEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena, penalizado) values(10013, 'Decoración Pepitos','http://www.glits.mx/ckfinder/userfiles/images/Atmosphere%20Gala%204.jpg' ,'1012345670', 3212121214, 'pepitosdec@hotmail.com', 'cll.2 #20-34','pepitos','hola', 1);

insert into ClienteEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena) values(10111, 'Nicolás Hernández','https://www.psicoactiva.com/blog/wp-content/uploads/2017/07/hombre-soltero-feliz.jpg', '1012345681', 3112121212, 'nm.hernandez10@uniandes.edu.co', 'cll.2 #25-25','nicos', 'asd123');
insert into ClienteEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena) values(10112, 'Sofía Arias','https://static.iris.net.co/semana/upload/images/2015/10/23/447377_20029_1.jpg' ,'1012345682', 3112121213, 's.arias@uniandes.edu.co', 'cll.94 # 7-12','sofia', 'asd123');
insert into ClienteEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena) values(10113, 'Cristian Amaya','https://files.merca20.com/uploads/2013/08/shutterstock_130105307.jpg' ,'1012345683', 3112121214, 'cm.amaya11@uniandes.edu.co', 'cll.95 #10-1','cm.amaya', 'asd123');

insert into ServicioEntity(id, nombre, descripcion, tipo, imagen) values(10019,'Alimentación', 'Todos los productos alimenticios y servicios de comida durante los eventos','Comida', 'https://www.zingermanscatering.com/wp-content/uploads/2018/03/carter_ZINGCATER_GREYLINE_1217_0217-e1520459898740.jpg');
insert into ServicioEntity(id, nombre, descripcion, tipo, imagen) values(10020,'Iluminación','Los implementos necesarios para iluminar de acuerdo a la temática','Estética','https://i.pinimg.com/originals/31/83/55/31835572a22beadd84875ecc49140c9d.jpg');
insert into ServicioEntity(id, nombre, descripcion, tipo, imagen) values(10021,'Decoración', 'Decoración de los espacios designados a los eventos de acuerdo a la temática y gusto del cliente', 'Estética','https://i.pinimg.com/originals/67/47/cd/6747cda5a08b03d6e38c82b53417d15e.jpg');
insert into ServicioEntity(id, nombre, descripcion, tipo, imagen) values(10022,'Pastelería', 'Postres para celebraciones de acuerdo al evento y el cliente', 'Comida','https://s-media-cache-ak0.pinimg.com/736x/b0/46/47/b0464768114ab3fb32a90e70b216283a.jpg');
insert into ServicioEntity(id, nombre, descripcion, tipo, imagen) values(10023,'Vestuario', 'Vestimenta, prendas y maquillaje acorde a la decoración y la temática de los eventos', 'Estética','https://i.pinimg.com/736x/8e/33/9a/8e339ab2a5f40641487edf3239c33cf8--beach-costumes-luau-costume.jpg');
insert into ServicioEntity(id, nombre, descripcion, tipo, imagen) values(10024,'Música', 'Instrumentos musicales, DJs y equipos musicales', 'Entretenimiento','https://i.pinimg.com/originals/26/57/b4/2657b4dce7ff049e72fc9d52b80c1813.jpg');
insert into ServicioEntity(id, nombre, descripcion, tipo, imagen) values(10025,'Baile', 'Coreografías y servicios de baile', 'Entretenimiento','http://2.bp.blogspot.com/-WmW5UDPBphs/UvA7F1f6thI/AAAAAAAAAA8/KSCGJn5-NyQ/s1600/i-2sfqHDr-L.jpg');
insert into ServicioEntity(id, nombre, descripcion, tipo, imagen) values(10026,'Otros', 'Todos los servicios que no entren en las categorías anteriores', 'Otro','https://i.pinimg.com/originals/e1/41/6b/e1416b7e8d87282363b1f4b8e6f3a51b.jpg');

insert into ProductoEntity(id,nombre,precio,descripcion,incluye,personal,imagen,proveedor_id,valoracionpromedio,servicio_id) values(10076,'Comida para boda',80000, 'Cena para 30 personas, con entrada, plato fuerte y postre','Incluye meseros',4,'https://mesadedulces.org/wp-content/uploads/2017/09/mesa-dulce1-1024x682.jpg', 10011,0,10019);
insert into ProductoEntity(id,nombre,precio,descripcion,incluye,personal,imagen,proveedor_id,valoracionpromedio,servicio_id) values(10077,'Audio para boda',300000, 'DJ para boda','Incluye lo que necesite el dj, más parlantes incluye varios estilos de musica',6,'http://elcorreo.ae/sites/default/files/images/trabajo/dj-editing-benefits.jpg', 10012,0,10020);
insert into ProductoEntity(id,nombre,precio,descripcion,incluye,personal,imagen,proveedor_id,valoracionpromedio,servicio_id) values(10078,'Recreaciones para boda',200000, 'Recreadores para mantener activa a la gente','Incluye lo que necesiten los recreadores',2,'http://doblescantanteschile.cl/wp-content/uploads/2016/10/IMG-20161021-WA0014.jpg', 10011,0,10020);

insert into TematicaEntity(id,nombre, descripcion) values(10095,'Bodas','Matrimonios');
insert into TematicaEntity(id,nombre, descripcion) values(10096,'Fiestas Infantiles','Fiestas para niños y niñas');
insert into TematicaEntity(id,nombre, descripcion) values(10097,'Bautizo','Bautizos');
insert into TematicaEntity(id, nombre, descripcion) values(10098,'Grado','Grado de Colegio o Universidad');


insert into HorarioEntity(id, fecha, horafin, horainicio) values(10041,'08/03/2018','22:00:00', '13:00:00');
insert into HorarioEntity(id, fecha, horafin, horainicio) values(10042,'08/03/2019','22:00:00', '13:00:00');
insert into HorarioEntity(id, fecha, horafin, horainicio) values(10043,'08/03/2028','22:00:00', '13:00:00');



insert into UsuarioEntity(id,nombre,rol,token,login,contrasena) values(10001,'Hamburguesería Mc Donalds','Proveedor',10011,'mcdonalds','asd123');
insert into UsuarioEntity(id,nombre,rol,token,login,contrasena) values(10002,'Iluminación de Diana','Proveedor',10012,'dianas','asd123');
insert into UsuarioEntity(id,nombre,rol,token,login,contrasena) values(10003,'Decoración Pepitos','Proveedor',10013,'pepitos','asd123');
insert into UsuarioEntity(id,nombre,rol,token,login,contrasena) values(10004,'Nicolás Hernández','Cliente',10011,'nicos','asd123');
insert into UsuarioEntity(id,nombre,rol,token,login,contrasena) values(10005,'Sofía Arias','Cliente',10012,'sofia','asd123');
insert into UsuarioEntity(id,nombre,rol,token,login,contrasena) values(10006,'Cristian Amaya','Cliente',10013,'cm.amaya','asd123');
insert into UsuarioEntity(id,nombre,rol,token,login,contrasena) values(10007,'David Nino','Admin',6,'dnino','asd123');

insert into BonoEntity(id, aplicadesde, codigo, descuento, expira, motivo, proveedor_id)values(10031, '04/22/2018','jihrfu9i3', 15, '05/22/2018', 'Día de san valentin', 10011);
insert into BonoEntity(id, aplicadesde, codigo, descuento, expira, motivo, proveedor_id)values(10032, '04/23/2018','u934hq', 30, '05/22/2018', 'Día de la madre', 10012);
insert into BonoEntity(id, aplicadesde, codigo, descuento, expira, motivo, proveedor_id)values(10033, '04/24/2018','54jkbsdv', 50, '05/22/2018', 'Navidad', 10013);

insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados,cliente_id,tematica_id) values(10021,'Cumpleaños Luciana','08/03/2018','Cumpleaños Luciana','Luciana Ayala','Calle 44 45-86',25,10111,10095);
insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados,cliente_id,tematica_id) values(10022,'Bautizo Juan','08/04/2018','Bautizo en la Capilla San Nicolas','Juan Jose','Carrera 89 90-86',15,10112,10096);
insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados,cliente_id,tematica_id) values(10023,'Grado Maria','08/04/2018','Se gradua de derecho en la Javeriana','Maria Perez','Carrera 46 18-86',45,10113,10098);

insert into ContratoEntity(id, estado, tyc, valor, proveedor_id,horario_id,evento_id) values(10101, 'Por pagar', 'Sin términos ni condiciones', 2000000000, 10012,10041,10021);
insert into ContratoEntity(id, estado, tyc, valor, proveedor_id, horario_id,evento_id) values(10102, 'Pagada la primera cuota', 'Si el cliente cancela despues de 2 semanas de haber contratado el servicio deberá pagar el 100% del costo', 345000000, 10011, 10042,10022);
insert into ContratoEntity(id, estado, tyc, valor, proveedor_id,horario_id,evento_id) values(10103, 'Pagado', 'Sin términos ni condiciones', 20000000, 10011,10043,10023);

insert into PagoEntity(id,realizado,estado,metodoDePago,valor,evento_id) values (10101,0,'Rechazado','PSE',100000,10023);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor,evento_id) values (10102,1,'Confirmado','Tarjeta de Credito',250000,10022);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor,evento_id) values (10103,0,'Rechazado','Consignacion',350000,10022);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor,evento_id) values (10104,1,'Confirmado','Consignacion',350000,10021);


insert into ValoracionEntity(id, calificacion, comentario, producto_id) values(10025,5,'Excelente servicio', 10076);
insert into ValoracionEntity(id, calificacion, comentario, producto_id) values(10026,4,'Me encanto', 10077);
insert into ValoracionEntity(id, calificacion, comentario, producto_id) values(10027,2, 'Pesimo servicio', 10078);


insert into BlogEntity(id, titulo, cuerpo, likes, cliente_id, evento_id, imagen)values(10091, 'Muy buena fiesta','El servicio estuvo excelente, la pasé muy bien', 10, 10111,10022, 'https://blogmedia.evbstatic.com/wp-content/uploads/bloguk/shutterstock_199419065-730x487.jpg');
insert into BlogEntity(id, titulo, cuerpo, likes, cliente_id, evento_id, imagen)values(10092, 'Meh','No estuvo mala, pero no fue nada fuera de lo común.', 1, 10112,10022, 'https://lock-clock.com/sites/default/files/games/18839768_451712511831072_8751551293307569149_o.jpg');
insert into BlogEntity(id, titulo, cuerpo, likes, cliente_id, evento_id, imagen)values(10093, 'Pésima','Fue un fiasco. La comida, la música, y la decoración.', 1, 10113,10022, 'https://diginights.com/uploads/images/event/2018/03/31/2018-03-31-bad-taste-party-bierhuebeli/flyer_image-default-1.jpg');


