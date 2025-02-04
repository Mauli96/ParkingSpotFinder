package com.example.parkingspotfinder.di

import android.app.Application
import androidx.room.Room
import com.example.parkingspotfinder.data.database.ParkingSpotDatabase
import com.example.parkingspotfinder.data.repository.ParkingSpotRepositoryImpl
import com.example.parkingspotfinder.domain.repository.ParkingSpotRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideParkingSpotDatabase(app: Application): ParkingSpotDatabase {
        return Room.databaseBuilder(
            app,
            ParkingSpotDatabase::class.java,
            "parking_spots.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideParkingSpotRepository(dataBase: ParkingSpotDatabase): ParkingSpotRepository {
        return ParkingSpotRepositoryImpl(dataBase.dao)
    }
}