package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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



//        //--------------------------------------------------------------------//
//        String inputstr = "КАКОВА ПРОДОЛЖИТЕЛЬНОСТЬ СТАЖИРОВКИ ЭЛЕКТРОТЕХНИЧЕСКОГО ПЕРСОНАЛА ДО НАЗНАЧЕНИЯ НА САМОСТОЯТЕЛЬНУЮ РАБОТУ? /1, П. 1.4.11/\t" +
//                "От 2 до 5 смен\t" +
//                "От 5 до 10 смен\t" +
//                "От 2 до 14 смен\n" +
//                "КАК ДОЛЖНЫ УСТАНАВЛИВАТЬСЯ ТРАНСФОРМАТОРЫ, ОБОРУДОВАННЫЕ УСТРОЙСТВАМИ ГАЗОВОЙ ЗАЩИТЫ? /1, П. 2.1.3/\t" +
//                "Чтобы крышка имела подъем по направлению к газовому реле не менее 1%\t" +
//                "Чтобы крышка имела подъем по направлению к газовому реле не менее 2%\t" +
//                "Чтобы маслопровод имел уклон по направлению к расширителю не менее 1%\t" +
//                "Чтобы маслопровод имел уклон по направлению к расширителю не более 1%\n" +
//                "НА КАКОЙ ГЛУБИНЕ ВЫЕМКА ГРУНТА В МЕСТАХ НАХОЖДЕНИЯ КАБЕЛЕЙ И ПОДЗЕМНЫХ СООРУЖЕНИЙ ДОЛЖНА ВЫПОЛНЯТЬСЯ ТОЛЬКО ЛОПАТАМИ? /1, П. 2.4.24/\t" +
//                "0,3 м и более\t" +
//                "0,4 м и более\t" +
//                "0,5 м и более\t" +
//                "0,6 м и более\n" +
//                "КАК ЧАСТО НЕОБХОДИМО ПРОВЕРЯТЬ ИСПРАВНОСТЬ ЗАЗЕМЛЕНИЯ КРЮКОВ И ШТЫРЕЙ ИЗОЛЯТОРОВ НА ВЛ НАПРЯЖЕНИЕМ ДО 1000 В? /1, П. 2.8.10/\t" +
//                "Ежегодно, перед грозовым сезоном, выборочно, но не менее 2% общего числа\t" +
//                "Ежегодно, перед грозовым сезоном, выборочно, по усмотрению ответственного за электрохозяйство\t" +
//                "Не реже 1 раза в 2 года, выборочно, по усмотрению ответственного за электрохозяйство\t" +
//                "1 раз в 3 года, выборочно, не менее 50% общего числа\n" +
//                "КАК ДОЛЖНЫ ПЛАНИРОВАТЬСЯ ПРОФИЛАКТИЧЕСКИЕ ИСПЫТАНИЯ ЭЛЕКТРОУСТАНОВОК? /6, П. 9.18/\t" +
//                "Непосредственно после профилактического ремонта перед подачей напряжения\t" +
//                "Перед профилактическим ремонтом с целью определить работоспособность оборудования\t" +
//                "Между двумя плановыми ремонтами как самостоятельная операция\n" +
//                "КАКУЮ ГРУППУ ПО ЭЛЕКТРОБЕЗОПАСНОСТИ ДОЛЖНЫ ИМЕТЬ ЛИЦА ОПЕРАТИВНОГО ПЕРСОНАЛА, ОБСЛУЖИВАЮЩИЕ ЭЛЕКТРОУСТАНОВКИ ДО 1000 В? /2, П. 1.3.2/\t" +
//                "Не ниже II группы\t" +
//                "Не ниже III группы\t" +
//                "Не ниже IV группы\n" +
//                "КАКОВ ПОРЯДОК ВОЗВРАТА КЛЮЧЕЙ ОТ ЭЛЕКТРОУСТАНОВОК ПО ОКОНЧАНИИ РАБОТЫ ИЛИ ОСМОТРА В ЭЛЕКТРОУСТАНОВКАХ, ГДЕ ИМЕЕТСЯ МЕСТНЫЙ ОПЕРАТИВНЫЙ ПЕРСОНАЛ? /2, П. 1.3.12/\t" +
//                "Обязателен ежедневный возврат ключей\t" +
//                "Ключи должны возвращаться не позднее следующего рабочего дня после осмотра или полного окончания работы\n" +
//                "КАКУЮ ГРУППУ ПО ЭЛЕКТРОБЕЗОПАСНОСТИ ДОЛЖЕН ИМЕТЬ ДОПУСКАЮЩИЙ К ПРОИЗВОДСТВУ РАБОТ В ЭЛЕКТРОУСТАНОВКАХ НАПРЯЖЕНИЕМ ВЫШЕ 1 КВ? /2, П. 2.1.6/\t" +
//                "Не ниже V группы\t" +
//                "Не ниже IV группы\t" +
//                "Не ниже III группы\n" +
//                "КТО ПРИ ИЗМЕНЕНИИ СОСТАВА БРИГАДЫ ДОЛЖЕН ПРОИНСТРУКТИРОВАТЬ РАБОТНИКОВ, ВВЕДЕННЫХ В СОСТАВ БРИГАДЫ? /2, П. 2.8.5/\t" +
//                "Производитель работ\t" +
//                "Наблюдающий\t" +
//                "Любой из вышеперечисленных работников\n" +
//                "КТО ИМЕЕТ ПРАВО УСТАНАВЛИВАТЬ ПЕРЕНОСНЫЕ ЗАЗЕМЛЕНИЯ В ЭЛЕКТРОУСТАНОВКАХ НАПРЯЖЕНИЕМ ВЫШЕ 1000 В? /2, П. 3.5.8/\t" +
//                "Два работника: один - имеющий группу IV (из числа оперативного персонала), другой - имеющий группу III\t" +
//                "Два работника: один - имеющий группу III (из числа оперативного персонала), другой - имеющий группу II\n" +
//                "УКАЖИТЕ ПРИЗНАКИ КОМЫ\t" +
//                "Отсутствует пульс и дыхание\t" +
//                "Есть пульс, нет дыхания\t" +
//                "Есть пульс, потеря сознания более 4 минут\t" +
//                "Отсутствие сознания, нет реакции зрачков\n" +
//                "!!!";
//        String copy=inputstr;
//        String[] input = inputstr.split("\n");
//        int i = 0;
//        String[] quest;
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        while (!input[i].equals("!!!"))
//        {
//                Map<String, Object> question = new HashMap<>();
//                quest = input[i].split("\t");
//                if (quest.length == 5 || quest.length==4 || quest.length==6 || quest.length==3) {
//                    if(quest.length==5) {
//                        question.put("Question", quest[0]);
//                        question.put("0", quest[1]);
//                        question.put("1", quest[2]);
//                        question.put("2", quest[3]);
//                        question.put("3", quest[4]);
//                    }
//                    if(quest.length==4)
//                    {
//                        question.put("Question", quest[0]);
//                        question.put("0", quest[1]);
//                        question.put("1", quest[2]);
//                        question.put("2", quest[3]);
//                    }
//                    if(quest.length==6) {
//                        question.put("Question", quest[0]);
//                        question.put("0", quest[1]);
//                        question.put("1", quest[2]);
//                        question.put("2", quest[3]);
//                        question.put("3", quest[4]);
//                        question.put("4", quest[5]);
//                    }
//                    if(quest.length==3)
//                    {
//                        question.put("Question", quest[0]);
//                        question.put("0", quest[1]);
//                        question.put("1", quest[2]);
//                    }
//                    String a;
//                    if (i < 10)
//                        a = "0" + String.valueOf(i);
//                    else
//                        a = String.valueOf(i);
//                    final String strr = " " + question;
//                    db.collection("TestQuestions").document("Questions").collection("Билет №30").document(a).set(question)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d("add", "added" + strr);
//                                }
//                            });
//                }
//            ++i;
//        }
//        //----------------------------------------------------------------------------//





        //to make progress bar invisible until the button will be pressed
        regProgressBar.setVisibility(View.INVISIBLE);

        myAuth = FirebaseAuth.getInstance();

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
                    showMessage("Для регистрации необходимо заполнить все поля.");
                    regProgressBar.setVisibility(View.INVISIBLE);

                }else{

                    //password and password2 are not equal
                    if(!password.equals(password2)){

                        showMessage("Пароли не совпадают.");
                        regProgressBar.setVisibility(View.INVISIBLE);

                    }else{

                        //everything is OK and all fields are filled
                        //start to create user account (if email is valid)
                        createUserAccount(surname, name, email, password);

                    }
                }

            }
        });
    }

    private void createUserAccount(final String surname, final String name, String email, String password) {

        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            //user account created successfully
                            showMessage("Аккаунт успешно создан!");

                            regProgressBar.setVisibility(View.INVISIBLE);
                            //update profile information after the creation of account
                            updateInfo(surname, name, myAuth.getCurrentUser());

                        }else{

                            //account creation failed
                            showMessage("Не удалось создать аккаунт!\n"+task.getException().getMessage());
                            regProgressBar.setVisibility(View.INVISIBLE);

                        }

                    }
                });

    }

    private void updateInfo(String surname, String name, FirebaseUser currentUser) {

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Map<String, Object> user= new HashMap<>();
        user.put("Name", name);
        user.put("Surname", surname);
        user.put("Email", currentUser.getEmail());
//        user.put("Билет 1(из 11)",null);
//        user.put("Билет 2(из 11)",null);
//        user.put("Билет 3(из 11)",null);
//        user.put("Билет 4(из 11)",null);
//        user.put("Билет 5(из 11)",null);
//        user.put("Билет 6(из 11)",null);
//        user.put("Билет 7(из 11)",null);
//        user.put("Билет 8(из 11)",null);
//        user.put("Билет 9(из 11)",null);
//        user.put("Билет 10(из 11)",null);
//        user.put("Билет 11(из 11)",null);
//        user.put("Билет 12(из 11)",null);
//        user.put("Билет 13(из 11)",null);
//        user.put("Билет 14(из 11)",null);
//        user.put("Билет 15(из 11)",null);
//        user.put("Билет 16(из 11)",null);
//        user.put("Билет 17(из 11)",null);
//        user.put("Билет 18(из 11)",null);
//        user.put("Билет 19(из 11)",null);
//        user.put("Билет 20(из 11)",null);
//        user.put("Билет 21(из 11)",null);
//        user.put("Билет 22(из 11)",null);
//        user.put("Билет 23(из 11)",null);
//        user.put("Билет 24(из 11)",null);
//        user.put("Билет 25(из 11)",null);
//        user.put("Билет 26(из 11)",null);
//        user.put("Билет 27(из 11)",null);
//        user.put("Билет 28(из 11)",null);
//        user.put("Билет 29(из 11)",null);
//        user.put("Билет 30(из 11)",null);

        db.collection("Users").document(currentUser.getEmail()).set(user);


        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                //user info updated successfully
                showMessage("Регистрация завершена!");
                updateUI();

            }
        });

    }

    private void updateUI() {

        Intent homeActivity=new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();

    }

    /**
     * Simple method to show toast message
     * @param s - text, that we want to show to the user
     */
    private void showMessage(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

    }
}
