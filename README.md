# Proyecto de referencia de Ingeniería de Software I

Este proyecto de referencia de Ingeniería de Software I de la Universidad
Nacional de Colombia implementa una interacción básica del caso de uso CU-01,
«Validar una declaración de importación prellenada», para el caso de una
importadora de calzado. Su propósito es servir como ejemplo de una aplicación
web organizada en capas y ejecutable en GitHub Codespaces.

## Alcance

La aplicación presenta dos declaraciones de ejemplo. La primera contiene
campos que requieren revisión; la segunda tiene sus campos confiables. Para
cualquier declaración, la persona usuaria puede consultar el borrador, corregir
o aceptar explícitamente el valor de un campo, y solicitar la validación. Si
quedan campos sin revisar, la aplicación muestra una advertencia con los
nombres pendientes. Cuando todos están revisados o son confiables, la
declaración cambia a estado `VALIDADA`.

No forman parte de este proyecto la creación de declaraciones, la carga de
documentos, la extracción automática de información u OCR, el cálculo de CIF y
tributos, ni la generación de un PDF.

## Tecnologías

- Java 21.
- Spring Boot 4.1.2, según la versión declarada en `pom.xml`.
- Thymeleaf para renderizar las vistas HTML.
- Maven Wrapper para compilar, probar y ejecutar la aplicación.
- H2 en modo archivo para conservar la base de datos entre ejecuciones.

## Abrir el proyecto en GitHub Codespaces

### Desde un navegador

En la página del repositorio, selecciona **Code → Codespaces → Create
codespace on main**. Codespaces prepara el entorno y abre el proyecto en
Visual Studio Code para la Web.

### Desde VS Code de escritorio

Instala la extensión **GitHub Codespaces**, inicia sesión en GitHub y, desde
la paleta de comandos, ejecuta **Codespaces: Connect to Codespace**. Elige un
Codespace creado para este repositorio.

### Qué prepara el contenedor

La configuración de `.devcontainer/devcontainer.json` usa una imagen de
devcontainer con Java 21 y configura estas extensiones de VS Code:

- Extension Pack for Java.
- Spring Boot Extension Pack.
- GitHub Copilot y GitHub Copilot Chat.

También reenvía el puerto 8080 y muestra un aviso cuando VS Code detecta la
aplicación en ese puerto. Maven no está instalado en este ambiente; el proyecto
incluye el Maven Wrapper y se debe usar siempre `./mvnw`.

El uso de Codespaces está sujeto a las cuotas gratuitas y límites del plan de
GitHub. Para reducir el consumo, detén el Codespace cuando termines de
trabajar. GitHub también detiene los Codespaces después de un periodo de
inactividad; ese periodo puede depender de la configuración de la cuenta.

## Ejecutar la aplicación

Abre una terminal en la raíz del proyecto y ejecuta:

```bash
./mvnw spring-boot:run
```

La aplicación escucha en el puerto 8080. VS Code puede mostrar un aviso para
abrir el puerto; también se puede abrir desde la pestaña **Ports**. La página
inicial muestra el título del proyecto y una lista de declaraciones con su
estado y un enlace al detalle. En el detalle se ven los documentos y los
campos, con formularios para corregirlos y un botón para validar.

Usa siempre `./mvnw` en lugar de `mvn`: Maven no está instalado en el
Codespace. Para detener la aplicación, interrumpe el proceso en la terminal.

## Ejecutar las pruebas

Desde la raíz del proyecto, ejecuta:

```bash
./mvnw test
```

Las pruebas comprueban:

- Las reglas de las entidades: validación con campos pendientes, validación
  cuando no quedan pendientes y corrección de un campo.
- El servicio con `DeclaracionRepositoryEnMemoria`, una implementación de la
  interfaz de repositorio basada en un `Map`, sin librerías de mocking.
- El repositorio JPA contra H2 en memoria, incluida la lectura de las
  declaraciones de ejemplo insertadas por `data.sql`.
- La interacción web para listar una declaración, advertir sobre campos
  pendientes, corregir campos y completar la validación.

La configuración de pruebas está en
`src/test/resources/application.properties` y usa una base H2 en memoria; no
usa el archivo de datos de desarrollo.

## Base de datos y consola H2

