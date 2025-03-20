# Config Service

Este microservicio se encarga de la configuración centralizada de la aplicación utilizando Spring Cloud Config.

## Importante!

**Este microservicio debe ser el primero en iniciar** antes que cualquier otro, ya que los demás dependen de él para obtener sus configuraciones.
