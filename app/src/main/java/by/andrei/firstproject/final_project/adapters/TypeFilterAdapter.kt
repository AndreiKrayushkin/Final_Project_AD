package by.andrei.firstproject.final_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.andrei.firstproject.final_project.R

class TypeFilterAdapter (
    var typeWineFilterList: List<String>
) : RecyclerView.Adapter<TypeFilterAdapter.TypeWineViewHolder>() {

    private var checkedType: String? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypeWineViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_type_filter, parent, false)
        return TypeWineViewHolder(view)
    }

    override fun onBindViewHolder(holder: TypeWineViewHolder, position: Int) {
        val type: String = typeWineFilterList[position]
        holder.bind(type)
        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                checkedType = type
            }
        }
    }

    override fun getItemCount(): Int {
        return typeWineFilterList.size
    }

    fun getCheckedType(): String? {
        return checkedType
    }

    class TypeWineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeWine: TextView = itemView.findViewById(R.id.textTypeWineFilter)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxTypeWineFilter)
        fun bind(type: String) {
            typeWine.text = type
        }
    }
}