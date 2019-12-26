package com.fatkus.testfirebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fatkus.registerfirebase.R
import com.fatkus.registerfirebase.Register
import com.google.firebase.database.FirebaseDatabase

class registerAdapter(val mCtx: Context,val layoutResId: Int,val registerList: List<Register>):ArrayAdapter<Register>(mCtx,layoutResId,registerList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textView)
        val textViewUpdate = view.findViewById<TextView>(R.id.textViewUpdate)
        val register = registerList[position]
        textViewName.text = register.name

        textViewUpdate.setOnClickListener {
            showUpdateDialog(register)
        }
        return view
    }
    fun showUpdateDialog(register: Register) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update Test")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.layout_update_test, null)

        val editName = view.findViewById<EditText>(R.id.editName)
        val editEmail = view.findViewById<EditText>(R.id.editEmail)
        val editPassword = view.findViewById<EditText>(R.id.editPassword)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

        editName.setText(register.name)
        editEmail.setText(register.email)
        editPassword.setText(register.password)
        ratingBar.rating = register.rating.toFloat()

        builder.setView(view)
        builder.setPositiveButton("Update"
        ) { dialog, which ->

            val dbTest = FirebaseDatabase.getInstance().getReference("user")
            val name = editName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if(name.isEmpty()){
                editName.error="please enter a name"
                editName.requestFocus()
                return@setPositiveButton
            }else if(email.isEmpty()){
                editEmail.error="please enter a email"
                editEmail.requestFocus()
                return@setPositiveButton
            }else if (password.isEmpty()){
                editPassword.error="please enter a password"
                editPassword.requestFocus()
                return@setPositiveButton
            }

            val register = Register(register.id, name, email, password, ratingBar.rating.toInt())

            dbTest.child(register.id.toString()).setValue(register)

            Toast.makeText(mCtx,"Test Update",Toast.LENGTH_SHORT)
        }
        builder.setNegativeButton("No"
        ) { dialog, which ->

        }
        val alert = builder.create()
        alert.show()
    }
}