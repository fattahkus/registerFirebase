package com.fatkus.registerfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.fatkus.testfirebase.registerAdapter
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var ratingBar: RatingBar
    lateinit var button: Button
    lateinit var listView: ListView

    lateinit var testRegister: MutableList<Register>
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testRegister = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("user")

        editName = findViewById(R.id.edtName)
        editEmail = findViewById(R.id.edtMail)
        editPassword = findViewById(R.id.edtPassword)
        ratingBar = findViewById(R.id.ratingBar)
        button = findViewById(R.id.button)
        listView = findViewById(R.id.listView)

        button.setOnClickListener {
            saveRegister()
        }
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    testRegister.clear()
                    for(h in p0.children){
                        val register = h.getValue(Register::class.java)
                        testRegister.add(register!!)
                    }
                    val adapter = registerAdapter(this@MainActivity,R.layout.test,testRegister)
                    listView.adapter = adapter
                }
            }
        })

    }
        private fun saveRegister(){
            val name = editName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if(name.isEmpty()){
                editName.error="please enter a name"
                return
            }else if(email.isEmpty()){
                editEmail.error="please enter a name"
                return
            } else if(password.isEmpty()){
                editPassword.error="please enter a name"
                return
            }
            val testId = ref.push().key
            val register = Register(testId,name,email,password,ratingBar.rating.toInt())

            ref.child(testId.toString()).setValue(register).addOnCompleteListener {
                Toast.makeText(applicationContext,"register saved",Toast.LENGTH_SHORT)
            }
        }
}
