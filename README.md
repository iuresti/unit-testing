# Descripción

La aplicación se encarga del proceso de entrega/préstamos de libros de una biblioteca. 

Cuando el usuario solicita/entrega un libro al bibliotecario la aplicación debe leer una 
tarjeta que identifica al usuario a través de un dispositivo conectado vía puerto serial y 
registrar el evento en la base de datos. 

La aplicación recibe la petición de entrega o préstamo a través de un endpoint HTTP. 

Reglas:
1. Solo se pueden prestar hasta 5 libros a un usuario. 
1. El máximo tiempo que un usuario puede tener un libro prestado es de 1 mes. 
1. Si el usuario que lee el dispositivo (con la tarjeta) no existe en la base de datos pero está vigente 
su tarjeta, éste debe ser registrado en la base de datos.

## Protocolo de comunicación al lector

Todo mensaje lleva el format VISA 2 
02 STX
2 bytes de longitud en Big Endian
Mensaje
03 ETX
LRC (XOR de todos los bytes del mensaje a excepción del ETX)

- El receptor debe responder con un 06 (ACK) en caso de que el LRC sea correcto.
- Si el LRC no es correcto, el receptor responderá con un 0x15 (NAK) y el emisor deberá reenviar el mensaje
- Después de 3 intentos fallidos el receptor responde un 04 (EOT) y el flujo termina

### Solicutud de lectura
Comando: AX0

### Respuesta
LL -> 1 byte de longitud 
Nombre del usuario
CURP (Longitud fija de 18 bytes)
Fecha de expiración (Longitud fija de 4 bytes en formato BCD YYYYMMDD)  

     

