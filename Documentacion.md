Hecho por **Freth David Piraban Hernandez**.

--- 

## Descripción

La documentación ha sido creada para explicar cómo se desarrolló la prueba técnica, de acuerdo con los requerimientos
solicitados.

## Base de datos
![Diagram.png](mysql_data%2FDiagram.png)

Con el fin de soportar los datos de la aplicación en una base de datos relacional, se creó el modélo tal como se ve en
la imágen superior.

---
## Requerimientos Obtenidos

1. Todos los datos de entrada son obligatorios
2. Si algún dato falta debe generar un error 500
3. El valor asegurado debe ser mayor cero
4. Consultar segun el usuario ingresado y validar si entra en un rango para aplicar un "amparo" 
5. Si no aplica a ningun amparo retornar una respuesta adecuada
6. Si le aplica más de un "amparo", el valor de la liquidación es la suma de todos los amparos que le apliquen según la edad.
7. Valor póliza = valor asegurado * % prima

## Diccionario
Amparo = Insurance
Primas = Insurance details

## Ruta de Scripts
mysql_data/insuranceDB.sql

## Urls
Api localmente : 127.0.0.1:8080/InsuranceAPI/calcularLiquidacion
DB Localmente : 127.0.0.1:3306/insuranceDB

# Json  
## Request  
[
{
"tipo_identificacion": "CC",
"nro_identificacion": "79000002",
"valor_asegurado": 5
}
]

## Response  
[
{
"tipo_identificacion": "79000002",
"nro_identificacion": "CC",
"valor_asegurado": 5,
"liquidación": [
{
"valor_prima": 0.10060,
"código_amparo": 1,
"nombre_amparo": "Muerte accidental"
}
],
"Valor_total": 0.10060
}
]