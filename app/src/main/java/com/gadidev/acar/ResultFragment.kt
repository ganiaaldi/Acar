package com.gadidev.acar

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadidev.acar.adapter.CardAdapter
import com.gadidev.acar.adapter.ColorAdapter
import com.gadidev.acar.databinding.FragmentResultBinding
import com.gadidev.acar.model.Card
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private var itemSelected : String = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        binding.btnSave.setOnClickListener {
            val imageBitmap = getBitmapFromView(binding.layoutResult)
            binding.imgResult.setImageBitmap(imageBitmap)
            val drawable =  binding.imgResult.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            if(bitmap!=null){
                val uri = activity?.let { it1 -> bitmap.saveImage(it1.applicationContext) }
                Toast.makeText(context, "Image saved in a file $uri.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Image not found.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun setView() {
        binding.rvCard.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            adapter = CardAdapter(cardData)

            (adapter as CardAdapter).setOnItemClickCallback(object : CardAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Card) {
                    showSelectedMain(data)
                }
            })
        }

        binding.rvColor.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            adapter = ColorAdapter(colorData)

            (adapter as ColorAdapter).setOnItemClickCallback(object : ColorAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Card) {
                    showSelectedColor(data)
                }
            })
        }

        }

    private fun showSelectedColor(data: Card) {
                when(data.name)
                {
                    "Black" -> binding.layoutResult.setBackgroundResource(R.drawable.button_black)
                    "Red" -> binding.layoutResult.setBackgroundResource(R.drawable.button_pink)
                    "Green" -> binding.layoutResult.setBackgroundResource(R.drawable.button_green)
                    "Blue" -> binding.layoutResult.setBackgroundResource(R.drawable.button_blue)
                }
    }

    private fun showSelectedMain(data: Card) {
        when (data.name){
            "Casual" -> {
                binding.frameCasual.visibility = View.VISIBLE
                binding.frameSimple.visibility = View.GONE
                binding.frameSakura.visibility = View.GONE
                binding.frameBubble.visibility = View.GONE
                binding.frameSquare.visibility = View.GONE
                itemSelected = "Casual"
                        }
            "Simple" -> {
                binding.frameCasual.visibility = View.GONE
                binding.frameSimple.visibility = View.VISIBLE
                binding.frameSakura.visibility = View.GONE
                binding.frameBubble.visibility = View.GONE
                binding.frameSquare.visibility = View.GONE
                itemSelected = "Simple"
            }
            "Sakura" -> {
                binding.frameCasual.visibility = View.GONE
                binding.frameSimple.visibility = View.GONE
                binding.frameSakura.visibility = View.VISIBLE
                binding.frameBubble.visibility = View.GONE
                binding.frameSquare.visibility = View.GONE
                itemSelected = "Sakura"
            }
            "Bubble" -> {
                binding.frameCasual.visibility = View.GONE
                binding.frameSimple.visibility = View.GONE
                binding.frameSakura.visibility = View.GONE
                binding.frameBubble.visibility = View.VISIBLE
                binding.frameSquare.visibility = View.GONE
                itemSelected = "Bubble"
            }
            "Square" -> {
                binding.frameCasual.visibility = View.GONE
                binding.frameSimple.visibility = View.GONE
                binding.frameSakura.visibility = View.GONE
                binding.frameBubble.visibility = View.GONE
                binding.frameSquare.visibility = View.VISIBLE
                itemSelected = "Square"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun getBitmapFromView(view: View): Bitmap {
        //Define a bitmap with the same size as the view
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable = view.background
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.BLACK)
        }
        // draw the view on the canvas
        view.draw(canvas)
        //return the bitmap
        return returnedBitmap
    }


    fun Bitmap.saveImage(context: Context): Uri? {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Cardid/")
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "img_${SystemClock.uptimeMillis()}")

            activity?.intent!!.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val uri: Uri? =
                    context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(this, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
                return uri
            }
        } else {
            val directory =
                    File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName =  "img_${SystemClock.uptimeMillis()}"+ ".png"
            val file = File(directory, fileName)
            saveImageToStream(this, FileOutputStream(file))
            if (file.absolutePath != null) {
                val values = contentValuesOf()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                // .DATA is deprecated in API 29
                activity?.intent!!.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values)
                MediaScannerConnection.scanFile(
                        context, arrayOf(directory.toString()),
                        arrayOf(directory.getName()), null
                )
                return Uri.fromFile(directory)
            }
        }
        return null
    }


    fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
