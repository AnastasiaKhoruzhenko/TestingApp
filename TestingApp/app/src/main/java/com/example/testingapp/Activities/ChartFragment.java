package com.example.testingapp.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.testingapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartFragment extends Fragment {

    private PieChart pieChart;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String email;
    private Integer count=0;
    private Integer allCount=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chart_lay, container, false);
        pieChart=v.findViewById(R.id.pieChart);
        email = getActivity().getIntent().getExtras().getString("Email");
        getResCount();

        return v;
    }

    private void getResCount()
    {
        db.collection("Users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> map = documentSnapshot.getData();
                        String tmp = "";
                        String container;

                        //если пользователь уже решил хотя бы 1 билет
                        if (map.containsKey(String.valueOf(30))) {
                            for (int i = 1; i <= 30; i++) {
                                if (documentSnapshot.getData().containsKey(String.valueOf(i))) {
                                    allCount+=11;
                                    if (map.get(String.valueOf(i)) == null) {
                                        container = "Не решен";
                                    } else {
                                        container = map.get(String.valueOf(i)).toString();
                                        count+= Integer.valueOf(container);
                                    }
                                }
                            }
                        }
                        List<PieEntry> pieEntries=new ArrayList<>();
                        pieEntries.add(new PieEntry(count, "Верно"));
                        pieEntries.add(new PieEntry(allCount-count, "Неверно"));

                        pieChart.setUsePercentValues(true);
                        PieDataSet dataSet=new PieDataSet(pieEntries, "Общие результаты");
                        dataSet.setColors(ContextCompat.getColor(getContext(), R.color.colorDarkBlue), ContextCompat.getColor(getContext(), R.color.colorLightBlue));
                        PieData pieData=new PieData(dataSet);
                        pieData.setValueFormatter(new PercentFormatter());
                        pieChart.setData(pieData);
                        //pieChart.invalidate();
                        pieChart.animateXY(1100, 1100);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
