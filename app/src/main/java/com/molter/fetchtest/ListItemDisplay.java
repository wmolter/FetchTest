package com.molter.fetchtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Class only used in the {@link RecyclerVersion} Activity for displaying each data point as an item
 * in the {@link RecyclerView}
 */
public class ListItemDisplay extends RecyclerView.ViewHolder {

    private TestListItem toDisplay;
    private TextView nameView, idView;
    public ListItemDisplay(View itemView){
        super(itemView);
        nameView = itemView.findViewById(R.id.name);
        idView = itemView.findViewById(R.id.idField);
    }

    public void SetDisplay(TestListItem toDisplay) {
        this.toDisplay = toDisplay;
        nameView.setText(toDisplay.getName());
        idView.setText(idView.getContext().getString(R.string.id_field, toDisplay.getId()));
    }

    public static final ListItemDisplay create(ViewGroup parent) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_display, parent, false);
        return new ListItemDisplay(view);
    }

}
