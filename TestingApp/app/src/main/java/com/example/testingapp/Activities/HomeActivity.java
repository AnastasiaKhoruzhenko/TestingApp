package com.example.testingapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView testInfo;
    private FirebaseAuth myAuth;

    private FirebaseFirestore db;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";



    Session session=null;
    ProgressDialog progressDialog=null;
    Context context=null;
    String rec;
    String sub, text="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        testInfo=(TextView)findViewById(R.id.testInformation);

        //конструирование навигационного меню
        configureNavigationaBar();

        //конструирование toolbar со значком меню
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarHome);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.menu);
        actionbar.setDisplayHomeAsUpEnabled(true);


        myAuth = FirebaseAuth.getInstance();

        //read rules text from txt file
        InputStream inputStream = getResources().openRawResource(R.raw.rules);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        testInfo.setText(byteArrayOutputStream.toString());
    }

    private void configureNavigationaBar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.dl);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                //user choose the category in the menu
                switch (id) {
                    case R.id.listOfTickets:
                        Intent intent=new Intent(getApplicationContext(), ChooseTicketActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        break;
                    case R.id.results:
                        Intent intent2=new Intent(getApplicationContext(), ResultsActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent2);
                        break;
                    case R.id.setEmployerEmail:
                        showAlertDialogWithEmailField();
                        Toast.makeText(HomeActivity.this, "Указать почту работодателя", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sendResults:
                        getResults();
                        Toast.makeText(HomeActivity.this, "Отправить результаты", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.exit:
                        showAlertDialogExit();
                        break;
                    default:
                        Toast.makeText(HomeActivity.this, "Something goes wrong...", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void sendNew() {
        context=this;
        rec="nastia.khoruzhenko@gmail.com";
        sub="Test";

        Properties properties=new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        session=Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("nastia.khoruzhenko@gmail.com", "Sotufi-24");
            }
        });

        progressDialog=ProgressDialog.show(context, "", "Отправка результатов...", true);

        RetrieveFeedTask task=new RetrieveFeedTask();
        task.execute();

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {

            try{

                Message message=new MimeMessage(session);
                message.setFrom(new InternetAddress("lizasayfutdinova@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(sub);
                message.setContent(text, "text/plain; charset=utf-8");

                Transport.send(message);

            }catch (MessagingException e)
            {
                e.printStackTrace();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();

        }
    }

    private void getResults() {
        final String[] to = {""};
        final String[] resMessage = {""};
        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(myAuth.getCurrentUser().getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getData().containsKey("Employer") && (documentSnapshot.getData().get("Employer")!=null))
                            to[0] = String.valueOf(documentSnapshot.getData().get("Employer"));

                        final List<String> list=new ArrayList<>();

                        db.collection("Users")
                                .document(myAuth.getCurrentUser().getEmail())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Map<String, Object> map=documentSnapshot.getData();

                                        //проход по всем билетам и поиск того, результат в котором не равен 0
                                        for(int i=1;i<=30;i++)
                                        {
                                            if(documentSnapshot.getData().containsKey(String.valueOf(i)))
                                            {
                                                if(documentSnapshot.getData().get(String.valueOf(i))==null)
                                                    list.add("Не решен");
                                                else
                                                    list.add(documentSnapshot.getData().get(String.valueOf(i)).toString());
                                            }
                                        }

                                        if(list.size()==0)
                                        {
                                            list.add("Не было решено ни одного билета");
                                            text="Не было решено ни одного билета."+"\n\n\n"+"С уважением, \n"+documentSnapshot.getData().get("Surname")+" "+documentSnapshot.getData().get("Name");
                                        }else {
                                            for (int i = 0; i < list.size(); i++)
                                                text += "Результат билета номер " + (i + 1) + ":  " + list.get(i) + "\n";
                                            text += "\n\n\nС уважением, \n" + documentSnapshot.getData().get("Surname") + " " + documentSnapshot.getData().get("Name");
                                        }

                                        sendNew();
                                    }
                                });
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
    }

    /**
     * To show to user the info about the test
     */
    public void showAlertDialogWithEmailField(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите почту работодателя");
        builder.setCancelable(false);
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean[] wantToCloseDialog = {false};

                final String email = myAuth.getCurrentUser().getEmail();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users")
                        .document(email)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Map<String, Object> map = new HashMap<>();

                                if (isEmailValid(input.getText().toString())) {
                                    map.put("Employer", input.getText().toString());
                                    updateEmployerEmail(email, map);
                                    wantToCloseDialog[0] = true;
                                }else{
                                    Toast.makeText(HomeActivity.this, "Неверная маска электронной почты", Toast.LENGTH_SHORT).show();
                                }

                                if (wantToCloseDialog[0])
                                    alertDialog.dismiss();
                            }
                        });
                }
            });
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    public boolean isEmailValid(String email) {
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    public void updateEmployerEmail(String email, Map<String, Object> map) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(email)
                .update(map);
    }

    public void showAlertDialogExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вы действительно хотите выйти из аккаунта?");
        builder.setCancelable(true);
        builder.setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //чтобы открывались достижения при нжаатии на кнопку
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //  ДОПИСААААТЬ
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
//        else{
//
//        }
    }
}
