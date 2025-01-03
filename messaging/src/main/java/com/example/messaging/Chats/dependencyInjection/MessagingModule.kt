package com.example.messaging.Chats.dependencyInjection

import com.example.messaging.Chats.data.repository.PeerToPeerChatRepositoryImpl
import com.example.messaging.Chats.domain.PeerToPeerChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MessagingModule {

    @Provides
    @Singleton
    fun providesPeerToPeerChatRepository(okHttpClient: OkHttpClient): PeerToPeerChatRepository =
        PeerToPeerChatRepositoryImpl(okHttpClient)



}