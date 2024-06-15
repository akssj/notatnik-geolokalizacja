package com.example.notatnik_geolokalizacja.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notatnik_geolokalizacja.databinding.ItemNoteBinding
import com.example.notatnik_geolokalizacja.database.model.Notes
import com.example.notatnik_geolokalizacja.viewmodel.NotesViewModel

class NoteAdapter(val viewModel: NotesViewModel,
                  val notesList: List<Notes>) :

    RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {

    class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val data = notesList[position]
        holder.binding.noteText.text = data.notes
        holder.binding.noteDate.text = data.date
        holder.binding.noteLocation.text = "Lat: ${data.latitude}, Lon: ${data.longitude}"

        holder.binding.root.setOnClickListener {
            viewModel.deleteNotes(data.id!!)
        }
    }

    override fun getItemCount() = notesList.size
}