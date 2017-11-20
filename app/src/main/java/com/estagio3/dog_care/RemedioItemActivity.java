package com.estagio3.dog_care;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RemedioItemActivity extends AppCompatActivity {

    Button buttonAddTrack;
    EditText editTextTrackName;
    TextView textViewArtist;
    ListView listViewTracks;

    DatabaseReference databaseTracks;

    List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedio_item);

        Intent intent = getIntent();

        /*
        * this line is important
        * this time we are not getting the reference of a direct node
        * but inside the node track we are creating a new child with the artist id
        * and inside that node we will store all the tracks with unique ids
        * */
        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(intent.getStringExtra(MedicacaoActivity.REMEDIO_ID));

        buttonAddTrack = (Button) findViewById(R.id.buttonAddTrack);
        editTextTrackName = (EditText) findViewById(R.id.editTextName);
        textViewArtist = (TextView) findViewById(R.id.textViewArtist);
        listViewTracks = (ListView) findViewById(R.id.listViewTracks);

        tracks = new ArrayList<>();

        textViewArtist.setText(intent.getStringExtra(MedicacaoActivity.REMEDIO_NAME));



        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Track track = postSnapshot.getValue(Track.class);
                    tracks.add(track);
                }
                TrackList trackListAdapter = new TrackList(RemedioItemActivity.this, tracks);
                listViewTracks.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveTrack() {
        String trackName = editTextTrackName.getText().toString().trim();
        if (!TextUtils.isEmpty(trackName)) {
            String id  = databaseTracks.push().getKey();
            Track track = new Track(id, trackName);
            databaseTracks.child(id).setValue(track);
            Toast.makeText(this, "RECOMENDAÇÕES SALVA", Toast.LENGTH_LONG).show();
            editTextTrackName.setText("");
        } else {
            Toast.makeText(this, "Por favr digite a recomendação", Toast.LENGTH_LONG).show();
        }
    }
}
