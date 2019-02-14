package com.example.androidphonepermission.interfaces

interface OTPReceiveInterface {
    fun onOtpReceived(otp : String)
    fun onOtpTimeout()
}