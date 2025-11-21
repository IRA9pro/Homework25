package com.example.notes.ui.main

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.data.models.NoteModel
import com.example.notes.databinding.FragmentMainBinding
import com.example.notes.App
import androidx.core.graphics.drawable.toDrawable

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private lateinit var adapter: NoteAdapter
    private var isRVViewGrid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteAdapter(App.db.dao().getAllNotes(), ::onModelClick, ::onLongModelClick)
        binding.rvNotesList.adapter = adapter

        binding.apply {
            btnAddNote.setOnClickListener {
                val navigate = MainFragmentDirections.actionMainFragmentToNoteFragment(null)
                findNavController().navigate(navigate)
            }

            btnShape.setOnClickListener {
                if (isRVViewGrid) {
                    rvNotesList.layoutManager = LinearLayoutManager(requireContext())
                    btnShape.setImageResource(R.drawable.img_view_grid)
                    isRVViewGrid = false
                } else {
                    rvNotesList.layoutManager = GridLayoutManager(requireContext(), 2)
                    btnShape.setImageResource(R.drawable.img_view_list)
                    isRVViewGrid = true
                }
            }

//            btnMenu.setOnClickListener {
//                val navigate = MainFragmentDirections.action_mainFragment_to_folderFragment()
//                findNavController().navigate(navigate)
//            }

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {
                }

                override fun onTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {
                    val listNotes = App.db.dao().search(binding.etSearch.text.toString())
                    adapter = NoteAdapter(listNotes, ::onModelClick, ::onLongModelClick)
                    binding.rvNotesList.adapter = adapter
                }

                override fun afterTextChanged(p0: Editable?) {}
            })


        }
    }

    private fun onModelClick(noteModel: NoteModel) {
        val navigate = MainFragmentDirections.actionMainFragmentToNoteFragment(noteModel)
        findNavController().navigate(navigate)
    }

    private fun onLongModelClick(noteModel: NoteModel) {

        val dialogView = layoutInflater.inflate(R.layout.item_delete, null)

        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog.show()

        dialogView.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            App.db.dao().deleteNote(noteModel)
            adapter = NoteAdapter(App.db.dao().getAllNotes(), ::onModelClick, ::onLongModelClick)
            binding.rvNotesList.adapter = adapter
            dialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }
    }
}