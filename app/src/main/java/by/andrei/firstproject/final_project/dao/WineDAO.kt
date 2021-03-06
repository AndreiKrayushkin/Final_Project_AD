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
    @Query("SELECT * FROM wine WHERE country = :countryValue")
    fun getWineListForCountry(countryValue: String): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE year = :yearValue")
    fun getWineListForYear(yearValue: String): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE type = :typeValue")
    fun getWineListForType(typeValue: String): MutableList<Wine>

    //Для двойного совпадения
    @Query("SELECT * FROM wine WHERE country = :countryValue AND year = :yearValue")
    fun getWineListForCountryAndYear(countryValue: String, yearValue: String): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE country = :countryValue AND type = :typeValue")
    fun getWineListForCountryAndType(countryValue: String, typeValue: String): MutableList<Wine>

    @Query("SELECT * FROM wine WHERE year = :yearValue AND type = :typeValue")
    fun getWineListForYearAndType(yearValue: String, typeValue: String): MutableList<Wine>

    //Для тройного совпадения
    @Query("SELECT * FROM wine WHERE country = :countryValue AND year = :yearValue AND type = :typeValue")
    fun getWineListForCountryAndYearAndType(countryValue: String, yearValue: String, typeValue: String): MutableList<Wine>

    @Query("SELECT country FROM wine")
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