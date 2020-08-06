package gridentertainment.net.projectpickle.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import gridentertainment.net.projectpickle.Models.SellerItem;
import gridentertainment.net.projectpickle.R;
import gridentertainment.net.projectpickle.UI.ItemsActivity;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.SellHolder>{

    private List<SellerItem> listdata;

    public SellerAdapter(List<SellerItem> listdata)
    {
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public SellHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item,parent,false);
        return new SellHolder(view);
    }

    public void onBindViewHolder(@NonNull SellHolder holder, int position) {
        final SellerItem data = listdata.get(position);
        holder.name.setText(data.getName());
        holder.rate.setText(data.getRatings());
        holder.tags.setText(data.getTags());

        Picasso.get().load(data.getImage()).placeholder(R.color.colorAccent).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemsActivity.class);
                intent.putExtra("UID", data.getUserID());
                intent.putExtra("name", data.getName());
                intent.putExtra("phone", data.getPhone());
                intent.putExtra("address", data.getAddress());
                intent.putExtra("dFee", data.getdFee());
                intent.putExtra("image", data.getImage());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class SellHolder extends RecyclerView.ViewHolder{
        TextView name, rate, tags;
        ImageView imageView;

        SellHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sell_name);
            rate = itemView.findViewById(R.id.sell_ratings);
            tags = itemView.findViewById(R.id.sell_tags);
            imageView = itemView.findViewById(R.id.sell_image);
        }
    }

}