package com.example.admin.test2;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<MantConstructor> mantenimientos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nombreLabel;
        public TextView tipoLabel;
        public TextView fechaLabel;
        public ViewHolder(View v) {
            super(v);
            nombreLabel = v.findViewById(R.id.equipoLabel);
            tipoLabel = v.findViewById(R.id.tipoLAbel);
            fechaLabel = v.findViewById(R.id.fechaLabel);
        }
    }

//    // Provide a suitable constructor (depends on the kind of dataset)
//    public Adapter(String[] myDataset) {
//        mDataset = myDataset;
//    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.resultrecycledview, null);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MantConstructor mant = mantenimientos.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.nombreLabel.setText(mant.getNombre());
        holder.tipoLabel.setText(mant.getTipo());
        holder.fechaLabel.setText(mant.getFecha());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mantenimientos.size();
    }
}
