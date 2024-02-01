package com.breno.evernotekt.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.breno.evernotekt.R
import com.breno.evernotekt.data.model.Note
import com.breno.evernotekt.databinding.ListItemNoteBinding


class NoteAdapter(private val notes: List<Note>, private val onClickListener: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteView =
        NoteView(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_note,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NoteView, position: Int) {
        holder.binding.note = notes[position]
        holder.binding.root.setOnClickListener {
            onClickListener.invoke(notes[position])
        }
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteView constructor(val binding: ListItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)
}
