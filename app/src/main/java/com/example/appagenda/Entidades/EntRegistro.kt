package com.example.appagenda.Entidades

import java.util.Date

data class EntRegistro(
    var codigoRegistro: Int = 0, // Se inicializa con 0 por defecto
    var nombre: String,
    var descripcion: String,
    var fecha: String,

) {
    constructor(
        nombre: String,
        descripcion: String,
        fecha: String,
    ) : this(0, nombre, descripcion, fecha ) // Constructor secundario

    override fun toString(): String {
        return "Registro(codigoRegistro=$codigoRegistro, nombre='$nombre', descripcion='$descripcion', fecha=$fecha)"
    }
}
