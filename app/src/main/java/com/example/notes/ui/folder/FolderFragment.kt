package com.example.notes.ui.folder

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.data.models.FolderModel
import com.example.notes.databinding.FragmentFolderBinding
import com.example.notes.App
import com.example.notes.ui.main.NoteAdapter

class FolderFragment : Fragment() {

    private lateinit var binding: FragmentFolderBinding
    private lateinit var adapter: FolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
//            adapter = FolderAdapter(getFolderList())
//            rvFolderList.adapter = adapter

            btnAddFolder.setOnClickListener {
                newFolderDialog()
            }
        }
    }

    private fun newFolderDialog() {
        val dialogView = layoutInflater.inflate(R.layout.item_add_folder, null)

        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog.show()

        dialogView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }
    }

//    private fun getFolderList(): List<FolderModel> {
//        return
//    }

}