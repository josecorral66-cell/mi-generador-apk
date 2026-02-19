package com.proinventory.admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dbHelper = DatabaseHelper(this)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPass = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvReg = findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_USERS} WHERE ${DatabaseHelper.COL_U_EMAIL} = ? AND ${DatabaseHelper.COL_U_PASS} = ?", arrayOf(email, pass))

            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_U_ID))
                val role = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_U_ROLE))
                
                val intent = if (role == "admin") {
                    Intent(this, AdminActivity::class.java)
                } else {
                    Intent(this, MainActivity::class.java)
                }
                intent.putExtra("USER_ID", id)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
        }

        tvReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}