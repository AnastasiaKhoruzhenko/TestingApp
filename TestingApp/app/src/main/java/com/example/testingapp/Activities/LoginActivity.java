package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText userLogin, userPassword;
    private Button enter;

    private ProgressBar loginProgressBar;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLogin=findViewById(R.id.userLogin);
        userPassword=findViewById(R.id.userPassword);
        enter=findViewById(R.id.enterButton);
        loginProgressBar=findViewById(R.id.loginProgressBar);

        myAuth=FirebaseAuth.getInstance();

        loginProgressBar.setVisibility(View.INVISIBLE);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgressBar.setVisibility(View.VISIBLE);
                enter.setVisibility(View.INVISIBLE);

                final String mail=userLogin.getText().toString();
                final String password=userPassword.getText().toString();

                if(mail.isEmpty()||password.isEmpty()){
                    showMessage("Не введен логин или пароль.");
                    loginProgressBar.setVisibility(View.INVISIBLE);
                    enter.setVisibility(View.VISIBLE);
                }else{
                    signIn(mail, password);
                }
            }
        });

    }

    private void signIn(final String mail, String password) {

        myAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    loginProgressBar.setVisibility(View.INVISIBLE);
                    enter.setVisibility(View.VISIBLE);
                    updateUI();

                }else{

                    loginProgressBar.setVisibility(View.INVISIBLE);
                    enter.setVisibility(View.VISIBLE);
                    showMessage(task.getException().getMessage());
                }
            }
        });
    }

    private void updateUI() {

        Intent homeActivity=new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}
