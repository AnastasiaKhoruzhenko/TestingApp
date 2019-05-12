package com.example.testingapp.Modules;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapp.Activities.InfoActivity;
import com.example.testingapp.R;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Person> listItems;
    private Context mContext;

    public MyAdapter(List<Person> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //launch activity with info about selected person

                Toast.makeText(viewGroup.getContext(), String.valueOf(viewHolder.txtTitle.getText()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, InfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Email", viewHolder.txtDescription.getText());
                mContext.startActivity(intent);
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        final Person item = listItems.get(i);
        viewHolder.txtTitle.setText(item.getSurname()+"  "+item.getName());
        viewHolder.txtDescription.setText(item.getEmail());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTitle;
        TextView txtDescription;
        CardView cardView;
        PieChart pieChart;

        public ViewHolder(View itemView)
        {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            pieChart=itemView.findViewById(R.id.pieChart);
            pieChart.setNoDataText("");
        }
    }
}
