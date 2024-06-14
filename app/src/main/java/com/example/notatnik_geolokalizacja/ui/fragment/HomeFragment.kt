package com.example.notatnik_geolokalizacja.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notatnik_geolokalizacja.R
import com.example.notatnik_geolokalizacja.adapter.NoteAdapter
import com.example.notatnik_geolokalizacja.databinding.FragmentHomeBinding
import com.example.notatnik_geolokalizacja.viewmodel.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            binding.noteContainer.layoutManager = LinearLayoutManager(requireContext())
            binding.noteContainer.adapter = NoteAdapter(viewModel, notesList)
        }

        binding.addNote.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNoteFragment)
        }

        return binding.root
    }

}