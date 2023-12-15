package com.example.limpiezaugb;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AsignacionAreas extends AppCompatActivity {
    TextView mTxtViewData;
    private int horaSeleccionada;
    private int minutoSeleccionado;
    Spinner mSpnUsers, zona;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ugblimpieza-default-rtdb.firebaseio.com/");

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_areas);

        mSpnUsers = findViewById(R.id.spnUsers);
        zona = findViewById(R.id.spinnerZona);
        Button btnSeleccionarHora = findViewById(R.id.btnSeleccionarHora);
        final Button btnAsignar = findViewById(R.id.btnAsignar);
        Button btnVolver = findViewById(R.id.btnVolver);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        loadUsers();

        btnSeleccionarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtiene la hora y los minutos actuales
                final Calendar c = Calendar.getInstance();
                int horaActual = c.get(Calendar.HOUR_OF_DAY);
                int minutoActual = c.get(Calendar.MINUTE);

                // Crea un diálogo para seleccionar la hora
                TimePickerDialog timePickerDialog = new TimePickerDialog(AsignacionAreas.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                horaSeleccionada = hourOfDay;
                                minutoSeleccionado = minute;

                                // Formatea la hora seleccionada
                                String horaFormateada = String.format(Locale.getDefault(), "%02d:%02d", horaSeleccionada, minutoSeleccionado);

                                // Muestra la hora en el TextView
                                TextView horaSeleccionada = findViewById(R.id.txtHoraSeleccionada);
                                horaSeleccionada.setText(horaFormateada);
                            }
                        }, horaActual, minutoActual, false);

                // Muestra el diálogo de selección de hora
                timePickerDialog.show();
            }
        });

        btnAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String horaTime = ((TextView) findViewById(R.id.txtHoraSeleccionada)).getText().toString();
                Users selectedUser = (Users) mSpnUsers.getSelectedItem(); // Mover la declaración aquí
                String selectedZona = zona.getSelectedItem().toString();

                if (horaTime.isEmpty()) {
                    Toast.makeText(AsignacionAreas.this, "Ingrese la hora de inicio", Toast.LENGTH_SHORT).show();
                } else {
                    final String nombreUsuario = selectedUser.getName();
                    final String usuario = selectedUser.getUser();

                    // Obtener la referencia a la base de datos
                    DatabaseReference areasRef = databaseReference.child("areas");

                    final String areaId = generateAreaId(usuario, selectedZona);

                    // Obtener la marca de tiempo actual
                    long currentTimeMillis = System.currentTimeMillis();

                    AreaAsignada areaAsignada = new AreaAsignada(nombreUsuario, selectedZona, horaTime, currentTimeMillis);
                    areaAsignada.setEstado("Sin comenzar");

                    // Guardar el objeto en la base de datos
                    areasRef.child(areaId).setValue(areaAsignada);

                    areasRef.child(areaId).child("usuario").setValue(usuario);

                    Toast.makeText(AsignacionAreas.this, "Área asignada con éxito", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AsignacionAreas.this, Asignacioness.class));
                    finish();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un intent para abrir MainActivity
                Intent intent = new Intent(AsignacionAreas.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual y vuelve a MainActivity
            }
        });
    }

    // Función para generar un ID único para el área
    private String generateAreaId(String nombreUsuario, String zona) {
        // Utilizar el nombre de usuario, zona y la hora actual como ID
        return nombreUsuario + "_" + zona + "_" + System.currentTimeMillis();
    }

    public void loadUsers(){
        final List<Users> users = new ArrayList<>();
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        String user = ds.getKey();
                        String name = ds.child("name").getValue().toString();
                        users.add(new Users(user, name));
                    }

                    ArrayAdapter<Users> arrayAdapter = new ArrayAdapter<>(AsignacionAreas.this, android.R.layout.simple_dropdown_item_1line, users);
                    mSpnUsers.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}