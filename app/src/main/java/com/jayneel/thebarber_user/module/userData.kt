package com.jayneel.thebarber_user.module
data class userData(var nm:String?=null,var email:String?=null,var ph:String?=null,var gender:String?=null,var userid:String?=null)
{
    constructor():this("","","","","")
}