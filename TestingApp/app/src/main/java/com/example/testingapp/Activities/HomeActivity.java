package com.example.testingapp.Activities;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.testingapp.R;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = (DrawerLayout) findViewById(R.id.dl);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                //user choose the category in the menu
                switch (id) {
                    case R.id.listOfTickets:
                        Toast.makeText(HomeActivity.this, "Список билетов", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.infoAboutTest:
                        Toast.makeText(HomeActivity.this, "Информация о тесте", Toast.LENGTH_SHORT).show();
                        showAlertDialogOfInfo();
                        break;
                    case R.id.results:
                        Toast.makeText(HomeActivity.this, "Результаты", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sendResults:
                        Toast.makeText(HomeActivity.this, "Отправить результаты", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.exit:
                        Toast.makeText(HomeActivity.this, "Выйти", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(HomeActivity.this, "Something goes wrong...", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * To show to user the info about the test
     */
    public void showAlertDialogOfInfo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Информация о тесте");
        builder.setMessage("СООБЩЕНИЕ.........");
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
