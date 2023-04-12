package com.ubpis.inventame.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.User;

import java.util.ArrayList;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.ViewHolder> {

    public interface OnClickHideListener {
        void OnClickHide(int position);
    }

    private ArrayList<User> mUsers;
    private OnClickHideListener mOnClickHideListener;

    // Constructor
    public UserCardAdapter(ArrayList<User> userList) {
        this.mUsers = userList;
    }

    public void setOnClickHideListener(OnClickHideListener listener) {
        this.mOnClickHideListener = listener;
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
        holder.bind(mUsers.get(position), this.mOnClickHideListener);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setUsers(ArrayList<User> users) {
        this.mUsers = users; // no recicla/repinta res
    }

    public void updateUsers() {
        notifyDataSetChanged();
    }

    public void hideUser(int position) {
        notifyItemRemoved(position);
    }

    /*
     * ViewHolder class. It's just a placeholder for the view (user_card_list.xml)
     * of the RecyclerView items. We can implement this outside RecyclerViewAdapter,
     * but it can be done inside.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mCardPictureUrl;
        private final TextView mCardFullName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardPictureUrl = itemView.findViewById(R.id.employee_image);
            mCardFullName = itemView.findViewById(R.id.employee_name);
        }

        public void bind(final User user, OnClickHideListener listener) {
            mCardFullName.setText(user.getName() + " " + user.getSurname());
            Picasso.get().load(user.getmPictureURL()).into(mCardPictureUrl);

        }
    }

}