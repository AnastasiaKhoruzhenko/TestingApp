package com.example.testingapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapp.Modules.Question;
import com.example.testingapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {

    TextView questionNumber, questionText, timer;
    Button answer1, answer2, answer3, answer4, answer5;

    String ticket_number;
    List<Question> qlist;
    Integer correct = 0, total = 0;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Log.d("success", "  start question activity");

        questionNumber = (TextView) findViewById(R.id.questonNumber);
        questionText = (TextView) findViewById(R.id.questionText);
        answer1 = (Button) findViewById(R.id.ans1);
        answer2 = (Button) findViewById(R.id.ans2);
        answer3 = (Button) findViewById(R.id.ans3);
        answer4 = (Button) findViewById(R.id.ans4);
        answer5 = (Button) findViewById(R.id.ans5);
        timer=(TextView)findViewById(R.id.timer);

        Intent intent = getIntent();
//        //получить выбранный номер билета
        ticket_number = intent.getStringExtra("number");

        Log.d("success", "  read intentExtra from HomeActivity");

        // чтение вопросов
        qlist = new ArrayList<>();

        readQuestions(ticket_number);

    }

    private void updateQuestion(int i)
    {
        total++;
        if (total > 11)
        {
            countDownTimer.cancel();
            Toast.makeText(QuestionActivity.this, "Correct = "+correct, Toast.LENGTH_SHORT).show();
            addScore();
            showAlertDialogOfResults(ticket_number);
        } else {
                switch (qlist.get(i).size) {
                    case 4:
                        answer1.setText(qlist.get(i).getAnswer1());
                        answer2.setText(qlist.get(i).getAnswer2());
                        answer3.setVisibility(View.GONE);
                        answer4.setVisibility(View.GONE);
                        answer5.setVisibility(View.GONE);
                        questionText.setText(qlist.get(i).getQuestion());
                        questionNumber.setText("Вопрос " + total + " из 11");
                        chooseFrom2Answers(i);
                        break;
                    case 5:
                        answer1.setText(qlist.get(i).getAnswer1());
                        answer2.setText(qlist.get(i).getAnswer2());
                        answer3.setText(qlist.get(i).getAnswer3());
                        answer4.setVisibility(View.GONE);
                        answer5.setVisibility(View.GONE);
                        questionText.setText(qlist.get(i).getQuestion());
                        questionNumber.setText("Вопрос " + total + " из 11");
                        chooseFrom3Answers(i);
                        break;
                    case 6:
                        answer1.setText(qlist.get(i).getAnswer1());
                        answer2.setText(qlist.get(i).getAnswer2());
                        answer3.setText(qlist.get(i).getAnswer3());
                        answer4.setText(qlist.get(i).getAnswer4());
                        answer5.setVisibility(View.GONE);
                        questionText.setText(qlist.get(i).getQuestion());
                        questionNumber.setText("Вопрос " + total + " из 11");
                        answer1.setEnabled(true);
                        answer2.setEnabled(true);
                        answer3.setEnabled(true);
                        answer4.setEnabled(true);
                        chooseFrom4Answers(i);
                        break;
                    case 7:
                        answer1.setText(qlist.get(i).getAnswer1());
                        answer2.setText(qlist.get(i).getAnswer2());
                        answer3.setText(qlist.get(i).getAnswer3());
                        answer4.setText(qlist.get(i).getAnswer4());
                        answer5.setText(qlist.get(i).getAnswer5());
                        questionText.setText(qlist.get(i).getQuestion());
                        questionNumber.setText("Вопрос " + total + " из 11");
                        chooseFrom5Answers(i);
                        break;
                }
        }
    }

    private void chooseFrom5Answers(final int i)
    {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        answer4.setEnabled(true);
        answer5.setEnabled(true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ans1:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);
                        answer5.setEnabled(false);

                        if (answer1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans2:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);
                        answer5.setEnabled(false);
                        if (answer2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans3:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);
                        answer5.setEnabled(false);
                        if (answer3.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans4:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);
                        answer5.setEnabled(false);
                        if (answer4.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans5:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);
                        answer5.setEnabled(false);
                        if (answer5.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                }
            }
        };
        answer1.setOnClickListener(onClickListener);
        answer2.setOnClickListener(onClickListener);
        answer3.setOnClickListener(onClickListener);
        answer4.setOnClickListener(onClickListener);
        answer5.setOnClickListener(onClickListener);
    }

    private void chooseFrom4Answers(final int i) {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        answer4.setEnabled(true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ans1:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);

                        if (answer1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans2:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);
                        if (answer2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans3:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);
                        if (answer3.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans4:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);
                        answer4.setEnabled(false);
                        if (answer4.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                }
            }
        };
        answer1.setOnClickListener(onClickListener);
        answer2.setOnClickListener(onClickListener);
        answer3.setOnClickListener(onClickListener);
        answer4.setOnClickListener(onClickListener);
    }

    private void chooseFrom3Answers(final int i) {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ans1:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);

                        if (answer1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans2:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);

                        if (answer2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans3:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        answer3.setEnabled(false);

                        if (answer3.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                }
            }
        };

        answer1.setOnClickListener(onClickListener);
        answer2.setOnClickListener(onClickListener);
        answer3.setOnClickListener(onClickListener);
    }

    private void chooseFrom2Answers(final int i) {
        answer1.setEnabled(true);
        answer2.setEnabled(true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ans1:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        if (answer1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                    case R.id.ans2:
                        answer1.setEnabled(false);
                        answer2.setEnabled(false);
                        if (answer2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        } else {
                            //answer is wrong...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestion(i + 1);
                                }
                            }, 1500);
                        }
                        break;
                }
            }
        };

        answer1.setOnClickListener(onClickListener);
        answer2.setOnClickListener(onClickListener);
    }

    /**
     * To show the information about the end of the test in this ticket
     *
     * @param ticket_number
     */
    public void showAlertDialogOfResults(final String ticket_number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Результаты прохождения тестирования!");
        builder.setMessage("Тестирование завершено! Вы можете посмотреть ваши результаты по этому билету во вкладке \"Результаты\"");
        builder.setCancelable(true);
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void readQuestions(String ticket_number) {
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
                        updateQuestion(0);
                    }
                });
    }

    public void addScore()
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

                        for (int i = 0; i < 30; ++i)
                            map.put(String.valueOf(i), (String)documentSnapshot.getData().get(String.valueOf(i)));
                        map.put(ticket_number, String.valueOf(correct) + "/11");
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

    public void startTimer()
    {
        countDownTimer = new CountDownTimer( 60000, 1000)
        {
            public void onTick(long l)
            {
                timer.setText(""+l/1000);
            }

            public void onFinish()
            {
                addScore();
                timer.setText("Время истекло!");
                showAlertDialogOfResults(ticket_number);
                countDownTimer.cancel();
            }
        };
        countDownTimer.start();

    }
}
