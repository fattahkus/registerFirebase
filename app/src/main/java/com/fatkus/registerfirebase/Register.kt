package com.fatkus.registerfirebase

class Register(val id: String?, val name: String,val email: String,val password: String, val rating: Int){
    constructor():this("","","","",0)
}