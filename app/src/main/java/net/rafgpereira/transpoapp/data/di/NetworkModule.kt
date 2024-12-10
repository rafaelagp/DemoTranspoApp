package net.rafgpereira.transpoapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.rafgpereira.transpoapp.data.network.IApiService
import net.rafgpereira.transpoapp.data.repository.Repository
import net.rafgpereira.transpoapp.domain.repository.IRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providerRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(IApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providerBackendApi(retrofit: Retrofit) : IApiService {
        return retrofit.create(IApiService::class.java)
    }

    @Provides
    @Singleton
    fun providerRepository(apiService: IApiService) : IRepository {
        return Repository(apiService)
    }
}