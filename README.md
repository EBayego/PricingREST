# PricingREST

## Estructura, funcionalidades y explicación de decisiones técnicas

### Estructura
No se ha realizado una estructura de microservicios, ya que la aplicación no lo requiere. Si en un futuro se quiere realizar así, habría que crear un repositorio padre con su pom, especificando los modulos hijos y en cada modulo especificando el parent. El resto seguiría igual, por lo que es igualmente escalable.

Se ha estructurado en una arquitectura hexagonal, alojando cada clase en su carpeta correspondiente.

### Java y SpringBoot

Se ha utilizado Java 21 y SpringBoot 3.3.4. Se ha utilizado también Maven para su compilación.

### Bases de Datos

Se ha utilizado H2 ya que es requerimiento indispensable, y no se ha utilizado ni MongoDB, Couchbase o DB2 ya que, por lo tanto, no era necesario. Con este requerimiento indispensable ya se cubrían todas las necesidades y no se podían implementar ninguna de estas otras tecnologías.

### Testing

Se han implementado tests unitarios y de integración con Junit y Mockito.

### MVC vs WebFlux

Se ha implementado la aplicación es un enfoque reactivo con WebFlux para crear así una aplicación anti-bloqueante, evitando que la aplicación se bloquee al utilizar un hilo por tarea en MVC. En WebFlux, es posible utilizar un hilo para realizar varias tareas, por lo que la aplicacion no se bloquea en ningun momento.

### Cloud

Se ha implementado Eureka, Gateway y LoadBalancer. Config se ha eliminado por el momento, ya que no hay microservicios por ahora. Con estas 3 dependencias, se balancea la carga y se escoge el servidor adecuado en todo momento.

### Data

Se ha implementado con R2DBC, que es la alternativa no bloqueante de JPA.

### API

Las APIs se han implementado con API Rest

### OpenAPI

Para la version de OpenAPI con WebFlux, debe de haber algun tipo de incompatibilidad, ya que tras realizar todas las pruebas posibles con todas las posibilidades de paquetes de maven, como de las versiones de cada uno de ellos, y de spring boot incluido, en todas por un fallo u otro no mostraba nunca la pantalla de documentación. Dejo indicado igualmente en el código su implementación.

### Grafana

Se ha incluido Prometheus para capturar métricas de Spring Boot, para poder utilizar Grafana para monitorear el rendimiento de la aplicación. 

### OpenShift

Se ha dockerizado la aplicación con un Dockerfile, y los siguientes pasos serían crear la aplicación en OpenShift a partir del repositorio Git, generando la imagen gracias al Dockerfile, un BuildConfig y un DeploymentConfig. Después, se expone la aplicación y ya se podrá acceder a ella.

### Postman

Se ha utilizado Postman para mandar peticiones a la API y comprobar que la respuesta es la deseada.

### Resto de tecnologías valorables:

- Protobuf es un formato de serialización eficiente utilizado principalmente con gRPC, por lo que no se ha implementado para este proyecto.

- RXJava facilita la programación reactiva, útil cuando hay operaciones asíncronas intensivas o múltiples solicitudes simultáneas. Dado que  Spring WebFlux ya soporta programación reactiva de manera nativa, RXJava no es necesario.

- JMS, junto con Kafka, sirven para la mensajería que permite la comunicación entre aplicaciones mediante colas de mensajes. Dado que no hay microservicios por ahora, no es necesario.

- Zipkin no se ha incluido por el mismo motivo, por ahora no es una aplicacion con microservicios

- Cypress es útil para pruebas de front-end, pero esta aplicación se centra en un servicio REST de backend.

- Selenium se usa para automatizar pruebas de UI en aplicaciones web. Dado que el foco del proyecto es un API REST, no hay interfaz web que probar con Selenium.

