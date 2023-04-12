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

public class NotificationCardAdapter extends RecyclerView.Adapter<NotificationCardAdapter.ViewHolder> {

    public interface OnClickHideListener {
        void OnClickHide(int position);
    }

    private ArrayList<Reminder> mReminder;
    private OnClickHideListener mOnClickHideListener;
    public NotificationCardAdapter(ArrayList<Reminder> remindtList) {
        this.mReminder = remindtList;

    }

    public void setOnClickHideListener(OnClickHideListener listener) {
        this.mOnClickHideListener = listener;
    }

    @NonNull
    @Override
    public NotificationCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_card_layout, parent, false);
        return new NotificationCardAdapter.ViewHolder(view);
    }

    /* Method called for every ViewHolder in RecyclerView */
    @Override
    public void onBindViewHolder(@NonNull NotificationCardAdapter.ViewHolder holder, int position) {
        holder.bind(mReminder.get(position), this.mOnClickHideListener);
    }

    @Override
    public int getItemCount() {
        return mReminder.size();
    }

    public void setUsers(ArrayList<Reminder> remind) {
        this.mReminder = remind; // no recicla/repinta res
    }

    public void updateProducts() {
        notifyDataSetChanged();
    }

    public void hideProduct(int position) {
        notifyItemRemoved(position);
    }

    /*
     * ViewHolder class. It's just a placeholder for the view (product_card_list.xml)
     * of the RecyclerView items. We can implement this outside RecyclerViewAdapter,
     * but it can be done inside.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mCardPictureUrl;
        private final TextView mCardNotification;
        private final TextView mCardTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardPictureUrl = itemView.findViewById(R.id.product_image);
            mCardNotification = itemView.findViewById(R.id.product_name);
            mCardTime = itemView.findViewById(R.id.product_stock);
        }

        public void bind(final Reminder reminder, OnClickHideListener listener) {
            mCardNotification.setText(reminder.getNotification());
            mCardTime.setText(reminder.getDesc());
            Picasso.get().load(reminder.getmPictureURL()).into(mCardPictureUrl);

            /*
            mWarningButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnClickHide(getAdapterPosition());
                }
            });
            */

        }
    }
}