package by.andrei.firstproject.final_project.ui.mainListWine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.adapters.OnWineClickListener
import by.andrei.firstproject.final_project.adapters.WineAdapter
import by.andrei.firstproject.final_project.data.Wine
import by.andrei.firstproject.final_project.data.WineDatabase

private const val OPERATION_ADD_FAVORITE_WINE: Int = 1
private const val OPERATION_INFO_WINE: Int = 2
private const val BUNDLE_YEAR = "bundle_year"
private const val BUNDLE_TYPE = "bundle_type"
private const val BUNDLE_COUNTRY = "bundle_country"
private const val NULL_VALUE = "null"
private const val WINE_ID = "wineID"

class WineList : Fragment() {

    private lateinit var wineAdapter: WineAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dao: WineDatabase
    private lateinit var onWineClickListener: OnWineClickListener
//    private var filterValue: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//    setHasOptionsMenu(true)
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_list, container, false)

        recyclerView = root.findViewById(R.id.itemListWineInActivityMain)

        dao = WineDatabase.init(context)

        onWineClickListener = object : OnWineClickListener {
            override fun invoke(wine: Wine, position: Int, operation: Int) {
                when(operation){
                    OPERATION_INFO_WINE -> getInfoWine(wine, root)
                    OPERATION_ADD_FAVORITE_WINE -> TODO()
                }
            }
        }

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        wineAdapter = WineAdapter(mutableListOf(), onWineClickListener)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = wineAdapter
        }
        checkDataBase()
//        Log.v("LOG-COUNTRY", arguments?.getString("bundle_filter").toString())
        return root
    }

    private fun checkDataBase() {
        var wineListFilterByCountry = mutableListOf<Wine>()

        val filterValueCountry = arguments?.getString(BUNDLE_COUNTRY).toString()
        val filterValueYear = arguments?.getString(BUNDLE_YEAR).toString()
        val filterValueType = arguments?.getString(BUNDLE_TYPE).toString()

        wineListFilterByCountry = if(filterValueCountry == NULL_VALUE && filterValueYear == NULL_VALUE && filterValueType == NULL_VALUE) {
            dao.getWineDAO().getWineList()
        } else if(filterValueCountry != NULL_VALUE && filterValueYear == NULL_VALUE && filterValueType == NULL_VALUE){
            dao.getWineDAO().getWineListForManufacturer(filterValueCountry)
        } else if(filterValueCountry == NULL_VALUE && filterValueYear != NULL_VALUE && filterValueType == NULL_VALUE){
            dao.getWineDAO().getWineListForYear(filterValueYear)
        } else if(filterValueCountry == NULL_VALUE && filterValueYear == NULL_VALUE && filterValueType != NULL_VALUE){
            dao.getWineDAO().getWineListForType(filterValueType)
        } else if(filterValueCountry != NULL_VALUE && filterValueYear != NULL_VALUE && filterValueType == NULL_VALUE){
            dao.getWineDAO().getWineListForManufacturerAndYear(filterValueCountry, filterValueYear)
        }else if(filterValueCountry != NULL_VALUE && filterValueYear == NULL_VALUE && filterValueType != NULL_VALUE){
            dao.getWineDAO().getWineListForManufacturerAndType(filterValueCountry, filterValueType)
        } else if(filterValueCountry == NULL_VALUE && filterValueYear != NULL_VALUE && filterValueType != NULL_VALUE){
            dao.getWineDAO().getWineListForYearAndType(filterValueYear, filterValueType)
        }else {
            dao.getWineDAO().getWineListForCountryAndYearAndType(filterValueCountry, filterValueYear, filterValueType)
        }
        wineAdapter.wineList = wineListFilterByCountry
        wineAdapter.notifyDataSetChanged()
    }

    private fun getInfoWine(wine: Wine, root: View){
        val bundle = Bundle()
        wine.id?.let { bundle.putInt(WINE_ID, it) }
        Navigation.findNavController(root).navigate(R.id.wine_info, bundle)
    }

    private fun addFavoriteWine(wine: Wine, root: View){
        val bundle = Bundle()
        wine.id?.let { bundle.putInt(WINE_ID, it) }
        Navigation.findNavController(root).navigate(R.id.wine_favorite, bundle)
    }

//  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//    inflater.inflate(R.menu.menu_wine_list, menu)
//    super.onCreateOptionsMenu(menu, inflater)
//  }
}