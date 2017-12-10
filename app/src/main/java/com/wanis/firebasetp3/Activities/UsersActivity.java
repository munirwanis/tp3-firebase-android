package com.wanis.firebasetp3.Activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wanis.firebasetp3.Adapters.UserAdapter;
import com.wanis.firebasetp3.Database.FirebaseDB;
import com.wanis.firebasetp3.Entities.UserEntity;
import com.wanis.firebasetp3.R;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    private ArrayAdapter<UserEntity> adapter;
    private ArrayList<UserEntity> users;
    private DatabaseReference dbRef;
    private ValueEventListener usersValueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        users = new ArrayList<>();

        ListView usersListView = findViewById(R.id.listViewUsuarios);
        adapter = new UserAdapter(this, users);

        usersListView.setAdapter(adapter);

        dbRef = FirebaseDB.getInstance().child("user");


        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserEntity userEntity = (UserEntity) parent.getItemAtPosition(position);

                AlertDialog.Builder adb = new AlertDialog.Builder(UsersActivity.this);
                adb.setTitle(userEntity.getName());

                StringBuilder sb = new StringBuilder();
                sb.append("E-mail: ").append(userEntity.getEmail());
                sb.append("\nSenha: ").append(userEntity.getPassword());
                sb.append("\nTelefone: ").append(userEntity.getPhone());
                sb.append("\nCelular: ").append(userEntity.getCellPhone());
                sb.append("\nCPF: ").append(userEntity.getCpf());
                sb.append("\nCidade: ").append(userEntity.getCity());

                adb.setMessage(sb.toString());
                adb.show();
            }
        });


        usersValueEventListener = dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    UserEntity user = data.getValue(UserEntity.class);
                    users.add(user);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        dbRef.removeEventListener(usersValueEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbRef.addValueEventListener(usersValueEventListener);
    }
}
