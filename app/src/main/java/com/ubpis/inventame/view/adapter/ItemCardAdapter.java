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

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ItemCardAdapter extends RecyclerView.Adapter<ItemCardAdapter.ViewHolder> {

    private final ArrayList<CartItem> itemList;

    public ItemCardAdapter(ArrayList<CartItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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

        public void bind(final CartItem cartItem){
            Picasso.get().load(cartItem.getImage()).transform(new RoundedCornersTransformation(16, 0)).fit()
                    .centerCrop().into(itemImage);
            itemName.setText(cartItem.getNameProduct());
            quantityNumber.setText(Integer.toString(cartItem.getQuantityNum()));
            if (!quantityNumber.getText().toString().equals("1")){
                lessQuantityButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#171A3A")));
                lessQuantityButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DBDCDF")));
            }
            itemPrice.setText(String.format("%.2f", cartItem.getTotalPrice()*cartItem.getQuantityNum()).replace(".",",")+"€");
        }
    }
}
