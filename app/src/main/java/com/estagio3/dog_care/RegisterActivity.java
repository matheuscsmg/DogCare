package com.estagio3.dog_care;


import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private EditText edtName;
    private ProgressBar progressBar;
    private String idUsuarioCorrent;

//    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbRef = FirebaseDatabase.getInstance().getReference().child("usuario");
        firebaseAuth = FirebaseAuth.getInstance(); //inicializa

//        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        edtName = (EditText) findViewById(R.id.edtName);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

//        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
//        if (googleApiAvailability.isGooglePlayServicesAvailable(getApplicationContext()) != ConnectionResult.SUCCESS) {
//            googleApiAvailability.makeGooglePlayServicesAvailable(this);
//        }

        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        idUsuarioCorrent = UUID.randomUUID().toString();



        if (TextUtils.isEmpty(email)){
            //e-mail vazio
            Toast.makeText(this, "Por favor insira seu e-mail", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //senha vazia
            Toast.makeText(this, "Por favor insira sua senha", Toast.LENGTH_SHORT).show();
            return;
        }
        //Se as validações foram OK
        //Então irá mostrar a barra de progresso //não

//        progressDialog.setMessage("Registrando seu usuário...");
//        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()) {

                            //usuário será registrado e fará logon
                            //inicio de um perfil aqui
                            //mostra apenas a tela
                            final String id = UUID.randomUUID().toString();
                            final List<String> lista = null;

                            Toast.makeText(RegisterActivity.this, "Registrado com sucesso", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Usuario u = new Usuario(user.getUid(), edtName.getText().toString().trim(), editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim(), "", lista);

                            dbRef.child(user.getUid()).setValue(u);

                            //setContentView(R.layout.activity_usuario);

                            //dbRef = FirebaseDatabase.getInstance().getReference("usuario").child("-KwXGizUXUEH0TfzGg-9");

//                            TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
//
//                            txtEmail.setText(dbRef.getKey());



                            Intent it = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(it);

                        }else{
                            Toast.makeText(RegisterActivity.this, "Usuário não registrado. Tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view){
        if (view == buttonRegister) registerUser();
        if (view == textViewSignin){
            //vai abrir o login active aqui.

        }
    }

}
