package com.ubpis.inventame.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Employee;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.ViewHolder> {

    private ArrayList<Employee> mUsers;
    private OnClickCardListener onClickCardListener;
    // Constructor
    public UserCardAdapter(ArrayList<Employee> userList) {
        this.mUsers = userList;
    }

    public void setOnClickCardListener(OnClickCardListener listener) {
        this.onClickCardListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_card_layout, parent, false);
        return new UserCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mUsers.get(position), this.onClickCardListener);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setUsers(ArrayList<Employee> users) {
        this.mUsers = users; // no recicla/repinta res
    }

    public void updateUsers() {
        notifyDataSetChanged();
    }

    public void hideUser(int position) {
        notifyItemRemoved(position);
    }

    public interface OnClickCardListener {
        void OnClickCard(int position);
    }

    /*
     * ViewHolder class. It's just a placeholder for the view (user_card_list.xml)
     * of the RecyclerView items. We can implement this outside RecyclerViewAdapter,
     * but it can be done inside.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mCardPictureUrl;
        private final TextView mCardFullName;

        private final MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardPictureUrl = itemView.findViewById(R.id.employee_image);
            mCardFullName = itemView.findViewById(R.id.employee_name);
            card = itemView.findViewById(R.id.card);
        }

        public void bind(final Employee user, OnClickCardListener listener) {
            mCardFullName.setText(user.getFullName());
            Picasso.get().load(user.getImageUrl()).transform(new RoundedCornersTransformation(50, 0)).fit()
                    .centerCrop().into(mCardPictureUrl);
            card.setOnClickListener(view -> {
                listener.OnClickCard(getAdapterPosition());
            });
        }
    }

}