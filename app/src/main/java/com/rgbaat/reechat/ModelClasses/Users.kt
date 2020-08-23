package com.rgbaat.reechat.ModelClasses

class Users {

    private var uid :String = ""
    private var username :String = ""
    private var profile :String = ""
    private var cover :String = ""
    private var facebook :String = ""
    private var status :String = ""
    private var search :String = ""
    private var instagram :String = ""
    private var wibsite :String = ""

    constructor()

    constructor(
        uid: String,
        username: String,
        profile: String,
        cover: String,
        facebook: String,
        status: String,
        search: String,
        instagram: String,
        wibsite: String
    ) {
        this.uid = uid
        this.username = username
        this.profile = profile
        this.cover = cover
        this.facebook = facebook
        this.status = status
        this.search = search
        this.instagram = instagram
        this.wibsite = wibsite
    }

    fun getUid():String{

        return uid
    }

    fun setUid(uid: String){

        this.uid = uid
    }

    fun getUsername():String{

        return username
    }

    fun setUsernmae(username: String){

        this.username = username
    }
    fun getProfile():String{

        return profile
    }

    fun setProfile(profile: String){

        this.profile = profile
    }
    fun getCover():String{

        return cover
    }

    fun setCover(cover: String){

        this.cover = cover
    }
    fun getfacebook():String{

        return facebook
    }

    fun setfacebook(facebook: String){
        this.facebook = facebook
    }

    fun getstatus():String{

        return status
    }

    fun setUstatus(status: String){

        this.status = status
    }

    fun gesearch():String{

        return search
    }

    fun setsearch(search:String){

        this.search = search
    }

    fun geinstagram():String{

        return instagram
    }

    fun setinstagram(instagram: String){

        this.instagram = instagram
    }

    fun geinwibsite():String{

        return wibsite
    }

    fun setinwibsite(wibsite: String){

        this.wibsite = wibsite
    }
}