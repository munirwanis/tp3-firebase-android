package com.wanis.firebasetp3.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wanis.firebasetp3.Database.FirebaseDB;
import com.wanis.firebasetp3.Entities.UserEntity;
import com.wanis.firebasetp3.Helpers.Base64Extension;
import com.wanis.firebasetp3.Helpers.AndroidPreferences;
import com.wanis.firebasetp3.R;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements Validator.ValidationListener {

    Validator validator;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadNome;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadSenha;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadConfirmarSenha;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    @Email(message = "Digite um email valido")
    private EditText etCadEmail;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadTelefone;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadCelular;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadCpf;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadCidade;


    private Button btnSalvar, btLimpar;
    private UserEntity userEntity;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        validator = new Validator(this);
        validator.setValidationListener(this);


        etCadNome = (EditText) findViewById(R.id.etCadNome);
        etCadSenha = (EditText) findViewById(R.id.etCadSenha);
        etCadConfirmarSenha = (EditText) findViewById(R.id.etCadConfirmarSenha);
        etCadEmail = (EditText) findViewById(R.id.etCadEmail);
        etCadTelefone = (EditText) findViewById(R.id.etCadTelefone);
        etCadCelular = (EditText) findViewById(R.id.etCadCelular);
        etCadCpf = (EditText) findViewById(R.id.etCadCpf);
        etCadCidade = (EditText) findViewById(R.id.etCadCidade);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btLimpar = (Button) findViewById(R.id.btLimpar);


        //criando mascara para o cpf
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(etCadCpf, smf);
        etCadCpf.addTextChangedListener(mtw);

        //criando mascara para o telefone
        SimpleMaskFormatter sm = new SimpleMaskFormatter("(NN)NNNN-NNNN");
        MaskTextWatcher mt = new MaskTextWatcher(etCadTelefone, sm);
        etCadTelefone.addTextChangedListener(mt);

        //criando mascara para o celular
        SimpleMaskFormatter s = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher m = new MaskTextWatcher(etCadCelular, s);
        etCadCelular.addTextChangedListener(m);


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

            }
        });


        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //limpar os campos edit text
                etCadNome.setText("");
                etCadEmail.setText("");
                etCadSenha.setText("");
                etCadConfirmarSenha.setText("");
                etCadCelular.setText("");
                etCadTelefone.setText("");
                etCadCpf.setText("");
                etCadCidade.setText("");
            }
        });

    }


    public void abrirLoginUsuario() {

        Intent intent = new Intent(RegistrationActivity.this, LoggedActivity.class);
        startActivity(intent);
        finish();

    }


    private void cadastrarUsuario() {

        autenticacao = FirebaseDB.getAuth();
        autenticacao.createUserWithEmailAndPassword(
                userEntity.getEmail(),
                userEntity.getPassword()
        ).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Usuario Cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    String identificadorUsuario = Base64Extension.toBase64(userEntity.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    userEntity.setId(identificadorUsuario);
                    userEntity.saveToFirebase();

                    AndroidPreferences androidPreferences = new AndroidPreferences(RegistrationActivity.this);
                    androidPreferences.saveUserPreferences(identificadorUsuario, userEntity.getName());
                    abrirLoginUsuario();
                } else {

                    String erroExcecao = "";

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres entre letras e números";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O email digitado é inválido , digite um novo email.";

                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse email já está cadastrado no sistema";

                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(RegistrationActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onValidationSucceeded() {
        if (etCadSenha.getText().toString().equals(etCadConfirmarSenha.getText().toString())) {


            userEntity = new UserEntity();
            userEntity.setName(etCadNome.getText().toString());
            userEntity.setEmail(etCadEmail.getText().toString());
            userEntity.setPassword(etCadSenha.getText().toString());
            userEntity.setCellPhone(etCadCelular.getText().toString());
            userEntity.setPhone(etCadTelefone.getText().toString());
            userEntity.setCpf(etCadCpf.getText().toString());
            userEntity.setCity(etCadCidade.getText().toString());


            cadastrarUsuario();
        } else {
            Toast.makeText(RegistrationActivity.this, "As Senhas não são correspondentes", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {

                ((EditText) view).setError(message);

            } else {
                Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_LONG).show();
            }

        }

    }
}
