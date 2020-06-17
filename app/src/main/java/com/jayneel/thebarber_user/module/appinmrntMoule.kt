package com.jayneel.thebarber_user.module

data class appinmrntMoule(var date:String?=null,var time:String?=null,var bookfor:ArrayList<iteamModule>?=null,var custunm:String?=null,var staus:String?=null,var shopname:String?=null,var uniqid:String?=null,var userid:String?=null)
{
    constructor():this("","", arrayListOf<iteamModule>(),"","","","","")
}