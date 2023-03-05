# AdventureGame
Un juego de plataformas donde debes conseguir una llave para abrir un cofre. Desarrollado con LibGDX para escritorio y Android.

# Cómo jugar
Pulsa las teclas Abajo,Arriba,Derecha e Izquierda para el movimiento si estas en escritorio o los lados de tu pantalla si estas en Android.
Para ganar debes conseguir la llave que hay escondida en el mapa y llegar hasta el cofre. Evita a los enemigos, ya que si te tocan se reiniciará el nivel.

# Cómo añadir niveles
    1. Usar Tiled y los assets que hay en el juego.
    2. El mapa debe tener dos capas de forma obligatoria: 1 capa de patrón llamada Terreno y 1 capa de objetos llamada Objetos.
    3. En la capa Terreno pinta el terreno por el que se va a mover el personaje.
    4. En la capa de objetos puedes añadir enemigos e items para que el juego los cargue posteriormente:
        -Para añadir un item, inserta un patrón y ponle de atributo "item" con valor el tipo ("Moneda", "Key" o "Pocion")
        -Para añadir un objeto, inserta un patrón y ponle de atributo "enemigo" con valor el tipo("Abeja" o "Jabali")
        -Es obligatorio que haya una llave y cofre (chest) en cada mapa. En caso de no haberlo, fallará la carga.
    5. Es obligatorio que el mapa tenga los siguientes atributos(Usar valores del Tiled):
        -float limiteDerecho: El limite derecho de la camara.
        -float limiteIzquierdo: El limite izquierdo de la camara.
        -float limiteInferior: El limite inferior de la camara.
        -float limiteSuperior: El limite superior de la camara.
        -float xJugador: En que posición X empieza el jugador.
        -float yJugador: En que posición Y empieza el jugador.

La clase LevelManager se encargará de generar el mapa y cargar los items/enemigos. Si se desea añadir otro tipo de enemigo o item debe modificarse esta clase para que los reconozca.