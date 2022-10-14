package com.example.uts_pppb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {
    private final ArrayList<Berita> values; //nampung data recycle
    private final LayoutInflater inflater; //buat ngeset view

    public BeritaAdapter(Context context, ArrayList<Berita> values) {
        this.values = values;
        this.inflater = LayoutInflater.from(context);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtKategori = itemView.findViewById(R.id.txt_kategori);
            txtPenulis = itemView.findViewById(R.id.txt_penulis);
            txtTglRilis = itemView.findViewById(R.id.txt_tglrilis);
            txtKonten = itemView.findViewById(R.id.txt_konten);
        }
    }
}
