# Gestion de centro de internacion

El software a desarrollar es sobre un centro de internación el cual sera administrado por el programa. 
El principal funcionamiento es el ingreso, egreso y evolucion de pacientes.

Condiciones a la hora de ingresar un paciente:

Al ingresar un paciente con el mismo DNI que un paciente ya existente este no podra agregarse a la lista de paciente, pero 
si podra iniciar una nueva internacion. se considerara la misma persona a aquellos pacientes que tengan el mismo DNI y el mismo codigo.

En cuyo caso que el nuevo paciente a ingresar tenga el mismo DNI de un paciente ya ingresado la internacion no podra seguir adelante.
En caso de que el hospital no cuente con habitaciones disponibles la internacion del paciente se vera interrumpida.
Pasaria lo mismo en caso de tratar de asignarle un doctor inexistente en el sistema.


## Alcance del proyecto

- Consultar la cantidad de pacientes
- Consultar la cantidad de doctores
- Consultar las habitaciones 
- Consultar las habitaciones disponibles
- Consultar la cantidad de internaciones
- Consultar la cantidad de internaciones por cada paciente
- Consultar la informacion del paciente
- Consultar la internaciones mas reciente de un paciente
- Dar de alta a un paciente
- Poder buscar pacientes y doctores 
- Poder seguir la evolucion de un paciente a lo largo de su estadia
- Obtener la cantidad a cobrar al paciente en base a que cobertura tiene

Agregar el codigo necesario para el correcto funcionamiento de la clase.

## Cálculo del monto total a pagar

La empresa proporciona las siguientes reglas para calcular el monto total a pagar, basadas en el tipo de plan de salud que tenga el cliente y la cantidad de dias que esté.

**Plan 5000**
Va a tener cubierto el 70% del valor de la internación.

**Plan 3000**
Va a tener cubierto el 50% del valor de la internación.

**Plan 1500**
Va a tener cubierto el 30% del valor de la internación.

**Sin cobertura medica**
Va a tener cubierto el 10% hasta los 10 dias de internación. Pasado el tiempo se le cobrara el 100% de valor real.

## Relacion entre clases
- Centro de Internacion 1 -- N pacientes 
- Centro de Internacion 1 -- N doctores 
- Centro de Internacion 1 -- N internaciones -- 1 pacientes -- N historial


