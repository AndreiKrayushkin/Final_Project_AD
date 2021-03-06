package by.andrei.firstproject.final_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.data.Wine
import by.andrei.firstproject.final_project.data.WineFavoriteDatabase
import com.bumptech.glide.Glide

typealias OnWineClickListener = (wine: Wine, position: Int, operation: Int) -> Unit
private const val OPERATION_ADD_FAVORITE_WINE: Int = 1
private const val OPERATION_INFO_WINE: Int = 2

class WineAdapter(
    var wineList: MutableList<Wine>,
    var onWineClickListener: OnWineClickListener
    ): RecyclerView.Adapter<WineAdapter.WineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WineViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wine_layout, parent, false)
        return WineViewHolder(view)
    }

    override fun onBindViewHolder(holder: WineViewHolder, position: Int) {
        val wine: Wine = wineList[position]
        holder.bind(wine, onWineClickListener)
    }

    override fun getItemCount(): Int {
        return wineList.size
    }

    class WineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameWine: TextView = itemView.findViewById(R.id.nameWineInItemWineLayout)
        private val valueRatingWine: TextView = itemView.findViewById(R.id.valueRatingInItemWineLayout)
        private val valueYear: TextView = itemView.findViewById(R.id.valueYearInItemWineLayout)
        private val typeAndColorWine: TextView = itemView.findViewById(R.id.valueTypeInItemWineLayout)
        private val country: TextView = itemView.findViewById(R.id.valueCountryInItemWineLayout)
//        private val valueComments: TextView = itemView.findViewById(R.id.valueCommentInItemWineLayout)
        private val photoMini: ImageView = itemView.findViewById(R.id.photoWineInItemWineLayout)
        private val favorite: ImageView = itemView.findViewById(R.id.iconLikeInItemWineLayout)
        private val manufacturer: TextView = itemView.findViewById(R.id.valueManufacturerInItemWineLayout)
        private val daoNew = WineFavoriteDatabase.init(itemView.context)

        fun bind(wine: Wine, onWineClickListener: OnWineClickListener) {
            nameWine.text = wine.name
            valueRatingWine.text = wine.rating.toString()
            valueYear.text = wine.year.toString()
            typeAndColorWine.text = wine.type
            manufacturer.text = wine.manufacturer
            country.text = wine.country
//            valueComments.text = "222"
            if(wine.wineImage == null) {
                photoMini.setImageResource(R.drawable.ic_baseline_photo_camera_24)
            }else {
                Glide.with(itemView).load(wine.wineImage).into(photoMini)
            }

            itemView.setOnClickListener {
                onWineClickListener.invoke(wine, adapterPosition, OPERATION_INFO_WINE)
            }

            favorite.setOnClickListener {
                onWineClickListener.invoke(wine, adapterPosition, OPERATION_ADD_FAVORITE_WINE)
            }

            if (daoNew.getWineFavoriteDAO().getItemFavWine(wine.id) == null){
                favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            } else {
                favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        }
    }
}