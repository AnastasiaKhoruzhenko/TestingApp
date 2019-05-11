package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testingapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseTicketActivity extends AppCompatActivity {

    private TextView chooseTicketText;
    private FirebaseAuth myAuth;
    private FirebaseFirestore db;
    private Button tic1,tic2,tic3,tic4,tic5,tic6,tic7,tic8,tic9,tic10,tic11,tic12,tic13,tic14,tic15,tic16,tic17,tic18,tic19,tic20,tic21,tic22,tic23,tic24,tic25,tic26,tic27,tic28,tic29,tic30;
    private Button[] buttons={tic1,tic2,tic3,tic4,tic5,tic6,tic7,tic8,tic9,tic10,tic11,tic12,tic13,tic14,tic15,tic16,tic17,tic18,tic19,tic20,tic21,tic22,tic23,tic24,tic25,tic26,tic27,tic28,tic29,tic30};
    private Integer [] BUTTON_ID={R.id.ticket1, R.id.ticket2, R.id.ticket3, R.id.ticket4, R.id.ticket5, R.id.ticket6, R.id.ticket7, R.id.ticket8, R.id.ticket9, R.id.ticket10, R.id.ticket11, R.id.ticket12, R.id.ticket13, R.id.ticket14, R.id.ticket15, R.id.ticket16, R.id.ticket17, R.id.ticket18, R.id.ticket19, R.id.ticket20, R.id.ticket21, R.id.ticket22, R.id.ticket23, R.id.ticket24, R.id.ticket25, R.id.ticket26, R.id.ticket27, R.id.ticket28, R.id.ticket29, R.id.ticket30};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ticket);

        chooseTicketText=(TextView)findViewById(R.id.chooseTicketText);

        Toolbar toolbar=findViewById(R.id.toolBarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAuth = FirebaseAuth.getInstance();
        final String email = myAuth.getCurrentUser().getEmail();
        db = FirebaseFirestore.getInstance();

        for(int i=0;i<buttons.length;i++)
        {
            buttons[i]=findViewById(BUTTON_ID[i]);
        }
            //те билеты, которые уже решены, сделать некликабельными
            for (int i = 0; i < BUTTON_ID.length; i++)
            {
                final int finalI = i;
                db.collection("Users")
                        .document(email)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                        {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot)
                            {
                                Map<String, Object> map = new HashMap<>();
                                if(documentSnapshot.getData().containsKey("1"))
                                    if (documentSnapshot.getData().get(String.valueOf(finalI + 1)) != null)
                                        buttons[finalI].setEnabled(false);
                            }
                        });
            }

        View.OnClickListener onClickListener=new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(), QuestionTestActivity.class);
                int index = Arrays.asList(BUTTON_ID).indexOf(v.getId());
                intent.putExtra("number", String.valueOf(index+1));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        };

        for (Button button : buttons)
        {
            button.setOnClickListener(onClickListener);
        }
    }

//    @Override
//    public void onBackPressed()
//    {
//        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//        startActivity(intent);
//        finish();
//    }
}
