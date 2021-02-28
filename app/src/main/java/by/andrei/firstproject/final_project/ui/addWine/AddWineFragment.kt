package by.andrei.firstproject.final_project.ui.addWine

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.andrei.firstproject.final_project.R
import by.andrei.firstproject.final_project.data.Wine
import by.andrei.firstproject.final_project.data.WineDatabase
import java.io.File
import java.util.UUID

private const val FORMAT_PHOTO = ".jpg"
private const val FILE_PROVIDER_AUTHORITY = "by.andrei.firstproject.final_project.fileprovider"

class AddWineFragment : Fragment() {

    private lateinit var nameWine: EditText
    private lateinit var yearWine: EditText
    private lateinit var manufacturerWine: EditText
    private lateinit var typeWine: EditText
    private lateinit var alcoholWine: EditText
    private lateinit var sugarInWine: EditText
    private lateinit var compositionWine: EditText
    private lateinit var buttonAddWine: Button
    private lateinit var buttonAddPhoto: Button
    private lateinit var navController: NavController
    private lateinit var photoWine: ImageView
    private lateinit var dao: WineDatabase
    private var photoFile: File? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        setHasOptionsMenu(true)
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wine_add, container, false)

        nameWine = root.findViewById(R.id.addNameWineInAddLayout)
        yearWine = root.findViewById(R.id.addYearWineInAddLayout)
        manufacturerWine = root.findViewById(R.id.addNameManufacturerWineInAddLayout)
        typeWine = root.findViewById(R.id.addTypeWineInAddLayout)
        alcoholWine = root.findViewById(R.id.addAlcoholWineInAddLayout)
        sugarInWine = root.findViewById(R.id.addSugarWineInAddLayout)
        compositionWine = root.findViewById(R.id.addCompositionWineInAddLayout)
        buttonAddWine = root.findViewById(R.id.buttonAddWineInList)
        buttonAddPhoto = root.findViewById(R.id.buttonAddPhoto)
        photoWine = root.findViewById(R.id.viewPhotoWineInAddLayout)

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
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePictureIntent, 1)
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
            manufacturer = manufacturerWine.text.toString(),
            type = typeWine.text.toString(),
            alcohol = alcoholWine.text.toString(),
            sugar = sugarInWine.text.toString(),
            composition = compositionWine.text.toString(),
            coordinateFirst = 50.0,
            coordinateSecond = 50.0,
            wineImage = photoFile?.path
        )

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_add_wine, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoWine.setImageURI(photoFile?.path?.toUri())
    }
}