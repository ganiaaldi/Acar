package com.gadidev.acar.input

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.net.toFile
import androidx.navigation.fragment.findNavController
import com.gadidev.acar.R
import com.gadidev.acar.URIPathHelper
import com.gadidev.acar.databinding.FragmentPhotoBinding
import java.io.File


class PhotoFragment : Fragment() {
    private var _binding : FragmentPhotoBinding? = null
    private val binding get() = _binding!!
    var profilePicture : File? = null
    var logoPicture : File? = null
    var statusProfile : Boolean = false
    var statusLogo : Boolean = false


    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadImage()
        nextBtn()
    }

    private fun nextBtn() {
        binding.btnNextPhoto.setOnClickListener {
            findNavController().navigate(R.id.resultFragment)
        }
    }

    private fun uploadImage() {
        binding.uploadPhoto.setOnClickListener {
            binding.uploadPhoto.visibility = View.GONE
            binding.uploadPhotoDone.visibility = View.VISIBLE
            statusProfile = true
            statusLogo = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
        binding.uploadPhotoDone.setOnClickListener {
            binding.uploadPhoto.visibility = View.GONE
            binding.uploadPhotoDone.visibility = View.VISIBLE
            statusProfile = true
            statusLogo = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
        binding.uploadPhotoLogo.setOnClickListener {
            binding.uploadPhotoLogo.visibility = View.GONE
            binding.uploadPhotoLogoDone.visibility = View.VISIBLE
            statusProfile = false
            statusLogo = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
        binding.uploadPhotoLogoDone.setOnClickListener {
            binding.uploadPhotoLogo.visibility = View.GONE
            binding.uploadPhotoLogoDone.visibility = View.VISIBLE
            statusProfile = false
            statusLogo = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            // val imagePath = data!!.data.toString()
            //gambarDestinasi = File(imagePath)
            if (Build.VERSION.SDK_INT < 23) {
                if (statusProfile == true){
                    binding.uploadPhotoDone.setImageURI(data?.data)
                    profilePicture = data!!.data!!.toFile()
                }
               else{
                    binding.uploadPhotoLogoDone.setImageURI(data?.data)
                    logoPicture = data!!.data!!.toFile()
                }
            } else {
                //uploadPhotoDone.setImageURI(data!!.data!!)
                    if(statusProfile == true){
                        val uriPathHelper = URIPathHelper()
                        val filePath = uriPathHelper.getPath(requireContext(), data!!.data!!)
                        val uriProfile : Uri
                        uriProfile = Uri.parse(filePath)
                        binding.uploadPhotoDone.setImageURI(uriProfile)
                        // Toast.makeText(context, "$filePath", Toast.LENGTH_LONG).show()
                        profilePicture = File(filePath)
                    }
                else{
                        val uriPathHelper = URIPathHelper()
                        val filePath = uriPathHelper.getPath(requireContext(), data!!.data!!)
                        val uriLogo : Uri
                        uriLogo = Uri.parse(filePath)
                        binding.uploadPhotoLogoDone.setImageURI(uriLogo)
                        // Toast.makeText(context, "$filePath", Toast.LENGTH_LONG).show()
                        logoPicture = File(filePath)
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}