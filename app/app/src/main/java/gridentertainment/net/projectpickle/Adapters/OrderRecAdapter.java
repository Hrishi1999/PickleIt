package gridentertainment.net.projectpickle.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gridentertainment.net.projectpickle.Models.OrderItem;
import gridentertainment.net.projectpickle.R;


public class OrderRecAdapter extends RecyclerView.Adapter<OrderRecAdapter.OrderRecHolder>{

    private List<OrderItem> listdata;
    private Context context;

    public OrderRecAdapter(List<OrderItem> listdata, Context context)
    {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderRecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_rec_item,parent,false);
        return new OrderRecHolder(view);
    }

    public void onBindViewHolder(@NonNull final OrderRecHolder holder, int position) {
        final OrderItem data = listdata.get(position);
        holder.name.setText(data.getName());
        holder.price.setText("Item Price: " + data.getPrice());
        holder.quantity.setText("Quantity: " + data.getQuantity());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class OrderRecHolder extends RecyclerView.ViewHolder{
        TextView name, price, quantity;

        OrderRecHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.or_rec_name);
            price = itemView.findViewById(R.id.or_rec_price);
            quantity = itemView.findViewById(R.id.or_rec_quantity);
        }
    }

}