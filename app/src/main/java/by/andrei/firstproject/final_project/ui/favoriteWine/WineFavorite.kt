package by.andrei.firstproject.final_project.ui.favoriteWine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.adapters.FavoriteWineAdapter
import by.andrei.firstproject.final_project.adapters.OnWineFavoriteClickListener
import by.andrei.firstproject.final_project.data.Wine

class WineFavorite : Fragment() {

    private var wineId: Int? = 0
    private lateinit var wineAdapter: FavoriteWineAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var onWineClickListener: OnWineFavoriteClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_favorite, container, false)

        recyclerView = root.findViewById(R.id.itemListWineInFavoriteFragment)
        onWineClickListener = object : OnWineFavoriteClickListener {
            override fun invoke(wine: Wine, position: Int, operation: Int) {
                Log.v("TODO", "Not yet implemented")
            }
        }
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        wineAdapter = FavoriteWineAdapter(mutableListOf(), onWineClickListener)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = wineAdapter
        }

        return root
    }
}