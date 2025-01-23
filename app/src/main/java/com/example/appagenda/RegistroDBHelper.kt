package com.example.appagenda

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appagenda.Entidades.EntRegistro

class RegistroDBHelper (context: Context): BDDAgenda(context) {
    fun insertarRegistro(registro: EntRegistro): Long {
        val db : SQLiteDatabase = this.writableDatabase

        var registroID = -1

        db.beginTransaction()

        try {
            val values: ContentValues = ContentValues()

            if (registro.codigoRegistro > 0){
                values.put("codigoRegistro", registro.codigoRegistro)
            }

            values.put("nombre", registro.nombre)
            values.put("descripcion", registro.descripcion)
            values.put("fecha", registro.fecha)

            registroID = db.insertOrThrow("registro", null, values).toInt()

            db.setTransactionSuccessful()

        }catch (e: Exception){
           println(e.message)
        }finally {
            db.endTransaction()
        }
        return registroID.toLong()
    }

    fun actualizarRegistro(registro: EntRegistro): Long {
        val db : SQLiteDatabase = this.writableDatabase

        var registroID = -1

        if (registro.codigoRegistro > 0){
            db.beginTransaction()
            try {
                val values: ContentValues = ContentValues()
                if (registro.codigoRegistro > 0){
                    values.put("codigoRegistro", registro.codigoRegistro)
                }

                values.put("nombre", registro.nombre)
                values.put("descripcion", registro.descripcion)
                values.put("fecha", registro.fecha)

                val rows = db.update("registro", values, "codigoRegistro = ?", arrayOf(registro.codigoRegistro.toString()))
                if (rows > 0){
                    db.setTransactionSuccessful()
                    registroID = registro.codigoRegistro
                }
            }catch (e: Exception){
                println(e.message)
            }finally {
                db.endTransaction()
            }
        }
        return registroID.toLong()
    }

    fun getRegistros(): List<EntRegistro>{
        val db : SQLiteDatabase = this.readableDatabase
        val registros = mutableListOf<EntRegistro>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM registro", null)

        if (cursor.moveToFirst()){
            do {
                val codigoRegistro = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val descripcion = cursor.getString(2)
                val fecha = cursor.getString(3)

                val registro = EntRegistro(codigoRegistro, nombre, descripcion, fecha)

                registros.add(registro)
            }while (cursor.moveToNext())
        }
        return registros
    }

    fun borrarRegistro(codigoRegistro: Int): Int{
        val db : SQLiteDatabase = this.writableDatabase
        var borrados = 0
        db.beginTransaction()

        try {
            borrados = db.delete("registro", "codigoRegistro = ?", arrayOf(codigoRegistro.toString()))

            db.setTransactionSuccessful()
        }catch (e: Exception){
            println(e.message)
        }finally {
            db.endTransaction()
        }
        return borrados
    }

    fun getRegistro(codigoRegistro: Int): EntRegistro? {
        val db : SQLiteDatabase = this.readableDatabase
        var registro: EntRegistro? = null
        val cursor: Cursor = db.rawQuery("SELECT * FROM registro WHERE codigoRegistro = ?", arrayOf(codigoRegistro.toString()))

        if (cursor.moveToFirst()){
            do {
                val codigoRegistro = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val descripcion = cursor.getString(2)
                val fecha = cursor.getString(3)

                val registro = EntRegistro(codigoRegistro, nombre, descripcion, fecha)
            }while (cursor.moveToNext())
        }
        return registro
    }


}