package by.andrei.firstproject.final_project.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import by.andrei.firstproject.final_project.data.Wine

@Dao
interface WineDAO {
    @Query("SELECT * FROM wine")
    fun getWineList(): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE id = :wineId")
    fun getItemWine(wineId: Int?): Wine

//Для одиночного совпадения
    @Query("SELECT * FROM wine WHERE manufacturer = :manufacturerValue")
    fun getWineListForManufacturer(manufacturerValue: String): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE year = :yearValue")
    fun getWineListForYear(yearValue: String): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE type = :typeValue")
    fun getWineListForType(typeValue: String): MutableList<Wine>

    //Для двойного совпадения
    @Query("SELECT * FROM wine WHERE manufacturer = :manufacturerValue AND year = :yearValue")
    fun getWineListForManufacturerAndYear(manufacturerValue: String, yearValue: String): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE manufacturer = :manufacturerValue AND type = :typeValue")
    fun getWineListForManufacturerAndType(manufacturerValue: String, typeValue: String): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE year = :yearValue AND type = :typeValue")
    fun getWineListForYearAndType(yearValue: String, typeValue: String): MutableList<Wine>

    //Для тройного совпадения
    @Query("SELECT * FROM wine WHERE manufacturer = :manufacturerValue AND year = :yearValue AND type = :typeValue")
    fun getWineListForCountryAndYearAndType(manufacturerValue: String, yearValue: String, typeValue: String): MutableList<Wine>

    @Query("SELECT manufacturer FROM wine")
    fun getCountryList(): List<String>

    @Query("SELECT year FROM wine")
    fun getYearList(): List<String>

    @Query("SELECT type FROM wine")
    fun getTypeList(): List<String>

    @Query("DELETE FROM wine")
    fun deleteAll()

    @Insert
    fun insertAll(wine: Wine)

    @Update
    fun update(wine: Wine)

    @Delete
    fun delete(wine: Wine)
}