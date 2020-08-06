package gridentertainment.net.projectpickle.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import gridentertainment.net.projectpickle.Models.SellersItems;
import gridentertainment.net.projectpickle.R;
import gridentertainment.net.projectpickle.UI.ItemsActivity;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>{

    private List<SellersItems> listdata;
    private Context context;
    private String uid;

    public ItemAdapter(List<SellersItems> listdata, Context context, String uid)
    {
        this.listdata = listdata;
        this.context = context;
        this.uid = uid;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sellers_item,parent,false);
        return new ItemHolder(view);
    }

    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        final SellersItems data = listdata.get(position);

        holder.name.setText(data.getName());
        holder.original_price.setText("₹" + data.getOriginal_price());
        holder.price.setText("₹" + data.getPrice());
        holder.original_price.setPaintFlags(holder.original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.quantity.setText(data.getQuantity());
        holder.tag.setText(data.getTags());
        Picasso.get().load(data.getImage()).into(holder.imageView);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = currentFirebaseUser.getUid();

        final FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("consumers").child(userID).child("cart").child(data.getItemID());
        final DatabaseReference databaseReference2 = database.getReference("consumers").child(userID).child("cart");
        final DatabaseReference finalDatabaseReference = databaseReference;

        final int[] count = {Integer.parseInt(holder.quantity.getText().toString())};

        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count[0] > 0)
                {
                    count[0]--;
                    holder.quantity.setText(String.valueOf(count[0]));

                    String it_name = data.getName();
                    String it_tags = data.getTags();
                    String it_quantity = holder.quantity.getText().toString();
                    String it_price = data.getPrice();

                    final OrderItem orderItem = new OrderItem();

                    orderItem.setName(it_name);
                    orderItem.setPrice(it_price);
                    orderItem.setName(it_quantity);
                    orderItem.setTags(it_tags);
                    orderItem.setQuantity(it_quantity);
                    orderItem.setSellerID(uid);

                    finalDatabaseReference
                            .setValue(orderItem);

                }


            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]++;
                holder.quantity.setText(String.valueOf(count[0]));

                String it_name = data.getName();
                String it_tags = data.getTags();
                String it_quantity = holder.quantity.getText().toString();
                String it_price = data.getPrice();

                final OrderItem orderItem = new OrderItem();

                orderItem.setName(it_name);
                orderItem.setPrice(it_price);
                orderItem.setTags(it_tags);
                orderItem.setQuantity(it_quantity);
                orderItem.setSellerID(uid);

                finalDatabaseReference
                        .setValue(orderItem);

            }
        });

        //Picasso.get().load(data.getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        TextView name, original_price, tag, price, quantity;
        ImageView imageView;
        CircleButton add, sub;

        ItemHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itm_name);
            original_price = itemView.findViewById(R.id.itm_op);
            price = itemView.findViewById(R.id.itm_price);
            tag = itemView.findViewById(R.id.itm_tag);
            imageView = itemView.findViewById(R.id.itm_image);
            add = itemView.findViewById(R.id.itm_btn_add);
            sub = itemView.findViewById(R.id.itm_btn_remove);
            quantity = itemView.findViewById(R.id.itm_quantity);
        }
    }

}