package kis.kan.jetreadearapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kis.kan.jetreadearapp.network.BooksApi
import kis.kan.jetreadearapp.repository.BookRepository
import kis.kan.jetreadearapp.utils.Constance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    // this for injection api to repository
    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) = BookRepository(api)


    // add module for loading from network api
    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constance.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}