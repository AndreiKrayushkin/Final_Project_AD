package by.andrei.firstproject.final_project.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import by.andrei.firstproject.final_project.data.Manufacturer

@Dao
interface ManufacturerDAO {
    @Query ("SELECT * FROM manufacturer")
    fun getManufacturerList(): List<Manufacturer>

    @Query ("SELECT name FROM manufacturer")
    fun getManufacturerNameList(): List<String>

    @Query("SELECT * FROM manufacturer WHERE name = :nameFactory")
    fun getManufacturerForFactory(nameFactory: String): List<Manufacturer>

    @Query("SELECT coordinateFirst FROM manufacturer WHERE name = :nameFactory")
    fun getCoordinateFirst(nameFactory: String): Double

    @Query("SELECT coordinateSecond FROM manufacturer WHERE name = :nameFactory")
    fun getCoordinateSecond(nameFactory: String): Double

    @Insert
    fun insertAll(manufacturer: Manufacturer)

    @Update
    fun update(manufacturer: Manufacturer)

    @Delete
    fun delete(manufacturer: Manufacturer)
}