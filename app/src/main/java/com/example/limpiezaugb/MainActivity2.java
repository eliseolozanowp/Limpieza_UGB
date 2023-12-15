package com.example.limpiezaugb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Ver asignaciones del día
        findViewById(R.id.btnAsignaciones).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, EmpleadosActivity.class);
                startActivity(intent);
            }
        });

        // Ver historial
        findViewById(R.id.btnHistorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, HistorialEmpleado.class);
                startActivity(intent);
            }
        });
    }
}