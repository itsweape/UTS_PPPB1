package com.example.uts_pppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class BeritaBookmarkActivity extends AppCompatActivity {

    private BeritaAdapter beritaAdapter;
    private RecyclerView recyclerView;

    private static DatabaseHelper mDatabaseHelpper;
    public ArrayList<Berita> berita = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_bookmark);

        mDatabaseHelpper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.rv_berita);

        //menampilkan berita yang di bookmark
        berita = mDatabaseHelpper.getBeritaBookMark();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewData();
    }
    private void viewData(){
        beritaAdapter = new BeritaAdapter(this, R.layout.data_berita, berita);
        recyclerView.setAdapter(beritaAdapter);
    }
}