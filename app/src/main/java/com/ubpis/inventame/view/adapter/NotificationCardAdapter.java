package com.ubpis.inventame.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Reminder;

import java.util.ArrayList;

public class NotificationCardAdapter extends RecyclerView.Adapter<NotificationCardAdapter.ViewHolder> {

    private ArrayList<Reminder> mReminder;
    private SwipeDismissBehavior<View> swipeDismissBehavior;
    private Context context;
    private static MaterialCardView card;
    public NotificationCardAdapter(ArrayList<Reminder> remindtList) {
        this.mReminder = remindtList;
    }

    @NonNull
    @Override
    public NotificationCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.notification_card_layout, parent, false);

        /** Setup with {@link SwipeDismissBehavior}*/
        swipeDismissBehavior = new SwipeDismissBehavior<>();
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);

        card = view.findViewById(R.id.card);
        CoordinatorLayout.LayoutParams coordinatorParams =
                (CoordinatorLayout.LayoutParams) card.getLayoutParams();

        coordinatorParams.setBehavior(swipeDismissBehavior);

        return new ViewHolder(view);
    }

    /* Method called for every ViewHolder in RecyclerView */
    @Override
    public void onBindViewHolder(@NonNull NotificationCardAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(mReminder.get(position));
        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                mReminder.remove(position);
                notifyItemRemoved(position);

                Toast.makeText(context, "Notificación eliminada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDragStateChanged(int state) {
                NotificationCardAdapter.onDragStateChanged(state, card);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReminder.size();
    }

    public void setUsers(ArrayList<Reminder> reminders) {
        this.mReminder = reminders; // no recicla/repinta res
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

        public void bind(final Reminder reminder) {
            mCardNotification.setText(reminder.getNotification());
            mCardTime.setText(reminder.getDesc());
            Picasso.get().load(reminder.getmPictureURL()).into(mCardPictureUrl);
        }
    }

        private static void onDragStateChanged(int state, MaterialCardView cardContentLayout) {
            switch (state) {
                case SwipeDismissBehavior.STATE_DRAGGING:
                case SwipeDismissBehavior.STATE_SETTLING:
                    cardContentLayout.setDragged(true);
                    break;
                case SwipeDismissBehavior.STATE_IDLE:
                    cardContentLayout.setDragged(false);
                    break;
                default: // fall out
            }
        }
}