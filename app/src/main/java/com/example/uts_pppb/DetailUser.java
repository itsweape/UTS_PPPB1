package com.example.uts_pppb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DetailUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText tanggal_lahir;
    Spinner spinner;
    Button btnBerita;
    String Spinners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        tanggal_lahir = findViewById(R.id.tanggalLahir);
        spinner = findViewById(R.id.label_spinner);
        btnBerita = findViewById(R.id.btnShowBerita);

        if(spinner != null){
            spinner.setOnItemSelectedListener(this);
        }

        //menampilkan spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        //fungsi menampilkan datepicker dan tidak editable
        tanggal_lahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    tanggal_lahir.setEnabled(false);
                    DialogFragment dateFragment = new DatePickerFragment();
                    dateFragment.show(getSupportFragmentManager(), "date-picker");
                }else{
                    tanggal_lahir.setEnabled(true);
                }
            }
        });

        //kondisi ketika button lihat berita di klik
        btnBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tl = String.valueOf(tanggal_lahir.getText());
                String kategori = String.valueOf(spinner.getSelectedItem());
                Intent intent = new Intent(DetailUser.this, BeritaRecyclerView.class);
                intent.putExtra("tanggalLahir", tl);
                intent.putExtra("kategori", kategori);
                startActivity(intent);
            }
        });
    }

    //method memproses datepicker
    public void processDatePickerResult(int day, int month, int year) {
        String day_string = Integer.toString(day);
        String month_string = Integer.toString(month+1);
        String year_string = Integer.toString(year);

        String dateMessage = day_string + "-" + month_string + "-" + year_string;
        tanggal_lahir.setText(dateMessage);
    }

    //kondisi ketika spinner dipilih
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinners = adapterView.getItemAtPosition(i).toString();
        showSpinnerText();
    }

    private void showSpinnerText() {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}