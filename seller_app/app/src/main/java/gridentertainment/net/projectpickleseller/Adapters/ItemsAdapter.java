package gridentertainment.net.projectpickleseller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gridentertainment.net.projectpickleseller.Models.SellersItems;
import gridentertainment.net.projectpickleseller.R;
import gridentertainment.net.projectpickleseller.UI.AddItemActivity;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemHolder>{

    private List<SellersItems> listdata;
    private Context context;

    public ItemsAdapter(List<SellersItems> listdata, Context context)
    {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item,parent,false);
        return new ItemHolder(view);
    }

    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        final SellersItems data = listdata.get(position);

        holder.name.setText(data.getName());
        holder.op.setText("Original Price: " + data.getOriginal_price());
        holder.op.setText("Price: " + data.getPrice());
        holder.tag.setText(data.getTags());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddItemActivity.class);
                intent.putExtra("itm_id", data.getItm_id());
               // intent.putExtra("orderID", data.getOrderID());
               // intent.putExtra("userID", userID);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        TextView name, op, price, tag;

        ItemHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sitem_name);
            price = itemView.findViewById(R.id.sitem_price);
            op = itemView.findViewById(R.id.sitem_op);
            tag = itemView.findViewById(R.id.sitem_tag);
        }
    }

}