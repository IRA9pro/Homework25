package com.example.notes.ui.main

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.data.models.NoteModel
import com.example.notes.databinding.FragmentMainBinding
import com.example.notes.ui.App

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

        adapter = NoteAdapter(App.db.dao().getAllNotes(), ::onModelClick)
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
        }
    }

    private fun onModelClick(noteModel: NoteModel) {
        val navigate = MainFragmentDirections.actionMainFragmentToNoteFragment(noteModel)
        findNavController().navigate(navigate)
    }
}