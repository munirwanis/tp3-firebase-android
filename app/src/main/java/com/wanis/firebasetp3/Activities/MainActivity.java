package com.wanis.firebasetp3.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wanis.firebasetp3.Database.FirebaseDB;
import com.wanis.firebasetp3.Entities.UserEntity;
import com.wanis.firebasetp3.R;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button usersBtn;
    private FirebaseAuth firebaseAuth;
    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseDB.getAuth();
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etSenha);
        usersBtn = findViewById(R.id.btnUsuarios);

        TextView registrationTextView = findViewById(R.id.tvCadastro);
        Button loginBtn = findViewById(R.id.btnLogin);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("")) {
                    userEntity = new UserEntity();
                    userEntity.setEmail(emailEditText.getText().toString());
                    userEntity.setPassword(passwordEditText.getText().toString());
                    validateLogin();
                } else {
                    Toast.makeText(MainActivity.this, "Preencha os campos do e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegistrationActivity();
            }
        });


        usersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUsersActivity();
            }
        });

    }

    private void validateLogin() {
        firebaseAuth = FirebaseDB.getAuth();
        firebaseAuth.signInWithEmailAndPassword(userEntity.getEmail(), userEntity.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    toLoggedActivity();
                    Toast.makeText(MainActivity.this, "Login Efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Digite o Login e Senha corretamente!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void toLoggedActivity() {
        Intent intent = new Intent(MainActivity.this, LoggedActivity.class);
        startActivity(intent);
    }

    public void toRegistrationActivity() {
        Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(i);
    }

    public void toUsersActivity() {
        firebaseAuth.signOut();
        Intent i = new Intent(MainActivity.this, UsersActivity.class);
        startActivity(i);
    }
}
