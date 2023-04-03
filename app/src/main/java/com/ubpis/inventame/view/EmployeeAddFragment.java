package com.ubpis.inventame.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.ubpis.inventame.R;

public class EmployeeAddFragment extends Fragment {

    private ImageView employeeImageView;
    private EditText employeeIdEditText;
    private EditText employeeNameEditText;
    private EditText employeeEmailEditText;

    public EmployeeAddFragment() {
        // Required empty public constructor
    }

    public static EmployeeAddFragment newInstance() {
        EmployeeAddFragment fragment = new EmployeeAddFragment();
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_inventory, container, false);

        // Find the product information views
        employeeImageView = view.findViewById(R.id.employee_image);
        employeeIdEditText = view.findViewById(R.id.employee_id_textfield);
        employeeNameEditText = view.findViewById(R.id.employee_name_textfield);
        employeeEmailEditText = view.findViewById(R.id.employee_email_textfield);

        // Add a listener to the "Add Product" button
        Button addProductButton = view.findViewById(R.id.bottom_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the input fields
                String productId = employeeIdEditText.getText().toString();
                String productName = employeeNameEditText.getText().toString();
                String productDescription = employeeEmailEditText.getText().toString();

                // TODO: Add the employee to the inventory

                // Clear the input fields
                employeeIdEditText.setText("");
                employeeNameEditText.setText("");
                employeeEmailEditText.setText("");
            }
        });

        return view;
    }





}
