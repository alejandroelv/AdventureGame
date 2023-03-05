# AdventureGame
Un juego de plataformas donde debes conseguir una llave para abrir un cofre. Desarrollado con LibGDX para escritorio y Android.

# Cómo jugar
Pulsa las teclas Abajo,Arriba,Derecha e Izquierda para el movimiento si estas en escritorio o los lados de tu pantalla si estas en Android.
Para ganar debes conseguir la llave que hay escondida en el mapa y llegar hasta el cofre. Evita a los enemigos, ya que si te tocan se reiniciará el nivel.

# Cómo añadir niveles
Para añadir niveles solo debes crearlos usando Tiled y los assets del juego. A la hora de añadir NPCs o items debes añadirles como atributo personalizado en Tiled de que tipo son (por ejemplo, para crear un Jabali inserta un objeto que tenga un atributo enemigo con valor Jabali). El LevelManager se encargará de generar el nivel y los NPCs cuando cargue el mapa.
