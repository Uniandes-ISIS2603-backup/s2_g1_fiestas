# Tutorial de documentación REST con JAX-RS Analyzer
A lo largo de este taller aprenderá cómo integrar la herramienta JAX-RS con su proyecto Maven
para utilizar la documentación de código para producir una documentación de 
API REST extensiva y legible.

## Vista general de los pasos
1. Actualizar Maven en su Netbeans si no lo ha hecho. [Tutorial Actualización Maven en Netbeans](mavennetbeans.md)
2. Documentar el código con Javadoc
3. Agregar y configurar JAX-RS Analyzer
4. Generar la documentación
5. Visualizarla en Swagger

### Actualizar Maven en su Netbeans
El plugin de Maven que se utilizará para generar la documentación necesita que 
se utilice una versión mínima de Maven3.3.9. 
Si nunca ha actualizado su Maven es posible que esté utilizando la versión que 
viene con Netbeans (3.0.5). Para actualizarla debe 
seguir los pasos del [Tutorial Actualización Maven en Netbeans](mavennetbeans.md)

### Documentar el código con Javadoc
Para producir una documentación que sea de utilidad para su equipo de desarrollo 
es importante que documente bien los métodos de los servicios REST para que, 
además de la generación automática del plugin, haya una descripción clara que explique su API.

Javadoc permite la utilización de algunos tag’s de HTML para crear una documentación 
más apropiada según sus requerimientos. La sugerencia es utilizar un formato de la siguiente forma:

``` html
/**
     * <h1>(GET|POST|PUT|DELETE) /url/{parametros} : Nombre Servicio</h1>
     * <pre>Cuerpo de petición (Si tiene): JSON {@link (ClaseDetailDTO)}
     * 
     * (Descripción del servicio)
     * 
     * Códigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK 
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * (Código de error HTTP)(Qué responde cuando hay error)
     * </code> 
     *</pre>
     * @param (Los parámetros del método)
     * @param (Si recibe JSON) {@link (ClaseDTO)} (Descripción del parámetro)
     * @return JSON (Si responde un JSON) {@link (ClaseDTO)} - (Descripción de la respuesta)
     * @throws (Tipo de Exepción) {@link (ClaseExcepción)} - (Descripción del escenario de error)
     */
```

Este formato contiene lo necesario para que tanto la documentación que se genera 
con Javadoc, como la que se genera con el plugin, 
sean claras y de utilidad para el equipo de desarrollo.

### Agregar y configurar JAX-RS Analyzer
JAX-RS es un plugin de Maven desarrollado por Sebastian Daschner que utiliza las 
anotaciones de JAX-RS y el Bytecode generado para generar una documentación de 
los servicios Rest que utiliza una aplicación.

