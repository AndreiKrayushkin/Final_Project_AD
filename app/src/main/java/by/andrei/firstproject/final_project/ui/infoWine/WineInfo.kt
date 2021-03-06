package by.andrei.firstproject.final_project.ui.infoWine

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.andrei.firstproject.final_project.MainActivity2
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.data.ManufacturerDatabase
import by.andrei.firstproject.final_project.data.Wine
import by.andrei.firstproject.final_project.data.WineDatabase
import com.google.android.libraries.maps.*
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions

class WineInfo : Fragment(), OnMapReadyCallback {

    private lateinit var textManufacturer: TextView
    private lateinit var textCountry: TextView
    private lateinit var yearWine: TextView
    private lateinit var alcoholValue: TextView
    private lateinit var sugarValue: TextView
    private lateinit var imageWine: ImageView
    private lateinit var dao: WineDatabase
    private lateinit var daoManufacturer: ManufacturerDatabase
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView
    private val wineID = "wineID"
    private var wine: Wine? = null
    private var wineId: Int? = 0

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_info, container, false)
        setHasOptionsMenu(true)
        textManufacturer = root.findViewById(R.id.textManufacturerInWineInfoFragment)
        textCountry = root.findViewById(R.id.textCountyInWineInfoFragment)
        yearWine = root.findViewById(R.id.textYearWineInWineInfoFragment)
        alcoholValue = root.findViewById(R.id.textAlcoholValueInWineInfoFragment)
        sugarValue = root.findViewById(R.id.textSugarValueInWineInfoFragment)
        imageWine = root.findViewById(R.id.imageWinePhotoInWineInfoFragment)

        daoManufacturer = ManufacturerDatabase.init(context)
        dao = WineDatabase.init(context)
        wineId = arguments?.getInt(wineID)
        wine = dao.getWineDAO().getItemWine(wineId)
        setTextInFragment()

        (activity as MainActivity2).supportActionBar?.title = wine?.name

        mapView = root.findViewById(R.id.mapInfoFactory)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return root
    }

    private fun setTextInFragment() {
        textManufacturer.text = wine?.manufacturer
        textCountry.text = wine?.country
        yearWine.text = wine?.year
        alcoholValue.text = wine?.alcohol
        sugarValue.text = wine?.sugar
        imageWine.setImageURI(wine?.wineImage?.toUri())
    }

    override fun onMapReady(googleMapView: GoogleMap) {
        googleMap = googleMapView

        val manufacturerCoordinateFirst = daoManufacturer.getManufacturerDAO().getCoordinateFirst(wine?.country!!)
        val manufacturerCoordinateSecond = daoManufacturer.getManufacturerDAO().getCoordinateSecond(wine?.country!!)

        val cameraPosition = CameraPosition.builder()
            .target(LatLng(manufacturerCoordinateFirst, manufacturerCoordinateSecond))
            .zoom(8F)
            .build()

        val cameraUpdate: CameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        val positionThis = LatLng(manufacturerCoordinateFirst, manufacturerCoordinateSecond)
        googleMap.addMarker(MarkerOptions().position(positionThis))
        googleMap.animateCamera(cameraUpdate)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_add_wine, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}