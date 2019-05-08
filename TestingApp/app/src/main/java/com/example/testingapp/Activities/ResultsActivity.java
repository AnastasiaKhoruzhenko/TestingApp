package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testingapp.Modules.AdapterInfo;
import com.example.testingapp.Modules.AdapterResultsWithPieChart;
import com.example.testingapp.Modules.RecyclerItem;
import com.example.testingapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    private TextView resultsText, allResults;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String email = mAuth.getCurrentUser().getEmail();


    private List<RecyclerItem> listItems;
    private List<Integer> countCorrect;
    private List<Integer> ticketCorrect;
    private RecyclerView recyclerView;
    private AdapterResultsWithPieChart adapterResultsWithPieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setTitle("Результаты");


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorButton));
        listItems=new ArrayList<>();
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

                        adapterResultsWithPieChart=new AdapterResultsWithPieChart(countCorrect, getApplicationContext(), ticketCorrect);
                        recyclerView.setAdapter(adapterResultsWithPieChart);
                    }
                });
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//        startActivity(intent);
//        finish();
//    }
}
