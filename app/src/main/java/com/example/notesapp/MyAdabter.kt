package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdabter (var listNote:ArrayList<Note>,private val context:Context )
    : RecyclerView.Adapter<MyAdabter.ViewHolder>() {
        private val dp:MyDatabase=MyDatabase(context)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleItem = view.findViewById<TextView>(R.id.title)
        val descItem = view.findViewById<TextView>(R.id.desc)
        val updareItem=view.findViewById<ImageView>(R.id.editbtn)
        val deleteItem=view.findViewById<ImageView>(R.id.deletebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val note=listNote[position]
        holder.titleItem.text=note.title
        holder.descItem.text=note.description
        holder.updareItem.setOnClickListener {

            val intent=Intent(holder.itemView.context,UpdateActivity::class.java).apply {
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)

        }
        holder.deleteItem.setOnClickListener {
            dp.deleteData(note.id)
            refreshData(dp.readAllData(context))
            Toast.makeText(holder.itemView.context,"Note Deleted", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(notes: ArrayList<Note>) {
        listNote = notes
        notifyDataSetChanged()
    }
}


