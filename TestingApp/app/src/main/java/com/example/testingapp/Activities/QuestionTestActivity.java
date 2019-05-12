package com.example.testingapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapp.Modules.Question;
import com.example.testingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionTestActivity extends AppCompatActivity {

    private TextView questionNumber, questionText, timer;
    private RadioGroup radioGroup;
    private RadioButton b1,b2,b3,b4,b5;
    private ImageButton previous, next;

    private String ticket_number;
    private List<Question> qlist;
    private List<Integer> answersList;
    private int correct = 0, total = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_test);

        questionNumber=findViewById(R.id.ticketNumber);
        questionText=findViewById(R.id.question);
        radioGroup=findViewById(R.id.radioGrQuestion);
        b1=findViewById(R.id.firstAnswer);
        b2=findViewById(R.id.secondAnswer);
        b3=findViewById(R.id.thirdAnswer);
        b4=findViewById(R.id.fourthAnswer);
        b5=findViewById(R.id.fifthAnswer);
        previous =findViewById(R.id.previousButton);
        next=findViewById(R.id.nextButton);
        timer=findViewById(R.id.timerQuest);

        Intent intent = getIntent();
//        //получить выбранный номер билета
        ticket_number = intent.getStringExtra("number");

        Log.d("success", "  read intentExtra from HomeActivity");

        // чтение вопросов
        qlist = new ArrayList<>();
        answersList=new ArrayList<>();
        for(int i=0;i<11;i++)
            answersList.add(-2);

        readQuestions(ticket_number);

    }

    private void readQuestions(String ticket_number) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TestQuestions")
                .document("Questions")
                .collection("Билет №"+ticket_number)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Log.d("success", "  reading documents");
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        Map<String, Object> d;
                        for (int i = 0; i < 11; ++i) {
                            d = list.get(i).getData();
                            switch (d.size()) {
                                case 4:
                                    qlist.add(new Question(d.get("Question").toString(), d.get("0").toString(), d.get("1").toString(), d.get("Answer").toString()));
                                    break;
                                case 5:
                                    qlist.add(new Question(d.get("Question").toString(), d.get("0").toString(), d.get("1").toString(), d.get("2").toString(), d.get("Answer").toString()));
                                    break;
                                case 6:
                                    qlist.add(new Question(d.get("Question").toString(), d.get("0").toString(), d.get("1").toString(), d.get("2").toString(), d.get("3").toString(), d.get("Answer").toString()));
                                    break;
                                case 7:
                                    qlist.add(new Question(d.get("Question").toString(), d.get("0").toString(), d.get("1").toString(), d.get("2").toString(), d.get("3").toString(), d.get("4").toString(), d.get("Answer").toString()));
                                    break;
                            }
                        }
                        startTimer();
                        total++;
                        updateQuestion(0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void startTimer()
    {
        countDownTimer = new CountDownTimer( 1800000, 1000)
        {
            public void onTick(long l)
            {
                timer.setText((l/1000)/60+":"+(l/1000)%60);
            }

            public void onFinish()
            {
                addScore(correct);
                timer.setText("Время истекло!");
                showAlertDialogOfResults(0);
                countDownTimer.cancel();
            }
        };
        countDownTimer.start();

    }

    public void updateQuestion(int i)
    {
        previous.setBackgroundResource(R.drawable.left_chevron);
        next.setBackgroundResource(R.drawable.right_chevron);
        radioGroup.clearCheck();
        previous.setVisibility(View.VISIBLE);
        if (total > 11)
        {
            countDownTimer.cancel();
            checkRes(ticket_number);
            showAlertDialogOfResults(1);
        } else{
            if(answersList.get(i)!=-2 && answersList.get(i)!=-1)
                ((RadioButton)radioGroup.getChildAt(answersList.get(i))).setChecked(true);

            if(i==0)
                previous.setVisibility(View.GONE);

            switch (qlist.get(i).size)
            {
                case 4:
                    b1.setText(qlist.get(i).getAnswer1());
                    b2.setText(qlist.get(i).getAnswer2());
                    b3.setVisibility(View.GONE);
                    b4.setVisibility(View.GONE);
                    b5.setVisibility(View.GONE);
                    questionText.setText(qlist.get(i).getQuestion());
                    questionNumber.setText("Вопрос " + total + " из 11");
                    chooseFrom2Answers(i);
                    break;
                case 5:
                    b1.setText(qlist.get(i).getAnswer1());
                    b2.setText(qlist.get(i).getAnswer2());
                    b3.setText(qlist.get(i).getAnswer3());
                    b4.setVisibility(View.GONE);
                    b5.setVisibility(View.GONE);
                    questionText.setText(qlist.get(i).getQuestion());
                    questionNumber.setText("Вопрос " + total + " из 11");
                    chooseFrom3Answers(i);
                    break;
                case 6:
                    b1.setText(qlist.get(i).getAnswer1());
                    b2.setText(qlist.get(i).getAnswer2());
                    b3.setText(qlist.get(i).getAnswer3());
                    b4.setText(qlist.get(i).getAnswer4());
                    b5.setVisibility(View.GONE);
                    questionText.setText(qlist.get(i).getQuestion());
                    questionNumber.setText("Вопрос " + total + " из 11");
                    b1.setEnabled(true);
                    b2.setEnabled(true);
                    b3.setEnabled(true);
                    b4.setEnabled(true);
                    chooseFrom4Answers(i);
                    break;
                case 7:
                    b1.setText(qlist.get(i).getAnswer1());
                    b2.setText(qlist.get(i).getAnswer2());
                    b3.setText(qlist.get(i).getAnswer3());
                    b4.setText(qlist.get(i).getAnswer4());
                    b5.setText(qlist.get(i).getAnswer5());
                    questionText.setText(qlist.get(i).getQuestion());
                    questionNumber.setText("Вопрос " + total + " из 11");
                    chooseFrom5Answers(i);
                    break;
            }
        }
    }

    public void chooseFrom2Answers(final int i)
    {
        b1.setEnabled(true);
        b2.setEnabled(true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nextButton:
                        next.setClickable(false);
                        previous.setClickable(false);
                        if (radioGroup.getCheckedRadioButtonId() == -1)
                            answersList.set(i, -1);
                        else {
                            if (b1.isChecked())
                                answersList.set(i, 0);
                            if (b2.isChecked())
                                answersList.set(i, 1);
                        }
                        total++;
                        updateQuestion(i + 1);
                        break;
                    case R.id.previousButton:
                        next.setClickable(false);
                        previous.setClickable(false);
                        if(radioGroup.getCheckedRadioButtonId()==-1)
                            answersList.set(i, -1);
                        else{
                            if(b1.isChecked())
                                answersList.set(i, 0);
                            if(b2.isChecked())
                                answersList.set(i, 1);
                        }
                        total--;
                        updateQuestion(i-1);
                        break;
                }
            }
        };

        next.setOnClickListener(onClickListener);
        previous.setOnClickListener(onClickListener);
    }

    public void chooseFrom3Answers(final int i)
    {
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nextButton:
                        next.setClickable(false);
                        previous.setClickable(false);
                        if (radioGroup.getCheckedRadioButtonId() == -1)
                            answersList.set(i, -1);
                        else {
                            if (b1.isChecked())
                                answersList.set(i, 0);
                            if (b2.isChecked())
                                answersList.set(i, 1);
                            if (b3.isChecked())
                                answersList.set(i, 2);
                        }
                        total++;
                        updateQuestion(i + 1);
                        break;
                    case R.id.previousButton:
                        next.setClickable(false);
                        previous.setClickable(false);
                        if(radioGroup.getCheckedRadioButtonId()==-1)
                            answersList.set(i, -1);
                        else{
                            if(b1.isChecked())
                                answersList.set(i, 0);
                            if(b2.isChecked())
                                answersList.set(i, 1);
                            if(b3.isChecked())
                                answersList.set(i, 2);

                        }
                        total--;
                        updateQuestion(i-1);
                        break;
                }
            }
        };

        next.setOnClickListener(onClickListener);
        previous.setOnClickListener(onClickListener);
    }

    public void chooseFrom4Answers(final int i)
    {
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nextButton:
                        next.setClickable(false);
                        previous.setClickable(false);
                        if (radioGroup.getCheckedRadioButtonId() == -1)
                            answersList.set(i, -1);
                        else {
                            if (b1.isChecked())
                                answersList.set(i, 0);
                            if (b2.isChecked())
                                answersList.set(i, 1);
                            if (b3.isChecked())
                                answersList.set(i, 2);
                            if (b4.isChecked())
                                answersList.set(i, 3);
                        }
                        total++;
                        updateQuestion(i + 1);
                        break;
                    case R.id.previousButton:
                        next.setClickable(false);
                        previous.setClickable(false);
                        if(radioGroup.getCheckedRadioButtonId()==-1)
                            answersList.set(i, -1);
                        else{
                            if(b1.isChecked())
                                answersList.set(i, 0);
                            if(b2.isChecked())
                                answersList.set(i, 1);
                            if(b3.isChecked())
                                answersList.set(i, 2);
                            if(b4.isChecked())
                                answersList.set(i, 3);

                        }
                        total--;
                        updateQuestion(i-1);
                        break;
                }
            }
        };

        next.setOnClickListener(onClickListener);
        previous.setOnClickListener(onClickListener);
    }

    public void chooseFrom5Answers(final int i)
    {
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nextButton:
                        next.setClickable(false);
                        previous.setClickable(false);
                        if (radioGroup.getCheckedRadioButtonId() == -1)
                            answersList.set(i, -1);
                        else {
                            if (b1.isChecked())
                                answersList.set(i, 0);
                            if (b2.isChecked())
                                answersList.set(i, 1);
                            if (b3.isChecked())
                                answersList.set(i, 2);
                            if (b4.isChecked())
                                answersList.set(i, 3);
                            if (b5.isChecked())
                                answersList.set(i, 4);
                        }
                        total++;
                        updateQuestion(i + 1);
                        break;
                    case R.id.previousButton:
                        next.setClickable(false);
                        previous.setClickable(false);
                        if(radioGroup.getCheckedRadioButtonId()==-1)
                            answersList.set(i, -1);
                        else{
                            if(b1.isChecked())
                                answersList.set(i, 0);
                            if(b2.isChecked())
                                answersList.set(i, 1);
                            if(b3.isChecked())
                                answersList.set(i, 2);
                            if(b4.isChecked())
                                answersList.set(i, 3);
                            if(b5.isChecked())
                                answersList.set(i, 4);

                        }
                        total--;
                        updateQuestion(i-1);
                        break;
                }
            }
        };

        next.setOnClickListener(onClickListener);
        previous.setOnClickListener(onClickListener);
    }

    public void addScore(final int corr)
    {
        FirebaseAuth myAuth = FirebaseAuth.getInstance();
        final String email = myAuth.getCurrentUser().getEmail();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> map = new HashMap<>();

                        if(!documentSnapshot.getData().containsKey("1"))
                            for (int i = 0; i < 30; ++i)
                                map.put(String.valueOf(i+1), null);
                        map.put(ticket_number, String.valueOf(corr));
                        updateScore(email, map);
                    }
                });
    }

    public void updateScore(String email, Map<String, Object> map)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(email)
                .update(map);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вы действительно хотите завершить прохождение теста?");
        builder.setCancelable(false);
        builder.setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer.cancel();
                        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        builder.setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
    public void checkRes(String ticket_number)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TestQuestions")
                .document("Questions")
                .collection("Билет №"+ticket_number)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        Map<String, Object> d;
                        for (int i = 0; i < 11; ++i) {
                            d = list.get(i).getData();
                            String cor_answ = qlist.get(i).getCorrectAnswer();
                            String real_answ="";
                            switch (answersList.get(i))
                            {
                                case -2:real_answ="";break;
                                case -1:real_answ="";break;
                                case 0: real_answ=qlist.get(i).getAnswer1();break;
                                case 1: real_answ=qlist.get(i).getAnswer2();break;
                                case 2: real_answ=qlist.get(i).getAnswer3();break;
                                case 3: real_answ=qlist.get(i).getAnswer4();break;
                                case 4: real_answ=qlist.get(i).getAnswer5();break;
                            }
                            if(cor_answ.equals(real_answ))
                                correct++;
                        }

                        addScore(correct);
                    }
                });
    }

    public void showAlertDialogOfResults(int flag) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        builder.setPositiveButton("К результатам",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //чтобы открывались достижения при нжаатии на кнопку
                        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog, null);

        switch (flag)
        {
            case 0:
                dialogLayout.findViewById(R.id.imageAlert).setBackgroundResource(R.drawable.clock);
                dialogLayout.findViewById(R.id.rel).setBackgroundResource(R.color.colorOrange);
                break;
            case 1:
                dialogLayout.findViewById(R.id.imageAlert).setBackgroundResource(R.drawable.chat);
                dialogLayout.findViewById(R.id.rel).setBackgroundResource(R.color.colorDarkBlue);
                TextView tw = dialogLayout.findViewById(R.id.firstInfo);
                tw.setText("Тестирование завершено!");
                break;
        }
        alertDialog.setView(dialogLayout);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        alertDialog.show();
    }
}
