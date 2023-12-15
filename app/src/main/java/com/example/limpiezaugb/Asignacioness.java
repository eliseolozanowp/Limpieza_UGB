package com.example.limpiezaugb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.limpiezaugb.adaptadores.MainAdapter;
import com.example.limpiezaugb.models.MainModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Asignacioness extends AppCompatActivity {
    RecyclerView recyclerview;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacioness);

        Button btnVolver = findViewById(R.id.btnVolver);

        recyclerview = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("areas"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerview.setAdapter(mainAdapter);

        // Obtén la fecha actual en milisegundos
        long currentTimeMillis = System.currentTimeMillis();

        // Itera a través de las asignaciones en el adaptador
        for (int i = 0; i < mainAdapter.getItemCount(); i++) {
            MainModel asignacion = mainAdapter.getItem(i);

            // Obtén la fecha de la asignación en milisegundos (puedes almacenarla en tu modelo)
            long fechaAsignacionMillis = asignacion.getFechaAsignacionMillis();

            // Calcula la diferencia en días entre la fecha actual y la fecha de la asignación
            long diferenciaDias = (currentTimeMillis - fechaAsignacionMillis) / (24 * 60 * 60 * 1000);

            // Define la cantidad de días que quieres considerar antes de eliminar la asignación
            int diasMaximos = 1; // Por ejemplo, eliminar después de 1 día

            if (diferenciaDias >= diasMaximos) {
                // Elimina la asignación de la vista
                mainAdapter.deleteItem(i);
            }
        }


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un intent para abrir MainActivity
                Intent intent = new Intent(Asignacioness.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual y vuelve a MainActivity
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}