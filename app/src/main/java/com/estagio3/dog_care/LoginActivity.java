package com.estagio3.dog_care;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import DAO.ConfiguracaoFirebase;


public class LoginActivity extends Activity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnEntrar;
    private TextView novoCadastro;
    private FirebaseAuth autenticacao;
    private Usuario usuarios;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = (EditText) findViewById(R.id.edtLEmail);
        edtSenha = (EditText) findViewById(R.id.edtLSenha);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        novoCadastro = (TextView) findViewById(R.id.novoCadastro);


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();

                usuarios = new Usuario();
                usuarios.setEmail(edtEmail.getText().toString());
                usuarios.setSenha(edtSenha.getText().toString());
                verificaCampos();

            }
        });



        novoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void verificaCampos(){
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //e-mail vazio
            Toast.makeText(this, "Por favor insira seu e-mail", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(senha)){
            //senha vazia
            Toast.makeText(this, "Por favor insira sua senha", Toast.LENGTH_SHORT).show();
            return;
        }

        validarLogin();
    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = autenticacao.getCurrentUser();
                            //updateUI(user);
                            //abrirTelaPrincipal();
                            Intent it = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(it);
                            Toast.makeText(getApplicationContext(), "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "Usuário ou Senha inválida !", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}

