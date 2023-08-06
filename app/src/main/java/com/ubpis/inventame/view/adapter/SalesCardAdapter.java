package com.ubpis.inventame.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.behavior.SwipeDismissBehavior.OnDismissListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Sale;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class SalesCardAdapter extends RecyclerView.Adapter<SalesCardAdapter.ViewHolder> {
    public interface OnClickCardListener {
        void OnClickCard(int position);
    }
    private final ArrayList<Sale> salesList;
    private OnClickCardListener clickCardListener;
    boolean enableListener;
    private SwipeDismissBehavior<View> swipeDismissBehavior;
    private CoordinatorLayout container;
    private MaterialCardView card;
    private ExtendedFloatingActionButton fab;
    public SalesCardAdapter(ArrayList<Sale> salesList, CoordinatorLayout container, ExtendedFloatingActionButton fab) {
        this.salesList = salesList;
        this.enableListener = true;
        this.container = container;
        this.fab = fab;
    }

    public SalesCardAdapter(ArrayList<Sale> salesList, boolean enableListener, CoordinatorLayout container, ExtendedFloatingActionButton fab) {
        this.salesList = salesList;
        this.enableListener = enableListener;
        this.container = container;
        this.fab = fab;
    }

    public void setOnClickCardListener(OnClickCardListener listener) {
        this.clickCardListener = listener;
    }

    @NonNull
    @Override
    public SalesCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sales_row_card, parent, false);

        /** Setup with {@link SwipeDismissBehavior} */
        swipeDismissBehavior = new SwipeDismissBehavior<>();
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);

        card = view.findViewById(R.id.card);
        CoordinatorLayout.LayoutParams coordinatorParams =
                (CoordinatorLayout.LayoutParams) card.getLayoutParams();

        coordinatorParams.setBehavior(swipeDismissBehavior);

        return new SalesCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesCardAdapter.ViewHolder holder, int position) {
        holder.bind(salesList.get(position), this.clickCardListener, this.enableListener);
        swipeDismissBehavior.setListener(new OnDismissListener() {
            final Sale sale = salesList.get(position);
            final MaterialCardView materialCardView = holder.card;
            @Override
            public void onDismiss(View view) {
                Snackbar.make(container, R.string.cat_card_dismissed, Snackbar.LENGTH_LONG)
                        .setAction(R.string.cat_card_undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                salesList.add(position, sale);
                                notifyItemInserted(position);
                                SalesCardAdapter.resetCard(materialCardView);
                            }
                        }).setAnchorView(fab).show();

                salesList.remove(position);
                notifyItemRemoved(position);
            }

            @Override
            public void onDragStateChanged(int state) {
                SalesCardAdapter.onDragStateChanged(state, card);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView card;
        private final TextView id;
        private final TextView date;
        private final TextView price;

        public ViewHolder(View view) {
            super(view);
            card = view.findViewById(R.id.card);
            id = view.findViewById(R.id.saleID);
            date = view.findViewById(R.id.saleDate);
            price = view.findViewById(R.id.salePrice);
        }

        public void bind(final Sale sale, OnClickCardListener listener, boolean enableListener){
            id.setText("#" + sale.getUuid().toString().substring(0,8));

            Date dateSale = sale.getDate();

            Instant instant = dateSale.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedDate = localDateTime.format(formatter);
            date.setText(formattedDate);
            price.setText(String.format("%.2f",sale.getTotal()).replace(".",",") + "€");

            if (enableListener){
                card.setOnClickListener(view -> {
                    listener.OnClickCard(getAdapterPosition());
                });
            }
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

    private static void resetCard(MaterialCardView cardContentLayout) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) cardContentLayout
                .getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        cardContentLayout.setAlpha(1.0f);
        cardContentLayout.requestLayout();
    }

    public void removeSale(int position) {
        salesList.remove(position);
        notifyItemInserted(position);
    }
    public void restoreSale(Sale sale, int position) {
        salesList.add(position, sale);
        notifyItemInserted(position);
    }
}