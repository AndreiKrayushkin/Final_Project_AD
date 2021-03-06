package by.andrei.firstproject.final_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.data.WineFavorite
import by.andrei.firstproject.final_project.data.WineFavoriteDatabase
import com.bumptech.glide.Glide

typealias OnWineFavoriteClickListener = (wine: WineFavorite, position: Int, operation: Int) -> Unit
private const val OPERATION_REMOVED_FROM_FAVORITE_WINE: Int = 1
private const val OPERATION_INFO_WINE: Int = 2

class FavoriteWineAdapter(
    var wineFavoriteList: MutableList<WineFavorite>,
    var onWineClickListener: OnWineFavoriteClickListener
    ): RecyclerView.Adapter<FavoriteWineAdapter.WineFavoriteViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WineFavoriteViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_wine, parent, false)
        return WineFavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: WineFavoriteViewHolder, position: Int) {
        val wine: WineFavorite = wineFavoriteList[position]
        holder.bind(wine, onWineClickListener)
    }

    override fun getItemCount(): Int {
        return wineFavoriteList.size
    }
    
    class WineFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameWine: TextView = itemView.findViewById(R.id.nameWineInFavoriteWineLayout)
        private val valueRatingWine: TextView = itemView.findViewById(R.id.valueRatingInFavoriteWineLayout)
        private val valueYear: TextView = itemView.findViewById(R.id.valueYearInFavoriteWineLayout)
        private val typeAndColorWine: TextView = itemView.findViewById(R.id.valueTypeInFavoriteWineLayout)
        private val manufacturer: TextView = itemView.findViewById(R.id.valueManufacturerInFavoriteWineLayout)
        private val country: TextView = itemView.findViewById(R.id.valueCountryInFavoriteWineLayout)
//        private val valueComments: TextView = itemView.findViewById(R.id.valueCommentInFavoriteWineLayout)
        private val photoMini: ImageView = itemView.findViewById(R.id.photoWineInFavoriteWineLayout)
        private val favorite: ImageView = itemView.findViewById(R.id.iconLikeInFavoriteWineLayout)
        private val daoNew = WineFavoriteDatabase.init(itemView.context)
        
        fun bind(wine: WineFavorite, onWineClickListener: OnWineFavoriteClickListener) {
            nameWine.text = wine.name
            valueRatingWine.text = wine.rating.toString()
            valueYear.text = wine.year.toString()
            typeAndColorWine.text = wine.type
            country.text = wine.country
            manufacturer.text = wine.manufacturer
//            valueComments.text = "222"
            Glide.with(itemView).load(wine.wineImage).into(photoMini)

            itemView.setOnClickListener {
                onWineClickListener.invoke(wine, adapterPosition, OPERATION_INFO_WINE)
            }

            favorite.setOnClickListener {
                onWineClickListener.invoke(wine, adapterPosition, OPERATION_REMOVED_FROM_FAVORITE_WINE)
            }
        }
    }
}