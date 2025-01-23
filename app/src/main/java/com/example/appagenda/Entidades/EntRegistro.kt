package com.example.appagenda.Entidades

data class EntRegistro(
    var codigoRegistro: Int = 0,
    var nombre: String,
    var descripcion: String,
    var fecha: String,

) {
    constructor(
        nombre: String,
        descripcion: String,
        fecha: String,
    ) : this(0, nombre, descripcion, fecha )

    override fun toString(): String {
        return "Registro(codigoRegistro=$codigoRegistro, nombre='$nombre', descripcion='$descripcion', fecha=$fecha)"
    }
}
