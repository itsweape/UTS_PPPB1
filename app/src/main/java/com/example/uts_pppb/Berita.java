package com.example.uts_pppb;

public class Berita {
    private String judul;
    private String kategori;
    private String konten;
    private String minUsia;
    private String penulis;
    private String tglRilis;

    public Berita(String judul, String kategori, String konten, String minUsia, String penulis, String tglRilis) {
        this.judul = judul;
        this.kategori = kategori;
        this.konten = konten;
        this.minUsia = minUsia;
        this.penulis = penulis;
        this.tglRilis = tglRilis;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public String getMinUsia() {
        return minUsia;
    }

    public void setMinUmur(String minUmur) {
        this.minUsia = minUmur;
    }
    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getTglRilis() {
        return tglRilis;
    }

    public void setTglRilis(String tglRilis) {
        this.tglRilis = tglRilis;
    }
}
