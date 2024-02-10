package com.smadu1.temperatureapp.menu.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smadu1.temperatureapp.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<HistoryModel, HistoryAdapter.ViewHolder>(diffCallBack) {

    private var itemClickListener: ((HistoryModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryModel, itemClickListener: ((HistoryModel) -> Unit)?) = with(binding) {
            tvDate.text = item.timeStamp
            tvPh.text = "PH: ${item.ph}"
            tvSuhu.text = "Suhu: ${item.suhu}"

            ivTrash.setOnClickListener { itemClickListener?.invoke(item) }
        }
    }

    fun updateData(newList: List<HistoryModel>) {
        val currentList = currentList.toMutableList()
        currentList.clear()
        currentList.addAll(newList)
        submitList(currentList)
    }

    fun setOnItemClickListener(listener: (HistoryModel) -> Unit) {
        itemClickListener = listener
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<HistoryModel>() {
            override fun areItemsTheSame(
                oldItem: HistoryModel,
                newItem: HistoryModel
            ): Boolean {
                return oldItem.timeStamp == newItem.timeStamp
            }

            override fun areContentsTheSame(
                oldItem: HistoryModel,
                newItem: HistoryModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
