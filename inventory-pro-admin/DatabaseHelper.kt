package com.proinventory.admin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "inventory.db"
        private const val DATABASE_VERSION = 1

        // Tabla de Usuarios
        const val TABLE_USERS = "users"
        const val COL_U_ID = "_id" // Se usa _id por compatibilidad con adaptadores de Android
        const val COL_U_NAME = "name"
        const val COL_U_EMAIL = "email"
        const val COL_U_PASS = "password"
        const val COL_U_ROLE = "role"

        // Tabla de Productos
        const val TABLE_PRODUCTS = "products"
        const val COL_P_ID = "_id"
        const val COL_P_UID = "user_id"
        const val COL_P_NAME = "name"
        const val COL_P_SKU = "sku"
        const val COL_P_PRICE = "price"
        const val COL_P_QTY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de usuarios
        db.execSQL("CREATE TABLE $TABLE_USERS ($COL_U_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_U_NAME TEXT, $COL_U_EMAIL TEXT, $COL_U_PASS TEXT, $COL_U_ROLE TEXT)")
        
        // Crear tabla de productos
        db.execSQL("CREATE TABLE $TABLE_PRODUCTS ($COL_P_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_P_UID INTEGER, $COL_P_NAME TEXT, $COL_P_SKU TEXT, $COL_P_PRICE REAL, $COL_P_QTY INTEGER)")

        // Usuario administrador inicial (Basado en tu lógica de AdminDashboard)
        db.execSQL("INSERT INTO $TABLE_USERS ($COL_U_NAME, $COL_U_EMAIL, $COL_U_PASS, $COL_U_ROLE) VALUES ('Admin', 'admin@pro.com', 'admin123', 'admin')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    // --- NUEVAS FUNCIONES PARA EL ADMIN DASHBOARD ---

    // Obtener todos los usuarios para la lista del Admin
    fun getAllUsers(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_USERS", null)
    }

    // Borrar un usuario (Equivalente a deleteUser en AdminDashboard.tsx)
    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_USERS, "$COL_U_ID = ?", arrayOf(id.toString()))
    }

    // Obtener todos los productos de todos los usuarios (Vista global de Admin)
    fun getAllProducts(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_PRODUCTS", null)
    }
}