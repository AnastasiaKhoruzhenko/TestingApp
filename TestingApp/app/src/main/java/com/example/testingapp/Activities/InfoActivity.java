package com.example.testingapp.Activities;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.testingapp.R;

import androidx.annotation.RequiresApi;

public class InfoActivity extends AppCompatActivity {

    private Bundle mArg;

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

        FrameLayout frameLayout = findViewById(R.id.fragment_container);

        String email = getIntent().getStringExtra("Email");
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
