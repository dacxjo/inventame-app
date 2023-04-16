package com.ubpis.inventame.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Product;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProductCardAdapter extends RecyclerView.Adapter<ProductCardAdapter.ViewHolder> {

    public interface OnClickCardListener {
        void OnClickCard(int position);
    }

    private ArrayList<Product> mProducts;
    private boolean enableListener;

    private boolean hideStock;
    private OnClickCardListener clickCardListener;


    public ProductCardAdapter(ArrayList<Product> productList) {
        this.mProducts = productList;
        this.enableListener = true;
        this.hideStock = false;
    }

    public ProductCardAdapter(ArrayList<Product> productList, boolean enableListener, boolean hideStock) {
        this.mProducts = productList;
        this.enableListener = enableListener;
        this.hideStock = hideStock;
    }

    public void setOnClickCardListener(OnClickCardListener listener) {
        this.clickCardListener = listener;
    }

    @NonNull
    @Override
    public ProductCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_card_layout, parent, false);
        return new ProductCardAdapter.ViewHolder(view);
    }

    /* Method called for every ViewHolder in RecyclerView */
    @Override
    public void onBindViewHolder(@NonNull ProductCardAdapter.ViewHolder holder, int position) {
        holder.bind(mProducts.get(position), this.clickCardListener, this.enableListener,this.hideStock);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void setUsers(ArrayList<Product> products) {
        this.mProducts = products; // no recicla/repinta res
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
        private final TextView mCardProductName;
        private final TextView mCardStock;
        private final ImageView mWarningButton;
        private MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardPictureUrl = itemView.findViewById(R.id.product_image);
            mCardProductName = itemView.findViewById(R.id.product_name);
            mCardStock = itemView.findViewById(R.id.product_stock);
            mWarningButton = itemView.findViewById(R.id.warning_icon);
            card = itemView.findViewById(R.id.card);
        }

        public void bind(final Product product, OnClickCardListener listener, boolean enableListener,boolean hideStock){
            mCardProductName.setText(product.getName());
            mCardStock.setText(product.getmStock());
            Picasso.get().load(product.getURL()).transform(new RoundedCornersTransformation(50, 0)).fit()
                    .centerCrop().into(mCardPictureUrl);
            if (product.isExpired()) {
                mWarningButton.setVisibility(View.VISIBLE);
            } else {
                mWarningButton.setVisibility(View.INVISIBLE);
            }
            if(hideStock){
                mCardStock.setVisibility(View.GONE);
            }
            if(enableListener){
                card.setOnClickListener(view -> {
                    listener.OnClickCard(getAdapterPosition());
                });
            }
        }
    }
}
