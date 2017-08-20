package app.dougaraujo.com.mylunchtime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.dougaraujo.com.mylunchtime.R;
import app.dougaraujo.com.mylunchtime.model.Favorite;

/**
 * Created by Dux-Douglas2 on 20/08/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Favorite> favoriteList;

    public FavoriteAdapter(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View favoriteLayout = inflater.inflate(R.layout.layout_favorite_row, parent, false);
        return new FavoriteViewHolder(favoriteLayout);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, int position) {
        Favorite favorite = favoriteList.get(position);
        holder.tvName.setText(favorite.getNome());
        holder.tvAddress.setText(favorite.getEndereco());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void update(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
        notifyDataSetChanged();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAddress;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvEndereco);
        }
    }
}
