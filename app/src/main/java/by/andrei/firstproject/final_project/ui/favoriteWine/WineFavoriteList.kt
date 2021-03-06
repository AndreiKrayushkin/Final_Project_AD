package by.andrei.firstproject.final_project.ui.favoriteWine

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
import by.andrei.firstproject.final_project.adapters.FavoriteWineAdapter
import by.andrei.firstproject.final_project.adapters.OnWineFavoriteClickListener
import by.andrei.firstproject.final_project.data.Wine
import by.andrei.firstproject.final_project.data.WineFavorite
import by.andrei.firstproject.final_project.data.WineFavoriteDatabase
import com.google.android.material.snackbar.Snackbar

private const val OPERATION_REMOVED_FROM_FAVORITE_WINE: Int = 1
private const val OPERATION_INFO_WINE: Int = 2
private const val WINE_ID = "wineID"

class WineFavoriteList : Fragment() {

    private var wineId: Int? = 0
    private lateinit var wineAdapter: FavoriteWineAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var onWineClickListener: OnWineFavoriteClickListener
    private lateinit var daoNew: WineFavoriteDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_favorite, container, false)

        recyclerView = root.findViewById(R.id.itemListWineInFavoriteFragment)
        onWineClickListener = object : OnWineFavoriteClickListener {
            override fun invoke(wine: WineFavorite, position: Int, operation: Int) {
                when(operation){
                    OPERATION_INFO_WINE -> getInfoWine(wine, root)
                    OPERATION_REMOVED_FROM_FAVORITE_WINE -> removedFavoriteWine(wine)
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        wineAdapter = FavoriteWineAdapter(mutableListOf(), onWineClickListener)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = wineAdapter
        }
        daoNew = WineFavoriteDatabase.init(context)

        checkDataBase()
        return root
    }

    private fun checkDataBase() {
        val wineListFavorite = daoNew.getWineFavoriteDAO().getWineList()
        wineAdapter.wineFavoriteList = wineListFavorite
        wineAdapter.notifyDataSetChanged()
    }

    private fun getInfoWine(wine: WineFavorite, root: View){
        val bundle = Bundle()
        wine.id?.let { bundle.putInt(WINE_ID, it) }
        Navigation.findNavController(root).navigate(R.id.wine_info, bundle)
    }

    private fun removedFavoriteWine(wine: WineFavorite) {
        daoNew.getWineFavoriteDAO().deleteThisObject(wine.id)
        Snackbar.make(requireView(), R.string.message_removed_wine_from_favorite, Snackbar.LENGTH_SHORT).show()
        checkDataBase()
    }
}