package com.slngl.spacexwithcompose.di

import com.google.gson.Gson
import com.slngl.spacexwithcompose.network.HistoryService
import com.slngl.spacexwithcompose.network.NetworkInterceptor
import com.slngl.spacexwithcompose.repository.HistoryRepository
import com.slngl.spacexwithcompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHistoryRepository(api: HistoryService) = HistoryRepository(api)

    @Singleton
    @Provides
    fun provideHistoryApi(
        gson: Gson,
        okHttpClient: OkHttpClient,
    ): HistoryService =
        Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(HistoryService::class.java)

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(NetworkInterceptor())
            .build()

    @Singleton
    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}
