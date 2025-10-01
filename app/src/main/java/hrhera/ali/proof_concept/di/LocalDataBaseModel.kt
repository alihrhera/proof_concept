package hrhera.ali.proof_concept.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hrhera.ali.proof_concept.data.local.LocalDataBase
import hrhera.ali.proof_concept.data.local.ProductDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataBaseModel {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): LocalDataBase {
        return Room.databaseBuilder(
            appContext, LocalDataBase::class.java, "code_in_my_cash_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideProductDao(db: LocalDataBase): ProductDao = db.productDao()

}