# 🚀 Simulador de Planificación de Procesos de SO

¡Bienvenido! Este proyecto es un simulador con interfaz gráfica (GUI) creado en Java ☕ para visualizar y entender las diferentes estrategias de planificación de procesos que utiliza un sistema operativo monoprocesador.

## 📄 Un Proyecto Académico

Este simulador fue desarrollado como el **Proyecto 1 para la materia de Sistemas Operativos**.

El objetivo principal, descrito en el enunciado (`Proyecto 1 Sistemas Operativos (2).pdf`), era implementar un simulador desde cero, cumpliendo con requisitos técnicos clave:

* **GUI Funcional:** Una interfaz (`PanelPrincipal.java`) creada en NetBeans para controlar la simulación.
* **Gestión de Procesos:** Manejo de procesos tipo **CPU-Bound** (`CPU_BOUND`) y **I/O-Bound** (`IO_BOUND`).
* **Ráfagas de CPU/E&S:** Los procesos I/O-Bound especifican ciclos de excepción (ráfaga de CPU) y ciclos de satisfacción (ráfaga de E/S).
* **Estructuras de Datos Propias:** El requisito clave fue **implementar estructuras de datos propias** (`Queue.java`, `ArrayList.java`) sin usar las colecciones estándar de `java.util`.
* **Carga de Configuración:** Cargar definiciones de procesos desde archivos.

El proyecto fue desarrollado en el IDE **NetBeans** usando **Java 21+**, como se especificó.

---

## ✨ Características Principales

* **Punto de Entrada (`main.java`):** 🏁 Inicia la aplicación, la GUI y los componentes centrales del SO.
* **Interfaz Gráfica (`PanelPrincipal.java`):** 💻 Un panel de control para cargar procesos, seleccionar algoritmos, definir el `quantum` e iniciar/detener la simulación.
* **Modelo de 7 Estados:** 🚦 Los procesos (`Process.java`) transicionan a través del modelo completo de 7 estados, definido en `StateProcess.java` (Nuevo, Listo, Ejecución, Bloqueado, Terminado, Listo/Suspendido, Bloqueado/Suspendido).
* **Simulación de Componentes Clave:**
    * **Núcleo (`OS.java`):** Orquesta todos los componentes, maneja los ciclos globales (`globalCycles`) y el estado del sistema (`StateOS.java`).
    * **CPU (`CPU.java`):** ⚙️ Un hilo (`Thread`) que ejecuta el `runningProcess`, maneja interrupciones y el `quantum`.
    * **PCB (`PCB.java`):** 📝 Cada proceso tiene su propio Bloque de Control de Proceso con su estado, PC (Contador de Programa), MAR (Registro de Dirección de Memoria) y prioridad.
* **Gestión de Procesos:**
    * **Límite por Instrucciones:** La "capacidad de memoria" se simula asignando a cada proceso un **número máximo de instrucciones** (`instructions`). Un proceso termina (`TERMINATED`) cuando su contador (`countInstructions`) iguala este número.
    * **Tipos de Proceso (`ProcessType.java`):** Distingue entre `CPU_BOUND` e `IO_BOUND`.
    * **Carga de Configuración (`ConfigurationManager.java`):** 📁 Utiliza `JFileChooser` para cargar un archivo de configuración de procesos.
    * **Registro de Eventos (`EventLogger.java`):** 🧾 Registra los eventos importantes de la simulación.

---

## 🛠️ Arquitectura y Estructuras de Datos

### Implementación Propia de Estructuras

Un requisito clave del proyecto fue no utilizar las colecciones estándar de `java.util`. Por ello, se implementaron desde cero:

* **`Queue.java` (Cola):** 🧱 Una cola genérica basada en una lista enlazada (usando `Node.java`). Se utiliza para manejar todas las colas de planificación (Listos, Bloqueados, Suspendidos, etc.).
* **`ArrayList.java`:** 🧱 Una implementación de una lista dinámica con capacidad de auto-redimensionamiento, usada para la cola de procesos finalizados.

### Patrón de Diseño Strategy 🤖

El núcleo del planificador (`Scheduler.java`) utiliza un **Patrón de Diseño Strategy** para cambiar dinámicamente el algoritmo de planificación.

* `SchedulerStrategy.java` es la clase abstracta que define el contrato.
* `StrategyScheduler.java` es un `enum` que lista las estrategias disponibles.

**Algoritmos Soportados:**

* **`FirstComeFirstServe.java` (FCFS):** El primero que llega, es el primero en ser atendido. (No apropiativo).
* **`SPN.java` (Shortest Process Next):** Ejecuta el proceso con la ráfaga de CPU más corta. (No apropiativo).
* **`SRT.java` (Shortest Remaining Time):** Versión apropiativa de SPN.
* **`RoundRobin.java` (RR):** Asigna a cada proceso un `quantum` de tiempo de forma rotativa.
* **`FB.java` (Feedback):** Planificación por colas de retroalimentación (Multilevel Feedback Queues).
* **`SRR.java`:** Una variante personalizada de estrategia.

---

## 🧑‍💻 Autores

* **Daniel De Oliveira**
* **Nicole Tolve**

*Este proyecto fue desarrollado en cumplimiento de los requisitos del "Proyecto 1 de Sistemas Operativos" de la Universidad Metropolitana.*
*Puntaje: 18.5/20*