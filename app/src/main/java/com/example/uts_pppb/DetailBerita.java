package com.example.uts_pppb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailBerita extends AppCompatActivity {
    TextView txtJudul, txtKategori, txtPenulis, txtTglRilis, txtKonten;
    String judul, kategori, penulis, tglRilis, konten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        txtJudul = findViewById(R.id.txt_judul);
        txtKategori = findViewById(R.id.txt_kategori);
        txtPenulis = findViewById(R.id.txt_penulis);
        txtTglRilis = findViewById(R.id.txt_tglrilis);
        txtKonten = findViewById(R.id.txt_konten);

        //get intent dari beritaadapter
        Intent intent = getIntent();
        judul = intent.getStringExtra("judul");
        kategori = intent.getStringExtra("kategori");
        penulis = intent.getStringExtra("penulis");
        tglRilis = intent.getStringExtra("tglRilis");
        konten = intent.getStringExtra("konten");

        //setTeks pada detail berita
        txtJudul.setText(judul);
        txtKategori.setText(kategori);
        txtPenulis.setText(penulis);
        txtTglRilis.setText(tglRilis);
        txtKonten.setText(konten);
    }
}