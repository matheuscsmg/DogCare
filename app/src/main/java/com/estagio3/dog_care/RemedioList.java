package com.estagio3.dog_care;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by matheus on 18/11/2017.
 */

public class RemedioList extends ArrayAdapter<Remedio> {
    private Activity context;
    List<Remedio> remedios2;

    public RemedioList(Activity context, List<Remedio> remedios2) {
        super(context, R.layout.layout_remedio_list, remedios2);
        this.context = context;
        this.remedios2 = remedios2;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_remedio_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Remedio remedio = remedios2.get(position);
        textViewName.setText(remedio.getNome());
        textViewGenre.setText(remedio.getGenre());

        return listViewItem;
    }
}