Al ejecutar la aplicación, H2 guarda la base de datos en el archivo cuyo
prefijo de ruta es `./data/calzado`, relativo a la raíz del proyecto. El
directorio `data/` está excluido del control de versiones.

El esquema está versionado en `src/main/resources/schema.sql`. Allí se definen
las tablas `declaracion`, `documento_soporte` y `campo_declaracion`, además de
las secuencias usadas para asignar identificadores. Los datos iniciales están
en `src/main/resources/data.sql`; cada inserción comprueba que el identificador
no exista, por lo que volver a iniciar la aplicación no reemplaza los datos
existentes ni sobrescribe cambios guardados.

Las dos declaraciones de ejemplo son:

1. **Declaración 1**, en estado `BORRADOR`, con tres documentos: factura,
   documento de transporte y lista de empaque. Incluye seis campos: NIT del
   proveedor, valor FOB, flete, peso bruto con conflicto, subpartida con baja
   confianza y seguro vacío para captura manual. Los campos pendientes deben
   corregirse o aceptarse explícitamente antes de validar.
2. **Declaración 2**, en estado `BORRADOR`, con los mismos tres tipos de
   documentos y cinco campos confiables: NIT del proveedor, valor FOB, flete,
   peso bruto y subpartida. Está lista para validar.

La consola H2 está habilitada en la ruta `/h2-console`. Cuando el puerto 8080
esté reenviado, ábrela en el mismo host de la aplicación con la ruta
`/h2-console` (por ejemplo, `http://localhost:8080/h2-console` si el puerto
está disponible localmente). También se puede abrir el puerto 8080 desde
**Ports** y añadir esa ruta al host que muestra VS Code.

En el formulario de conexión de la consola utiliza estos datos, definidos en
`src/main/resources/application.properties`:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:file:./data/calzado;DB_CLOSE_ON_EXIT=FALSE` |
| Usuario | `sa` |
| Contraseña | Vacía |

La consola puede mostrar una JDBC URL predeterminada distinta. Reemplázala por
la JDBC URL indicada arriba antes de conectarte, para abrir la misma base de
datos que usa la aplicación.

## Estructura del proyecto

Las clases de la aplicación están bajo `co.edu.unal.sweng1.calzado` y se
organizan en estos paquetes:

| Paquete | Clases principales | Responsabilidad |
|---|---|---|
| `presentacion` | `HomeController`, `DeclaracionController` | Recibir solicitudes web, preparar los datos de las vistas y coordinar las acciones de consulta, corrección y validación. |
| `negocio` | `Declaracion`, `CampoDeclaracion`, `DocumentoSoporte`, `DeclaracionService`, `EstadoDeclaracion`, `EstadoCampo`, `TipoDocumento` y excepciones de negocio | Representar las entidades y reglas de negocio. El servicio coordina las operaciones y las entidades deciden si se puede validar una declaración. |
| `persistencia` | `DeclaracionRepository`, `DeclaracionRepositoryJpa` | Definir las operaciones de persistencia e implementarlas con `EntityManager` de JPA. |

Las plantillas Thymeleaf están en `src/main/resources/templates`; el esquema,
los datos iniciales y la configuración de la aplicación están en
`src/main/resources`.

## Convenciones de trabajo

Las instrucciones para asistentes de IA del proyecto están en
`.github/copilot-instructions.md`. Establecen el uso de `./mvnw` en lugar de
`mvn`, la ejecución de `./mvnw test` después de cada cambio y que solo se
realice un commit cuando todas las pruebas pasen. Los mensajes de commit deben
estar en español y describir el cambio. No se debe hacer push: lo realiza una
persona después de revisar los cambios.

## Uso de asistentes de inteligencia artificial

Este proyecto se construyó con GitHub Copilot en modo agente, dentro de VS Code
conectado a un Codespace. Se utilizó como apoyo para generar el esqueleto del
proyecto con Spring Initializr, crear la vista inicial, desarrollar las
entidades y sus reglas, implementar el servicio y el repositorio, construir
las vistas de declaraciones y preparar las pruebas. El propósito fue agilizar
la creación del ejemplo y apoyar la implementación de sus distintas capas.

Cada cambio fue revisado por una persona antes de subirse al repositorio.
