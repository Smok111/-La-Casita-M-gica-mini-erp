# 🏠 La Casita Mágica - Mini ERP
## Guía de Instalación y Ejecución

---

## 📋 REQUISITOS PREVIOS (instalar antes de empezar)

Tu PC necesita tener instalado lo siguiente:

| Programa | Versión mínima | Descarga |
|---|---|---|
| **Java JDK** | 17 o superior | https://www.oracle.com/java/technologies/downloads/ |
| **Node.js** | 18 o superior | https://nodejs.org/ |
| **MariaDB** o **MySQL** | Cualquier versión reciente | https://mariadb.org/download/ |
| **Git** | Cualquier versión | https://git-scm.com/downloads |

> ⚠️ **IMPORTANTE:** Después de instalar Java, Node.js y Git, **reinicia tu computadora** antes de continuar.

---

## 📥 PASO 1: Clonar el proyecto

Abre **CMD** o **PowerShell** y escribe:

```
git clone https://github.com/Smok111/-La-Casita-M-gica-mini-erp.git
cd -La-Casita-M-gica-mini-erp
```

---

## 🗄️ PASO 2: Configurar la Base de Datos

1. Abre **MySQL / MariaDB** (puedes usar HeidiSQL, MySQL Workbench, o la consola)
2. Conéctate con tu usuario root
3. Ejecuta este script SQL para crear el usuario admin:

```sql
-- Crear la base de datos (se crea automáticamente, pero por si acaso)
CREATE DATABASE IF NOT EXISTS MagicHouseDB;
USE MagicHouseDB;
```

> La base de datos y las tablas **se crean solas automáticamente** cuando arrancas el backend por primera vez.

### ⚙️ Cambiar credenciales de la base de datos

Si tu MySQL/MariaDB tiene una contraseña diferente a `123456789a`, debes editarlo:

1. Abre el archivo: `MagicHouse-miniERP/src/main/resources/application.properties`
2. Cambia estas líneas con tu usuario y contraseña reales:

```properties
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA_AQUI
```

---

## ⚙️ PASO 3: Iniciar el Backend (Java/Spring Boot)

Abre una **nueva ventana de CMD** y ejecuta:

**En Windows:**
```
cd -La-Casita-M-gica-mini-erp\MagicHouse-miniERP
mvnw.cmd spring-boot:run
```

**En Mac/Linux:**
```
cd -La-Casita-M-gica-mini-erp/MagicHouse-miniERP
chmod +x mvnw
./mvnw spring-boot:run
```

> ⏳ Espera a que aparezca el mensaje:
> `Started MagicHouseMiniErpApplication in X seconds`
>
> ✅ Eso significa que el backend está corriendo en: **http://localhost:8080**

---

## 🎨 PASO 4: Iniciar el Frontend (Angular)

Abre **otra ventana de CMD** (sin cerrar la del backend) y ejecuta:

```
cd -La-Casita-M-gica-mini-erp\MagiHouse-miniERP-frontend
npm install
npm run start
```

> ⏳ Espera a que aparezca:
> `Application bundle generation complete.`
>
> ✅ La app estará disponible en: **http://localhost:4200**

---

## 🔐 PASO 5: Crear el usuario Admin

La primera vez que arranques el backend, las tablas se crean vacías.
Debes insertar el usuario admin manualmente en la base de datos:

Abre tu cliente MySQL/MariaDB y ejecuta:

```sql
USE MagicHouseDB;

-- Insertar rol ADMIN (si no existe)
INSERT IGNORE INTO rol (id_rol, nombre) VALUES (1, 'ADMIN');

-- Insertar usuario admin con contraseña "admin"
INSERT INTO usuarios (nombre_usuario, contrasenia_hash, fecha_hora_registro, fecha_hora_modificacion, esta_activo)
VALUES ('admin', CONCAT('$2a$10$', 'orq/rCEgYl71PKICb56gl.92qM5kPqwLnYfzUkRUc8wmq63VurZS6'), NOW(), NOW(), true);

-- Asociar el usuario al rol ADMIN
INSERT INTO usuario_rol (id_usuario, id_rol)
SELECT id_usuario, 1 FROM usuarios WHERE nombre_usuario = 'admin';
```

---

## 🚀 PASO 6: Abrir la aplicación

Abre tu navegador y ve a:

👉 **http://localhost:4200**

Inicia sesión con:
- **Usuario:** `admin`
- **Contraseña:** `admin`

---

## 📁 Estructura del proyecto

```
-La-Casita-M-gica-mini-erp/
│
├── MagicHouse-miniERP/          ← Backend (Java + Spring Boot)
│   ├── src/
│   └── pom.xml
│
└── MagiHouse-miniERP-frontend/  ← Frontend (Angular)
    ├── src/
    └── package.json
```

---

## ❓ Problemas frecuentes

| Problema | Solución |
|---|---|
| `java: command not found` | Instala Java JDK 17+ y reinicia la PC |
| `npm: command not found` | Instala Node.js y reinicia la PC |
| `Access denied for user 'root'` | Cambia la contraseña en `application.properties` |
| Puerto 8080 ocupado | Cierra otros programas que usen ese puerto |
| Puerto 4200 ocupado | Ejecuta `npm run start -- --port 4201` |
| La página dice "Bad credentials" | Ejecuta el script SQL del Paso 5 |

---

## 🛑 Para detener la aplicación

- En la ventana del **backend**: presiona `Ctrl + C`
- En la ventana del **frontend**: presiona `Ctrl + C`

---

*Proyecto desarrollado con Angular 19 + Spring Boot 3 + MariaDB*
