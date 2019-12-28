package com.example.madfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginUser extends AppCompatActivity {

    EditText txtEmail,txtPassword;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPasswrod);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void Login(View v){
        final String Email = txtEmail.getText().toString();
        String Password = txtPassword.getText().toString();

        if(TextUtils.isEmpty(Password) || TextUtils.isEmpty(Email)){
            Toast.makeText(this, "All fields are required!",
                    Toast.LENGTH_LONG).show();
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Intent intent = new Intent(LoginUser.this,MainActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginUser.this, "Invalid Login!",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }

    }

    public void Register(View v){
        Intent intent = new Intent(getBaseContext(), RegisterUser.class);
        startActivity(intent);
    }

}
