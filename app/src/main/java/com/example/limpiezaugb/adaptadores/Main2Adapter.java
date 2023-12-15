package com.example.limpiezaugb.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.limpiezaugb.R;
import com.example.limpiezaugb.models.MainModel;
import com.example.limpiezaugb.models.Model2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        String estado = model.getEstado();

        // Configura el fondo de la tarjeta según el estado
        if ("Sin comenzar".equals(estado)) {
            holder.itemView.setBackgroundResource(R.drawable.card_background_sin_comenzar);
        } else if ("En progreso".equals(estado)) {
            holder.itemView.setBackgroundResource(R.drawable.card_background_en_progreso);
        } else if ("Tarea completada".equals(estado)) {
            holder.itemView.setBackgroundResource(R.drawable.card_background_tarea_completada);
        }

        // Verificar si el estado es "Tarea completada" y ocultar la vista si es así
        if ("Tarea completada".equals(estado)) {
            holder.confirmarButton.setVisibility(View.GONE);
        } else {
            holder.confirmarButton.setVisibility(View.VISIBLE);

            holder.zona.setText(model.getZona());
            holder.hora.setText(model.getHora());

            // Obtener la referencia de la asignación actual en la base de datos
            DatabaseReference asignacionRef = FirebaseDatabase.getInstance().getReference().child("areas").child(getRef(position).getKey());

            // Agregar un listener de clic al botón "Confirmar"
            holder.confirmarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Obtener el estado actual
                    asignacionRef.child("estado").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String estadoActual = snapshot.getValue(String.class);

                            // Cambiar el estado en función del estado actual
                            if ("Sin comenzar".equals(estadoActual)) {
                                // Cambiar a "En progreso" si estaba en "Sin comenzar"
                                asignacionRef.child("estado").setValue("En progreso");
                                holder.confirmarButton.setText("Completar");
                            } else if ("En progreso".equals(estadoActual)) {
                                // Cambiar a "Tarea completada" si estaba en "En progreso"
                                asignacionRef.child("estado").setValue("Tarea completada");
                                holder.confirmarButton.setText("Tarea completada");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Maneja el error si es necesario
                        }
                    });
                }
            });
        }
    }

    @NonNull
    @Override
    public Main2Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_2,parent,false);
        Main2Adapter.myViewHolder viewHolder = new Main2Adapter.myViewHolder(view);

        return viewHolder;
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        Button confirmarButton;

        CircleImageView img;
        TextView zona, hora;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            zona = (TextView)itemView.findViewById(R.id.txtZona);
            hora = (TextView)itemView.findViewById(R.id.txtHora);
            confirmarButton = (Button) itemView.findViewById(R.id.btnConfirmar);

        }
    }

}
