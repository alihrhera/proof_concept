package hrhera.ali.proof_concept.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hrhera.ali.proof_concept.data.network.ApiService
import hrhera.ali.proof_concept.data.network.FakeApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworksServiceProviders {
    @Provides
    @Singleton
    fun provideApiService(): ApiService = FakeApi()
}


