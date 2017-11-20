package com.estagio3.dog_care;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.UUID;

public class VacinaActivity extends AppCompatActivity {

    private CheckBox status1_vacina;
//    private CheckBox status2_vacina;
    private Button buttonSalvar;
    private EditText data_vacina;
    private EditText data_vacina2;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceVacina = database.getReference("vacinas");
//    DatabaseReference databaseReferenceVacina2 = database.getReference("vacinas");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacina);



        buttonSalvar = (Button) findViewById(R.id.salvarVacina);
        status1_vacina = (CheckBox) findViewById(R.id._status1);
//        status2_vacina = (CheckBox) findViewById(R.id._status2);
        data_vacina = (EditText) findViewById(R.id._data1);
//        data_vacina2 = (EditText) findViewById(R.id._data2);


        status1_vacina.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    status1_vacina.setChecked(true);
                    Vacinas vacina = new Vacinas("", "", status1_vacina);
                }
                else{
                    status1_vacina.setChecked(false);
                    Vacinas vacina = new Vacinas("", "", status1_vacina);
                }
            }
        });

//        status2_vacina.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if(((CheckBox) v).isChecked()){
//                    status2_vacina.setChecked(true);
//                    Vacinas vacina = new Vacinas("", "", status2_vacina);
//                }
//                else{
//                    status1_vacina.setChecked(false);
//                    Vacinas vacina = new Vacinas("", "", status2_vacina);
//                }
//            }
//        });
//        confereStatus(marcado);



        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = UUID.randomUUID().toString();
//                final String id2 = UUID.randomUUID().toString();

                Vacinas vacina = new Vacinas(id, data_vacina.getText().toString().trim(), null);
//                Vacinas vacina2 = new Vacinas(id2, data_vacina2.getText().toString().trim(), null);


                DatabaseReference vacinasRef = databaseReferenceVacina.child("vacinas");
//                DatabaseReference vacinasRef2 = databaseReferenceVacina2.child("vacinas").child(id2);
                vacinasRef.setValue(vacina);
//                vacinasRef2.setValue(vacina2);
                Intent it = new Intent(VacinaActivity.this, Main2Activity.class);
                startActivity(it);
            }
        });


        retornoVacina();
//        retornoVacina2();
    }


    public void retornoVacina (){
        databaseReferenceVacina.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        Vacinas a = snap.getValue(Vacinas.class);
                        data_vacina.setText(a.getData_vacina());
                        String check = String.valueOf(a.getCheckbox_vacina());
                        if (a.getData_vacina() != null){
                            status1_vacina.setChecked(true);
                        }else{
                            status1_vacina.setChecked(false);
                        }

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nenhum animal adicionado!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//    public void retornoVacina2 (){
//        databaseReferenceVacina2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
//                        Vacinas a = snap.getValue(Vacinas.class);
//                        data_vacina2.setText(a.getData_vacina());
//                        String check = String.valueOf(a.getCheckbox_vacina());
//                        if (a.getData_vacina() != null){
//                            status2_vacina.setChecked(true);
//                        }else{
//                            status2_vacina.setChecked(false);
//                        }
//
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Nenhum animal adicionado!", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}
