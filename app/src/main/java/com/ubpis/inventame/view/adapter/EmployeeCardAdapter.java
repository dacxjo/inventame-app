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
import com.ubpis.inventame.data.model.Employee;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class EmployeeCardAdapter extends RecyclerView.Adapter<EmployeeCardAdapter.ViewHolder> {

    private ArrayList<Employee> employees;
    private OnClickCardListener onClickCardListener;
    // Constructor
    public EmployeeCardAdapter(ArrayList<Employee> userList) {
        this.employees = userList;
    }

    public void setOnClickCardListener(OnClickCardListener listener) {
        this.onClickCardListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_card_layout, parent, false);
        return new EmployeeCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(employees.get(position), this.onClickCardListener);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void updateEmployees() {
        notifyDataSetChanged();
    }

    public void hideEmployee(int position) {
        notifyItemRemoved(position);
    }

    public interface OnClickCardListener {
        void OnClickCard(Employee employee);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mCardPictureUrl;
        private final TextView mCardFullName;
        private final MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardPictureUrl = itemView.findViewById(R.id.employee_image);
            mCardFullName = itemView.findViewById(R.id.employee_name);
            card = itemView.findViewById(R.id.card);
        }

        public void bind(final Employee employee, OnClickCardListener listener) {
            mCardFullName.setText(employee.getFullName());
            int randomInt = (int) (Math.random() * 1000000);
            String randomPath = "https://api.dicebear.com/6.x/notionists/png?seed=" + randomInt;
            Picasso.get().load(employee.getImageUrl().isEmpty() ? randomPath : employee.getImageUrl()).transform(new RoundedCornersTransformation(50, 0)).fit()
                        .centerCrop().into(mCardPictureUrl);
            card.setOnClickListener(view -> {
                listener.OnClickCard(employee);
            });
        }
    }

}