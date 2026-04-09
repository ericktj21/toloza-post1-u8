# Clean Pedidos - Clean Architecture

## Descripción
Sistema de gestión de pedidos implementado siguiendo los principios de **Clean Architecture** con sus cuatro círculos concéntricos: Entities, Use Cases, Interface Adapters y Frameworks & Drivers.

## Requisitos
- **Java**: JDK 17 o superior
- **Spring Boot**: 3.2.0
- **Maven**: 3.8+
- **Base de datos**: H2 (embedded)

## Instalación y Ejecución

### 1. Compilar el proyecto
```bash
mvn clean install
```

### 2. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación iniciará en `http://localhost:8080`

## Arquitectura - Los 4 Círculos de Clean Architecture

### 1️⃣ Círculo Entities (Domain)
Contiene la lógica de negocio pura, sin dependencias de frameworks.

```
domain/
├── entity/
│   └── Pedido.java                 ← Aggregate Root
└── valueobject/
    ├── PedidoId.java               ← Identidad tipada (UUID)
    ├── Dinero.java                 ← Value Object de dinero inmutable
    ├── LineaPedido.java            ← Value Object de línea de pedido
    └── EstadoPedido.java           ← Enum de dominio
```

**Características:**
- Value Objects inmutables con Record de Java 17
- Validaciones en constructores
- Lógica de negocio encapsulada
- Sin imported de Spring o JPA

### 2️⃣ Círculo Use Cases
Contiene los puertos (interfaces) y las implementaciones de casos de uso.

```
usecase/
├── CrearPedidoUseCase.java         ← Interfaz del caso de uso
├── ConsultarPedidoUseCase.java     ← Interfaz del caso de uso
├── port/
│   └── PedidoRepositoryPort.java   ← Puerto de salida (hexagonal)
└── impl/
    ├── CrearPedidoService.java     ← Implementación del caso de uso
    └── ConsultarPedidoService.java ← Implementación del caso de uso
```

**Características:**
- Servicios independientes de frameworks
- Inyección de dependencias por constructor
- SRP: cada servicio con una responsabilidad

### 3️⃣ Círculo Interface Adapters
Traduce entre la API HTTP y los casos de uso. Contiene Controllers y DTOs.

```
adapter/
├── in/web/
│   ├── PedidoController.java       ← REST Controller
│   └── dto/
│       ├── CrearPedidoRequest.java
│       ├── PedidoResponse.java
│       └── LineaPedidoDto.java
└── out/persistence/
    ├── PedidoJpaEntity.java        ← Entidad JPA (solo mapeo)
    ├── PedidoJpaRepository.java    ← Spring Data JPA
    └── PedidoRepositoryAdapter.java ← Adaptador (convierte JPA ↔ Domain)
```

**Características:**
- DTOs solo en el adaptador, NO en el dominio
- Mapeo bidireccional (Domain ↔ JPA)
- Separación clara entre API y persistencia

### 4️⃣ Círculo Frameworks & Drivers
Spring Boot, JPA y configuración.

```
config/
└── PedidoConfiguration.java        ← Wiring de beans
CleanPedidosApplication.java        ← Entrada principal
application.properties              ← Propiedades H2
```

## Flujo de Datos

```
HTTP Request
     ↓
[PedidoController] ← interfaz de entrada
     ↓
[CrearPedidoUseCase] ← caso de uso (interfaz)
     ↓
[CrearPedidoService] ← implementación (sin Spring)
     ↓
[Pedido (Domain)] ← entidad de dominio pura
     ↓
[PedidoRepositoryPort] ← puerto (interfaz)
     ↓
[PedidoRepositoryAdapter] ← adaptador JPA
     ↓
[PedidoJpaRepository] ← Spring Data JPA
     ↓
[Base de datos H2]
```

## Endpoints

### 1. Crear un pedido
```bash
POST /api/pedidos
Content-Type: application/json

{
  "clienteNombre": "Ana García",
  "lineas": [
    {
      "productoNombre": "Laptop",
      "cantidad": 1,
      "precioUnitario": 1500.00
    }
  ]
}
```

**Respuesta (201 Created):**
```json
{
  "pedidoId": "a1b2c3d4-e5f6-4a5b-9c8d-7e6f5a4b3c2d"
}
```

### 2. Obtener un pedido por ID
```bash
GET /api/pedidos/{pedidoId}
```

