package com.example.limpiezaugb.adaptadores;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.limpiezaugb.DetallesArea;
import com.example.limpiezaugb.R;
import com.example.limpiezaugb.models.MainModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    public interface OnDetallesClickListener {
        void onDetallesClick(int position);
    }
    private OnDetallesClickListener detallesClickListener;

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.nombre.setText(model.getNombre());
        holder.zona.setText(model.getZona());
        holder.estado.setText(model.getEstado());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        myViewHolder viewHolder = new myViewHolder(view);

        viewHolder.setOnDetallesClickListener(new OnDetallesClickListener() {
            @Override
            public void onDetallesClick(int position) {
                MainModel selectedItem = getItem(position);
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetallesArea.class);
                intent.putExtra("nombre", selectedItem.getNombre());
                intent.putExtra("zona", selectedItem.getZona());
                intent.putExtra("estado", selectedItem.getEstado());
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView nombre, zona, estado;
        Button btnDetalles;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            nombre = (TextView)itemView.findViewById(R.id.txtEmpleado);
            zona = (TextView)itemView.findViewById(R.id.txtZona);
            estado = (TextView)itemView.findViewById(R.id.txtEstado);
            btnDetalles = itemView.findViewById(R.id.btnDetalles);

            btnDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detallesClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            detallesClickListener.onDetallesClick(position);
                        }
                    }
                }
            });
        }
        public void setOnDetallesClickListener(OnDetallesClickListener listener) {
            detallesClickListener = listener;
        }
    }
}