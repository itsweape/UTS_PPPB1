package com.example.uts_pppb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "berita-db";
    private static final int DATABASE_VERSION = 1;

    private static final String BERITA_TABLE = "berita";
    private static final String BERITA_ID ="id";
    private static final String BERITA_JUDUL ="judul";
    private static final String BERITA_KATEGORI ="kategori";
    private static final String BERITA_KONTEN ="konten";
    private static final String BERITA_MINUSIA ="minUsia";
    private static final String BERITA_PENULIS ="penulis";
    private static final String BERITA_TGLRILIS ="tglrilis";

    //constructor
    public  DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_STATIONERY = "CREATE TABLE " + BERITA_TABLE + "(" + BERITA_ID + " STRING PRIMARY KEY, "
                + BERITA_JUDUL + " STRING, " +
                BERITA_KATEGORI + " STRING, " + BERITA_KONTEN + " STRING, " +
                BERITA_MINUSIA + " STRING, " + BERITA_PENULIS + " STRING, " +
                BERITA_TGLRILIS + " STRING )";
        sqLiteDatabase.execSQL(CREATE_TABLE_STATIONERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    //insert berita ke halaman bookmark
    public void insertBeritaBookmark(Berita berita){
        SQLiteDatabase db = getWritableDatabase();

        //CONVERT berita ke contentvalues
        ContentValues values = new ContentValues();
        values.put(BERITA_JUDUL, berita.getJudul());
        values.put(BERITA_KATEGORI, berita.getKategori());
        values.put(BERITA_KONTEN, berita.getKonten());
        values.put(BERITA_MINUSIA, berita.getMinUsia());
        values.put(BERITA_PENULIS, berita.getPenulis());
        values.put(BERITA_TGLRILIS, berita.getTglRilis());

        db.insert(BERITA_TABLE, null, values);
        db.close();
    }

    //menampilkan semua data yang telah di klik bookmark di halaman bookmark
    public ArrayList<Berita> getBeritaBookMark(){
        ArrayList<Berita> allContentBerita = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * from " + BERITA_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //mengambil semua data dengan looping
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(0);
                String judul = cursor.getString(1);
                String kategori = cursor.getString(2);
                String konten = cursor.getString(3);
                String minUsia = cursor.getString(4);
                String penulis = cursor.getString(5);
                String tglRilis = cursor.getString(6);

                allContentBerita.add(new Berita(id, judul, kategori,
                        konten, minUsia, penulis, tglRilis));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return allContentBerita;
    }

    //delete berita dari bookmark
    public void deleteBeritaBookmark(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(BERITA_TABLE, BERITA_ID + "=?",
                new String[] {id});
        db.close();
    }
}
