package app.dougaraujo.com.mylunchtime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.widget.ShareButton;

import java.util.List;

import app.dougaraujo.com.mylunchtime.R;
import app.dougaraujo.com.mylunchtime.model.Favorite;

/**
 * Created by Dux-Douglas2 on 20/08/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Favorite> favoriteList;
    private OnItemClickListener onItemClickListener;

    public FavoriteAdapter(List<Favorite> favoriteList, OnItemClickListener onItemClickListener) {
        this.favoriteList = favoriteList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View favoriteLayout = inflater.inflate(R.layout.layout_favorite_row, parent, false);
        return new FavoriteViewHolder(favoriteLayout);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, final int position) {
        Favorite favorite = favoriteList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(favoriteList.get(position));
            }
        });

        holder.tvName.setText(favorite.getNome());
        holder.tvAddress.setText(favorite.getCep());
        holder.tvPhone.setText(favorite.getTelefone());
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public void update(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
        notifyDataSetChanged();
    }


    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAddress;
        TextView tvPhone;
        ShareButton shareButton;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvEndereco);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
        }
    }
}
