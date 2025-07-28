🚀 Proyecto Backend desarrollado con Spring Boot y JWT

📌 Descripción
ForoHub es una API REST diseñada para gestionar tópicos (posts) en un foro en línea. Ofrece autenticación segura con JWT y operaciones CRUD completas para la gestión de tópicos.

🔹 Tecnologías principales:
Spring Boot 3
Spring Security + JWT
MySQL (o H2 para desarrollo)
Swagger/OpenAPI (Documentación interactiva)
Flyway (Manejo de migraciones de BD)

✨ Características
✅ Autenticación JWT (Registro y Login)
✅ CRUD Completo de Tópicos
✅ Validación de Datos
✅ Documentación con Swagger
✅ Seguridad por Roles (USER/ADMIN)
✅ Manejo de Errores Personalizado

🔧 Configuración
1. Requisitos
Java 17+
Maven
MySQL 

2. Instalación
Clonar el repositorio:

bash
git clone https://github.com/tu-usuario/forohub.git
cd forohub
Configurar base de datos (MySQL):

Crear una base de datos llamada forohub

Configurar credenciales en application.properties

Ejecutar la aplicación:

bash
mvn spring-boot:run
3. Endpoints Principales
Método	Endpoint	Descripción	Requiere Auth
POST  /api/autenticacion/registro	      Registrar nuevo usuario	❌
POST	/api/autenticacion/iniciar-sesion	Iniciar sesión (obtener JWT)	❌
GET   /api/topicos                    	Listar todos los tópicos	✅
POST	/api/topicos                    	Crear un nuevo tópico	✅
GET	  /api/topicos/{id}	                Obtener tópico por ID	✅
PUT	  /api/topicos/{id}	                Actualizar tópico	✅
DELETE	/api/topicos/{id}             	Eliminar tópico	✅

📚 Documentación API
🔗 Swagger UI: http://localhost:8080/swagger-ui.html
📄 OpenAPI JSON: http://localhost:8080/v3/api-docs

🚀 Cómo Contribuir FrontEnd
Haz un fork del proyecto
Crea una rama (git checkout -b feature/nueva-funcionalidad)
Realiza tus cambios y haz commit (git commit -m "Agrega nueva funcionalidad")
Haz push a la rama (git push origin feature/nueva-funcionalidad)

📞 Contacto
👨‍💻 LinkedIn: Eleuterio Vargas
📧 Email: eleuterio09@gmail.com
🐱 GitHub: @eleuteriovargas

🎉 ¡Gracias por visitar el proyecto!
🌟 ¡Si te gustó, déjale una estrella en GitHub!
