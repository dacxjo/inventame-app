package com.ubpis.inventame.view.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.behavior.SwipeDismissBehavior.OnDismissListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.CartItem;
import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.data.model.Sale;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class SaleCardAdapter extends RecyclerView.Adapter<SaleCardAdapter.ViewHolder> {
    private final ArrayList<CartItem> itemsList;
    
    public SaleCardAdapter(ArrayList<CartItem> itemsList) {
        this.itemsList = itemsList;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sale_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemImage;
        private final TextView itemName;
        private final TextView quantityNumber;
        private final TextView itemPrice;

        public ViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.itemImage);
            itemName = view.findViewById(R.id.itemHeadline);
            quantityNumber = view.findViewById(R.id.quantityNumber);
            itemPrice = view.findViewById(R.id.itemPrice);
        }

        public void bind(final CartItem cartItem){
            Picasso.get().load(cartItem.getImage()).transform(new RoundedCornersTransformation(16, 0)).fit()
                    .centerCrop().into(itemImage);
            itemName.setText(cartItem.getNameProduct());
            quantityNumber.setText(String.format(quantityNumber.getText().toString(), cartItem.getQuantityNum()));
            itemPrice.setText(String.format("%.2f", (cartItem.getTotalPrice()*cartItem.getQuantityNum())).replace(".",",")+"€");
        }
    }
}