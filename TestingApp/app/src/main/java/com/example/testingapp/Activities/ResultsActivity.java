package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.testingapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    TextView resultsText, allResults;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String email = mAuth.getCurrentUser().getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsText=findViewById(R.id.resultText);
        allResults=findViewById(R.id.allResults);
        allResults.setMovementMethod(new ScrollingMovementMethod());

        LoadResults();


    }

    public void LoadResults()
    {
        db.collection("Users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> map=documentSnapshot.getData();
                        int countTickets=0;
                        String tmp="";

                        //проход епо всем билетам и поиск того, результат в котором не равен 0
                        for(int i=1;i<=30;i++)
                        {
                            if(documentSnapshot.getData().containsKey(String.valueOf(i)) && (map.get(String.valueOf(i)))!=null)
                            {
                                countTickets++;
                                tmp=tmp.concat("Билет №"+i+":  "+map.get(String.valueOf(i)).toString()+"\n\n");
                            }
                        }

                        if(countTickets==0)
                            tmp="Вы еще не решали ни одного билета.";

                        allResults.setText(tmp);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
