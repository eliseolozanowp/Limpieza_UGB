package com.example.limpiezaugb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.limpiezaugb.adaptadores.Main2Adapter;
import com.example.limpiezaugb.adaptadores.MainAdapter;
import com.example.limpiezaugb.models.MainModel;
import com.example.limpiezaugb.models.Model2;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class EmpleadosActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    Main2Adapter main2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);
        String usuarioActual = obtenerUsuarioActual();

        Button btnVolver = findViewById(R.id.btnVolver);


        recyclerview = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(layoutManager);

        // Coloca el primer registro de depuración antes de la consulta
        Log.d("ConsultaFirebase", "Consulta a Firebase para usuario: " + usuarioActual);

        FirebaseRecyclerOptions<Model2> options =
                new FirebaseRecyclerOptions.Builder<Model2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("areas").orderByChild("usuario").equalTo(usuarioActual), Model2.class)
                        .build();

        // Coloca el segundo registro de depuración después de obtener las opciones de FirebaseRecyclerOptions
        Log.d("OpcionesFirebase", "Opciones de FirebaseRecyclerOptions: " + options.toString());

        main2Adapter = new Main2Adapter(options);
        recyclerview.setAdapter(main2Adapter);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un intent para abrir MainActivity
                Intent intent = new Intent(EmpleadosActivity.this, MainActivity2.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual y vuelve a MainActivity
            }
        });

    }
    // Método ficticio para obtener el nombre de usuario del usuario actual
    private String obtenerUsuarioActual() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("usuario_actual", ""); // Devuelve el nombre de usuario actual
    }

    @Override
    protected void onStart() {
        super.onStart();
        main2Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        main2Adapter.stopListening();
    }
}