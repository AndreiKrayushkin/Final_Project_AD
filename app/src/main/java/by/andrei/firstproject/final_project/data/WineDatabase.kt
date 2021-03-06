package by.andrei.firstproject.final_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.andrei.firstproject.final_project.dao.WineDAO

@Database
    (entities = [Wine::class], version = 3)

abstract class WineDatabase : RoomDatabase() {
    abstract fun getWineDAO(): WineDAO

    companion object{
        fun init(context: Context?) = Room.databaseBuilder(context!!, WineDatabase::class.java, "database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}