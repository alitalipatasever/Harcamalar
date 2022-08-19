package com.alitalipatasever.harcamalar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterListeler extends ArrayAdapter<Listeler> {
    private Activity context;
    private List<Listeler> listeler;
    String emailAd;

    public CustomAdapterListeler(Activity context, List<Listeler> listeler){
        super(context, R.layout.list_item_listeler, listeler);
        this.context = context;
        this.listeler = listeler;
    }

    public View getView(int position, View convetView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_listeler,null,true);

        TextView tvEmail = listViewItem.findViewById(R.id.tvEmail);
        TextView tvTarih = listViewItem.findViewById(R.id.TVtarih);
        TextView tvListeAdi = listViewItem.findViewById(R.id.tvListeAdi);
        TextView tvListeId = listViewItem.findViewById(R.id.tvListeId);



        Listeler listeler1 = listeler.get(position);

        tvEmail.setText(listeler1.getEmail());
        tvTarih.setText(listeler1.getTarih());
        tvListeAdi.setText(listeler1.getlisteAdi());
        tvListeId.setText(listeler1.getId());

        return listViewItem;
    }
}
