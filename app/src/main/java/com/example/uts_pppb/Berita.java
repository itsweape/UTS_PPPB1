package com.example.uts_pppb;

public class Berita {
    private String id;
    private String judul;
    private String kategori;
    private String konten;
    private String minUsia;
    private String penulis;
    private String tglRilis;

    public Berita(String id, String judul, String kategori, String konten, String minUsia, String penulis, String tglRilis) {
        this.id = id;
        this.judul = judul;
        this.kategori = kategori;
        this.konten = konten;
        this.minUsia = minUsia;
        this.penulis = penulis;
        this.tglRilis = tglRilis;
    }

    public Berita() {

    }

    public Berita(String judul, String kategori, String penulis, String tglRilis, String minUsia, String konten) {
        this.judul = judul;
        this.kategori = kategori;
        this.penulis = penulis;
        this.tglRilis = tglRilis;
        this.minUsia = minUsia;
        this.konten = konten;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setMinUsia(String minUsia) {
        this.minUsia = minUsia;
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
