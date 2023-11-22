    package com.example.limpiezaugb;

    import androidx.annotation.NonNull;
    import androidx.annotation.RequiresApi;
    import androidx.appcompat.app.AppCompatActivity;

    import android.os.Build;
    import android.os.Bundle;
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

    import java.security.MessageDigest;
    import java.security.NoSuchAlgorithmException;
    import java.security.SecureRandom;
    import java.util.Base64;

    public class Register extends AppCompatActivity {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ugblimpieza-default-rtdb.firebaseio.com/");

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            final EditText name = findViewById(R.id.txtName);
            final EditText email = findViewById(R.id.txtEmail);
            final EditText user = findViewById(R.id.txtUser);
            final EditText password = findViewById(R.id.txtPassword);
            final EditText conPassword = findViewById(R.id.txtconPassword);

            final Button btnRegister = findViewById(R.id.btnRegister);
            final TextView loginNowBtn = findViewById(R.id.loginNowBtn);

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {

                    final String nameTxt = name.getText().toString();
                    final String emailTxt = email.getText().toString();
                    final String userTxt = user.getText().toString();
                    final String passwordTxt = password.getText().toString();
                    final String conPasswordTxt = conPassword.getText().toString();

                    if (nameTxt.isEmpty() || emailTxt.isEmpty() || userTxt.isEmpty() || passwordTxt.isEmpty()){
                        Toast.makeText(Register.this, "Por favor ingresa tus datos", Toast.LENGTH_SHORT).show();
                    }

                    else if (!passwordTxt.equals((conPasswordTxt))){
                        Toast.makeText(Register.this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 if (snapshot.hasChild(userTxt)){
                                     Toast.makeText(Register.this, "Usuario existente", Toast.LENGTH_SHORT).show();
                                 }
                                 else {
                                     databaseReference.child("users").child(userTxt).child("name").setValue(nameTxt);
                                     databaseReference.child("users").child(userTxt).child("email").setValue(emailTxt);
                                     databaseReference.child("users").child(userTxt).child("password").setValue(passwordTxt);

                                     Toast.makeText(Register.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
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

            loginNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }