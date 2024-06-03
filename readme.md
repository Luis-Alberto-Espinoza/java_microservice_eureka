# Proyecto de Microservicios

Este proyecto implementa un sistema basado en microservicios utilizando Spring Boot, Eureka para el descubrimiento de servicios, Feign para la comunicación entre microservicios, MySQL y PostgreSQL como base de datos. Los microservicios incluidos en este proyecto son:

- **Microservicio de Estudiantes (msvc-student)**
- **Microservicio de Cursos (msvc-course)**
- **Microservicio de Gateway (msvc-gateway)**
- **Microservicio de Configuración (config-server)**
- **Servidor de Eureka (eureka-server)**

## Estructura del Proyecto

El proyecto está estructurado de la siguiente manera:

```
.
├── msvc-student
├── msvc-course
├── msvc-gateway
├── config-server
└── eureka-server
```

## Prerrequisitos

- Java 11 o superior
- Maven 3.6.3 o superior
- PostgreSQL
- IDE como IntelliJ IDEA

## Configuración de los Microservicios

### 1. Microservicio de Estudiantes (msvc-student)

Este microservicio gestiona la información de los estudiantes.

**Dependencias:**
- Spring Web
- Spring Data JPA
- Config Client
- Eureka Discovery Client
- Spring Boot Actuator
- PostgreSQL Driver


### 2. Microservicio de Cursos (msvc-course)

Este microservicio gestiona la información de los cursos y se comunica con el microservicio de estudiantes.

**Dependencias:**
- Spring Web
- Spring Data JPA
- Config Client
- Eureka Discovery Client
- Spring Boot Actuator
- PostgreSQL Driver
- OpenFeign


### 3. Microservicio de Gateway (msvc-gateway)

Este microservicio actúa como una puerta de enlace para enrutar las solicitudes a los microservicios correspondientes.

**Dependencias:**
- Spring Cloud Gateway
- Eureka Discovery Client
- Config Client


### 4. Microservicio de Configuración (config-server)

Este microservicio centraliza y gestiona las configuraciones de todos los demás microservicios.

**Dependencias:**
- Spring Cloud Config Server


### 5. Servidor de Eureka (eureka-server)

Este microservicio actúa como el servidor de descubrimiento de Eureka.

**Dependencias:**
- Eureka Server
- Config Client

## Instrucciones para Ejecutar el Proyecto

1. Clona el repositorio.
2. Configura las variables de entorno necesarias (`POSTGRE_USER`, `POSTGRE_PASSWORD`, `_HOST`).
3. Arranca los microservicios en el siguiente orden:
    - **Config Server**: Ejecuta la clase principal `ConfigServiceApplication`.
    - **Eureka Server**: Ejecuta la clase principal `EurekaServerApplication`.
    - **Microservicio de Estudiantes**: Ejecuta la clase principal `StudentServiceApplication`.
    - **Microservicio de Cursos**: Ejecuta la clase principal `CourseServiceApplication`.
    - **Gateway**: Ejecuta la clase principal `GatewayApplication`.

## Comunicación entre Microservicios

### Usando OpenFeign en `msvc-course`

**Definición de Cliente Feign:**

**Uso del Cliente Feign en el Servicio:**

```java
@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private StudentClient studentClient;

    @Override
    public StudentByCourseResponse findStudentByIdCourse(Long idCourse) {
        Course course = courseRepository.findById(idCourse).orElse(new Course());
        List<StudentDTO> studentDTOList = studentClient.findAllStudentByCourse(idCourse);
        return StudentByCourseResponse.builder()
                .courseName(course.getName())
                .teacher(course.getTeacher())
                .studentDTOList(studentDTOList)
                .build();
    }
}
```

**Controlador en `msvc-course`:**

```java
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @GetMapping("/search-student/{idCourse}")
    public ResponseEntity<?> findStudentByIdCourse(@PathVariable Long idCourse) {
        return ResponseEntity.ok(courseService.findStudentByIdCourse(idCourse));
    }
}
```
