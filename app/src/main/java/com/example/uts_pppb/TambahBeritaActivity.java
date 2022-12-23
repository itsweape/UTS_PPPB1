package com.example.uts_pppb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahBeritaActivity extends AppCompatActivity {
    EditText edt_judul, edt_kategori, edt_penulis, edt_tglRilis, edt_konten, edt_minUsia;
    Button insertData;
    DatabaseReference mDatabaseRererenceBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_berita);

        mDatabaseRererenceBerita = FirebaseDatabase.getInstance().getReference(Berita.class.getSimpleName());

        edt_judul = findViewById(R.id.judul);
        edt_kategori = findViewById(R.id.kategori);
        edt_konten = findViewById(R.id.konten);
        edt_penulis = findViewById(R.id.penulis);
        edt_tglRilis = findViewById(R.id.tglRilis);
        edt_minUsia = findViewById(R.id.minUsia);
        insertData = findViewById(R.id.insert_btn);

        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                Intent intent = new Intent(TambahBeritaActivity.this, BeritaRecyclerView.class);
                startActivity(intent);
            }
        });

    }

    //menambahkan berita
    private void insertData(){
        String judul = edt_judul.getText().toString();
        String kategori = edt_kategori.getText().toString();
        String konten = edt_konten.getText().toString();
        String minUsia =  edt_minUsia.getText().toString();
        String penulis = edt_penulis.getText().toString();
        String tglRilis = edt_tglRilis.getText().toString();

        Berita newBerita = new Berita();
        if( judul != "" && kategori != "" && konten != ""&& minUsia != ""&& penulis != ""&& tglRilis != ""){
            newBerita.setJudul(judul);
            newBerita.setKategori(kategori);
            newBerita.setKonten(konten);
            newBerita.setMinUsia(minUsia);
            newBerita.setPenulis(penulis);
            newBerita.setTglRilis(tglRilis);

            mDatabaseRererenceBerita.push().setValue(newBerita);
            Toast.makeText(this, "Succesfully insert news data!", Toast.LENGTH_SHORT).show();
        }
    }

}