package com.wanis.firebasetp3.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.wanis.firebasetp3.Database.FirebaseDB;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by munirwanis on 10/12/17.
 */

public class UserEntity {

    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String cellPhone;
    private String cpf;
    private String city;

    public UserEntity() {
    }

    public void saveToFirebase(){
        DatabaseReference dbRef = FirebaseDB.getInstance();
        dbRef.child("user").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String , Object > hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("password", getPassword());
        hashMapUsuario.put("name", getName());
        hashMapUsuario.put("phone", getPhone());
        hashMapUsuario.put("cellPhone", getCellPhone());
        hashMapUsuario.put("cpf", getCpf());
        hashMapUsuario.put("city", getCity());

        return hashMapUsuario;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}