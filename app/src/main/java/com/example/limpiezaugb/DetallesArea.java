package com.example.limpiezaugb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.limpiezaugb.R;

public class DetallesArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_area);

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverToMainActivity();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String nombre = intent.getStringExtra("nombre");
            String zona = intent.getStringExtra("zona");
            String estado = intent.getStringExtra("estado");

            // Asignar los datos a las vistas en la tarjeta
            TextView txtNombre = findViewById(R.id.txtNombreDetalle);
            TextView txtZona = findViewById(R.id.txtZonaDetalle);
            TextView txtEstado = findViewById(R.id.txtEstadoDetalle);

            txtNombre.setText(nombre);
            txtZona.setText(zona);
            txtEstado.setText(estado);
        }

    }
    public void volverToMainActivity() {
        Intent intent = new Intent(this, Asignacioness.class);
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }
}