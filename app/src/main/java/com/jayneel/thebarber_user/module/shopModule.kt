package com.jayneel.thebarber_user.module

data class shopModule(var openingTime:String?=null,var city:String?=null,var email:String?=null,var mobile:String?=null,var oname:String?=null,var type:String?=null,var status:String?=null,var password:String?=null,var shopName:String?=null,var userName:String?=null,var imgurl:String?,var avgrating:Float?=null){
    constructor():this("","","","","","","","","","","",0.0f)
}