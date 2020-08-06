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

import gridentertainment.net.projectpickle.Models.SearchItem;
import gridentertainment.net.projectpickle.R;
import gridentertainment.net.projectpickle.UI.ItemsActivity;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder>{

    private List<SearchItem> listdata;

    public SearchAdapter(List<SearchItem> listdata)
    {
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new SearchHolder(view);
    }

    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        final SearchItem data = listdata.get(position);
        holder.name.setText(data.getName());
        holder.rate.setText(data.getRatings());
        holder.tags.setText(data.getTags());

        Picasso.get().load(data.getImage()).placeholder(R.color.colorAccent).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemsActivity.class);
                intent.putExtra("UID", data.getSellerID());
                intent.putExtra("name", data.getName());
                intent.putExtra("ratings", data.getRatings());
                intent.putExtra("image", data.getImage());
                intent.putExtra("phone", data.getPhone());
                intent.putExtra("address", data.getAddress());
                intent.putExtra("image", data.getImage());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder{
        TextView name, rate, tags;
        ImageView imageView;

        SearchHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.search_name);
            rate = itemView.findViewById(R.id.search_ratings);
            tags = itemView.findViewById(R.id.search_tags);
            imageView = itemView.findViewById(R.id.search_image);
        }
    }

}