# Manual Técnico — Quetzal Space Defender: Side Scroller

## 1. Introducción
Este documento describe la arquitectura técnica, las clases principales y las decisiones de diseño del proyecto Quetzal Space Defender, desarrollado para la Práctica 2 del curso Introducción a la Programación y Computación 1 (USAC).

## 2. Arquitectura del proyecto
El proyecto sigue el patrón **MVC (Modelo–Vista–Controlador)**:
* **model:** Representa el estado del juego (piloto, nave, enemigos, etc.), sin saber cómo se dibuja o se controla.
* **controller:** Contiene toda la lógica de movimiento, colisiones, generación de objetos y persistencia en disco.
* **view:** Contiene únicamente las ventanas y paneles Swing; delegan la lógica a las clases del controlador.

## 3. Clases principales

### 3.1 Paquete model
* **Movil:** Clase abstracta base que define posición (x, y), tamaño y estado vivo/muerto.
* **MovilHorizontal:** Extiende a `Movil`; agrega velocidad para objetos que se mueven a la izquierda.
* **Enemigo:** Nave enemiga. Incluye bandera `escapo` para saber si salió de pantalla sin ser destruida.
* **Asteroide:** Obstáculo (Bludger) que bloquea al jugador al chocar.
* **Quaffle:** Otorga +10 puntos al chocar.
* **Snitch:** Otorga +150 puntos y destruye enemigos visibles al chocar.
* **Proyectil:** Disparo del jugador; se mueve hacia la derecha.
* **NaveJugador:** Representa la nave del jugador durante una partida (vidas, bloqueo temporal).
* **Piloto:** Perfil de un jugador registrado (nombre, nivel de dificultad, mejor puntaje).
* **NivelDificultad:** Enum con los 3 modelos de nave (Explorador, Caza Estelar, Acorazado) y sus tiempos de `sleep()`.
* **Partida:** Registro inmutable de una partida jugada (para historial y top).
* **Escena:** Contiene, en un arreglo, todos los objetos activos de la partida actual.

### 3.2 Paquete controller
* **ControlTeclado:** Traduce eventos de teclado (Swing) a banderas booleanas consultables por los hilos.
* **HiloMovimientoJugador:** Hilo que mueve la nave del jugador según el teclado.
* **HiloMovimientoHorizontal:** Hilo genérico que mueve cualquier objeto horizontal.
* **HiloGeneradorObjetos:** Hilo que crea objetos nuevos periódicamente de forma aleatoria.
* **HiloDisparo:** Controla la cadencia de disparo del jugador según su dificultad.
* **HiloProyectil:** Mueve un proyectil individual hacia la derecha.
* **Arbitro:** Único hilo que centraliza todas las detecciones de colisión y el cálculo del puntaje.
* **RegistroPilotos:** Administra el arreglo de pilotos, validaciones y persistencia.
* **HistorialPartidas:** Administra el arreglo de partidas jugadas y calcula el Top N.
* **GestorPersistencia:** Utilidad reutilizable para leer/escribir archivos de texto plano línea por línea.
* **GeneradorReporte:** Genera la gráfica (JFreeChart) y el reporte HTML del top de puntajes.

### 3.3 Paquete view
* **MenuPrincipal:** Ventana principal con opciones de navegación.
* **VentanaCrearPiloto:** Formulario para registrar un piloto nuevo.
* **VentanaEliminarPiloto:** Selector para eliminar un piloto existente.
* **VentanaJuego:** Arma una partida, crea la nave, la escena y arranca los hilos.
* **VentanaTopPuntajes:** Muestra el ranking y permite generar el reporte.
* **PanelJuego:** Dibuja la nave, los objetos activos y el HUD.

## 4. Hilos utilizados
Cumpliendo el requerimiento de que cada proyectil y cada enemigo sea un hilo independiente:
* `1` hilo `HiloMovimientoJugador` por partida.
* `1` hilo `HiloDisparo` por partida.
* `1` hilo `HiloGeneradorObjetos` por partida.
* `1` hilo `HiloMovimientoHorizontal` por cada enemigo/asteroide/premio generado.
* `1` hilo `HiloProyectil` por cada disparo realizado.
* `1` hilo `Arbitro` por partida (centraliza colisiones y evita condiciones de carrera).

## 5. Persistencia
Se usan arreglos para almacenar pilotos, historial y objetos activos, con crecimiento manual al llenarse. Los datos se guardan en disco como texto plano en la carpeta `datos/` (`pilotos.txt`, `historial.txt`) usando `FileWriter`/`BufferedReader`, centralizado en `GestorPersistencia`.

## 6. Librerías externas
* **JFreeChart** (`org.jfree:jfreechart`, vía Maven en `pom.xml`) para generar la gráfica de barras del Top de Puntajes.
