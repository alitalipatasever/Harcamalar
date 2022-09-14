package com.alitalipatasever.harcamalar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder> {

    List<Listeler> listeler;
    LayoutInflater inflater;
    Context context;
    private ItemClickListener mItemListener;

    public CustomRecyclerAdapter(Context context, List<Listeler> listeler, ItemClickListener itemClickListener) {
        inflater = LayoutInflater.from(context);
        this.listeler = listeler;
        this.context = context;
        this.mItemListener = itemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_listeler, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Listeler selectedProduct = listeler.get(position);
        holder.setData(selectedProduct, position);

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(listeler.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listeler.size();
    }

    public interface ItemClickListener {
        void onItemClick(Listeler listeler);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvEmail, tvTarih, tvListeAdi;
        Listeler listeler1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvTarih = itemView.findViewById(R.id.TVtarih);
            tvListeAdi = itemView.findViewById(R.id.tvListeAdi);
        }

        public void setData(Listeler listeler1, int position) {
            tvEmail.setText(listeler1.getEmail());
            tvTarih.setText(listeler1.getTarih());
            tvListeAdi.setText(listeler1.getlisteAdi());
            listeler1 = listeler.get(position);
        }


        @Override
        public void onClick(View v) {


        }


    }
}
