package gridentertainment.net.projectpickle.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import at.markushi.ui.CircleButton;
import gridentertainment.net.projectpickle.Models.OrderItem;
import gridentertainment.net.projectpickle.Models.SellerItem;
import gridentertainment.net.projectpickle.R;
import gridentertainment.net.projectpickle.UI.ItemsActivity;
import gridentertainment.net.projectpickle.UI.ViewOrderActivity;

public class PreviousOrderAdapter extends RecyclerView.Adapter<PreviousOrderAdapter.PreHolder>{

    private List<OrderItem> listdata;
    private Context context;

    public PreviousOrderAdapter(List<OrderItem> listdata, Context context)
    {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public PreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_order_item,parent,false);
        return new PreHolder(view);
    }

    public void onBindViewHolder(@NonNull final PreHolder holder, int position) {
        final OrderItem data = listdata.get(position);
        holder.name.setText(data.getName());
        holder.status.setText(data.getStatus());
        holder.price.setText("₹" + data.getPrice());
        holder.quantity.setText(data.getQuantity() + " items");

        if(data.getStatus().equals("On the way!"))
        {
            holder.status.setTextColor(context.getColor(R.color.ada_green));
        }
        if(data.getStatus().equals("Cancelled"))
        {
            holder.status.setTextColor(context.getColor(R.color.ada_red));
        }
        if(data.getStatus().equals("Preparing"))
        {
            holder.status.setTextColor(context.getColor(R.color.ada_yellow));
        }

        //Picasso.get().load(data.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewOrderActivity.class);
                intent.putExtra("total", data.getPrice());
                intent.putExtra("orderID", data.getOrderID());
                intent.putExtra("status", data.getStatus());
                intent.putExtra("sellerID", data.getSellerID());

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class PreHolder extends RecyclerView.ViewHolder{
        TextView name, price, status, quantity;

        PreHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pre_name);
            quantity = itemView.findViewById(R.id.pre_items);
            price = itemView.findViewById(R.id.pre_price);
            status = itemView.findViewById(R.id.pre_status);
        }
    }

}