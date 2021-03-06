package by.andrei.firstproject.final_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.andrei.firstproject.final_project.dao.WineFavoriteDAO

@Database
    (entities = [WineFavorite::class], version = 3)

abstract class WineFavoriteDatabase : RoomDatabase() {
    abstract fun getWineFavoriteDAO(): WineFavoriteDAO

    companion object{
        fun init(context: Context?) = Room.databaseBuilder(context!!, WineFavoriteDatabase::class.java, "databaseFav")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}