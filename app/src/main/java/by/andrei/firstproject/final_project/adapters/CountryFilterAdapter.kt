package by.andrei.firstproject.final_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.andrei.firstproject.final_project.R

class CountryFilterAdapter(
    var countryFilterList: List<String>
    ) : RecyclerView.Adapter<CountryFilterAdapter.CountryViewHolder>() {

    private var checkedCountry: String? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coutnry_filter, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country: String = countryFilterList[position]
        holder.bind(country)

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                checkedCountry = country
            }
        }
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    fun getCheckedCountry(): String? {
        return checkedCountry
    }

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val countryName: TextView = itemView.findViewById(R.id.textCountryFilter)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxCountryFilter)
        fun bind(country: String) {
            countryName.text = country
        }
    }
}