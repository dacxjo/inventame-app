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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.CartItem;
import com.ubpis.inventame.data.model.Sale;
import com.ubpis.inventame.view.adapter.SaleCardAdapter;

import java.util.ArrayList;

public class SaleCardAdapter extends RecyclerView.Adapter<SaleCardAdapter.ViewHolder> {
    private final ArrayList<Sale> salesList;

    public SaleCardAdapter(ArrayList<Sale> salesList) {
        this.salesList = salesList;
    }

    @NonNull
    @Override
    public SaleCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_row_item, parent, false);
        return new SaleCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleCardAdapter.ViewHolder holder, int position) {
        holder.bind(salesList.get(position));
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemImage;
        private final TextView itemName;
        private final TextView quantityNumber;
        private final ImageButton lessQuantityButton;
        private final TextView itemPrice;

        public ViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.itemImage);
            itemName = view.findViewById(R.id.itemHeadline);
            quantityNumber = view.findViewById(R.id.quantityNumber);
            lessQuantityButton = view.findViewById(R.id.buttonLessQuantity);
            itemPrice = view.findViewById(R.id.itemPrice);
        }

        public void bind(final Sale saleCard){

        }
    }
}