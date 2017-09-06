package app.dougaraujo.com.mylunchtime.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;
import app.dougaraujo.com.mylunchtime.EditFavoriteFragment;
import app.dougaraujo.com.mylunchtime.MainActivity;
import app.dougaraujo.com.mylunchtime.R;
import app.dougaraujo.com.mylunchtime.model.Favorite;

/**
 * Created by Dux-Douglas2 on 20/08/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Favorite> favoriteList;
    private OnItemClickListener onItemClickListener;

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
    public void onBindViewHolder(final FavoriteAdapter.FavoriteViewHolder holder, final int position) {
        Favorite favorite = favoriteList.get(position);
        holder.tvName.setText(favorite.getNome());
        holder.tvAddress.setText(favorite.getEnd());
        holder.tvPhone.setText(favorite.getTelefone());
        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                Toast.makeText(, favoriteList.get(position),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + favoriteList.get(position).getTelefone()));
//                    startActivity(intent);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(intent);


            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity context = (MainActivity) v.getContext();
                Favorite favorito = favoriteList.get(position);
                FavoriteDAO favoriteDAO = new FavoriteDAO(context);
                favorito = favoriteDAO.selectSingleFavorite(favorito.getId());
                EditFavoriteFragment editFavoriteFragment = new EditFavoriteFragment();
                android.support.v4.app.FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                Bundle data = new Bundle();
                data.putParcelable("dados", favorito);
                editFavoriteFragment.setArguments(data);
                transaction.replace(R.id.content_main, editFavoriteFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity context = (MainActivity) v.getContext();
                Favorite favorito = favoriteList.get(position);
                FavoriteDAO favoriteDAO = new FavoriteDAO(context);
                favoriteDAO.deleteFavorite(favorito.getId());
                favoriteList.remove(position);
                update(favoriteList);
                notifyDataSetChanged();
                Toast.makeText(context, context.getString(R.string.txt_delete_favorite), Toast.LENGTH_SHORT).show();
            }
        });
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
        ImageView ivCall;
        ImageView ivEdit;
        ImageView ivDelete;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvEndereco);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            ivCall = (ImageView) itemView.findViewById(R.id.ivCall);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }
}
