package com.jayneel.thebarber_user.module

data class ratingModel(var uniqid:String?=null,var userid:String?=null,var ratin:Float?=null,var user:String?=null)
{
    constructor():this("","",0.0f,"")
}