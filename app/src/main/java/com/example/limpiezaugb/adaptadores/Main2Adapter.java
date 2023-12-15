package com.example.limpiezaugb.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.limpiezaugb.R;
import com.example.limpiezaugb.models.MainModel;
import com.example.limpiezaugb.models.Model2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Adapter extends FirebaseRecyclerAdapter<Model2,Main2Adapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public Main2Adapter(@NonNull FirebaseRecyclerOptions<Model2> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull Main2Adapter.myViewHolder holder, int position, @NonNull Model2 model) {
        holder.zona.setText(model.getZona());
        holder.hora.setText(model.getHora());
    }

    @NonNull
    @Override
    public Main2Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_2,parent,false);
        Main2Adapter.myViewHolder viewHolder = new Main2Adapter.myViewHolder(view);

        return viewHolder;
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView zona, hora;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            zona = (TextView)itemView.findViewById(R.id.txtZona);
            hora = (TextView)itemView.findViewById(R.id.txtHora);

        }
    }

}
