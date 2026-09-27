# Diagrama de Flujo — Quetzal Space Defender: Side Scroller

Representación gráfica del funcionamiento general del sistema, desde que
inicia el programa hasta que se cierra. El diagrama está escrito en
**Mermaid**, que GitHub renderiza automáticamente al ver este archivo en
el repositorio.

```mermaid
flowchart TD
    A[Inicio del programa] --> B[Cargar pilotos e historial desde disco]
    B --> C[Mostrar Menu Principal]

    C --> D{Opcion elegida}

    D -->|Crear Piloto| E[Formulario: nombre + nivel de dificultad]
    E --> E1{Validaciones ok?}
    E1 -->|No| E2[Mostrar mensaje de error] --> E
    E1 -->|Si| E3[Guardar piloto en el arreglo] --> C

    D -->|Eliminar Piloto| F[Seleccionar piloto de la lista]
    F --> F1{Confirmar eliminacion?}
    F1 -->|Si| F2[Eliminar del arreglo y actualizar archivo] --> C
    F1 -->|No| C

    D -->|Jugar| G{Hay pilotos registrados?}
    G -->|No| G1[Mostrar aviso: crear piloto primero] --> C
    G -->|Si| H[Seleccionar piloto - si hay mas de uno]
    H --> I[Crear NaveJugador segun su nivel]
    I --> J[Iniciar hilos: Movimiento, Disparo, Generador de objetos, Arbitro]
    J --> K[Bucle de la partida en curso]

    K --> K1[Hilo Movimiento: mueve la nave segun teclado]
    K --> K2[Hilo Disparo: crea proyectiles segun cadencia]
    K --> K3[Hilo Generador: crea enemigos / asteroides / quaffle / snitch]
    K --> K4[Hilo Arbitro: revisa colisiones y actualiza puntaje]

    K4 --> L{Vidas del jugador = 0?}
    L -->|No| K
    L -->|Si| M[Detener todos los hilos de la partida]
    M --> N[Guardar partida en el historial]
    N --> O[Actualizar mejor puntaje del piloto] --> C

    D -->|Top de Puntajes| P[Ordenar historial de mayor a menor puntaje]
    P --> Q[Mostrar ranking]
    Q --> R{Generar reporte?}
    R -->|Si| S[Crear grafica JFreeChart + archivo HTML]
    S --> T[Abrir reporte en el navegador] --> C
    R -->|No| C

    D -->|Salir| U{Confirmar salida?}
    U -->|Si| V[Guardar pilotos e historial en disco]
    V --> W[Fin del programa]
    U -->|No| C
```

## Notas sobre el diagrama

- El bloque **K** (bucle de la partida) representa los 4 hilos que corren
  de forma simultánea e independiente mientras la partida está en curso:
  movimiento de la nave, disparo, generación de objetos y arbitraje de
  colisiones. Además, cada objeto generado (enemigo, asteroide, quaffle,
  snitch) y cada proyectil disparado lanza su propio hilo adicional de
  movimiento, no representado individualmente en el diagrama por
  simplicidad.
- El `Arbitro` es el único punto donde se decide si la partida continúa o
  termina (cuando las vidas del jugador llegan a 0).
