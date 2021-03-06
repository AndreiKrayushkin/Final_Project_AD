package by.andrei.firstproject.final_project.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WineFavorite(
    val name: String?,                        //название
    @ColumnInfo val rating: Double,           //
    @ColumnInfo val year: String?,            //год урожая
    @ColumnInfo val country: String?,         //страна
    @ColumnInfo val manufacturer: String?,    //изготовитель
    @ColumnInfo val type: String?,            //тип (красное, белое и т.д.)
    @ColumnInfo val alcohol: String?,         //процент алкоголя
    @ColumnInfo val sugar: String?,           //количетво сахара г/л
    @ColumnInfo val composition: String?,     //
    @ColumnInfo val coordinateFirst: Double,  //
    @ColumnInfo val coordinateSecond: Double, //
    @ColumnInfo val wineImage: String?,       //изображение вина
    @ColumnInfo val idSave: Int?              //id вина из общего списка
): Parcelable {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo var id: Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt()) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(rating)
        parcel.writeString(year)
        parcel.writeString(country)
        parcel.writeString(manufacturer)
        parcel.writeString(type)
        parcel.writeString(alcohol)
        parcel.writeString(sugar)
        parcel.writeString(composition)
        parcel.writeDouble(coordinateFirst)
        parcel.writeDouble(coordinateSecond)
        parcel.writeString(wineImage)
        idSave?.let { parcel.writeInt(it) }
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Wine> {
        override fun createFromParcel(parcel: Parcel): Wine {
            return Wine(parcel)
        }

        override fun newArray(size: Int): Array<Wine?> {
            return arrayOfNulls(size)
        }
    }
}