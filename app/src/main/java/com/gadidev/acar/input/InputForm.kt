package com.gadidev.acar.input

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gadidev.acar.databinding.FragmentInputFormBinding

class InputForm : Fragment() {
    private var _binding: FragmentInputFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputFormBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextBtn()
    }

    private fun nextBtn() {
        binding.btnNext.setOnClickListener {
            val title = binding.inputTitle.text.toString()
            val fullName = binding.inputFullName.text.toString()
            val nickname = binding.inputNickname.text.toString()
            val note = binding.inputNote.text.toString()
            val region = binding.inputRegion.text.toString()
            Log.d("Apa","${title},${fullName},${nickname},${note},${region}")
//            saveShare()
            val args = InputFormDirections.actionInputFormToResultFragment(title,fullName,nickname,note,region)
            findNavController().navigate(args)
//            findNavController().navigate(R.id.photoFragment)
        }
    }

}