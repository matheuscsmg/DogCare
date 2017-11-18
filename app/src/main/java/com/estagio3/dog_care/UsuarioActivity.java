package com.estagio3.dog_care;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioActivity extends AppCompatActivity {

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        ref = FirebaseDatabase.getInstance().getReference("usuario").child("-KwXGizUXUEH0TfzGg-9");

        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);

        txtEmail.setText(ref.getKey());

    }
}