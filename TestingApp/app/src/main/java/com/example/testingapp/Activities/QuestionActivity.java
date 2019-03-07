package com.example.testingapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testingapp.R;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


    }

    /**
     * To show the information about the end of the test in this ticket
     * @param ticket_number
     * @param score
     */
    public void showAlertDialogOfResults(final String  ticket_number,final String score){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Результаты прохождения тестирования!");
        builder.setMessage("Тестирование завершено! Вы можете посмотреть ваши результаты по этому билету во вкладке \"Результаты\"");
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("tiket_number", ticket_number);
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();
                    }
                });

        builder.setNeutralButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //исправить чтобы открывались достижения при нжаатии на кнопку
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("tiket_number", ticket_number);
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
