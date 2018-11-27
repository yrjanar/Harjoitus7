package com.example.rauliyrjana.harjoitus7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;


public class MainActivity extends AppCompatActivity {
    private EditText nro;
    private EditText nimi;
    private EditText hankinta;
    private EditText painos;
    public static final int RC_SIGN_IN = 1;

    private ListView mAkuListView;
    private ArrayAdapter mAkuListAdapter;

    //TODO muuttujat Firebase ja DatabaseReference tarvitaan
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    //TODO muuttuja ChildEventListener tarvitaan
    private ChildEventListener mChildEventListener;

    //TODO muuttuja ja kuuntelija authille
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO Firebase ja DatabaseReference kytkennÃ¤t tehdÃ¤Ã¤n
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("akut");

        nro=findViewById(R.id.editTextNumero);
        nimi=findViewById(R.id.editTextNimi);
        hankinta=findViewById(R.id.editTextHankinta);
        painos=findViewById(R.id.editTextPainos);
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
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };

        //TODO KytketÃ¤Ã¤n DatabaseReference ja ChildEventListener
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);

        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    upDateUI(user);
                }else{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setProviders(
                                            AuthUI.GOOGLE_PROVIDER
                                            )
                                    .build(),
                                    RC_SIGN_IN);
                }
            }
        };
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
                aku.setHankinta(hankinta.getText().toString());
                aku.setPainos(painos.getText().toString());

                //TODO DatabaseReferenceen tuupataan yllÃ¤ luotu aku
                mMessagesDatabaseReference.push().setValue(aku);

                tyhjenna();
                break;
            case R.id.delete:
                Log.d("aku", "delete tuli");
                Toast.makeText(this, "Painoit delete -nappia, ei vaikutusta!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void tyhjenna(){
        //tyhjennys
        nro.setText("");
        nimi.setText("");
        painos.setText("");
        hankinta.setText("");
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        //updateUI(null);
    }


    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
