package com.example.uts_pppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateBeritaActivity extends AppCompatActivity{

    String judul, kategori, penulis, tglRilis, konten, minUsia, key;
    EditText edt_judul, edt_kategori, edt_penulis, edt_tglRilis, edt_konten, edt_minUsia;
    Button btn_update;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_berita);

        edt_judul = findViewById(R.id.judul);
        edt_kategori = findViewById(R.id.kategori);
        edt_konten = findViewById(R.id.konten);
        edt_penulis = findViewById(R.id.penulis);
        edt_tglRilis = findViewById(R.id.tglRilis);
        edt_minUsia = findViewById(R.id.minUsia);
        btn_update = findViewById(R.id.update_btn);

        Intent intent = getIntent();
        judul = intent.getStringExtra("Judul");
        kategori = intent.getStringExtra("Kategrori");
        penulis = intent.getStringExtra("penulis");
        tglRilis = intent.getStringExtra("tglRilis");
        konten = intent.getStringExtra("konten");

        edt_judul.setText(judul);
        edt_kategori.setText(kategori);
        edt_penulis.setText(penulis);
        edt_tglRilis.setText(tglRilis);
        edt_minUsia.setText(minUsia);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //button update
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBerita();
                Intent intent = new Intent(view.getContext(), BeritaRecyclerView.class);
                startActivity(intent);

            }
        });
    }

    //fungsi untuk update berita
    private void updateBerita() {
        Berita updateBerita = new Berita();

        databaseReference.child("Berita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()) {
                    if((item.getValue(Berita.class)).getJudul().equals(judul)){
                        key = item.getKey();
                        updateBerita.setJudul(edt_judul.getText().toString());
                        updateBerita.setKategori(edt_kategori.getText().toString());
                        updateBerita.setKonten(edt_konten.getText().toString());
                        updateBerita.setMinUsia(edt_minUsia.getText().toString());
                        updateBerita.setPenulis(edt_penulis.getText().toString());
                        updateBerita.setTglRilis(edt_tglRilis.getText().toString());

                        databaseReference.child(key).setValue(updateBerita);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}