package com.example.parkingspotfinder.di

import android.app.Application
import androidx.room.Room
import com.example.parkingspotfinder.data.database.ParkingSpotDatabase
import com.example.parkingspotfinder.data.location.DefaultLocationTracker
import com.example.parkingspotfinder.data.repository.ParkingSpotRepositoryImpl
import com.example.parkingspotfinder.domain.location.LocationTracker
import com.example.parkingspotfinder.domain.repository.ParkingSpotRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideParkingSpotDatabase(application: Application): ParkingSpotDatabase {
        return Room.databaseBuilder(
            application,
            ParkingSpotDatabase::class.java,
            "parking_spots.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideParkingSpotRepository(dataBase: ParkingSpotDatabase): ParkingSpotRepository {
        return ParkingSpotRepositoryImpl(dataBase.dao)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Provides
    @Singleton
    fun provideLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker {
        return DefaultLocationTracker(
            locationClient = fusedLocationProviderClient,
            application = application
        )
    }
}