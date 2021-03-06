package by.andrei.firstproject.final_project.ui.addWine

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.dao.ManufacturerDAO
import by.andrei.firstproject.final_project.data.ManufacturerDatabase
import by.andrei.firstproject.final_project.data.Wine
import by.andrei.firstproject.final_project.data.WineDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.util.UUID

private const val FORMAT_PHOTO = ".jpg"
private const val FILE_PROVIDER_AUTHORITY = "by.andrei.firstproject.final_project.fileprovider"

class AddWineFragment : Fragment() {

    private lateinit var nameWine: EditText
    private lateinit var yearWine: EditText
    private lateinit var countryWine: EditText
    private lateinit var typeWine: EditText
    private lateinit var alcoholWine: EditText
    private lateinit var sugarInWine: EditText
    private lateinit var compositionWine: EditText
    private lateinit var buttonAddWine: FloatingActionButton
    private lateinit var buttonAddPhoto: FloatingActionButton
    private lateinit var navController: NavController
    private lateinit var photoWine: ImageView
    private lateinit var manufacturerSpinner: Spinner
    private lateinit var dao: WineDatabase
    private lateinit var daoManufacturer: ManufacturerDatabase
    private var photoFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_add, container, false)
        setHasOptionsMenu(true)
        nameWine = root.findViewById(R.id.addNameWineInAddLayout)
        yearWine = root.findViewById(R.id.addYearWineInAddLayout)
        countryWine = root.findViewById(R.id.addNameCountryWineInAddLayout)
        typeWine = root.findViewById(R.id.addTypeWineInAddLayout)
        alcoholWine = root.findViewById(R.id.addAlcoholWineInAddLayout)
        sugarInWine = root.findViewById(R.id.addSugarWineInAddLayout)
        compositionWine = root.findViewById(R.id.addCompositionWineInAddLayout)
        buttonAddWine = root.findViewById(R.id.buttonAddWineInList)
        buttonAddPhoto = root.findViewById(R.id.buttonAddPhoto)
        photoWine = root.findViewById(R.id.viewPhotoWineInAddLayout)
        manufacturerSpinner = root.findViewById(R.id.manufacturerSpinnerInAddLayout)

        daoManufacturer = ManufacturerDatabase.init(context)
        val country = daoManufacturer.getManufacturerDAO().getManufacturerNameList()
        val adapterMy = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, country)
        adapterMy.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        manufacturerSpinner.adapter = adapterMy

        dao = WineDatabase.init(context)

        buttonAddWine.setOnClickListener {
            val wine = createWineObject()
            dao.getWineDAO().insertAll(wine)
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.wine_list) }
        }

        buttonAddPhoto.setOnClickListener {
            photoFile = File(context?.filesDir, UUID.randomUUID().toString() + FORMAT_PHOTO)
            val photoUri = context?.let { it1 -> FileProvider.getUriForFile(it1, FILE_PROVIDER_AUTHORITY, photoFile!!
            ) }
            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intentGetPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            getFileWritingPermission(intentGetPhoto, photoUri)
            startActivityForResult(intentGetPhoto, 5)
        }
        return root
    }
    @SuppressLint("QueryPermissionsNeeded")
    private fun getFileWritingPermission(intentGetPhoto: Intent, photoUri: Uri?) {
        val cameraActivities: List<ResolveInfo> = context?.packageManager!!.queryIntentActivities(
            intentGetPhoto,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        for (cameraActivity in cameraActivities) {
            requireContext().grantUriPermission(
                cameraActivity.activityInfo.packageName,
                photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        }
    }

    private fun createWineObject() =
        Wine(
            name = nameWine.text.toString(),
            rating = 2.0,
            year = yearWine.text.toString(),
            manufacturer = manufacturerSpinner.selectedItem.toString(),
            country = countryWine.text.toString(),
            type = typeWine.text.toString(),
            alcohol = alcoholWine.text.toString(),
            sugar = sugarInWine.text.toString(),
            composition = compositionWine.text.toString(),
            coordinateFirst = 50.0,
            coordinateSecond = 50.0,
            wineImage = photoFile?.path
        )

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_empty, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoWine.setImageURI(photoFile?.path?.toUri())
    }
}