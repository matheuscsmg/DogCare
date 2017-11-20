package com.estagio3.dog_care;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {
    ////
    private CircleImageView photo;
    private Button buttonGravar;
    private EditText nomeDog;
    private EditText raca;
    private EditText peso;
    private EditText idade;
    private RadioGroup Sexo;
    private RadioButton sexoM;
    private RadioButton sexoF;
    private ProgressBar progressBar;

    private Button add;
    private Button vacinacao;
    private Button menu_beck;

    private FirebaseAuth firebaseAuth;

    public static final int CODIGO_CAMERA = 567;
    private String caminhoFoto;
    private String sexoValor;


////    private ProgressDialog progressDialog;
//
//    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
//    private DatabaseReference dogref = dbRef.child("usuario");

    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference, databaseReferenceAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);




        buttonGravar = (Button) findViewById(R.id.buttonGravar);
        photo = (CircleImageView) findViewById(R.id.photo);
        nomeDog = (EditText) findViewById(R.id.nomeDog);
        raca = (EditText) findViewById(R.id.raca);
        idade = (EditText) findViewById(R.id.idade);
        peso = (EditText)  findViewById(R.id.peso);
        sexoM = (RadioButton) findViewById(R.id.Masculino);
        sexoF = (RadioButton) findViewById(R.id.Feminino);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Log.i("ID DO USUARIO CORRENTE", "" + user.getUid());

        databaseReference = FirebaseDatabase.getInstance().getReference("usuario");
        databaseReferenceAnimal = FirebaseDatabase.getInstance().getReference("animais");

        buttonGravar.setOnClickListener(this);
        photo.setOnClickListener(this);
    }
//        private void inicializarFirebase (){
//        FirebaseApp.initializeApp(PerfilActivity.this);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
//    }

    Foto f = new Foto();

    public void onClick (View v){
        if (v==photo){
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
            File arquivoFoto = new File(caminhoFoto);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
            startActivityForResult(intentCamera, CODIGO_CAMERA);

        }
        if (v==buttonGravar){

            String nome = nomeDog.getText().toString().trim();
            String idade2 = idade.getText().toString().trim();
            String raca2 = raca.getText().toString().trim();
            String peso2 = peso.getText().toString().trim();


            if (TextUtils.isEmpty(nome)){
                //e-mail vazio
                Toast.makeText(this, "Por favor insira o nome do animal", Toast.LENGTH_SHORT).show();
                return;
            }else if (TextUtils.isEmpty(raca2)){
                //senha vazia
                Toast.makeText(this, "Por favor insira a raça do animal", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(peso2)){
                //senha vazia
                Toast.makeText(this, "Por favor insira o peso do animal", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(idade2)){
                //senha vazia
                Toast.makeText(this, "Por favor insira a idade do animal", Toast.LENGTH_SHORT).show();
                return;
            }

            if(sexoM.isChecked()){
                sexoValor = "Macho";
            }
            if (sexoF.isChecked()){
                sexoValor = "Fêmea";
            }

            gravarDog(sexoValor);
            startActivity(new Intent(this, Main2Activity.class));
        }


    }

    private void gravarDog(String sexoValor){

        String id = UUID.randomUUID().toString();
        String nome = nomeDog.getText().toString().trim();
        String racaDog = raca.getText().toString().trim();
        String pesoDog = peso.getText().toString().trim();
        String Idade = idade.getText().toString().trim();
        String foto = f.getFoto();


        Animal dog = new Animal(id, nome, racaDog, Idade, pesoDog, sexoValor, foto);
        databaseReferenceAnimal.child(id).setValue(dog);
        databaseReference.child(user.getUid()).child("idAnimal").setValue(id);
        Toast.makeText(this,"Pet salvo !", Toast.LENGTH_LONG).show();

    }



//
//        photo.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
//                File arquivoFoto = new File(caminhoFoto);
//                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
//                startActivityForResult(intentCamera, CODIGO_CAMERA);
//            }
//
//        });}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA) {
            CircleImageView foto = (CircleImageView) findViewById(R.id.photo);
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            foto.setImageBitmap(bitmapReduzido);
            String fotoDog = String.valueOf(foto);
            f.setFoto(fotoDog);


            // foto.setScaleType(ImageView.ScaleType.FIT_XY);

        }
    }




}



//        final String nome = nomeDog.getText().toString().trim();
//        final String raçaDog = raça.getText().toString().trim();
//        final String pesoDog = peso.getText().toString().trim();
//        final String nascimento = Nascimento.getText().toString().trim();
////
////
//        ImageView photo = (ImageView) findViewById(R.id.photo);
//        Button buttonGravar = (Button) findViewById(R.id.buttonGravar);
//        EditText raça = (EditText) findViewById(R.id.raça);
//        EditText nomeDog = (EditText) findViewById(R.id.nomeDog);
//        EditText Nascimento = (EditText) findViewById(R.id.Nascimento);
//        EditText peso = (EditText) findViewById(R.id.peso);
////
//        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.Sexo);
//        final Button btnConfirm = (Button) findViewById(R.id.buttonGravar);
//
//        btnConfirm.setOnClickListener(new View.OnClickListener(){
//          @Override
//            public void onClick(View v) {
//              int idRadioSelecionado = radioGroup.getCheckedRadioButtonId();
//              final RadioButton radio = (RadioButton) findViewById(idRadioSelecionado);
//              String opcao = radio.getText().toString();
//              Toast.makeText(getApplicationContext(), "Opção: " + opcao, Toast.LENGTH_SHORT).show();
//
//
//              Animal dog = new Animal(nome, raçaDog, nascimento, pesoDog, opcao);
//
//              dogref.child("usuario/KyCrJVndNuMIqUeSZou").setValue(dog);
//          }
//        });
//
//
////        progressDialog = new ProgressDialog(this);
//
//
//        buttonGravar.setOnClickListener(this);
//        photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Selecione a imagem"), 1);
//            }
//        });
//    }
//
//
//
//
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//           if (resultCode == Activity.RESULT_OK) {
//                if (requestCode==1) {
//                    Uri imagemSelecionada = data.getData();
//                    try {
//                        Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(imagemSelecionada));
//                        photo.setImageBitmap(bm);
//                        photo.setMaxHeight(180);
//                        photo.setMaxWidth(180);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }
//
//
////        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
////        if (googleApiAvailability.isGooglePlayServicesAvailable(getApplicationContext()) != ConnectionResult.SUCCESS) {
////            googleApiAvailability.makeGooglePlayServicesAvailable(this);
////        }
//
//
//
//
//
//    private void registerDog(){
//        final String nome = nomeDog.getText().toString().trim();
//        final String raçaDog = raça.getText().toString().trim();
//        String pesoDog = peso.getText().toString().trim();
//        String nascimento = Nascimento.getText().toString().trim();
//
//        if (TextUtils.isEmpty(nome)){
//            //e-mail vazio
//            Toast.makeText(this, "Por favor insira o nome", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(TextUtils.isEmpty(raçaDog)){
//            //senha vazia
//            Toast.makeText(this, "Por favor insira a raça", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(pesoDog)){
//            //senha vazia
//            Toast.makeText(this, "Por favor insira o peso", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(TextUtils.isEmpty(nascimento)){
//            //senha vazia
//            Toast.makeText(this, "Por favor insira a data de nascimento", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //Se as validações foram OK
//        //Então irá mostrar a barra de progresso //não
//
////        progressDialog.setMessage("Registrando seu usuário...");
////        progressDialog.show();
//
//
//    }
//
//    @Override
//    public void onClick(View view){
//        if (view == buttonGravar) registerDog();
//    }
