package com.example.androidphonepermission.global

import android.app.Application
import com.example.androidphonepermission.helper.AppSignatureHelper

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        /*Following will generate the hash code*/
        var appSignature = AppSignatureHelper(this)
        appSignature.getAppSignatures()
    }
}