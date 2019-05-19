# idealista-test

Url swagger: http://localhost:8080/swagger-ui.html

La idea general de la solución fue utilizar el framework de spring boot ya que me siento cómodo con el y es con el que trabajo actualmente en mis proyectos reales
a parte creo que da muchas facilidades a la hora de crear una aplicación.

A la parte que le di mayor importancia fue a las validaciones, ya que eran muchas y quería utilizar algún patrón de diseño que hiciera este código claro y fácil de mantener.

Utilicé las validaciones lambdas, que es la primera vez que las utilizo, creo que pueden ser de bastante ayuda y solo implementé una parte, me hubiese gustado aplicar
estás validaciones a toda la aplicación, pero por cuestiones de tiempo y de conocimientos no pude hacerlo al 100%.

La forma en que planteé la solución fue ir verifcando que tenía cada anuncio y así ir sumando la puntuación que tendría al final de todas las validaciones.

Utilicé dos modelos, uno para los anuncios y otra para las fotos, utilicé lombok para el tema de los getters y setters.

He intentado dejar lo más separado posible el código por paquetes para que quede claro lo que es la estructura del proyecto.

He utlizado ficheros json para obtener los datos.

Mi aplicación se podría explotar mejor en el caso de que hubiesen muchos datos, algún proceso batch que por las noches calculara la puntuación de cada anuncio.