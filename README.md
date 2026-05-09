# 🚀 ECommerce API REST

Aplicación Spring Boot que expone una API REST que permite la gestión de carritos de compras a los clientes. El proyecto está documentado mediante OpenAPI y permite simular el backend usando base de datos en memoria (H2).

---

## 🧱 Stack tecnológico

![Java](https://img.shields.io/badge/Java-21-%23000000?logo=openjdk&&color=yellow)

| Componente        | Descripción                                                 |
|-------------------|-------------------------------------------------------------|
| Java 21           | Lenguaje base del proyecto                                  |
| Spring Boot 4.0.6 | Framework principal para la app REST                        |
| SpringDoc OpenAPI | Generación automática de documentación Swagger UI           |
| Maven             | Gestión del proyecto y ciclo de compilación                 |

---

## 📡 Arquitectura funcional


- **Entrada REST:** Número de documento (DNI), identificador de producto, cantidad y si es carrito especial o no. 
- **Salida REST:** Creación de nuevo carrito para el cliente


- **Entrada REST:** Identificador de carrito.
- **Salida REST:** Finalización de carrito abierto con cálculo de descuentos que apliquen. 

---

## 🎯 Endpoints principales

- **ABM de Carts**
### `GET /carts`  Obtiene la lista de carritos
### `POST /carts`  Crea un nuevo carrito
### `PUT /carts/add-item`  Agrega un item a un carrito abierto
### `PUT /carts/remove-item`  Elimina un item de un carrito abierto
### `DELETE /carts/{id}`  Elimina un carrito abierto

- **Purchase y PurchaseItems**
### `GET /purchase`  Obtiene la lista de compras general 
### `POST /purchases/checkout/idCart`  Finalizar carrito con descuentos que apliquen
### `GET /purchases/dni/number/range?from=date&to=date`  Filtrar lista de compra por fechas

Obtiene los carritos de compra en estado abierto.

#### ✅ Ejemplo de request

```http
GET /carts
Accept: application/xml
```

📦 Ejemplo de respuesta (Json)

```java
[
        {
            "id": 1,
            "dni": "12345678",
            "dateCreated": "2026-05-07T21:14:00.29438",
            "dateUpdated": null,
            "status": "OPEN",
            "items": [
              {
                "id": 1,
                "quantity": 2,
                "product": {
                "id": 1,
                "name": "Auriculares",
                "price": 150
                  }  
              },
              {
                "id": 2,
                "quantity": 1,
                "product": {
                "id": 2,
                "name": "Teclado",
                "price": 300
                  }
              }
            ],
            "special": false
      }
]
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