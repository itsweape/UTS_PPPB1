package com.example.uts_pppb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {
    private final ArrayList<Berita> values; //nampung data recycle
    private final LayoutInflater inflater; //buat ngeset view
    private static DatabaseHelper mDatabaseHelpper;
    Context context;

    DatabaseReference mDatabaseRererenceBerita = FirebaseDatabase.getInstance().getReference("Berita");

    public BeritaAdapter(Context context, int data_berita, ArrayList<Berita> values) {
        this.values = values;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        mDatabaseHelpper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public BeritaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.data_berita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaAdapter.ViewHolder holder, int position) {
        final Berita berita = values.get(position);

        holder.txtJudul.setText(berita.getJudul());
        holder.txtKategori.setText(berita.getKategori());
        holder.txtPenulis.setText(berita.getPenulis());
        holder.txtTglRilis.setText(berita.getTglRilis());
        holder.txtKonten.setText(berita.getKonten());

        //data untuk view berita
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailBerita.class);
                intent.putExtra("judul", berita.getJudul());
                intent.putExtra("kategori", berita.getKategori());
                intent.putExtra("penulis", berita.getPenulis());
                intent.putExtra("tglRilis", berita.getTglRilis());
                intent.putExtra("konten", berita.getKonten());

                holder.itemView.getContext().startActivity(intent);
            }
        });

        //data untuk bookmark berita
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), BeritaBookmarkActivity.class);
                intent.putExtra("judul", berita.getJudul());
                intent.putExtra("kategori", berita.getKategori());
                intent.putExtra("penulis", berita.getPenulis());
                intent.putExtra("tglRilis", berita.getTglRilis());
                intent.putExtra("konten", berita.getKonten());

                mDatabaseHelpper.insertBeritaBookmark(berita);
                holder.itemView.getContext().startActivity(intent);
                Toast.makeText(context, "Data Berhasil Ditambahkan ke Bookmark", Toast.LENGTH_SHORT).show();

            }
        });

        //delete bookmark
        holder.deleteBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelpper.deleteBeritaBookmark(berita.getId());

                Toast.makeText(context, "Data Berhasil Dihapus dari Bookmark", Toast.LENGTH_SHORT).show();

                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
            }
        });

        //delete berita
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mDatabaseRererenceBerita.child(berita.getId()).removeValue();

                Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtJudul;
        TextView txtKategori;
        TextView txtPenulis;
        TextView txtTglRilis;
        TextView txtKonten;
        CardView cardView;
        ImageButton bookmark, deleteBookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtKategori = itemView.findViewById(R.id.txt_kategori);
            txtPenulis = itemView.findViewById(R.id.txt_penulis);
            txtTglRilis = itemView.findViewById(R.id.txt_tglrilis);
            txtKonten = itemView.findViewById(R.id.txt_konten);
            cardView = itemView.findViewById(R.id.cardView);
            bookmark = itemView.findViewById(R.id.btn_bookmark);
            deleteBookmark = itemView.findViewById(R.id.btn_deleteBookmark);
        }
    }
}
