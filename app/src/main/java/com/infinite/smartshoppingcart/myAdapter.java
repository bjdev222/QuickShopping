package com.infinite.smartshoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapter extends FirebaseRecyclerAdapter<model,myAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public myAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull model model) {
        holder.title.setText(model.getTitle());
        holder.price.setText(model.getPrice());
        holder.qty.setText(model.getSt());

        Glide.with(holder.pImage.getContext()).load(model.getPurl()).into(holder.pImage);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scanpro,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

TextView title,price,qty;
ImageView pImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            pImage=itemView.findViewById(R.id.pimage);
            title=itemView.findViewById(R.id.pname);
            price=itemView.findViewById(R.id.pprice);
            qty=itemView.findViewById(R.id.pqty);


        }
    }
}
