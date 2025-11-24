package com.example.notes.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
import com.bumptech.glide.Glide
import com.example.notes.ui.dialogDelete
import com.example.notes.ui.listener
import com.example.notes.ui.resetUX
import com.example.notes.ui.userUX
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private lateinit var adapter: NoteAdapter
    private var isRVViewGrid = false
    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userUX()

        userView()

        initializeRV()

        binding.apply {
            buttonsListener()
            search()
        }
    }

    private fun initializeRV() {
        adapter = NoteAdapter(App.db.dao().getAllNotes(), ::onModelClick, ::onLongModelClick)
        binding.rvNotesList.adapter = adapter
    }

    private fun userView() {
        val user = auth.currentUser

        binding.tvUserName.text = user?.displayName
        Glide.with(binding.root).load(user?.photoUrl).into(binding.tvUserPhoto)

        userTap()
    }

    private fun userTap() {
        binding.tvUser.setOnLongClickListener {
            dialogUser()
            true
        }
    }

    private fun dialogUser() {
        val user = auth.currentUser!!
        val dialogView = layoutInflater.inflate(R.layout.dialog_user, null)

        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog.show()

        dialogView.findViewById<TextView>(R.id.tv_userEmail).text = user.email
        dialogView.findViewById<TextView>(R.id.tv_userName).text = user.displayName
        Glide.with(binding.root).load(user.photoUrl)
            .into(dialogView.findViewById(R.id.tv_userPhoto))

        dialogView.findViewById<TextView>(R.id.btn_logOut).setOnClickListener {
            resetUX()
            dialog.dismiss()
            true
        }
        dialogView.findViewById<ImageView>(R.id.btn_close).setOnClickListener { dialog.dismiss() }
    }

    private fun FragmentMainBinding.search() {
        etSearch.listener({
            val listNotes = App.db.dao().search(binding.etSearch.text.toString())
            adapter = NoteAdapter(listNotes, ::onModelClick, ::onLongModelClick)
            binding.rvNotesList.adapter = adapter
        })
    }

    private fun FragmentMainBinding.buttonsListener() {
        btnAddNote.setOnClickListener {
            val navigate = MainFragmentDirections.actionMainFragmentToNoteFragment(null)
            findNavController().navigate(navigate)
        }

        btnShape.setOnClickListener {
            changeManager()
        }

        btnMenu.setOnClickListener {
            findNavController().navigate(R.id.folderFragment)
        }
    }

    private fun FragmentMainBinding.changeManager() {
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

    private fun onModelClick(noteModel: NoteModel) {
        val navigate = MainFragmentDirections.actionMainFragmentToNoteFragment(noteModel)
        findNavController().navigate(navigate)
    }

    private fun onLongModelClick(noteModel: NoteModel) {
        dialogDelete {
            App.db.dao().deleteNote(noteModel)
            adapter = NoteAdapter(App.db.dao().getAllNotes(), ::onModelClick, ::onLongModelClick)
            binding.rvNotesList.adapter = adapter
        }
    }
}