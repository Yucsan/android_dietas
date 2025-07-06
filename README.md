## 🍲 App de Dietas

Aplicación de Dietas – Modelado Nutricional Avanzado
Este proyecto es una aplicación Android desarrollada con Jetpack Compose y Room, centrada en la gestión estructurada de alimentos y su composición nutricional.

El modelo principal gira en torno a la entidad ComponenteDieta, que puede representar distintos tipos de alimentos:

Simple, Procesado, Receta, Menú y Dieta.

Los tres últimos son componentes compuestos, lo que permite construir estructuras jerárquicas donde, por ejemplo, un Menú puede contener una Receta, varios alimentos simples o incluso otro Menú anidado. Esta relación recursiva es gestionada mediante una tabla intermedia, lo que habilita una lógica de composición flexible y escalable.

Cada componente almacena información nutricional básica: hidratos de carbono, lípidos y proteínas, y al estar compuestos, la aplicación calcula automáticamente los valores totales agregados en base a sus ingredientes y cantidades correspondientes.

La lógica implementada permite:

Editar dinámicamente los componentes: nombre, cantidad en gramos, o valores nutricionales.

Agregar o eliminar ingredientes en componentes compuestos.

Recalcular en tiempo real las calorías totales según la estructura y cantidad de cada ingrediente.

Este proyecto demuestra un enfoque sólido para representar datos complejos, relaciones recursivas y lógica nutricional integrada en un entorno Android moderno.

## El flujo principal permite al usuario:

- Ver una lista de recetas almacenadas.
- Consultar el detalle de cada receta.
- Añadir o eliminar recetas de manera intuitiva.

## Diagrama Relacional

<img src="assets/diagrama_relacional.jpg" width="100%"/>

## Capturas de pantalla de la aplicación

<img src="assets/interfaces1.png" width="100%"/>
<img src="assets/interfaces2.png" width="100%"/>

## Se han trabajdo los colores en modalidad Pantalla Oscura y Luminosa

<img src="assets/interfaces3.png" width="100%"/>

