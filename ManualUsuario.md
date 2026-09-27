# Manual de Usuario — Quetzal Space Defender: Side Scroller

## 1. ¿Qué es Quetzal Space Defender?

Un simulador de combate espacial en el que tu nave atraviesa un cinturón de
asteroides y naves enemigas que se acercan desde la derecha de la pantalla.
Debes esquivar, disparar y recolectar premios para conseguir el mayor
puntaje posible.

## 2. Requisitos

- Java (JDK) instalado.
- El proyecto abierto y compilado en NetBeans (o ejecutado desde un `.jar`
  ya compilado).

## 3. Iniciar el programa

Ejecuta la clase `Main`. Se abrirá el **Menú Principal** con 5 opciones:

- **Jugar**
- **Crear Piloto**
- **Eliminar Piloto**
- **Top de Puntajes**
- **Salir**
  
  <img width="475" height="480" alt="image" src="https://github.com/user-attachments/assets/227dcf1b-ec58-4090-ba6a-b4316db55501" />


## 4. Crear un piloto

1. Haz clic en **Crear Piloto**.
2. Escribe un nombre (mínimo 3 caracteres, no puede repetirse con uno ya
   existente).
3. Elige un modelo de nave, que define tu dificultad:

| Nave | Dificultad | Características |
|---|---|---|
| Explorador | Fácil | Movimiento muy fluido, recarga de disparo cada 2 seg. |
| Caza Estelar | Normal | Equilibrio entre movimiento y cadencia de disparo (1 seg). |
| Acorazado | Difícil | Nave pesada y difícil de esquivar, pero dispara en ráfagas cada 0.3 seg. |

4. Haz clic en **Registrar Piloto**. Si hay un error (nombre vacío,
   repetido o muy corto), se mostrará un mensaje explicando por qué.

   <img width="627" height="386" alt="image" src="https://github.com/user-attachments/assets/575bd296-4d67-4043-98a9-5f814b888099" />


## 5. Eliminar un piloto

1. Haz clic en **Eliminar Piloto**.
2. Selecciona el piloto en la lista desplegable.
3. Confirma la eliminación. Esta acción no se puede deshacer.

   <img width="428" height="226" alt="image" src="https://github.com/user-attachments/assets/a22cda85-926d-4ba5-8bde-8809518e7b44" />


## 6. Jugar una partida

1. Haz clic en **Jugar**. Si tienes más de un piloto registrado, se te
   pedirá elegir con cuál quieres jugar.
2. Controles durante la partida:
   - **Flechas del teclado**: mover la nave (arriba, abajo, izquierda, derecha).
   - **Barra espaciadora**: disparar (mantenla presionada para disparar de
     forma continua, respetando la cadencia de tu nave).

     <img width="993" height="637" alt="image" src="https://github.com/user-attachments/assets/88a7d24d-3986-40a8-8c64-38e9ed7f9967" />


### Objetos en el campo de batalla

| Objeto | Color/forma | Efecto al chocar |
|---|---|---|
| Nave enemiga | Flecha roja | Te destruye una nave y pierdes una vida. |
| Asteroide (Bludger) | Roca gris irregular | Bloquea tu nave durante 2 segundos. |
| Contenedor Quaffle | Esfera amarilla | Suma 10 puntos. |
| Snitch Espacial | Esfera naranja con alitas | Suma 150 puntos y destruye todos los enemigos visibles en pantalla. |

Además, si dejas que una nave enemiga salga de la pantalla sin destruirla,
pierdes 1 punto (nunca baja de 0).

### Fin de la partida

La partida termina cuando pierdes tus 3 vidas ("game over"). Se mostrará tu
puntaje final, y este se guarda automáticamente en tu historial y, si es tu
mejor marca, se actualiza tu mejor puntaje.

<img width="988" height="661" alt="image" src="https://github.com/user-attachments/assets/c8c60bad-da91-49b6-b942-4ced36da9aaa" />


## 7. Top de Puntajes

Desde el menú principal, haz clic en **Top de Puntajes** para ver las
mejores partidas jugadas (piloto, nave usada y puntaje), ordenadas de mayor
a menor.

<img width="577" height="543" alt="image" src="https://github.com/user-attachments/assets/43984cb5-132c-4f91-9d7a-e2a96b8c1bde" />



### Generar el reporte

Dentro de la ventana de Top de Puntajes, haz clic en **Generar Reporte
(HTML + gráfica)**. Esto crea una carpeta `reportes/` junto al proyecto con
una gráfica de barras y una tabla de resultados, y abre el reporte
automáticamente en tu navegador. Desde ahí, puedes usar
**Imprimir → Guardar como PDF** para obtener el PDF final.

<img width="576" height="223" alt="image" src="https://github.com/user-attachments/assets/735e86c8-ee6d-4592-a97d-6b02a31d3069" />



## 8. Salir del programa

Al hacer clic en **Salir** (o cerrar la ventana con la X), se te pedirá
confirmación. Al confirmar, tus pilotos y tu historial de partidas se
guardan automáticamente en la carpeta `datos/`, para que estén disponibles
la próxima vez que abras el programa.
