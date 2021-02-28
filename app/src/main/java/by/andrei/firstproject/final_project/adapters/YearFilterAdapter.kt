package by.andrei.firstproject.final_project.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.andrei.firstproject.final_project.R

class YearFilterAdapter (
    var yearFilterList: List<String>
) : RecyclerView.Adapter<YearFilterAdapter.YearViewHolder>() {

    private var checkedYear: String? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): YearViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_year_filter, parent, false)
        return YearViewHolder(view)
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        val year: String = yearFilterList[position]
        holder.bind(year)

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                checkedYear = year
            }
        }
    }

    override fun getItemCount(): Int {
        return yearFilterList.size
    }

    fun getCheckedYear(): String? {
        return checkedYear
    }

    class YearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val yearValue: TextView = itemView.findViewById(R.id.textYearFilter)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxYearFilter)
        fun bind(year: String) {
            yearValue.text = year
        }
    }
}