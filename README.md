Implementación del ejercicio de Tenis del PEI utilizando React para Frontend y Springboot para backend.

NOTA: este documento queda como un formalismo para cumplir con el estandar de Git. Pedir al administrdor del repositorio la guia completa de instalación, documento llamado "Setup de Ambiente Remoto - React-Java (RJ-PEI) 2020.doc"



### *Prerequisitos*
##### Instalar Java y Maven (Windows)

Se puede bajar desde esta página: https://java.com/en/download/manual.jsp

1 - Descargar  Java 1.8.0 (Testeado con 231, pero debería funcionar perfecto con la ultima), Ejemplo:


2 - Instalar java ejecutando el archivo .exe descargado y darle clic a “install” para comenzar la instalación. Ejemplo:


3-Finalmente luego de algunos diálogos intermedios la instalación termina. Ejemplo:


4-Verificando java. Acorde a la documentación oficial, hay que ir a la página https://java.com/en/download/uninstallapplet.jsp y hacer clic en el botón “Agree and Continue”.

NOTA: hacerlo con Firefox o iExplorer (dado que Chrome no soporta ejecutar el applet de java que dispara el botón). 

Si todo sale bien debería verse algo como:


5-Testear java en una consola. Para esto debemos ir a inicio de windows y escribir “cmd” y luego enter. Cuando abra la ventana escribir:

“java -version”

6- En caso que algo salga mal, deberemos verificar si están configuradas las variables de entorno apuntando a las carpetas de JDK de java. 

Abrir cuadro para variables de entorno: para ello hacemos clic con el botón derecho del ratón sobre Equipo > Propiedades > Configuración avanzada del sistema > pestaña Opciones avanzadas > botón Variables de entorno. 

Tenemos que añadir las siguientes:

CLASSPATH: C:\Program Files\Java\jdk1.8.0_111\jre

JAVA_HOME: C:\Program Files\Java\jdk1.8.0_111

PATH: … ;C:\Program Files\Java\jdk1.8.0_111\bin; … (en este caso, la variable PATH ya existirá, así que añadimos esa cadena a continuación de lo que haya, separada por

##### Maven Windows

1-Descargar la última versión de https://maven.apache.org/download.cgi. Ejemplo: apache-maven-3.6.3-bin.zip.

Más detalles de instalación en https://maven.apache.org/install.html

2- Descomprimir el archivo en C:\maven, de modo que todos los archivos de Maven se encontrarán en C:\maven\apache-maven-3.6.3

3- Configuramos las variables de entorno al igual que lo hicimos en el caso anterior con java. 

Abrir cuadro para variables de entorno: para ello hacemos clic con el botón derecho del ratón sobre Equipo > Propiedades > Configuración avanzada del sistema > pestaña Opciones avanzadas > botón Variables de entorno. 

En este caso añadiríamos:

M2: %M2_HOME%\bin

M2_HOME: C:\maven\apache-maven-3.6.3

PATH: …;C:\maven\apache-maven-3.6.3\bin; …

5-Verificar maven en una consola. Para esto debemos ir a inicio de windows y escribir “cmd” y luego enter. Cuando abra la ventana escribir:

“mvn --version”

NOTA: la consola no debe estar abierta previamente, dado que no tomaría los cambios de las variables anteriores.



### *Levantar backend y compilar*

La primera vez se instalan las dependecias usadas por el proyecto, esto lo realizará el comando mvn desde un repositorio de internet todo lo necesario.

1-Ir al raiz del proyecto tennis-app.

2-Ir a la carpeta **tennis-api**

3-Abrir una consola y ejecutar el siguiente comando:

mvn spring-boot:run

Este comando compila, hace build y levanta spring-boot. La primera vez descargará lo necesario. Luego solamente levanta spring-boot.

**NOTA: tener presente que la base de datos debe estar creada y configurada, de lo contrario el backend da error y no levanta. Ver punto 4 de sección Postgresql y pgAdmin.**





