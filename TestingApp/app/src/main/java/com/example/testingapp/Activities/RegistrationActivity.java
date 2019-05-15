package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText regSurname, regName, regEmail, regPassword, regPassword2;
    private Button regButton;
    private ProgressBar regProgressBar;
    private RadioGroup radioGroup;
    private RadioButton employer, employee;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regSurname=(EditText)findViewById(R.id.regSurname);
        regName=(EditText)findViewById(R.id.regName);
        regEmail=(EditText)findViewById(R.id.regEmail);
        regPassword=(EditText)findViewById(R.id.regPassword);
        regPassword2=(EditText)findViewById(R.id.regPassword2);
        regButton=(Button)findViewById(R.id.regButton);
        regProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        employee=(RadioButton)findViewById(R.id.employee);
        employer=(RadioButton)findViewById(R.id.employer);

        //to make progress bar invisible until the button will be pressed
        regProgressBar.setVisibility(View.INVISIBLE);

        myAuth = FirebaseAuth.getInstance();
        regButton.setClickable(true);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regProgressBar.setVisibility(View.VISIBLE);

                final String email=regEmail.getText().toString();
                final String password=regPassword.getText().toString();
                final String password2=regPassword2.getText().toString();
                final String surname=regSurname.getText().toString();
                final String name=regName.getText().toString();

                if(email.isEmpty()||surname.isEmpty()||name.isEmpty()||password.isEmpty()
                ||password2.isEmpty()){

                    //some of the fields are not filled
                    showMessage("Для регистрации необходимо заполнить все поля");
                    regProgressBar.setVisibility(View.INVISIBLE);

                }else{

                    //password and password2 are not equal
                    if(!password.equals(password2)){

                        showMessage("Пароли не совпадают");
                        regProgressBar.setVisibility(View.INVISIBLE);

                    }else{
                        if(password.length()<6||password.length()>14)
                        {
                            showMessage("Пароль должен содержать от 6 до 14 символов");
                            regProgressBar.setVisibility(View.INVISIBLE);
                        }else {
                            if (radioGroup.getCheckedRadioButtonId() == -1) {
                                showMessage("Выберите тип вашей регистрации");
                                regProgressBar.setVisibility(View.INVISIBLE);
                            } else {
                                String type = "";
                                if (employee.isChecked())
                                    type = "employee";
                                if (employer.isChecked())
                                    type = "employer";
                                //everything is OK and all fields are filled
                                //start to create user account (if email is valid)
                                createUserAccount(surname, name, email, password, type);
                            }
                        }

                    }
                }

            }
        });
    }

    private void createUserAccount(final String surname, final String name, String email, String password, final String type) {

        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            //user account created successfully
                            showMessage("Аккаунт успешно создан");

                            regProgressBar.setVisibility(View.INVISIBLE);
                            regButton.setClickable(false);
                            //update profile information after the creation of account
                            updateInfo(surname, name, myAuth.getCurrentUser(), type);

                        }else{

                            //account creation failed
                            showMessage("Пользователь с таким логином уже существует");
                            regProgressBar.setVisibility(View.INVISIBLE);

                        }

                    }
                });

    }

    private void updateInfo(String surname, String name, FirebaseUser currentUser, final String type) {

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Map<String, Object> user= new HashMap<>();
        user.put("Name", name);
        user.put("Surname", surname);
        user.put("Email", currentUser.getEmail());

        //если нанимаемый, то добавить поле для email работодателя
        if(type.equals("employee"))
        {
            user.put("Employer",null);
            db.collection("Users").document(currentUser.getEmail()).set(user);
        }
        else if(type.equals("employer"))
            db.collection("Employer").document(currentUser.getEmail()).set(user);


        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                //user info updated successfully
                updateUI(type);

            }
        });

    }

    private void updateUI(String type) {
        Intent homeActivity;
        if(type.equals("employee"))
            homeActivity=new Intent(getApplicationContext(), HomeActivity.class);
        else
            homeActivity=new Intent(getApplicationContext(), EmployerHomeActivity.class);
        homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeActivity);
        finish();
    }

    /**
     * Simple method to show toast message
     * @param s - text, that we want to show to the user
     */
    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
