package com.shiftkey.codingchallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shiftkey.codingchallenge.viewModel.ViewModelFactory
import com.shiftkey.codingchallenge.databinding.FragmentShiftListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShiftListFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory<ShiftListViewModel>
    private val viewModel: ShiftListViewModel by viewModels { factory }

    private lateinit var binding: FragmentShiftListBinding
    private val adapter = ShiftAdapter()

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy != 0 && !recyclerView.canScrollVertically(1)) {
                viewModel.fetchAdditionalShifts()
            }
            super.onScrolled(recyclerView, dx, dy)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShiftListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.shiftRecyclerView.addOnScrollListener(scrollListener)
        binding.shiftRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        adapter.setClickListener(viewModel)
        binding.shiftRecyclerView.adapter = adapter
        return binding.root
    }

    /**
     * this will save our list position
     */
    override fun onDestroyView() {
        viewModel.listState = binding.shiftRecyclerView.layoutManager?.onSaveInstanceState()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.shiftRecyclerView.layoutManager?.onRestoreInstanceState(viewModel.listState)

        viewModel.shifts.observe(viewLifecycleOwner) { shifts ->
            val state = binding.shiftRecyclerView.layoutManager?.onSaveInstanceState()
            adapter.updateShifts(shifts)
            binding.shiftRecyclerView.layoutManager?.onRestoreInstanceState(state)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}