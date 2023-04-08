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
import com.ubpis.inventame.data.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public interface OnClickCardListener {
        void OnClickCard(int position);
    }
    ArrayList<Category> categoryArrayList;
    private OnClickCardListener clickCardListener;

    public CategoryAdapter(ArrayList<Category> categoryList) {
        this.categoryArrayList = categoryList;
    }

    public void setOnClickCardListener(OnClickCardListener listener) {
        this.clickCardListener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.register_category, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.bind(categoryArrayList.get(position), this.clickCardListener);
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categoryArrayList = categories;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView categoryImg;
        private final TextView categoryTitle;
        private final TextView categoryDesc;
        private final MaterialCardView categoryCard;

        private boolean isCardChecked = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.categoryImg);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryDesc = itemView.findViewById(R.id.categoryDesc);
            categoryCard = itemView.findViewById(R.id.categoryCard);
        }

        public void bind(final Category category, OnClickCardListener listener) {
            categoryTitle.setText(category.getTitle());
            categoryDesc.setText(category.getDescription());
            Picasso.get().load(category.getImg()).into(categoryImg);
            categoryCard.setOnClickListener(view -> {
                isCardChecked = !isCardChecked;
                categoryCard.setChecked(isCardChecked);
                listener.OnClickCard(getAdapterPosition());
            });
        }
    }
}
