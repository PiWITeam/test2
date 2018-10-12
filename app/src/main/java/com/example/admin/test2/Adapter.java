package com.example.admin.test2;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;




public class Adapter extends  RecyclerView.Adapter<Adapter.ViewHolderMantenimiento>{
    @NonNull
    ArrayList<MantConstructor> listaMantenimientos;
    OnItemClickListener onItemClickListener;

    public Adapter(ArrayList<MantConstructor> listMantenimientos) {
        this.listaMantenimientos = listMantenimientos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolderMantenimiento onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.resultrecycledview, null,false);
        return new ViewHolderMantenimiento(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMantenimiento holder, int i) {
        holder.nombreLabel.setText(listaMantenimientos.get(i).getNombre());
        holder.tipoLabel.setText(listaMantenimientos.get(i).getTipo());
        holder.fechaLabel.setText(listaMantenimientos.get(i).getFecha());
        holder.bind(listaMantenimientos.get(i), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaMantenimientos.size();
    }

    public class ViewHolderMantenimiento extends RecyclerView.ViewHolder {

        TextView nombreLabel, tipoLabel, fechaLabel;

        public ViewHolderMantenimiento(@NonNull View itemView) {
            super(itemView);
            nombreLabel = itemView.findViewById(R.id.nombreLabel);
            tipoLabel = itemView.findViewById(R.id.tipoLabel);
            fechaLabel = itemView.findViewById(R.id.fechaLabel);
        }

        public void bind(final MantConstructor item, final OnItemClickListener onItemClickListener){
            itemView.findViewById(R.id.cardView1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(MantConstructor item);
    }
}
