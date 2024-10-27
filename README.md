# NovelManager

Este proyecto es una aplicación Android creada para gestionar una biblioteca personal de novelas. La aplicación permite al usuario agregar novelas, visualizarlas en una lista, ver detalles individuales y marcarlas como favoritas. Además, incluye funciones de almacenamiento y personalización.

## Funcionalidades

1. **Agregar y listar novelas**  
   La aplicación permite al usuario añadir novelas ingresando información como título, autor, género y año. Las novelas se almacenan en una base de datos SQLite y se muestran en una lista usando `RecyclerView`.

2. **Visualización de detalles y reseñas**  
   Cada novela guardada puede seleccionarse para ver su información detallada. También es posible añadir reseñas para mantener un registro de opiniones personales sobre cada novela.

3. **Favoritos**  
   Existe la posibilidad de marcar novelas como favoritas. Estas quedan identificadas en la lista para acceder a ellas fácilmente.

4. **Configuración del usuario**  
   Mediante `SharedPreferences`, se guarda la preferencia de tema de la aplicación, pudiendo ser claro u oscuro. Esto permite que la configuración se mantenga incluso al reiniciar la aplicación.

5. **Base de datos con SQLite**  
   La aplicación utiliza SQLite para almacenar los datos de las novelas de manera persistente, de modo que los datos ingresados permanezcan guardados aún si se cierra la aplicación.

## Organización del Proyecto

### Clases Principales

- **`MainActivity.java`**: Administra la pantalla principal, la lista de novelas y las opciones de navegación a otras actividades de la aplicación.
- **`Novel.java`**: Clase modelo que representa la estructura de cada novela, con propiedades como título, autor, género, año y estado de favorito.
- **`NovelViewModel.java`**: Proporciona una interfaz para que `MainActivity` acceda a los datos de las novelas, gestionando la comunicación con la base de datos.
- **`NovelAdapter.java`**: Adaptador para `RecyclerView`, que enlaza los datos de las novelas con cada elemento de la lista en la interfaz de usuario.
- **`SettingsActivity.java`**: Actividad donde el usuario puede cambiar configuraciones de la aplicación, como el tema.

### Archivos XML

- **`activity_main.xml`**: Define la estructura de la pantalla principal, incluyendo el `RecyclerView` para la lista de novelas y los botones para agregar novelas y acceder a las configuraciones.
- **`novel_item.xml`**: Define el diseño de cada elemento individual en el `RecyclerView`, mostrando el título, autor, género y año de cada novela.

## Dependencias

El proyecto incluye las siguientes dependencias para mejorar la funcionalidad y el diseño:

```groovy
dependencies {
    implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'androidx.recyclerview:recyclerview:1.2.0'
    implementation 'androidx.room:room-runtime:2.3.0'
    annotationProcessor 'androidx.room:room-compiler:2.3.0'
}
```

Estas dependencias permiten el uso de `RecyclerView` para la lista de novelas, `Room` para la gestión de la base de datos SQLite, y componentes de Material Design para la interfaz de usuario.

