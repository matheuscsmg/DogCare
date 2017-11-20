package com.estagio3.dog_care;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MedicacaoActivity extends AppCompatActivity {

    public static final String REMEDIO_NAME = "com.estagio3.dog_care.remedioname";
    public static final String REMEDIO_ID = "com.estagio3.dog_care.remedioid";

    DatabaseReference remedio, usuario;
    private FirebaseAuth firebaseAuth;

    FirebaseUser user;
    EditText editTextName;
    Spinner spinnerGenre;
    Button buttonAddRemedios;
    ListView listViewRemedios;

    List<Remedio> remedios3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicacao);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        usuario = FirebaseDatabase.getInstance().getReference("usuario");
        remedio = FirebaseDatabase.getInstance().getReference("remedios");

        editTextName = (EditText) findViewById(R.id.editTextName);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        listViewRemedios = (ListView) findViewById(R.id.listViewRemedios);

        buttonAddRemedios = (Button) findViewById(R.id.buttonAddRemedio);
        remedios3 = new ArrayList<>();



        buttonAddRemedios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                gravarRemedio();
            }
        });


        listViewRemedios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Remedio remedio6 = remedios3.get(i);
                Intent intent = new Intent(getApplicationContext(), RemedioItemActivity.class);

                //putting artist name and id to intent
                intent.putExtra(REMEDIO_ID, remedio6.getId());
                intent.putExtra(REMEDIO_NAME, remedio6.getNome());

                //starting the activity with intent
                startActivity(intent);
                return true;
            }
        });


    }

    private void gravarRemedio(){

        String id = UUID.randomUUID().toString();
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        if (TextUtils.isEmpty(name)){
            //e-mail vazio
            Toast.makeText(this, "Por favor insira o nome do remédio", Toast.LENGTH_SHORT).show();
            return;
        }

        id = remedio.push().getKey();

        Remedio remedio1 = new Remedio(name, id, genre);
        remedio.child(id).setValue(remedio1);
        usuario.child(user.getUid()).child("idRmedio").setValue(id);
        Toast.makeText(this,"Remedio Adicionado !", Toast.LENGTH_LONG).show();

        resgataDadosDoRemedioDoAnimal();

    }

//    public void resgataDadosDoUsuario(){
//
//        String valor = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Query query = usuario.orderByKey().equalTo(valor);
//        usuario.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Usuario u = snapshot.getValue(Usuario.class);
//                    Log.i("ID DO ANIMAL:", "" + u.getIdRemedio());
//                    resgataDadosDoRemedioDoAnimal(u.getIdRemedio());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }



    // Aqui é q é o pulo do gato...
    public void resgataDadosDoRemedioDoAnimal(){

        // Faço a busca ordenando os ids do animal e comparando ao idAnimal do usuário
        //Query query = remedio.orderByKey().equalTo(String.valueOf(idRemedio));
        remedio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //remedios3.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Remedio remedios = postSnapshot.getValue(Remedio.class);
                    remedios3.add(remedios);

                }

                RemedioList remedioAdapter = new RemedioList(MedicacaoActivity.this, remedios3);
                //attaching adapter to the listview
                listViewRemedios.setAdapter(remedioAdapter);
//                if(dataSnapshot.exists()){
//                    for(DataSnapshot snap : dataSnapshot.getChildren()){
//                        Remedio a = snap.getValue(Remedio.class);
//                        name.setText(a.getNome());
//                    }
//                }else{
//                    Toast.makeText(getApplicationContext(), "Animal não encontrado!", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewRemedios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Remedio remedio4 = remedios3.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), RemedioItemActivity.class);

                //putting artist name and id to intent
                intent.putExtra(REMEDIO_NAME, remedio4.getNome());
                intent.putExtra(REMEDIO_ID, remedio4.getId());

                //starting the activity with intent
                startActivity(intent);
            }
        });
    }
//
//
//    private void showUpdateDeleteDialog(final String remedioId, String remediotName) {
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
//        dialogBuilder.setView(dialogView);
//
//        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
//        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
//        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
//        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);
//
//        dialogBuilder.setTitle(remediotName);
//        final AlertDialog b = dialogBuilder.create();
//        b.show();
//
//
//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = editTextName.getText().toString().trim();
//                String genre = spinnerGenre.getSelectedItem().toString();
//                if (!TextUtils.isEmpty(name)) {
//                    updateRemedio(remedioId, name, genre);
//                    b.dismiss();
//                }
//            }
//        });
//
//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                deleteRemedio(remedioId);
//                b.dismiss();
//
//            }
//        });
//    }
//
//    private boolean updateRemedio(String id, String name, String genre) {
//        //getting the specified artist reference
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("remedio").child(id);
//
//        //updating artist
//        Remedio remedio5 = new Remedio(id, name, genre);
//        dR.setValue(remedio5);
//        Toast.makeText(getApplicationContext(), "Remédio Atualizado", Toast.LENGTH_LONG).show();
//        return true;
//    }
//
//
//    private boolean deleteRemedio(String id) {
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("remedio").child(id);
//
//        dR.removeValue();
//
//
//        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);
//
//        drTracks.removeValue();
//        Toast.makeText(getApplicationContext(), "Remédio Excluido", Toast.LENGTH_LONG).show();
//
//        return true;
//    }
//

}
