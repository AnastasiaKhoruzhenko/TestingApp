package com.example.testingapp.Modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.testingapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class AdapterResultsWithPieChart extends RecyclerView.Adapter<AdapterResultsWithPieChart.ViewHolder> {

    private List<Integer> listItems;
    private List<Integer> ticketCorrect;
    private Context mContext;

    public AdapterResultsWithPieChart(List<Integer> listItems, Context mContext, List<Integer> ticketCorrect) {
        this.listItems = listItems;
        this.mContext = mContext;
        this.ticketCorrect=ticketCorrect;
    }

    @NonNull
    @Override
    public AdapterResultsWithPieChart.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        final AdapterResultsWithPieChart.ViewHolder viewHolder = new AdapterResultsWithPieChart.ViewHolder(v);
        return new AdapterResultsWithPieChart.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResultsWithPieChart.ViewHolder viewHolder, int i) {
        viewHolder.txtTitle.setText("Билет №"+String.valueOf(ticketCorrect.get(i)));
        viewHolder.txtDescription.setText("В билете 11 вопросов");

        List<PieEntry> value=new ArrayList<>();
        value.add(new PieEntry(listItems.get(i)));
        value.add(new PieEntry(11-listItems.get(i)));

        viewHolder.pieChart.setUsePercentValues(true);
        PieDataSet pieDataSet=new PieDataSet(value,"res");
        PieData pieData=new PieData(pieDataSet);
        viewHolder.pieChart.setData(pieData);
        viewHolder.pieChart.animateXY(1100, 1100);
        viewHolder.pieChart.getLegend().setEnabled(false);
        viewHolder.pieChart.setCenterText(String.valueOf(listItems.get(i))+"/11");

        pieDataSet.setColors(ContextCompat.getColor(mContext, R.color.colorDarkBlue), ContextCompat.getColor(mContext, R.color.colorLightBlue));
        viewHolder.pieChart.getDescription().setText("");
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDescription;
        CardView cardView;
        PieChart pieChart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            pieChart=itemView.findViewById(R.id.pieChart);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
