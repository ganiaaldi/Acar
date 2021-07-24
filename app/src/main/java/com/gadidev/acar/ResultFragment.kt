package com.gadidev.acar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadidev.acar.adapter.CardAdapter
import com.gadidev.acar.adapter.ColorAdapter
import com.gadidev.acar.databinding.FragmentResultBinding
import com.gadidev.acar.model.Card

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
}
