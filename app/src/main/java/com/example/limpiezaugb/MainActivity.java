package com.example.limpiezaugb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.limpiezaugb.AsignacionEmpleado;
import com.example.limpiezaugb.EmpleadosActivity;
import com.example.limpiezaugb.Asignacioness;
import com.example.limpiezaugb.Historial;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón para Asignar
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AsignacionAreas.class);
                startActivity(intent);
            }
        });

        // Botón para Asignaciones
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Asignacioness.class);
                startActivity(intent);
            }
        });

        // Botón para Historial
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Historial.class);
                startActivity(intent);
            }
        });
    }

}