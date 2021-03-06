package by.andrei.firstproject.final_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.andrei.firstproject.final_project.dao.ManufacturerDAO

@Database
    (entities = [Manufacturer::class], version = 1)

abstract class ManufacturerDatabase: RoomDatabase() {
    abstract fun getManufacturerDAO(): ManufacturerDAO

    companion object{
        fun init(context: Context?) = Room.databaseBuilder(context!!, ManufacturerDatabase::class.java, "databaseManufacturer")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}