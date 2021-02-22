# Personajes Marvel
Simple app listado - detalle de personajes Marvel usando la API que proporciona https://developer.marvel.com/

## Arquitectura
La app se ha desarrollado aplicando la **arquitectura de presentación MVVM** usando los componentes de arquitectura que proporciona el *framework* de Android y que recomienda Google (https://developer.android.com/jetpack/guide?hl=es-419).

### Vista General
![Architecture Overview](https://i.ibb.co/R44MGTg/architecture.png)

El código está estructurado por *features*, y cada una mantiene la misma jerarquía de paquetes (Presentación, Dominio y Datos). Se ha incluído un paquete común para el código compartido por todas las *features* y donde se ha incluído los componentes para la inyección de dependencias.

En la capa de presentación se ha incluído todo lo que concierne a la vista: actividades, fragmentos, adaptadores, etc. así como los modelos de presentación y mapeadores (dominio - presentación).

En la capa de dominio se han incluído los casos de uso y modelos.

En la capa de datos se encuentran los repositorios y librerías de acceso a datos, modelos que usan los orígenes de datos y mapeadores (datos - dominio).

## Librerías
- **Glide**: Usada para la carga de recursos de imágenes remotas.
- **Koin**: Inyector de dependencias.
- **Timber**: Librería para *logging*.
- **Retrofit**: Usada para la conexión con APIs remotas.

## Puesta en marcha
Modificar del fichero común **Constant.kt** las constantes
```kotlin
const val PUBLIC_API_KEY = ""
const val PRIVATE_API_KEY = ""
```
