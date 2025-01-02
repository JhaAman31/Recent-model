package com.example.recentsmodule

import android.app.Application
import com.airbnb.viewmodeladapter.BuildConfig
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.google.GoogleEmojiProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : Application(){
//    override fun onCreate() {
//        super.onCreate()
//        // Installing the EmojiProvider
//        EmojiManager.install(GoogleEmojiProvider())
//    }
}