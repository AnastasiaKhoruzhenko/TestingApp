package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testingapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseTicketActivity extends AppCompatActivity {

    TextView chooseTicketText;
    Button tic1,tic2,tic3,tic4,tic5,tic6,tic7,tic8,tic9,tic10,tic11,tic12,tic13,tic14,tic15,tic16,tic17,tic18,tic19,tic20,tic21,tic22,tic23,tic24,tic25,tic26,tic27,tic28,tic29,tic30;
    Button[] buttons={tic1,tic2,tic3,tic4,tic5,tic6,tic7,tic8,tic9,tic10,tic11,tic12,tic13,tic14,tic15,tic16,tic17,tic18,tic19,tic20,tic21,tic22,tic23,tic24,tic25,tic26,tic27,tic28,tic29,tic30};
    Integer [] BUTTON_ID={R.id.ticket1, R.id.ticket2, R.id.ticket3, R.id.ticket4, R.id.ticket5, R.id.ticket6, R.id.ticket7, R.id.ticket8, R.id.ticket9, R.id.ticket10, R.id.ticket11, R.id.ticket12, R.id.ticket13, R.id.ticket14, R.id.ticket15, R.id.ticket16, R.id.ticket17, R.id.ticket18, R.id.ticket19, R.id.ticket20, R.id.ticket21, R.id.ticket22, R.id.ticket23, R.id.ticket24, R.id.ticket25, R.id.ticket26, R.id.ticket27, R.id.ticket28, R.id.ticket29, R.id.ticket30};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ticket);

        for(int i=0;i<buttons.length;i++)
        {
            buttons[i]=findViewById(BUTTON_ID[i]);
        }

        chooseTicketText=(TextView)findViewById(R.id.chooseTicketText);

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), QuestionActivity.class);
                int index = Arrays.asList(BUTTON_ID).indexOf(v.getId());
                intent.putExtra("number", String.valueOf(index+1));
                startActivity(intent);
                finish();
            }
        };

        for(int i=0;i<buttons.length;i++)
        {
            buttons[i].setOnClickListener(onClickListener);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
