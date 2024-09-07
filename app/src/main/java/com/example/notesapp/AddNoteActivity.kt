package com.example.notesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var myDatabaseHelper: MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDatabaseHelper=MyDatabase(this)
        binding.save.setOnClickListener {
            val title=binding.titeltext.text.toString()
            val desc=binding.desctext.text.toString()
            if(title.isEmpty()){
                Toast.makeText(this,"Title Not Added", Toast.LENGTH_SHORT).show()
            }else{
                val note=Note(0,title,desc)
                myDatabaseHelper.insertData(this,note)
                finish()
                Toast.makeText(this,"Note Added", Toast.LENGTH_SHORT).show()
            }

        }

    }
}