package com.example.limpiezaugb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AsignacionAreas extends AppCompatActivity {

    TextView mTxtViewData;
    Spinner mSpnUsers;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ugblimpieza-default-rtdb.firebaseio.com/");

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_areas);

        mSpnUsers = findViewById(R.id.spnUsers);
        final EditText zona = findViewById(R.id.txtZona);
        final EditText hora = findViewById(R.id.horaInicio);
        final Button btnAsignar = findViewById(R.id.btnAsignar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        loadUsers();

        btnAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String zonaTxt = zona.getText().toString();
                final String horaTime = hora.getText().toString();
                Users selectedUser = (Users) mSpnUsers.getSelectedItem(); // Mover la declaración aquí

                if (zonaTxt.isEmpty() || horaTime.isEmpty()) {
                    Toast.makeText(AsignacionAreas.this, "Ingrese datos", Toast.LENGTH_SHORT).show();
                } else {
                    final String nombreUsuario = selectedUser.getName();

                    // Obtener la referencia a la base de datos
                    DatabaseReference areasRef = databaseReference.child("areas");

                    final String areaId = generateAreaId(nombreUsuario, zonaTxt); // Pasar el usuario seleccionado

                    // Obtener la marca de tiempo actual
                    long currentTimeMillis = System.currentTimeMillis();

                    AreaAsignada areaAsignada = new AreaAsignada(nombreUsuario, zonaTxt, horaTime, currentTimeMillis);

                    // Guardar el objeto en la base de datos
                    areasRef.child(areaId).setValue(areaAsignada);

                    Toast.makeText(AsignacionAreas.this, "Área asignada con éxito", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AsignacionAreas.this, MainActivity.class));
                    finish();
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_inicio) {
                    startActivity(new Intent(AsignacionAreas.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_asignacion) {
                    startActivity(new Intent(AsignacionAreas.this, AsignacionAreas.class));
                    return true;
                } else if (itemId == R.id.navigation_empleados) {
                    startActivity(new Intent(AsignacionAreas.this, NuevoActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_logout) {
                    // Cerrar Sesión
                    FirebaseAuth.getInstance().signOut(); // Cierra la sesión actual
                    startActivity(new Intent(AsignacionAreas.this, Login.class)); // Redirige a la pantalla de inicio de sesión
                    finish(); // Cierra la actividad actual
                    return true;
                }
                return false;
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