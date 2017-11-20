package com.estagio3.dog_care;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {

    private Button add;
    private Button perfil;
    private Button menu_beck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(MainActivity.this, PerfilActivity.class);
                startActivity(it);
            }
        });

        perfil = (Button) findViewById(R.id.perfil);

        perfil.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(it);
            }
        });

        menu_beck = (Button) findViewById(R.id.menu_back);


        menu_beck.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(MainActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                existeapp();
            }

        });



    }



    private void existeapp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Deseja sair ?");
        builder.setCancelable(true);
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();

            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }


}
