package com.shiftkey.codingchallenge.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shiftkey.codingchallenge.model.Shift
import com.shiftkey.codingchallenge.databinding.ItemShiftBinding

class ShiftAdapter : RecyclerView.Adapter<ShiftViewHolder>() {

    private var shifts = emptyList<Shift>()
    private lateinit var listener: ShiftListener

    fun updateShifts(newShifts: List<Shift>) {
        val result = DiffUtil.calculateDiff(ShiftDiffCallback(shifts, newShifts))
        result.dispatchUpdatesTo(this)
        shifts = newShifts
    }

    fun setClickListener(listener: ShiftListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        val binding = ItemShiftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        holder.bind(shifts[position], listener)
    }

    override fun getItemCount() = shifts.size
}

class ShiftViewHolder(private val binding: ItemShiftBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(shift: Shift, listener: ShiftListener) {
        binding.shift = shift
        binding.listener = listener
        binding.executePendingBindings()
    }
}

class ShiftDiffCallback(
    private val oldList: List<Shift>,
    private val newList: List<Shift>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].shiftId == newList[newItemPosition].shiftId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

interface ShiftListener {
    fun onClick(shift: Shift)
}