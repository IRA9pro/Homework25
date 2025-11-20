package com.example.notes.ui.user_confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.databinding.FragmentDeleteBinding

class DeleteFragment : Fragment() {

    private lateinit var binding: FragmentDeleteBinding
//    private val navArgument: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.apply {
//            btnDelete.setOnClickListener { App.db.dao().deleteNote(navArgument.note!!) }
//            btnCancel.setOnClickListener { findNavController().navigateUp() }
//        }
    }
}