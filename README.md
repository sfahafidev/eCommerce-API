# 🚀 ECommerce API REST

Aplicación Spring Boot que expone una API REST que permite la gestión de carritos de compras a los clientes. El proyecto está documentado mediante OpenAPI y permite simular el backend usando base de datos en memoria (H2).

---

## 🧱 Stack tecnológico

![Java](https://img.shields.io/badge/Java-21-%23000000?logo=openjdk&&color=yellow)

| Componente                     | Descripción                                                 |
|-------------------------------|-------------------------------------------------------------|
| Java 21                       | Lenguaje base del proyecto                                  |
| Spring Boot 3.5.3             | Framework principal para la app REST                        |
| SpringDoc OpenAPI             | Generación automática de documentación Swagger UI           |
| Maven                         | Gestión del proyecto y ciclo de compilación                 |

---

## 📡 Arquitectura funcional


- **Entrada REST:** Número y tipo de documento del cliente
- **Transformación:** Datos SOAP → modelos REST serializables
- **Salida REST:** Lista de destinatarios con sus cuentas (CBUs)

---

## 🎯 Endpoints principales

### `GET /`

Obtiene los carritos de compra en estado abierto.

#### ✅ Ejemplo de request

```http
GET /v1/transfer/customers-document/20304050/recipients?customer-document-type=DNI
Accept: application/xml
```

📦 Ejemplo de respuesta (Json)

```java
  {
        "id": 2,
        "dni": "87654321",
        "price": 0,
        "dateCreated": "2026-05-07T17:55:24.080582",
        "dateUpdated": null,
        "status": "OPEN",
        "items": [
          {
            "id": 3,
            "name": "Mouse",
            "price": 200
          }
        ],
        "special": true
}
```

⚙️ Ejecución local
1. Cloná el proyecto y asegurate de tener Java 21

```bash
  mvn clean install
```

2. Una vez instaladas las dependencias podrás correr el proyecto utilizando:

```bash
  mvn spring-boot:run
```

3. La aplicación se ejecuta en:

http://localhost:8080

4. Documentación interactiva

Swagger UI:

http://localhost:8080/swagger-ui/index.html

## Autor ✒️
- [Sharbel Fahafi   (Java Developer)](mailto:sfahafidev@gmail.com)