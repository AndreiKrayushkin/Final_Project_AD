package by.andrei.firstproject.final_project.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Manufacturer(
    val name: String?,                  //производитель
    val coordinateFirst: String?,       //координата по широте
    val coordinateSecond: String?       //координата по долготе
): Parcelable {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo var id: Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(coordinateFirst)
        parcel.writeString(coordinateSecond)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Manufacturer> {
        override fun createFromParcel(parcel: Parcel): Manufacturer {
            return Manufacturer(parcel)
        }

        override fun newArray(size: Int): Array<Manufacturer?> {
            return arrayOfNulls(size)
        }
    }
}