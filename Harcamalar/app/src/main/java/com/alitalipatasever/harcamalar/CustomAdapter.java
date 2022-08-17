package com.alitalipatasever.harcamalar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Harcamalar> {
    private Activity context;
    private List<Harcamalar> harcamalar;
    String emailAd;

    public CustomAdapter(Activity context, List<Harcamalar> harcamalar){
        super(context, R.layout.list_item, harcamalar);
        this.context = context;
        this.harcamalar = harcamalar;
    }

    public View getView(int position, View convetView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item,null,true);

        TextView tvEmail = listViewItem.findViewById(R.id.tvEmail);
        TextView tvTarih = listViewItem.findViewById(R.id.TVtarih);
        TextView tvAciklama = listViewItem.findViewById(R.id.TVdetay);
        TextView tvTutar = listViewItem.findViewById(R.id.txtTutar);



        Harcamalar harcama = harcamalar.get(position);

        //Email ön ad
        String email = harcama.getEmail();
        String[] For_split_email = email.split("[@]");
        for (int j = 0; j <= For_split_email.length - 1; j++)
        {
            //System.out.println("splited emails----------" + For_split_email[j]);
            email = For_split_email[j];
        }
        email = For_split_email[0];

        //TL simge eki
        String tlSimge = harcama.getTutar()+" ₺";

        tvEmail.setText(email);
        tvTarih.setText(harcama.getTarih());
        tvAciklama.setText(harcama.getAciklama());
        tvTutar.setText(tlSimge);

        return listViewItem;
    }
}
