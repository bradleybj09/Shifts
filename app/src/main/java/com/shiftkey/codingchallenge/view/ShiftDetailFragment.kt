package com.shiftkey.codingchallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.shiftkey.codingchallenge.viewModel.ShiftDetailViewModel
import com.shiftkey.codingchallenge.databinding.FragmentShiftDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShiftDetailFragment : Fragment() {

    private val args: ShiftDetailFragmentArgs by navArgs()

    @Inject
    lateinit var factory: ShiftDetailViewModel.AssistedFactory
    private val viewModel: ShiftDetailViewModel by viewModels {
        ShiftDetailViewModel.createFactory(
            factory,
            args.shiftId
        )
    }

    private lateinit var binding: FragmentShiftDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShiftDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}