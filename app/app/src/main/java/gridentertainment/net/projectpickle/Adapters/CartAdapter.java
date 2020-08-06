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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>{

    private List<OrderItem> listdata;
    private Context context;

    public CartAdapter(List<OrderItem> listdata, Context context)
    {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new CartHolder(view);
    }

    public void onBindViewHolder(@NonNull final CartHolder holder, int position) {
        final OrderItem data = listdata.get(position);
        holder.name.setText(data.getName());
        holder.tags.setText(data.getTags());
        holder.price.setText("₹" + data.getPrice());
        holder.quantity.setText(data.getQuantity());

        Picasso.get().load(data.getImage()).placeholder(R.color.colorAccent).into(holder.imageView);

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemsActivity.class);
                intent.putExtra("UID", data.getUserID());
                intent.putExtra("name", data.getName());
                intent.putExtra("phone", data.getPhone());
                intent.putExtra("address", data.getAddress());

                v.getContext().startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class CartHolder extends RecyclerView.ViewHolder{
        TextView name, tags, price, quantity;
        ImageView imageView;
        CircleButton add, sub;

        CartHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.or_itm_name);
            quantity = itemView.findViewById(R.id.or_itm_quantity);
            price = itemView.findViewById(R.id.or_itm_price);
            tags = itemView.findViewById(R.id.or_itm_tag);
            imageView = itemView.findViewById(R.id.or_itm_image);
            add = itemView.findViewById(R.id.itm_btn_add2);
            sub = itemView.findViewById(R.id.itm_btn_remove2);
        }
    }

}