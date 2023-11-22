package com.example.limpiezaugb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.limpiezaugb.adaptadores.MainAdapter;
import com.example.limpiezaugb.models.MainModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class EmpleadosActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        recyclerview = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("areas").orderByChild("createdAt"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerview.setAdapter(mainAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_inicio) {
                    startActivity(new Intent(EmpleadosActivity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_logout) {
                    // Cerrar Sesión
                    FirebaseAuth.getInstance().signOut(); // Cierra la sesión actual
                    startActivity(new Intent(EmpleadosActivity.this, Login.class)); // Redirige a la pantalla de inicio de sesión
                    finish(); // Cierra la actividad actual
                    return true;
                }
                return false;
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