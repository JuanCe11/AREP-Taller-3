# Título del Proyecto

Taller 3 - AREP 

Clientes y Servicios

 [![Deployed to Heroku](https://www.herokucdn.com/deploy/button.png)](https://arep-taller-3.herokuapp.com/)
 
 [![CircleCI](https://circleci.com/gh/circleci/circleci-docs.svg?style=svg)](https://app.circleci.com/pipelines/github/JuanCe11/AREP-Taller-3)

 

## Comenzando 

Revise el archivo [Descripción](https://github.com/JuanCe11/AREP-Taller-3/blob/master/Taller_3_AREP.pdf) para tener informacion basica del problema y la solucion propuesta.

Para tener una copia del repositorio, desde consola ejecute el siguiente comando.

```
git clone https://github.com/JuanCe11/AREP-Taller-3.git
```

### Pre-requisitos 

- Java 8 - [How install](https://www.java.com/es/download/help/windows_manual_download.xml)
- Git - [How install](https://git-scm.com/book/es/v2/Inicio---Sobre-el-Control-de-Versiones-Instalaci%C3%B3n-de-Git)
- Maven - [How install](https://maven.apache.org/install.html)


### Instalacion 

Para la correcta instalacion se debe primero clonar el repositorio como se indico anteriormente, despues se ingresa al directorio del proyecto y para ejecutar la clase App (el servicio web) se ejecutan los siguientes comandos en windows.

```
cd AREP-Taller-3
mvn package
java -cp target/classes;target/dependency/* edu.eci.escuelaing.taller3.Application
```
Para sistemas linux usar: 

```
cd AREP-Taller-3
mvn package
java -cp target/classes:target/dependency/* edu.eci.escuelaing.taller3.Application
```
Cuando se tenga el servicio corriendo se ingresa a la direccion http://localhost:1234/ para ver el el servicio en el navegador.

Puede revisar las fuentes del proyecto revisar a detalle los test.

Puede generar la documentacion usando:

```
mvn javadoc:javadoc
```

## Ejecutando las pruebas 

Ahora para ejecutar los test se utiliza el comando:

```
mvn test
```

## Construido con 

* [Java 8](https://www.java.com/es/about/whatis_java.jsp)
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Spark Java](http://sparkjava.com/) - Framework de aplicaci�n web.


## Wiki

Puedes encontrar mucho mas de camo utilizar este proyecto en nuestra [Wiki](https://github.com/JuanCe11/AREP-Taller-3/wiki)


## Autores 

* **Juan Sebastia Gomez Lopez** - *Trabajo Inicial* - [JuanCe11](https://github.com/JuanCe11)


## Licencia

Este proyecto esta bajo la Licencia GNU General Public License - mira el archivo [LICENSE.txt](LICENSE.txt) para detalles

