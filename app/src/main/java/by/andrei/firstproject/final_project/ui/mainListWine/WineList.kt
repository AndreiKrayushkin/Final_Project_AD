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
import by.andrei.firstproject.final_project.data.WineFavorite
import by.andrei.firstproject.final_project.data.WineFavoriteDatabase
import com.google.android.material.snackbar.Snackbar

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
    private lateinit var daoNew: WineFavoriteDatabase
    private lateinit var onWineClickListener: OnWineClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_list, container, false)
        setHasOptionsMenu(true)
        recyclerView = root.findViewById(R.id.itemListWineInActivityMain)

        dao = WineDatabase.init(context)
        daoNew = WineFavoriteDatabase.init(context)

        onWineClickListener = object : OnWineClickListener {
            override fun invoke(wine: Wine, position: Int, operation: Int) {
                when(operation){
                    OPERATION_INFO_WINE -> getInfoWine(wine, root)
                    OPERATION_ADD_FAVORITE_WINE -> addFavoriteWine(wine)
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
        return root
    }

    private fun checkDataBase() {

        var wineListFilterByCountry = mutableListOf<Wine>()

        //фильтр для листа
        val filterValueCountry = arguments?.getString(BUNDLE_COUNTRY).toString()
        val filterValueYear = arguments?.getString(BUNDLE_YEAR).toString()
        val filterValueType = arguments?.getString(BUNDLE_TYPE).toString()

        wineListFilterByCountry = if(filterValueCountry == NULL_VALUE && filterValueYear == NULL_VALUE && filterValueType == NULL_VALUE) {
            dao.getWineDAO().getWineList()
        } else if(filterValueCountry != NULL_VALUE && filterValueYear == NULL_VALUE && filterValueType == NULL_VALUE){
            dao.getWineDAO().getWineListForCountry(filterValueCountry)
        } else if(filterValueCountry == NULL_VALUE && filterValueYear != NULL_VALUE && filterValueType == NULL_VALUE){
            dao.getWineDAO().getWineListForYear(filterValueYear)
        } else if(filterValueCountry == NULL_VALUE && filterValueYear == NULL_VALUE && filterValueType != NULL_VALUE){
            dao.getWineDAO().getWineListForType(filterValueType)
        } else if(filterValueCountry != NULL_VALUE && filterValueYear != NULL_VALUE && filterValueType == NULL_VALUE){
            dao.getWineDAO().getWineListForCountryAndYear(filterValueCountry, filterValueYear)
        } else if(filterValueCountry != NULL_VALUE && filterValueYear == NULL_VALUE && filterValueType != NULL_VALUE){
            dao.getWineDAO().getWineListForCountryAndType(filterValueCountry, filterValueType)
        } else if(filterValueCountry == NULL_VALUE && filterValueYear != NULL_VALUE && filterValueType != NULL_VALUE){
            dao.getWineDAO().getWineListForYearAndType(filterValueYear, filterValueType)
        } else {
            dao.getWineDAO().getWineListForCountryAndYearAndType(filterValueCountry, filterValueYear, filterValueType)
        }
        wineAdapter.wineList = wineListFilterByCountry
        wineAdapter.notifyDataSetChanged()
    }
    //переход к информации о вине
    private fun getInfoWine(wine: Wine, root: View){
        val bundle = Bundle()
        wine.id?.let { bundle.putInt(WINE_ID, it) }
        Navigation.findNavController(root).navigate(R.id.wine_info, bundle)
    }
    //добавление вина в избранный список
    private fun addFavoriteWine(wine: Wine){
        if (daoNew.getWineFavoriteDAO().getItemFavWine(wine.id) == null) {
            val wineNewObject = setNewWineObject(wine)
            daoNew.getWineFavoriteDAO().insertAll(wineNewObject)
            Snackbar.make(requireView(), R.string.message_add_wine_to_favorite, Snackbar.LENGTH_SHORT).show()
            checkDataBase()
        } else {
            daoNew.getWineFavoriteDAO().deleteThisObject(wine.id)
            Snackbar.make(requireView(), R.string.message_removed_wine_from_favorite, Snackbar.LENGTH_SHORT).show()
            checkDataBase()
        }
    }
    private fun setNewWineObject(wine: Wine) =
        WineFavorite(
            name = wine.name,
            rating = wine.rating,
            year = wine.year,
            country = wine.country,
            manufacturer = wine.manufacturer,
            type = wine.type,
            alcohol = wine.alcohol,
            sugar = wine.sugar,
            composition = wine.composition,
            coordinateFirst = wine.coordinateFirst,
            coordinateSecond = wine.coordinateSecond,
            wineImage = wine.wineImage,
            idSave = wine.id
    )
}