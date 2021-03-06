package by.andrei.firstproject.final_project.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import by.andrei.firstproject.final_project.data.WineFavorite

@Dao
interface WineFavoriteDAO {
    @Query("SELECT * FROM wineFavorite")
    fun getWineList(): MutableList<WineFavorite>

    @Query("SELECT * FROM wineFavorite WHERE id = :wineId")
    fun getItemWine(wineId: Int?): WineFavorite

    @Query("SELECT * FROM wineFavorite WHERE idSave = :wineId")
    fun getItemFavWine(wineId: Int?): WineFavorite

    @Query("DELETE FROM wineFavorite WHERE idSave = :wineId")
    fun deleteThisObject(wineId: Int?)

    @Query("DELETE FROM wineFavorite")
    fun deleteAll()

    @Insert
    fun insertAll(wineFavorite: WineFavorite)

    @Update
    fun update(wineFavorite: WineFavorite)

    @Delete
    fun delete(wineFavorite: WineFavorite)
}