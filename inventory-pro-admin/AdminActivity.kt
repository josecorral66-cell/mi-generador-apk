package com.proinventory.admin

import android.app.Activity
import android.os.Bundle
import android.widget.*
import android.database.Cursor

class AdminActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val dbHelper = DatabaseHelper(this)
        val lvUsers = findViewById<ListView>(R.id.lvUsers)
        val btnLogout = findViewById<Button>(R.id.btnAdminLogout)

        // Función para cargar los usuarios desde la DB
        fun refreshUserList() {
            val cursor = dbHelper.getAllUsers()
            val adapter = SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2, // Diseño de lista estándar de Android
                cursor,
                arrayOf(DatabaseHelper.COL_U_EMAIL, DatabaseHelper.COL_U_ROLE),
                intArrayOf(android.R.id.text1, android.R.id.text2),
                0
            )
            lvUsers.adapter = adapter
        }

        refreshUserList()

        // Borrar usuario al mantener presionado (Lógica del AdminDashboard original)
        lvUsers.setOnItemLongClickListener { _, _, _, id ->
            dbHelper.deleteUser(id.toInt())
            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()
            refreshUserList()
            true
        }

        btnLogout.setOnClickListener {
            finish() // Regresa al Login
        }
    }
}