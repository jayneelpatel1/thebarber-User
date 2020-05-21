package com.jayneel.thebarber_user.module

data class iteamModule(var name:String?=null,var minute:Int?=null,var price:String?=null){
    constructor():this("",0,"")
}
