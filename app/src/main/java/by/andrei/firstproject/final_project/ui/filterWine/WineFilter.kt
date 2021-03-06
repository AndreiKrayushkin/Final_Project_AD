package by.andrei.firstproject.final_project.ui.filterWine

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.andrei.firstproject.final_project.MainActivity2
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.adapters.CountryFilterAdapter
import by.andrei.firstproject.final_project.adapters.TypeFilterAdapter
import by.andrei.firstproject.final_project.adapters.YearFilterAdapter
import by.andrei.firstproject.final_project.data.WineDatabase

private const val BUNDLE_YEAR = "bundle_year"
private const val BUNDLE_TYPE = "bundle_type"
private const val BUNDLE_COUNTRY = "bundle_country"

class WineFilter : Fragment() {

    private lateinit var recyclerViewCountry: RecyclerView
    private lateinit var recyclerViewYear: RecyclerView
    private lateinit var recyclerViewType: RecyclerView
    private lateinit var dao: WineDatabase
    private lateinit var countryFilterAdapter: CountryFilterAdapter
    private lateinit var yearFilterAdapter: YearFilterAdapter
    private lateinit var typeFilterAdapter: TypeFilterAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var buttonApplyFilter: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_filter, container, false)
        setHasOptionsMenu(true)

        recyclerViewCountry = root.findViewById(R.id.countryInFilterList)
        recyclerViewYear = root.findViewById(R.id.yearInFilterList)
        recyclerViewType = root.findViewById(R.id.typeInFilterList)
        buttonApplyFilter = root.findViewById(R.id.applyForFilter)

        dao = WineDatabase.init(context)

        getCountryList()
        getYearList()
        getTypeList()

        buttonApplyFilter.setOnClickListener {
            val year = yearFilterAdapter.getCheckedYear().toString()
            val type = typeFilterAdapter.getCheckedType().toString()
            val country = countryFilterAdapter.getCheckedCountry().toString()
            val bundle = Bundle()
            bundle.putString(BUNDLE_YEAR, year)
            bundle.putString(BUNDLE_TYPE, type)
            bundle.putString(BUNDLE_COUNTRY, country)
            Navigation.findNavController(root).navigate(R.id.wine_list, bundle)
        }
        return root
    }

    private fun getCountryList(){
        val filter = dao.getWineDAO().getCountryList()
        val filterSort = filter.toSortedSet().toList()
        countryFilterAdapter = CountryFilterAdapter(filterSort)
        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewCountry.apply {
            layoutManager = linearLayoutManager
            adapter = countryFilterAdapter
        }
    }

    private fun getYearList(){
        val filter = dao.getWineDAO().getYearList()
        val filterSort = filter.toSortedSet().toList()
        yearFilterAdapter = YearFilterAdapter(filterSort)
        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewYear.apply {
            layoutManager = linearLayoutManager
            adapter = yearFilterAdapter
        }
    }

    private fun getTypeList(){
        val filter = dao.getWineDAO().getTypeList()
        val filterSort = filter.toSortedSet().toList()
        typeFilterAdapter = TypeFilterAdapter(filterSort)
        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewType.apply {
            layoutManager = linearLayoutManager
            adapter = typeFilterAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_empty, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}