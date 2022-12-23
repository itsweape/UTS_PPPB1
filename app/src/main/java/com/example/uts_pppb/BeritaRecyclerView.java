package com.example.uts_pppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BeritaRecyclerView extends AppCompatActivity {

    private String txt_tanggalLahir;
    public  String txt_kategori;
    private TextView kategori;
    private BeritaAdapter beritaAdapter;
    private RecyclerView recyclerView;
    int usia;
    String key;
    FloatingActionButton fab;
    ImageButton bookmarks;

    DatabaseReference mDatabaseRererenceBerita;
    public ArrayList<Berita> berita = new ArrayList<>();
    public ArrayList<Berita> filterBerita = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_recycler_view);
        kategori = findViewById(R.id.kategories);
        recyclerView = findViewById(R.id.rv_berita);
        fab = findViewById(R.id.floatingBtn);
        bookmarks = findViewById(R.id.btn_bookmarks);
        mDatabaseRererenceBerita = FirebaseDatabase.getInstance().getReference();

        //get intent dari detail user
        Intent intent = getIntent();
        txt_tanggalLahir = intent.getStringExtra("tanggalLahir");
        txt_kategori = intent.getStringExtra("kategori");

//        //menambahkan data berita pada list berita
//        addData();

        //menghitung usia user
        String tanggalLahirUser[] = txt_tanggalLahir.split("-");
        usia = 2022-Integer.parseInt(tanggalLahirUser[2]);
        Toast.makeText(this, String.valueOf(usia), Toast.LENGTH_SHORT).show();

        //filter berita dengan usia dan kategori yang dipilih
        for (Berita dataContent : berita){
            Integer minUsia = Integer.valueOf(dataContent.getMinUsia());
            if (minUsia <= usia && dataContent.getKategori().toLowerCase().equals(txt_kategori.toLowerCase())){
                filterBerita.add(dataContent);
            }
        }
        kategori.setText(txt_kategori);
        showData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeritaRecyclerView.this, TambahBeritaActivity.class);
                startActivity(intent);
            }
        });

        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeritaRecyclerView.this, BeritaBookmarkActivity.class);
                startActivity(intent);
            }
        });


    }

    private void showData(){
        mDatabaseRererenceBerita.child("Berita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    key = item.getKey();
                    Berita beritaa = item.getValue(Berita.class);
                    beritaa.setId(item.getKey());
                    berita.add(beritaa);
                }
                for (Berita dataContent : berita) {
                    Integer minUsia = Integer.valueOf(dataContent.getMinUsia());
                    if (minUsia <= usia && dataContent.getKategori().toLowerCase().equals(txt_kategori.toLowerCase())) {
                        filterBerita.add(dataContent);
                    }
                }
                beritaAdapter = new BeritaAdapter(BeritaRecyclerView.this, R.layout.data_berita, filterBerita);
                recyclerView.setAdapter(beritaAdapter);
                beritaAdapter.notifyDataSetChanged();
//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void addData() {
//        //berita olahraga
//       berita.add(new Berita("891 Santri Ikut Pekan Olahraga dan Seni Pesantren Daerah Sulsel IX", "Olahraga", "Sebanyak 891 santri mengikuti Pekan Olahraga dan Seni Antarpondok Pesantren Daerah (Pospeda) Sulawesi Selatan IX 2022 yang dibuka langsung oleh Staf Khusus Menteri Agama Bidang Toleransi, Terorisme, Radikalisme dan Pesantren Mohammad Nuruzzaman mewakili Menag Yaqut Cholil Qoumas.\n" +
//               "\n" +
//               "Muhammad Nuruzzaman mengatakan pondok pesantren adalah lembaga pendidikan yang bertujuan melahirkan santri yang berakhlakul karimah. \"Pospeda ini berbeda dengan event olahraga lainnya karena Pospeda harus berbeda dengan ajang kompetisi serupa yang digelar di luar pesantren. Tunjukkan bahwa kita berbeda karena lebih mengedepankan akhlak dan saling menghargai,\" ujarnya, Jumat (14/10/2022). Ia mengatakan, menang bukan satu-satunya tujuan, namun tujuan utama Pospeda adalah sebagai ajang silaturahim dan sebagai wahana menjalin persaudaraan. \"Itulah perbedaannya, ajang Pospeda ini menjadi wadah bagi para santri se-Sulawesi Selatan untuk mempererat tali silaturahim serta menjalin persaudaraan,\" katanya.\n" +
//               "\n" +
//               "Kepala Kanwil Kemenag Sulsel Khaeroni dalam sambutan mengungkapkan Pospeda ke-9 tahun 2022 ini dilaksanakan secara mandiri atau pembiayaannya non-DIPA. \"Kegiatan Pospeda ini sifatnya mandiri, atas biaya sendiri. Dan tentunya karena kita intens membangun komunikasi lintas stakeholder sehingga kegiatan ini dapat terlaksana,\" ujar Khaeroni.\n" +
//               "\n" +
//               "Adapun santri yang berlaga pada ajang Pospeda Sulsel ini, kata Khaeroni, yakni 475 santri untuk cabang olahraga dan 416 santri berlomba pada cabang seni dan lainnya. Selain diikuti ratusan santri, seremoni pembukaan Pospeda ini juga dihadiri oleh para Kepala Bidang lingkup Kanwil Kemenag Sulsel, para Kepala Kantor Kemenag Kabupaten dan Kota se-Sulawesi Selatan serta para Kepala Seksi PD Pontren Kemenag Kabupaten se-Sulawesi Selatan.\n" +
//               "\n" +
//               "Mengusung tema Gerak Santri Bangkit Negeri, Pospeda yang merupakan ajang 3 tahunan ini akan digelar selama 3 hari mulai tanggal 14 sampai 16 Oktober 2022, dengan diikuti 891 santri dari 23 Kabupaten/Kota se Sulawesi Selatan, minus Toraja Utara yang memang tidak memiliki pondok pesantren.", "10", "detik-detik", "Jumat, 14 Oktober 2022"));
//
//       berita.add(new Berita("Berita Inter Milan : Hasil Laga Lawan Barcelona Berbuntut Panjang , Ada Pemain yang Dipecat ?", "Olahraga", " Pertandingan Barcelona vs Inter Milan yang berakhir imbang 3-3 di Liga Champions berbuntut panjang.\n" +
//               "\n" +
//               "Barcelona mengalami masalah serius . Tentu saja soal internal Barcelona yang kini mendapat sorotan.\n" +
//               "\n" +
//               "Bahkan kabar yang mengejutkan bisa saja terjadi di Barcelona . Sebab, sang presiden Barcelona Joan Laporta dikabarkan marah besar. Karena kemarahannya itu, dikabarkan akan ada sosok yang jadi tumbal.\n" +
//               "\n" +
//               "Tak tanggung-tanggung, akan ada pemain yang dipecat usai pertandingan yang sangat merugikan Barcelona itu.\n" +
//               "\n" +
//               "Ya, hasil imbang 3-3 Barcelona dengan Inter Milan di Liga Champions UEFA akan membawa dampak besar, baik dari perspektif ekonomi dan olahraga.\n" +
//               "\n" +
//               "Joan Laporta sudah marah dengan pelatih Xavi Hernandez, yang secara aktif mencari jawaban dari para pemainnya tentang bencana baru-baru ini. Menurut Reshad Rehman , Gerard Pique adalah salah satu pemain yang menghadapi banyak panas. Bek Barcelona adalah salah satu pemain dengan performa terburuk dalam hasil imbang Barcelona dengan Inter Milan. Ia melakukan kesalahan yang berujung pada gol Nicolo Barella. Dia juga memiliki masalah melacak Denzel Dumfries. Ketidakmampuannya memainkan peran kunci di balik pertahanan rentan Barcelona yang kebobolan tiga kali.", "20", "detik-detik", "Jumat, 14 Oktober 2022"));
//
//        berita.add(new Berita("Hasil Laga Lawan Barcelona Berbuntut Panjang", "Olahraga", " Pertandingan Barcelona vs Inter Milan yang berakhir imbang 3-3 di Liga Champions berbuntut panjang.\n" +
//                "\n" +
//                "Barcelona mengalami masalah serius . Tentu saja soal internal Barcelona yang kini mendapat sorotan.\n" +
//                "\n" +
//                "Bahkan kabar yang mengejutkan bisa saja terjadi di Barcelona . Sebab, sang presiden Barcelona Joan Laporta dikabarkan marah besar. Karena kemarahannya itu, dikabarkan akan ada sosok yang jadi tumbal.\n" +
//                "\n" +
//                "Tak tanggung-tanggung, akan ada pemain yang dipecat usai pertandingan yang sangat merugikan Barcelona itu.\n" +
//                "\n" +
//                "Ya, hasil imbang 3-3 Barcelona dengan Inter Milan di Liga Champions UEFA akan membawa dampak besar, baik dari perspektif ekonomi dan olahraga.\n" +
//                "\n" +
//                "Joan Laporta sudah marah dengan pelatih Xavi Hernandez, yang secara aktif mencari jawaban dari para pemainnya tentang bencana baru-baru ini. Menurut Reshad Rehman , Gerard Pique adalah salah satu pemain yang menghadapi banyak panas. Bek Barcelona adalah salah satu pemain dengan performa terburuk dalam hasil imbang Barcelona dengan Inter Milan. Ia melakukan kesalahan yang berujung pada gol Nicolo Barella. Dia juga memiliki masalah melacak Denzel Dumfries. Ketidakmampuannya memainkan peran kunci di balik pertahanan rentan Barcelona yang kebobolan tiga kali.", "20", "detik-detik", "Jumat, 14 Oktober 2022"));
//
//        //berita entertainments
//       berita.add(new Berita("Foto Pribadi Jennie BLACKPINK Diduga Bocor, YG Entertainment Tegas Bakal Perkarakan Ke Jalur Hukum", "Entertainment", " Jennie BLACKPINK dan V BTS saat ini tengah menjadi target rumor hingga diduga menjadi korban pelanggaran privasi. Dugaan itu muncul sejak foto-foto kencan terduga Jennie dan V disebarkan oleh akun Twitter dan Telegram (G). Setelah dinantikan, akhirnya YG Entertainment selaku agensi akhirnya angkat bicara.\n" +
//               "\n" +
//               "Pada 3 Oktober, YG Entertainment melalui media setempat Xportnews mengumumkan bakal mengambil tindakan hukum pada penyebar foto pribadi Jennie. Bahkan mereka sudah mulai bergerak sejak September sebelum mantap menempuh jalur hukum.\n" +
//               "\n" +
//               "\"Kami YG Entertainment. Kami telah secara resmi meminta polisi untuk menyelidiki penyebar asli foto-foto pribadi Jennie BLACKPINK. Kami telah memantau situasi dan mengajukan keluhan kami pada bulan September setelah mengumpulkan informasi,\" buka YG Entertainment.\n" +
//               "\n" +
//               "Mereka membeberkan alasan YG Entertainment tetap bungkam meski berita sudah menyebar di mana-mana. Namun pada akhirnya mereka memutuskan untuk mengambil langkah tegas.\n" +
//               "\n" +
//               "\"YG telah menahan diri tidak berkomentar dan mengungkapkan posisi kami sebelumnya untuk meminimalisir kerusakan. Namun, kami merasa bertanggung jawab dan menyatakan tidak mungkin lagi memaafkan masifnya penyebaran rumor, kritik, serangan pribadi, pelecehan seksual dan pelanggaran privasi yang menyertai foto-foto pribadi. Oleh karena itu, kami ingin mengklarifikasi bahwa kami mengambil tindakan hukum demi memperbaiki situasi,\" beber mereka.\n" +
//               "\n" +
//               "Selain menegaskan tidak akan memberi maaf penyebar foto Jennie, mereka juga menggarisbawahi undang-undang yang bisa melindungi artis mereka.", "20", "detik-detik", "Jumat, 14 Oktober 2022"));
//
//       berita.add(new Berita("SF9 Dikabarkan Kecelakaan Mobil, Manajemen Buka Suara", "Entertainment", "SF9 tampil di acara penutupan National Sports Festival di Ulsan pada 13 Oktober 2022. Namun, mereka tiba terlambat karena dikabarkan mengalami kecelakaan.\n" +
//               "Kabar tersebut tentunya membuat fans heboh. Pihak manajemen, FNC Entertainment, pun akhirnya merilis pernyataan.\n" +
//               "\n" +
//               "Mereka mengungkap kronologi kecelakaan yang mereka sebut dengan kecelakaan kecil. \"SF9 melakukan perjalanan ke Ulsan untuk berpartisipasi dalam upacara penutupan National Sports Festival. Saat berada di rest area, ada insiden kecil terjadi,\" ungkap FNC Entertainment.\n" +
//               "\n" +
//               "\"Sebuah mobil yang sedang parkir menggores bagian dari mobil SF9 yang sedang diparkir. Pada saat itu, para anggota dan staf sedang beristirahat setelah keluar dari mobil. Sehingga, kami ingin mengabarkan sama sekali tidak ada cedera pada member dan staf,\" jelasnya.",
//               "17", "detik-detik", "Jumat, 14 Oktober 2022"));
//
//       berita.add(new Berita("Manajemen Buka Suara, NCT dikabarkan datang ke Indoensia ", "Entertainment", "SF9 tampil di acara penutupan National Sports Festival di Ulsan pada 13 Oktober 2022. Namun, mereka tiba terlambat karena dikabarkan mengalami kecelakaan.\n" +
//                "Kabar tersebut tentunya membuat fans heboh. Pihak manajemen, FNC Entertainment, pun akhirnya merilis pernyataan.\n" +
//                "\n" +
//                "Mereka mengungkap kronologi kecelakaan yang mereka sebut dengan kecelakaan kecil. \"NCT melakukan perjalanan ke Ulsan untuk berpartisipasi dalam upacara penutupan National Sports Festival. Saat berada di rest area, ada insiden kecil terjadi,\" ungkap FNC Entertainment.\n" +
//                "\n" +
//                "\"Sebuah mobil yang sedang parkir menggores bagian dari mobil SF9 yang sedang diparkir. Pada saat itu, para anggota dan staf sedang beristirahat setelah keluar dari mobil. Sehingga, kami ingin mengabarkan sama sekali tidak ada cedera pada member dan staf,\" jelasnya.",
//                "17", "detik-detik", "Jumat, 14 Oktober 2022"));
//
//       //berita politik
//       berita.add(new Berita("Wakil Ketua DPD Sultan Najamudin Ingin Capres Berkomitmen Perbarui Sistem Politik", "Politik", "Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//               "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.\n" +
//               "\n" + "Wakil Ketua DPD Sultan Najamudin Ingin Capres Berkomitmen Perbarui Sistem Politik politik. Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//               "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.\n" + "\"Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//               "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.", "20", "detik-detik", "Jumat, 14 Oktober 2022"));
//
//       berita.add(new Berita("Capres Berkomitmen Perbarui Sistem Politik", "Politik", "Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//                "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.\n" +
//                "\n" + "Wakil Ketua DPD Sultan Najamudin Ingin Capres Berkomitmen Perbarui Sistem Politik politik. Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//                "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.\n" + "\"Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//                "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.", "20", "detik-detik", "Jumat, 14 Oktober 2022"));
//
//       berita.add(new Berita("Sultan Najamudin Berkomitmen Perbarui Sistem Politik", "Politik", "Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//                "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.\n" +
//                "\n" + "Wakil Ketua DPD Sultan Najamudin Ingin Capres Berkomitmen Perbarui Sistem Politik politik. Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//                "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.\n" + "\"Wakil ketua Dewan Perwakilan Daerah (DPD RI) Sultan B Najamudin, berharap para Capres 2024 punya gagasan yang fundamental untuk bangsa. Termasuk dalam pembaharuan sistem politik dan struktur ketatanegaraan.  Dia mengatakan, ini sebagai wacana politik dan perhatian utama DPD RI yang menginginkan terjadi perubahan sistem politik demokrasi Indonesia. Menurutnya perubahan itu sejalan dengan nilai-nilai dasar Pancasila.\n" +
//                "\n"+ "\"Harus kita akui bahwa sistem demokrasi Indonesia saat ini sudah sangat liberal dan jauh dari nilai-nilai kebangsaan yang luhur. Jika dipertahankan, demokrasi prosedural yang tidak relevan ini tidak akan signifikan memberikan dampak kesejahteraan bagi rakyat,\" ujar Sultan, melalui keterangan resminya, Jumat 14 Oktober 2022.", "17", "detik-detik", "Jumat, 14 Oktober 2022"));
//    }

}