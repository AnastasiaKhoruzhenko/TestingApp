package com.example.testingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.testingapp.Modules.MyAdapter;
import com.example.testingapp.Modules.Person;
import com.example.testingapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployerHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private Toolbar toolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);

        toolbar=findViewById(R.id.toolBar);
        searchView=findViewById(R.id.searchView);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Все прошедшие");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        final FirebaseAuth myAuth=FirebaseAuth.getInstance();
        final List<Person> personList=new ArrayList<>();

        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    Person person=new Person(documentSnapshot.getString("Name"), documentSnapshot.getString("Surname"), documentSnapshot.getString("Email"), documentSnapshot.getString("Employer"));
                    if(person.getEmployerEmail()!=null && person.getEmployerEmail().equals(myAuth.getCurrentUser().getEmail()))
                        personList.add(person);
                }

                adapter=new MyAdapter(personList, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }
        });

        if(searchView!=null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    searchThatValue(s, personList);
                    return false;
                }
            });
        }
    }

        private void searchThatValue(String str, List<Person> personList) {
        List<Person> myList=new ArrayList<>();
        for(Person person:personList)
        {
            if(person.getName().toLowerCase().contains(str.toLowerCase())
                    ||person.getSurname().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(person);
            }
        }

        adapter=new MyAdapter(myList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logOut)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return true;
    }

//    public void getPeople()
//    {
//        final FirebaseAuth myAuth=FirebaseAuth.getInstance();
//
//        final String email=myAuth.getCurrentUser().getEmail();
//
//        FirebaseFirestore db=FirebaseFirestore.getInstance();
//
//        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
//                {
//                    Person person=new Person(documentSnapshot.getString("Name"), documentSnapshot.getString("Surname"), documentSnapshot.getString("Email"), documentSnapshot.getString("Employer"));
//                    if(person.getEmployerEmail()!=null && person.getEmployerEmail().equals(email))
//                        personList.add(person);
//                }
//
//                getAllItems(personList);
//            }
//        });
//    }
//
//    private void getAllItems(List<Person> personList) {
//        for(int i=0;i<personList.size();i++)
//        {
//            listItems.add(new RecyclerItem(personList.get(i).getEmail(), personList.get(i).getSurname()+" "+personList.get(i).getName()));
//        }
//        adapter=new MyAdapter(listItems, this);
//        recyclerView.setAdapter(adapter);
//    }
}