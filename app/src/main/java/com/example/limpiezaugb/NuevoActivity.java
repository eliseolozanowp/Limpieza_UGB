package com.example.limpiezaugb;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NuevoActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ugblimpieza-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        final EditText name = findViewById(R.id.txtNombre);
        final EditText email = findViewById(R.id.txtCorreo);
        final EditText phone = findViewById(R.id.telefono);
        final EditText user = findViewById(R.id.txtUsuario);
        final EditText password = findViewById(R.id.txtContraseña);
        final EditText conPassword = findViewById(R.id.txtRepContraseña);

        final Button btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                final String nameTxt = name.getText().toString();
                final String emailTxt = email.getText().toString();
                final String phoneTxt = phone.getText().toString();
                final String userTxt = user.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                if (nameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || userTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(NuevoActivity.this, "Por favor ingrese los datos", Toast.LENGTH_SHORT).show();
                }

                else if (!passwordTxt.equals((conPasswordTxt))){
                    Toast.makeText(NuevoActivity.this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(userTxt)){
                                Toast.makeText(NuevoActivity.this, "Usuario existente", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Crear un objeto que represente los datos del nuevo usuario
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("name", nameTxt);
                                userData.put("email", emailTxt);
                                userData.put("phone", phoneTxt);
                                userData.put("password", passwordTxt);
                                userData.put("isAdmin", false); // Establecer isAdmin como false por defecto

                                // Guardar los datos del usuario en la base de datos
                                databaseReference.child("users").child(userTxt).setValue(userData);

                                Toast.makeText(NuevoActivity.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_inicio) {
                    startActivity(new Intent(NuevoActivity.this, MainActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_asignacion) {
                    startActivity(new Intent(NuevoActivity.this, AsignacionAreas.class));
                    return true;
                } else if (itemId == R.id.navigation_empleados) {
                    startActivity(new Intent(NuevoActivity.this, NuevoActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_logout) {
                    // Cerrar Sesión
                    FirebaseAuth.getInstance().signOut(); // Cierra la sesión actual
                    startActivity(new Intent(NuevoActivity.this, Login.class)); // Redirige a la pantalla de inicio de sesión
                    finish(); // Cierra la actividad actual
                    return true;
                }
                return false;
            }
        });
    }

}