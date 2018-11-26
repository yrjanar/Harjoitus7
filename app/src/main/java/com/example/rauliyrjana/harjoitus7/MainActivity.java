package com.example.rauliyrjana.harjoitus7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText nro;
    private EditText nimi;
    private ListView mAkuListView;
    private ArrayAdapter mAkuListAdapter;

    //TODO muuttujat Firebase ja DatabaseReference tarvitaan
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    //TODO muuttuja ChildEventListener tarvitaan
    private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Firebase ja DatabaseReference kytkennÃ¤t tehdÃ¤Ã¤n
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("akut"); //tässä saadaan viittaus juurenn ja lapsella akuihin

        nro=findViewById(R.id.editTextNumero);
        nimi=findViewById(R.id.editTextNimi);
        mAkuListView = (ListView) findViewById(R.id.listView);

        List<Aku> kaikkiAkut=new ArrayList<>();
        mAkuListAdapter = new AkuArrayAdapter(this, R.layout.item_aku, kaikkiAkut);
        mAkuListView.setAdapter(mAkuListAdapter);

        //TODO ChildEventListener luodaan tÃ¤ssÃ¤
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Aku aku = dataSnapshot.getValue(Aku.class);
                mAkuListAdapter.add(aku);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //TODO KytketÃ¤Ã¤n DatabaseReference ja ChildEventListener
mMessagesDatabaseReference.addChildEventListener(mChildEventListener);

    }
    public void onClick(View view) {
        //ArrayAdapter<Aku> adapter=(ArrayAdapter<Aku>)getListAdapter();
        Aku aku = null;

        switch (view.getId()) {
            case R.id.add:
                // save the new comment to the database
                aku = new Aku();
                aku.setKirjanNumero(nro.getText().toString());
                aku.setKirjanNimi(nimi.getText().toString());


                //TODO DatabaseReferenceen tuupataan yllÃ¤ luotu aku
                mMessagesDatabaseReference.push().setValue(aku);

                tyhjenna();
                break;
            case R.id.delete:
                Log.d("aku", "delete tuli");

                break;
        }
    }
    public void tyhjenna(){
        //tyhjennys
        nro.setText("");
        nimi.setText("");
    }
}
