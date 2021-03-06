package by.andrei.firstproject.final_project

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import by.andrei.firstproject.final_project.data.Manufacturer
import by.andrei.firstproject.final_project.data.ManufacturerDatabase

private lateinit var manufacturerName: EditText
private lateinit var manufacturerCoordX: EditText
private lateinit var manufacturerCoordY: EditText
private lateinit var buttonAddNewManufacturer: Button
private lateinit var dao: ManufacturerDatabase

class AddNewFactory : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_manufacturer_add, container, false)
        setHasOptionsMenu(true)
        manufacturerName = root.findViewById(R.id.addManufacturerNameInAddManufacturer)
        manufacturerCoordX = root.findViewById(R.id.addManufacturerCoordinateXInAddManufacturer)
        manufacturerCoordY = root.findViewById(R.id.addManufacturerCoordinateYInAddManufacturer)
        buttonAddNewManufacturer = root.findViewById(R.id.saveNewManufacturer)

        dao = ManufacturerDatabase.init(context)

        buttonAddNewManufacturer.setOnClickListener {
            val manufacturer = createManufacturerObject()
            dao.getManufacturerDAO().insertAll(manufacturer)
        }
        return root
    }

    private fun createManufacturerObject() =
        Manufacturer(
            name = manufacturerName.text.toString(),
            coordinateFirst = manufacturerCoordX.text.toString(),
            coordinateSecond = manufacturerCoordY.text.toString()
        )

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_empty, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}