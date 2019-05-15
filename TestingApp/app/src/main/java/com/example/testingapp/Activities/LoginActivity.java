package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText userLogin, userPassword;
    private Button enter;
    private ProgressBar loginProgressBar;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        RelativeLayout rel=findViewById(R.id.startWhite);
        ProgressBar pr=findViewById(R.id.progressBarStart);
        pr.setVisibility(View.VISIBLE);
        enter = findViewById(R.id.enterButton);
        enter.setVisibility(View.INVISIBLE);

        if (user != null) {
            DocumentReference dsn=db.collection("Users").document(user.getEmail());
            dsn.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        if(task.getResult().exists())
                        {
                            Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent=new Intent(getApplicationContext(), EmployerHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }else {
            rel.setVisibility(View.INVISIBLE);
            pr.setVisibility(View.INVISIBLE);
            userLogin = findViewById(R.id.userLogin);
            enter.setVisibility(View.VISIBLE);
            userPassword = findViewById(R.id.userPassword);
            loginProgressBar = findViewById(R.id.loginProgressBar);
            TextView regText = (TextView) findViewById(R.id.haveAccount);

            myAuth = FirebaseAuth.getInstance();

            regText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                    startActivity(intent);
                }
            });

            loginProgressBar.setVisibility(View.INVISIBLE);
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginProgressBar.setVisibility(View.VISIBLE);
                    enter.setVisibility(View.INVISIBLE);

                    final String mail = userLogin.getText().toString();
                    final String password = userPassword.getText().toString();

                    if (mail.isEmpty() || password.isEmpty()) {
                        showMessage("Не введен логин или пароль");
                        loginProgressBar.setVisibility(View.INVISIBLE);
                        enter.setVisibility(View.VISIBLE);
                    } else {
                            signIn(mail, password);
                    }
                }
            });
        }

    }

    private void signIn(final String mail, String password) {

        myAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    loginProgressBar.setVisibility(View.INVISIBLE);
                    enter.setVisibility(View.VISIBLE);
                    updateUI(mail);
                    showMessage("Вход выполнен");

                }else{

                    loginProgressBar.setVisibility(View.INVISIBLE);
                    enter.setVisibility(View.VISIBLE);
                    showMessage("Логин или пароль введены неверно");
                }
            }
        });
    }

    private void updateUI(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference dsn=db.collection("Users").document(email);
        dsn.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent=new Intent(getApplicationContext(), EmployerHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
