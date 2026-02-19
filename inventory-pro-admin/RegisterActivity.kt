package com.proinventory.admin

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.widget.*

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val dbHelper = DatabaseHelper(this)
        val btn = findViewById<Button>(R.id.btnDoRegister)

        btn.setOnClickListener {
            val name = findViewById<EditText>(R.id.etRegName).text.toString()
            val email = findViewById<EditText>(R.id.etRegEmail).text.toString()
            val pass = findViewById<EditText>(R.id.etRegPass).text.toString()

            if(name.isEmpty() || email.isEmpty() || pass.isEmpty()) return@setOnClickListener

            val db = dbHelper.writableDatabase
            val cv = ContentValues()
            cv.put(DatabaseHelper.COL_U_NAME, name)
            cv.put(DatabaseHelper.COL_U_EMAIL, email)
            cv.put(DatabaseHelper.COL_U_PASS, pass)
            cv.put(DatabaseHelper.COL_U_ROLE, "user")

            val res = db.insert(DatabaseHelper.TABLE_USERS, null, cv)
            if (res != -1L) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error: Email duplicado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}