> Si quiere conocer más de cómo funciona Jax-Rs analyzer puede ver el video 
explicativo de su creador Sebastian Daschner (https://github.com/sdaschner): 
[Create REST documentation with JAX-RS Analyzer - 
YouTube](https://youtu.be/TmG0Tnqv3gk)  

El plugin funciona desde Maven por lo que basta con configurar el Pom para que se 
descargue y se ejecute de manera automática. Para hacerlo debe buscar su archivo 
pom.xml del proyecto del FrontEnd en la capa que ofrece los servicios Rest y 
agregar el siguiente código en la sección de plugins:

```xml
<plugin>
	<groupId>com.sebastian-daschner</groupId>
  	<artifactId>jaxrs-analyzer-maven-plugin</artifactId>
  	<version>0.16</version>
  	<executions>
  		<execution>
      			<goals>
          			<goal>analyze-jaxrs</goal>
          		</goals>
      			<configuration>
          			<backend>swagger</backend>
 				<deployedDomain>localhost:8080</deployedDomain>
          		</configuration>
		</execution>
	</executions>
</plugin>
```

> Si no encuentra la sección de plugins dentro de su pom.xml puede agregarla al 
final del archivo de la siguiente forma:  

```xml
<project ...>
	<!--Tags anteriores-->
  	<build>
  		<plugins>
      			<!--Plugin de Jax-RS Analyzer-->
		</plugins>
	</build>
</plugin>
```

El plugin se puede configurar para elegir de qué manera se genera la documentación, 
se puede elegir un archivo de texto plano con un formato para que se entienda el 
API o un documento Ascii o un archivo de Swagger UI. En este tutorial utilizaremos 
Swagger para producir una documentación que sea útil para el equipo de desarrollo.

> Swagger UI es una plataforma de visualización de documentos de API Rest bastante
 amigable y sencilla de mantener. Si quiere leer más sobre la plataforma puede 
hacerlo en la página: [API Development Tools | Swagger UI | Swagger](https://swagger.io/swagger-ui/)  

### Generar la documentación
En primer lugar puede generar la documentación de Javadoc para verificar el 
formato y la claridad de su Javadoc.

> Si quiere hacer una prueba rápida sobre una sola clase de su proyecto puede 
utilizar el comando de consola de javadoc:  
> `javac -d RutaDondeQuiereGuardarElJavaDoc RutaDelArchivo.java`  

Al generar el Javadoc usted debería tener un formato similar a este:
![](https://preview.ibb.co/ihz3MH/Screen_Shot_2018_02_06_at_4_11_47_PM.png)

Este Javadoc ya le permite entender la funcionalidad de su API REST y comunicar a su equipo de desarrollo la forma de utilizar los servicios que su proyecto expone. Sin embargo el plugin, al utilizar esta documentación y la generación que hace con el análisis de JAX-RS, crea un documento mucho más útil para su equipo.

El plugin de JAX-RS Analyzer está integrado con el proceso de build, como se puede apreciar en la configuración del POM. Lo que está especificando es que cuando se haga build del proyecto se utilizará la función `<goal>` de `analyze-jaxrs` para generar la documentación. Es decir que basta con hacer `Clean and Build` en Netbeans para generarla.

> Una vez que inicie el proceso de build es probable que vea unos errores en consola de tipo `Could not access static property, reason: ...` Puede ignorar estos errores que son generados por el plugin y están en proceso de ser corregidos tal como lo expresa el desarrollador en el Issue Tracker de Github. Sin embargo estos errores no afectan la generación del archivo de documentación por lo que puede ignorarlos.  

Cuando termine el build de su proyecto puede buscar el archivo `swagger.json` en la carpeta `target` de su proyecto. Dentro de la carpeta de su proyecto front deberá buscar `target > jaxrs-analyzer > swagger.json`

![](https://preview.ibb.co/mW1t1H/Screen_Shot_2018_02_04_at_8_03_49_PM.png)

### Visualizarla en Swagger

Finalmente la documentación está lista para ser visualizada en Swagger UI. Lo primero que debe hacer es registrarse en la plataforma para poder crear el proyecto de su equipo dentro de la misma.

Para registrarse vaya a este link: 
[Build, Collaborate & Integrate APIs | 
SwaggerHub](https://app.swaggerhub.com/signup?utm_source=swaggerio&utm_medium=download-ui&utm_campaign=swaggerhub&_ga=2.121288456.1497930554.1517791522-1302079190.1515022654)

![](https://preview.ibb.co/iMaxTx/Screen_Shot_2018_02_04_at_8_06_32_PM.png)

> Puede iniciar sesión con su Github para mayor facilidad  

Siga las instrucciones de la página para crear su organización (su equipo de 
desarrollo). Luego podrá invitar a sus compañeros para que todos estén dentro del mismo proyecto.

Una vez complete el registro le aparecerá una pantalla en la que podrá crear un 
nuevo API:

![](https://preview.ibb.co/ifccTx/Screen_Shot_2018_02_04_at_8_10_05_PM.png)

Seleccione la opción de Importar un API para que pueda subir el archivo 
`swagger.json` que se generó con el plugin. Puede elegir darle un nombre a su API 
y la versión en la que se encuentra, o puede dejarlo con los valores por defecto.

Una vez finalice la importación en Swagger usted estará en una pantalla como esta:
![](https://preview.ibb.co/na0XFc/Screen_Shot_2018_02_06_at_4_16_06_PM.png)

Como puede ver Swagger le permite ver de forma muy clara cómo es su API Rest, 
qué servicios expone, cuáles son los parámetros que recibe, la respuesta y 
los códigos de respuesta. Explore la interfaz para que se familiarice con ella.

Al abrir el detalle de alguno de los métodos debería ver algo similar a esto:

![](https://preview.ibb.co/eVrHgH/Screen_Shot_2018_02_06_at_4_15_38_PM.png)

> Algunas funcionalidades de Swagger le permiten crear versiones de su API por 
ejemplo para permitirle crear una actualización en la que cambie algunos de los 
servicios pero que siga existiendo la documentación para versiones antiguas. 
Le permite compartir su API si desea hacerla pública o incluso le genera el código 
base para comunicarse con su API en distintos lenguajes como Angular, AngularJS, Android, Swift, Php y muchos otros.  

Por el momento el plugin no será capaz de entender sus códigos de respuesta en 
caso de error puesto que no hay implementación de lógica dentro de sus servicios. 
A medida que avance en el desarrollo del proyecto podrá ver cómo la información 
que extrae el plugin se vuelve más precisa.

El resultado final es que su equipo podrá compartir la documentación de una forma 
clara y con funcionalidades útiles para el desarrollo y la organización de su software.
