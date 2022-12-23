package com.example.uts_pppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    EditText email, password, confirmPassword;
    TextView haveAccount;
    Button register;
    String emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    private NotificationManager mNotificationManager;
    private final static String CHANNEL_ID = "primary-channel";
    private int NOTIFICATION_ID = 0;

    private final static String ACTION_NOTIF = "action-notif";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        confirmPassword = findViewById(R.id.confirm_password);
        register = findViewById(R.id.btnRegister);
        haveAccount = findViewById(R.id.login);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        mNotificationManager = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"app net",
                    NotificationManager.IMPORTANCE_HIGH); //high = popup
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth();
            }
        });
    }
    private void Auth(){
        String ema = email.getText().toString();
        String pass = password.getText().toString();
        String conf_pass = confirmPassword.getText().toString();

        if (!ema.matches(emailValidation)){
            email.setError("Maskkan email dengan benar");
        }else if(pass.isEmpty() || pass.length()<6){
            password.setError("Password hars lebih dari 6 karakter");
        }else if(!pass.equals(conf_pass)){
            confirmPassword.setError("Password tidak sama");
        }else{
            firebaseAuth.createUserWithEmailAndPassword(ema, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        showLoginActivity();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "" +task.getException() ,Toast.LENGTH_SHORT).show();
                    }
                }
            });
            progressDialog.setMessage("Tunggu hingga proses registrasi selesai");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }
    }
    private void showLoginActivity(){
        sendNotification();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private NotificationCompat.Builder getNotificationBuilder(){

        Intent notificationIntent = new Intent(this, DetailUser.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("New Notification!")
                .setContentText("Register Berhasil")
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

}