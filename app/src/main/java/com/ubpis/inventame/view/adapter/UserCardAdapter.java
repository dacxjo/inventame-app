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
import com.ubpis.inventame.data.model.Reminder;

import java.util.ArrayList;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.ViewHolder> {

    public interface OnClickHideListener {
        void OnClickHide(int position);
    }

    private ArrayList<Reminder> mUsers;
    private OnClickHideListener mOnClickHideListener;

    // Constructor
    public UserCardAdapter(ArrayList<Reminder> userList) {
        this.mUsers = userList;
    }

    public void setOnClickHideListener(OnClickHideListener listener) {
        this.mOnClickHideListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_card_layout, parent, false);
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

    public void setUsers(ArrayList<Reminder> users) {
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
        private final TextView mCardFullDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardPictureUrl = itemView.findViewById(R.id.product_image);
            mCardFullName = itemView.findViewById(R.id.product_name);
            mCardFullDesc = itemView.findViewById(R.id.product_stock);
        }

        public void bind(final Reminder user, OnClickHideListener listener) {
            mCardFullName.setText(user.getNotification());
            mCardFullDesc.setText(user.getDesc());
            Picasso.get().load(user.getmPictureURL()).into(mCardPictureUrl);

        }
    }

}

