package com.example.testingapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapp.Modules.AdapterInfo;
import com.example.testingapp.Modules.MyAdapter;
import com.example.testingapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class InfoActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    Bundle mArg;
    private String email;
    Integer count=0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.toolBarInfo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        frameLayout=findViewById(R.id.fragment_container);

        email = getIntent().getStringExtra("Email");
        mArg = new Bundle();
        mArg.putString("Email", email);

        Fragment fragment=new ChartFragment();
        fragment.setArguments(mArg);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment=null;

                    switch (menuItem.getItemId())
                    {
                        case R.id.navChart:
                            selectedFragment=new ChartFragment();
                            selectedFragment.setArguments(mArg);
                            break;
                        case R.id.navTickets:
                            selectedFragment=new TicketsResFragment();
                            selectedFragment.setArguments(mArg);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}
