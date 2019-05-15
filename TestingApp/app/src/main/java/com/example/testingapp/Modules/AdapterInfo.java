package com.example.testingapp.Modules;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testingapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterInfo extends RecyclerView.Adapter<AdapterInfo.ViewHolder>{

    private List<String> scoresList;

    public AdapterInfo(List<String> list)
    {
        scoresList=new ArrayList<>();
        this.scoresList=list;
    }

    @NonNull
    @Override
    public AdapterInfo.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.info_ticket_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInfo.ViewHolder viewHolder, int i) {
        final String item = scoresList.get(i);
        viewHolder.txtNumber.setText("Билет №"+String.valueOf(i+1));
        if(!item.equals("Не решен"))
            viewHolder.txtTicketInfo.setText(item+"/11");
        else
            viewHolder.txtTicketInfo.setText(item);
    }

    @Override
    public int getItemCount() {
        return scoresList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtNumber;
        TextView txtTicketInfo;
        CardView cardView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            txtNumber = itemView.findViewById(R.id.ticketNumberInfo);
            txtTicketInfo = itemView.findViewById(R.id.scoreInfo);
            cardView = (CardView) itemView.findViewById(R.id.info_item);
        }
    }
}
