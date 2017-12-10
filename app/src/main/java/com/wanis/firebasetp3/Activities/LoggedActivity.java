package com.wanis.firebasetp3.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.wanis.firebasetp3.Database.FirebaseDB;
import com.wanis.firebasetp3.R;

public class LoggedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        firebaseAuth = FirebaseDB.getAuth();
        logoutBtn = findViewById(R.id.btnSair);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut(){
        firebaseAuth.signOut();
        Intent i = new Intent (LoggedActivity.this , MainActivity.class);
        startActivity(i);
        finish();
    }
}
