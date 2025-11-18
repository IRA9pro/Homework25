package com.example.notes.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgument
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.models.NoteColor
import com.example.notes.data.models.NoteModel
import com.example.notes.databinding.ItemNoteBinding
import com.example.notes.ui.App
import org.w3c.dom.Text

class NoteAdapter(
    var noteList: List<NoteModel>,
    val onModelClick: (NoteModel) -> Unit,
    val onLongModelClick: (NoteModel) -> Unit
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        holder.onBind(noteList[position])
    }

    override fun getItemCount() = noteList.size

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(noteModel: NoteModel) {
            binding.apply {
                tvTitle.text = noteModel.title
                tvText.text = noteModel.text
                tvTime.text = noteModel.time

                val pickedColor = when (noteModel.color) {
                    NoteColor.YELLOW -> R.color.yellow_pick
                    NoteColor.PURPLE -> R.color.purple_pick
                    NoteColor.PINK -> R.color.pink_pick
                    NoteColor.RED -> R.color.red_pick
                    NoteColor.GREEN -> R.color.green_pick
                    NoteColor.BLUE -> R.color.blue_pick
                }

                box.setBackgroundColor(ContextCompat.getColor(itemView.context, pickedColor))
            }

            itemView.setOnClickListener { onModelClick(noteModel) }
            itemView.setOnLongClickListener {
                onLongModelClick(noteModel)
                true
            }
        }
    }
}