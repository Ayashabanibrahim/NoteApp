package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.AddNoteActivity
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var adabter: MyAdabter
    private lateinit var myDatabaseHelper: MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDatabaseHelper=MyDatabase(this)
        adabter=MyAdabter(
            myDatabaseHelper.readAllData(this)
            ,this)
        binding.recycler.layoutManager=LinearLayoutManager(this)
        binding.recycler.adapter=adabter

        binding.addbtn.setOnClickListener {
            startActivity(Intent(this,AddNoteActivity::class.java))
        }




    }

    override fun onResume() {
        super.onResume()
       adabter.refreshData(
           myDatabaseHelper.readAllData(this)
       )
    }
}