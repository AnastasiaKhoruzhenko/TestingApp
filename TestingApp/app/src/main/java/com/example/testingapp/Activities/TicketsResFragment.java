package com.example.testingapp.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapp.Modules.AdapterInfo;
import com.example.testingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketsResFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView nameSurname, emailText;
    private List<String> scoresList=new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterInfo adapterInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tickets_res_lay, container, false);
        nameSurname = v.findViewById(R.id.NameSurname);
        emailText = v.findViewById(R.id.Email);
        recyclerView = v.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(manager);

        String email = getActivity().getIntent().getExtras().getString("Email");

        getInfoAboutUser(email);

        return v;
    }

    private void getInfoAboutUser(final String email) {
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
                                    if (map.get(String.valueOf(i)) == null) {
                                        container = "Не решен";
                                    } else {
                                        container = map.get(String.valueOf(i)).toString();
                                    }
                                    scoresList.add(container);
                                }
                            }

                            adapterInfo = new AdapterInfo(scoresList);
                            recyclerView.setAdapter(adapterInfo);
                        }

                        //для общей информации
                        tmp = documentSnapshot.getData().get(String.valueOf("Surname")).toString() + "  " + documentSnapshot.getData().get(String.valueOf("Name")).toString();
                        nameSurname.setText("ФИ: "+tmp);
                        emailText.setText("Email: "+documentSnapshot.getData().get(String.valueOf("Email")).toString());

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
