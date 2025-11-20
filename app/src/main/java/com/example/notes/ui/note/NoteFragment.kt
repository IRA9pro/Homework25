package com.example.notes.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.R
import com.example.notes.data.models.NoteColor
import com.example.notes.data.models.NoteModel
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.ui.App
import com.google.android.material.card.MaterialCardView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private val navArgument: NoteFragmentArgs by navArgs()

    companion object {
        var pickedColor = NoteColor.YELLOW
        lateinit var lastPicked: MaterialCardView
        var isOptionsOpened = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lastPicked = pickYellow

            navArgument.note?.let { it ->
                title.setText(it.title)
                text.setText(it.text)
                tvTime.text = it.time
                pickedColor = it.color
                pickedColor(pickedColor)

                btnDelete.setOnClickListener {
                    App.Companion.db.dao().deleteNote(navArgument.note!!)
                    findNavController().navigateUp()
                }
            }

            btnReady.setOnClickListener {
                val title = title.text.toString()
                val text = text.text.toString()
                val time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                val color = pickedColor

                if (navArgument.note == null)
                    App.Companion.db.dao()
                        .saveNote(NoteModel(title = title, text = text, color = color, time = time))
                else
                    App.Companion.db.dao()
                        .saveNote(
                            NoteModel(
                                title = title,
                                text = text,
                                color = color,
                                time = time,
                                id = navArgument.note!!.id
                            )
                        )
                findNavController().navigateUp()
            }
            btnBack.setOnClickListener { findNavController().navigateUp() }
            btnOptions.setOnClickListener {
                if (isOptionsOpened) {
                    options.visibility = View.GONE
                    btnOptions.setImageResource(R.drawable.img_color_pick_unselected)
                    isOptionsOpened = false
                } else {
                    options.visibility = View.VISIBLE
                    btnOptions.setImageResource(R.drawable.img_color_pick_selected)
                    isOptionsOpened = true
                }
            }

            pickYellow.setOnClickListener {
                onCLickPickColor(pickYellow); pickedColor = NoteColor.YELLOW
            }
            pickPurple.setOnClickListener {
                onCLickPickColor(pickPurple); pickedColor = NoteColor.PURPLE
            }
            pickPink.setOnClickListener { onCLickPickColor(pickPink); pickedColor = NoteColor.PINK }
            pickRed.setOnClickListener { onCLickPickColor(pickRed); pickedColor = NoteColor.RED }
            pickGreen.setOnClickListener {
                onCLickPickColor(pickGreen); pickedColor = NoteColor.GREEN
            }
            pickBlue.setOnClickListener { onCLickPickColor(pickBlue); pickedColor = NoteColor.BLUE }
        }

    }

    fun onCLickPickColor(picker: MaterialCardView) {
        lastPicked.strokeWidth = 0
        picker.strokeWidth = 10
        lastPicked = picker
    }

    fun pickedColor(noteColor: NoteColor) {
        binding.apply {
            when (noteColor) {
                NoteColor.YELLOW -> onCLickPickColor(pickYellow)
                NoteColor.PURPLE -> onCLickPickColor(pickPurple)
                NoteColor.PINK -> onCLickPickColor(pickPink)
                NoteColor.RED -> onCLickPickColor(pickRed)
                NoteColor.GREEN -> onCLickPickColor(pickGreen)
                NoteColor.BLUE -> onCLickPickColor(pickBlue)
            }
        }
    }
}