**Respuesta (200 OK):**
```json
{
  "id": "a1b2c3d4-e5f6-4a5b-9c8d-7e6f5a4b3c2d",
  "clienteNombre": "Ana García",
  "estado": "CONFIRMADO",
  "lineas": [
    {
      "productoNombre": "Laptop",
      "cantidad": 1,
      "precioUnitario": 1500.00
    }
  ],
  "total": 1500.00
}
```

### 3. Listar todos los pedidos
```bash
GET /api/pedidos
```

**Respuesta (200 OK):**
```json
[
  {
    "id": "a1b2c3d4-e5f6-4a5b-9c8d-7e6f5a4b3c2d",
    "clienteNombre": "Ana García",
    "estado": "CONFIRMADO",
    "lineas": [...],
    "total": 1500.00
  }
]
```

## Validaciones de Dominio

El sistema implementa validaciones en el Aggregate Root que respetan las reglas de negocio:

- ✅ No se puede confirmar un pedido sin líneas
- ✅ No se puede agregar líneas a un pedido confirmado
- ✅ La cantidad de línea debe ser mayor a cero
- ✅ El nombre del cliente es obligatorio
- ✅ El dinero no puede ser negativo

## Principios Aplicados

| Principio | Implementación |
|-----------|------------------|
| **DIP** (Dependency Inversion) | Servicios dependen de puertos (interfaces), no de implementaciones |
| **SRP** (Single Responsibility) | Cada servicio con una única responsabilidad |
| **OCP** (Open/Closed) | Fácil agregar nuevos casos de uso sin modificar existentes |
| **LSP** (Liskov Substitution) | Adaptadores intercambiables (JPA, MongoDB, etc.) |
| **ISP** (Interface Segregation) | Interfaces pequeñas y específicas (CrearPedidoUseCase, ConsultarPedidoUseCase) |

## Checkpoints Verificados

✅ El proyecto compila con `mvn clean compile`  
✅ El paquete `domain/` no contiene imports de Spring ni JPA  
✅ El paquete `usecase/` no contiene imports de Spring  
✅ POST /api/pedidos retorna 201 Created con UUID  
✅ GET /api/pedidos/{id} retorna el pedido completo  
✅ Validaciones de dominio funcionan correctamente  
✅ La clase Pedido puede instanciarse sin @SpringBootTest  
✅ Las dependencias apuntan solo hacia adentro (al dominio)  

## Commits

```
ba0b25b - feat: Círculo Entities - Value Objects y Aggregate Root
197916e - feat: Círculo Use Cases e Interface Adapters
03dbbed - feat: Círculo Frameworks & Drivers - Persistencia y Configuración
```

## Screenshots

### Crear un Pedido (200 Líneas desde diferente terminal)
```
POST http://localhost:8080/api/pedidos
Content-Type: application/json

Body:
{
  "clienteNombre": "Juan Pérez",
  "lineas": [
    {"productoNombre": "Monitor 27\"", "cantidad": 2, "precioUnitario": 350.00},
    {"productoNombre": "Teclado Mecánico", "cantidad": 1, "precioUnitario": 120.00}
  ]
}

Response: 201 Created
{
  "pedidoId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### Consultar Pedido
```
GET http://localhost:8080/api/pedidos/550e8400-e29b-41d4-a716-446655440000

Response: 200 OK
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "clienteNombre": "Juan Pérez",
  "estado": "CONFIRMADO",
  "lineas": [
    {"productoNombre": "Monitor 27\"", "cantidad": 2, "precioUnitario": 350.00},
    {"productoNombre": "Teclado Mecánico", "cantidad": 1, "precioUnitario": 120.00}
  ],
  "total": 820.00
}
```

## Estructura de Dependencias

```
                      Domain (Puro)
                           ↑
                           |
    ┌─────────────────┬────┴────┬─────────────────┐
    |                 |         |                 |
Entities         Use Cases   Adapters         Frameworks
    |                 |         |                 |
    └─────────────────┴─────────┴─────────────────┘
                      
Solo apunta hacia adentro →
```

## Notas

- La base de datos H2 se crea en memoria y se limpia al reiniciar la aplicación
- El esquema se crea automáticamente con `spring.jpa.hibernate.ddl-auto=create-drop`
- La consola H2 está disponible en `http://localhost:8080/h2-console`

## Autor
Toloza - 2026
