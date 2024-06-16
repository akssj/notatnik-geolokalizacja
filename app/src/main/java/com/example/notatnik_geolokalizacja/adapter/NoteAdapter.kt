package com.example.notatnik_geolokalizacja.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notatnik_geolokalizacja.databinding.ItemNoteBinding
import com.example.notatnik_geolokalizacja.database.model.Notes
import com.example.notatnik_geolokalizacja.viewmodel.NotesViewModel

/**
 * Adapter class for displaying notes.
 * @param viewModel NotesViewModel handling the notes data.
 * @param notesList list of notes to be displayed.
 */
class NoteAdapter(val viewModel: NotesViewModel,
                  val notesList: List<Notes>) :

    RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {

    /**
     * ViewHolder class that holds the view for each note item.
     * @param binding
     */
    class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Creates new ViewHolder for a note item.
     * @param parent parent view.
     * @param viewType view type.
     * @return new NotesViewHolder object.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Binds the data to the view for a note item.
     * @param holder NotesViewHolder.
     * @param position position of the item.
     */
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val data = notesList[position]
        holder.binding.noteText.text = data.notes
        holder.binding.noteDate.text = data.date
        holder.binding.noteLocation.text = "Lat: ${data.latitude}, Lon: ${data.longitude}"

        holder.binding.root.setOnClickListener {
            viewModel.deleteNotes(data.id!!)
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount() = notesList.size
}