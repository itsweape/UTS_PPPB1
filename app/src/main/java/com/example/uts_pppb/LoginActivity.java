package com.example.uts_pppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

//    String username="pakjoko";
//    String password="yangpentingcuan";
    EditText txtUser, txtPass;
    TextView blmDaftar;
    Button login;
    String emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    private NotificationManager mNotificationManager;
    private final static String CHANNEL_ID = "primary-channel";
    private int NOTIFICATION_ID = 0;

    private final static String ACTION_NOTIF = "action-notif";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    boolean loginn = false;

    private SharedPreferences SharedPref;
    private final String sharedPrefFile = "com.example.sharedpreferenceapp";

    private final String LOGIN_KEY = "login-key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser = findViewById(R.id.username);
        txtPass = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);
        blmDaftar = findViewById(R.id.daftar);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        mNotificationManager = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"app net",
                    NotificationManager.IMPORTANCE_HIGH); //high = popup
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        SharedPref = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        loginn = SharedPref.getBoolean(LOGIN_KEY, false);

        if (loginn){
            Intent intent = new Intent(LoginActivity.this, DetailUser.class);
            startActivity(intent);
        }

        blmDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(txtUser.getText().toString().equals(username)&&txtPass.getText().toString().equals(password)){
//                    startActivity(new Intent(LoginActivity.this, DetailUser.class));
//                }else{
//                    showAlertDialog();
//                }
                login();
            }
        });
    }
    private void login(){
        String ema = txtUser.getText().toString();
        String pass = txtPass.getText().toString();

        if (!ema.matches(emailValidation)){
            txtUser.setError("Maskkan email dengan karakter yang sesuai");

        }else if(pass.isEmpty() || pass.length()<6){
            txtPass.setError("Password harus lebih dari 6 karakter");
        }else{
            progressDialog.setMessage("Tunggu hingga proses login selesai");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        firebaseAuth.signInWithEmailAndPassword(ema, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    loginn = true;
                    showDetailUserActivity();
                    saveLogin();
                } else{
                    progressDialog.dismiss();
                    showAlertDialog();
                }
            }
        });
    }
    private void showDetailUserActivity(){
        sendNotification();
        Intent intent = new Intent(LoginActivity.this, DetailUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void saveLogin(){
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.putBoolean(LOGIN_KEY, loginn);
        editor.apply();
    }
    private NotificationCompat.Builder getNotificationBuilder(){

        Intent notificationIntent = new Intent(this, DetailUser.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("New Notification!")
                .setContentText("Login Berhasil")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent);
        return notifyBuilder;
    }

    private void sendNotification(){
        Intent notifIntent = new Intent(ACTION_NOTIF);
        PendingIntent notifPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        mNotificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
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