package com.example.notatnik_geolokalizacja.ui.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.notatnik_geolokalizacja.R
import com.example.notatnik_geolokalizacja.databinding.FragmentCreateNoteBinding
import com.example.notatnik_geolokalizacja.database.model.Notes
import com.example.notatnik_geolokalizacja.viewmodel.NotesViewModel
import java.util.Date

class CreateNoteFragment : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)

        binding.saveNote.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(view: View) {
        val notes = binding.editTextNotes.text.toString()
        val currentDate = DateFormat.format("dd MM yyyy", Date()).toString()

        val data = Notes(
            null,
            notes = notes,
            date = currentDate
        )

        viewModel.addNotes(data)

        Navigation.findNavController(view).navigate(R.id.action_createNoteFragment_to_homeFragment)
    }
}
