package com.example.limpiezaugb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    String usuarioTxt;
    private

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ugblimpieza-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText user = findViewById(R.id.txtUser);
        final EditText password = findViewById(R.id.txtPassword);
        final Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userTxt = user.getText().toString();
                final String passwordTxt = password.getText().toString();

                if (userTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Login.this, "Por favor ingresa tus datos", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Verificar si el usuario existe en la base de datos
                            if (snapshot.hasChild(userTxt)){
                                DataSnapshot userDataSnapshot = snapshot.child(userTxt);
                                // Obtener el valor del campo "isAdmin"
                                boolean isAdmin = userDataSnapshot.child("isAdmin").getValue(Boolean.class);

                                // Verificar si es un administrador
                                if (isAdmin) {
                                    // Usuario es administrador, verificar la contraseña
                                    String getPassword = userDataSnapshot.child("password").getValue(String.class);

                                    if (getPassword.equals(passwordTxt)) {
                                        Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(Login.this, MainActivity.class)); // Redirigir a MainActivity para administradores
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Después de guardar el nombre de usuario en preferencias compartidas
                                    Log.d("UsuarioActual", "Valor del campo de entrada de usuario: " + user.getText().toString());
                                    usuarioTxt = user.getText().toString();

                                    guardarUsuarioActual(userTxt);

                                    Toast.makeText(Login.this, "Inicio de sesión exitoso como usuario normal", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity2.class)); // Redirigir a EmpleadoActivity para usuarios normales
                                    finish();
                                }
                            } else {
                                Toast.makeText(Login.this, "Usuario no existente", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    // Método para guardar el nombre de usuario en las preferencias compartidas
    private void guardarUsuarioActual(String usuario) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario_actual", usuario);
        editor.apply();
    }
}