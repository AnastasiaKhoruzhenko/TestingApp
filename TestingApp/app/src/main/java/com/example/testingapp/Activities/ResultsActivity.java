package com.example.testingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.testingapp.Modules.AdapterResultsWithPieChart;
import com.example.testingapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String email = mAuth.getCurrentUser().getEmail();
    private List<Integer> countCorrect;
    private List<Integer> ticketCorrect;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recyclerView=findViewById(R.id.recyclerView);

        Toolbar toolbar=findViewById(R.id.toolBarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Результаты");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        countCorrect=new ArrayList<>();
        ticketCorrect=new ArrayList<>();

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

                        //проход по всем билетам и поиск того, результат в котором не равен 0
                        for(int i=1;i<=30;i++)
                        {
                            if(documentSnapshot.getData().containsKey(String.valueOf(i)) && (map.get(String.valueOf(i)))!=null)
                            {
                                countCorrect.add(Integer.valueOf(documentSnapshot.getData().get(String.valueOf(i)).toString()));
                                ticketCorrect.add(i);
                            }
                        }

                        if(countCorrect.size()==0)
                            tmp = "Вы еще не решили ни одного билета";

                        AdapterResultsWithPieChart adapterResultsWithPieChart=new AdapterResultsWithPieChart(countCorrect, getApplicationContext(), ticketCorrect);
                        recyclerView.setAdapter(adapterResultsWithPieChart);
                    }
                });
    }
}
