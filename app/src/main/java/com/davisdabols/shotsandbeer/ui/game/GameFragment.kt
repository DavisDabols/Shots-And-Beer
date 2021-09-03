package com.davisdabols.shotsandbeer.ui.game

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.davisdabols.shotsandbeer.R
import com.davisdabols.shotsandbeer.common.launchMain
import com.davisdabols.shotsandbeer.common.openFragment
import com.davisdabols.shotsandbeer.databinding.FragmentGameBinding
import com.davisdabols.shotsandbeer.ui.GameViewModel
import kotlinx.coroutines.flow.collect

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val viewModel by activityViewModels<GameViewModel>()

    private val adapter by lazy { GameAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameGrid.adapter = adapter
        binding.gameGrid.layoutManager = GridLayoutManager(requireContext(), 4)

        viewModel.startGame()

        binding.submitInput.setOnClickListener {
            viewModel.checkInput(binding.numberInput.text.toString())
            binding.numberInput.text.clear()
        }

        launchMain {
            viewModel.guesses.collect { guesses ->
                adapter.guesses = guesses
            }
        }

        launchMain {
            viewModel.onGameOver.collect { time ->
                if (time == null) return@collect
                AlertDialog.Builder(context)
                    .setMessage(getString(R.string.game_over_template, time))
                    .setPositiveButton("Ok") { popup, _ ->
                        popup.dismiss()
                        openFragment(R.id.navigation_menu)
                    }
                    .setCancelable(false)
                    .show()
            }
        }
    }

}