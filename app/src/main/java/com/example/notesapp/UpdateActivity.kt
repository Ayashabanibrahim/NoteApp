package com.example.notesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateBinding
    private lateinit var myDatabaseHelper: MyDatabase
    private var noteId=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDatabaseHelper=MyDatabase(this)

        noteId=intent.getIntExtra("note_id",-1)
        if(noteId==-1){
            finish()
            return
        }
        val note=myDatabaseHelper.getNoteById(noteId)
        binding.updatetiteltext.setText(note.title)
       binding.updatedesctext.setText(note.description)

        binding.update.setOnClickListener {
            val newTitle=binding.updatetiteltext.text.toString()
            val newDesc=binding.updatedesctext.text.toString()
            val updatedNote=Note(noteId,newTitle,newDesc)
            if(newTitle.isEmpty()){
                Toast.makeText(this,"Title Is Empty!", Toast.LENGTH_SHORT).show()
            }else {
                myDatabaseHelper.updateData(updatedNote)
                finish()
                Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            }
        }


    }
}