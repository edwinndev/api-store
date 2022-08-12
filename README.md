# Empezando

##Version 1.0.0

## Documentacion de referencia

ApiStore es una aplicacion de tipo API-RESTfull, cuenta con las operaciones basicas en una base de datos como: crear, actualizar, buscar y eliminar recursos.
Está desarrollada con el lenguaje de programacion Java y el marco de trabajo Spring Boot.<br>
Como Servidor de Base de datos: MySQL y PostgreSQL ambos de tipo Relacional,
autenticacion mediante JWT.



### Contenido
Lo siguiente describe el contenido de esta aplicacion
* CRUD de Productos
* CRUD de Ordenes(Pedidos)
* CRUD de Usuarios
* Autenticacion y autorizacion
* Seguridad con JWT

<hr>
### Funcionalidad:

Un usuario puede registrarse con sus datos personales los cuales la aplicacion guardará en la Base de Datos. Los datos sensibles como el password se encriptan antes ser guardados para evitar riesgos.

Teniendo el usuario sus credenciales, este puede autenticarse mediante su usuario y password, ademas segenerará un TOKEN de ACCESSO para el usuario en cuestion, es necesario este token para poder acceder a consumir recursos de esta aplicacion.

Esta aplicacion esta asegurada con Spring Security, lo cual evita que aquel usuario no autenticado ni autorizado pueda consumir recursos.

<hr>
### Tecnologias usadas

* Language	: Java Amazon Correto JDK 11
* Framework    	: Spring Boot 2.7.2
* Building     	: Maven 3.8.5
* BBDD         	: MySQL 8.0.30 - PostgreSQL
* IDE's        	: IntelliJ y NetBeans
* Rest-Client  	: Postman