package com.example.uts_pppb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    String username="pakjoko";
    String password="yangpentingcuan";
    EditText txtUser, txtPass;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser = findViewById(R.id.username);
        txtPass = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUser.getText().toString().equals(username)&&txtPass.getText().toString().equals(password)){
                    startActivity(new Intent(LoginActivity.this, DetailUser.class));
                }else{
                    showAlertDialog();
                }
            }
        });
    }
    public void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("Peringatan!");
        alert.setMessage("Username dan Password yang anda masukkan salah!");
        alert.setPositiveButton("Coba lagi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtUser.setText("");
                txtPass.setText("");
            }
        });
        alert.show();
    }
}