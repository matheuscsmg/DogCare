package com.estagio3.dog_care;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;


public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button vacinacao2;
    private Button medic;
    private Button perfil;
    private Button menu_beck2;
    private CircleImageView imageView;
    private TextView nome1;
    private TextView raca1;
    private TextView idade;
    private TextView peso1;
    private TextView sexo1;
    private TextView info;
    FirebaseUser user;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("usuario");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("animais");

        resgataDadosDoUsuario();
//
////
        vacinacao2 = (Button) findViewById(R.id.vacinacao2);

        vacinacao2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(Main2Activity.this, VacinaActivity.class);
                startActivity(it);
            }
        });


        medic = (Button) findViewById(R.id.medic);

        medic.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(Main2Activity.this, MedicacaoActivity.class);
                startActivity(it);
            }
        });

        perfil = (Button) findViewById(R.id.perfil);


        perfil.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(Main2Activity.this, Main2Activity.class);
                startActivity(it);
            }
        });

        menu_beck2 = (Button) findViewById(R.id.menu_back);


        menu_beck2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(it);
            }
        });



        info = (TextView) findViewById(R.id.textView);

        info.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent it = new Intent(Main2Activity.this, InfoActivity.class);
                startActivity(it);
            }
        });


        Button exit2 = (Button) findViewById(R.id.exit);
        exit2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                existeapp();
            }

        });

    }

    private void existeapp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
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


    public void resgataDadosDoUsuario(){

        nome1 = (TextView) findViewById(R.id.nome1);
        raca1 = (TextView) findViewById(R.id.raca1);
        idade = (TextView) findViewById(R.id.idade);
        imageView = (CircleImageView) findViewById(R.id.imageView);
        peso1 = (TextView) findViewById(R.id.peso1);
        sexo1 = (TextView) findViewById(R.id.sexo1);

        String nome = nome1.getText().toString().trim();
        String raca = raca1.getText().toString().trim();
        String idade1 = idade.getText().toString().trim();
        String peso = peso1.getText().toString().trim();
        String sexo = sexo1.getText().toString().trim();
        String imagem = imageView.getBackground().toString().trim();

        String valor = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = databaseReference.orderByKey().equalTo(valor);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Usuario u = snapshot.getValue(Usuario.class);
                    Log.i("ID DO ANIMAL:", "" + u.getIdAnimal());
                    resgataDadosDoAnimalDoUsuario(u.getIdAnimal());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Aqui é q é o pulo do gato...
    public void resgataDadosDoAnimalDoUsuario(String idAnimalNoUsuario){

        // Faço a busca ordenando os ids do animal e comparando ao idAnimal do usuário
        Query query = databaseReference2.orderByKey().equalTo(idAnimalNoUsuario);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snap : dataSnapshot.getChildren()){
                        Animal a = snap.getValue(Animal.class);
                        nome1.setText(a.getNomeDog());
                        idade.setText(a.getIdade());
                        raca1.setText(a.getRaca());
                        peso1.setText(a.getPeso());
                        sexo1.setText(a.getSexo());
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Nenhum animal adicionado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
