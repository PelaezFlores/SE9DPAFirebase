package com.example.se9dpafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.compose.material3.Snackbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        val db = FirebaseFirestore.getInstance()
        val tvCurso: TextView = findViewById(R.id.tvCurso)
        val tvNota: TextView = findViewById(R.id.tvNota)
        db.collection("courses")
            .addSnapshotListener { snapshots, e->
                if(e!=null){
                    Snackbar.make(findViewById(android.R.id.content)
                            , "Ocurrio un error al obtener la coleccion"
                            ,Snackbar.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                for (dc in snapshots!!.documentChanges){
                    when(dc.type){
                        DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED->{
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                ,"Se agrego o modifico un documento"
                                ,Snackbar.LENGTH_LONG).show()
                            tvCurso.text = dc.document.data["descripcion"].toString()
                            tvNota.text = dc.document.data["score"].toString()

                        }
                        DocumentChange.Type.REMOVED -> {
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                ,"Se ilimino un documento"
                                ,Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

            }


    }
}