package by.andrei.firstproject.final_project.ui.infoWine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.data.Wine
import by.andrei.firstproject.final_project.data.WineDatabase

class WineInfo : Fragment() {

    private lateinit var textManufacturer: TextView
    private lateinit var yearWine: TextView
    private lateinit var alcoholValue: TextView
    private lateinit var sugarValue: TextView
    private lateinit var imageWine: ImageView
    private lateinit var dao: WineDatabase
    private val wineID = "wineID"
    private var wine: Wine? = null
    private var wineId: Int? = 0

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_info, container, false)

        textManufacturer = root.findViewById(R.id.textManufacturerInWineInfoFragment)
        yearWine = root.findViewById(R.id.textYearWineInWineInfoFragment)
        alcoholValue = root.findViewById(R.id.textAlcoholValueInWineInfoFragment)
        sugarValue = root.findViewById(R.id.textSugarValueInWineInfoFragment)
        imageWine = root.findViewById(R.id.imageWinePhotoInWineInfoFragment)

        dao = WineDatabase.init(context)
        wineId = arguments?.getInt(wineID)
        wine = dao.getWineDAO().getItemWine(wineId)
        setTextInFragment()

        return root
    }

    private fun setTextInFragment() {
        textManufacturer.text = wine?.manufacturer
        yearWine.text = wine?.year
        alcoholValue.text = wine?.alcohol
        sugarValue.text = wine?.sugar
        imageWine.setImageURI(wine?.wineImage?.toUri())
    }
}