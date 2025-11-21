package com.example.notes.ui.folder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.data.models.FolderModel
import com.example.notes.databinding.ItemFolderBinding

class FolderAdapter(val folderList: List<FolderModel>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FolderViewHolder {
        return FolderViewHolder(
            ItemFolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: FolderViewHolder,
        position: Int
    ) {
        holder.onBind(folderList[position])
    }

    override fun getItemCount() = folderList.size

    inner class FolderViewHolder(private val binding: ItemFolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(folderModel: FolderModel) {
            binding.apply {
                binding.tvFolderName.text = folderModel.name
            }
        }
    }
}