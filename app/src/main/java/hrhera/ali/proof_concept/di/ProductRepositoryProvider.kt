package hrhera.ali.proof_concept.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hrhera.ali.proof_concept.data.repository.ProductRepositoryImpl
import hrhera.ali.proof_concept.domain.repository.ProductRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRepositoryProvider {

    @Binds
    @Singleton
   abstract fun provideProductRepository(repository: ProductRepositoryImpl): ProductRepository
}
