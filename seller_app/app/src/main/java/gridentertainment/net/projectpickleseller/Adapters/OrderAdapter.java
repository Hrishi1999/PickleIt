package gridentertainment.net.projectpickleseller.Adapters;

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

import java.util.List;

import gridentertainment.net.projectpickleseller.Models.OrderItem;
import gridentertainment.net.projectpickleseller.R;
import gridentertainment.net.projectpickleseller.UI.ViewOrderActivity;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>{

    private List<OrderItem> listdata;
    private Context context;

    public OrderAdapter(List<OrderItem> listdata, Context context)
    {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new OrderHolder(view);
    }

    public void onBindViewHolder(@NonNull final OrderHolder holder, int position) {
        final OrderItem data = listdata.get(position);
        holder.id.setText("#" + data.getOrderID());
        holder.price.setText("Order total: " + data.getPrice());
        holder.quantity.setText("Total items: " + data.getQuantity());
        holder.status.setText("Status: " + data.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewOrderActivity.class);
                intent.putExtra("orderID", data.getOrderID());
                intent.putExtra("userID", data.getuserID());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        TextView id, price, quantity, status;

        OrderHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.or_itm_orderid);
            price = itemView.findViewById(R.id.or_itm_price);
            quantity = itemView.findViewById(R.id.or_itm_items);
            status = itemView.findViewById(R.id.or_itm_status);

        }
    }

}