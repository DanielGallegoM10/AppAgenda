package com.example.appagenda

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class BDDAgenda (context: Context): SQLiteOpenHelper(context, "bddAgenda", null, 1) {

    private val crearTablaRegistro = "CREATE TABLE registro(codigoRegistro INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, fecha TEXT)"

    private val borrarTablaRegistro = "DROP TABLE IF EXISTS registro"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(crearTablaRegistro)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(borrarTablaRegistro)

        onCreate(db)
    }


}