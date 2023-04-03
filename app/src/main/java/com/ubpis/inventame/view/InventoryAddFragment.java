package com.ubpis.inventame.view;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.ubpis.inventame.R;

import java.util.Locale;

public class InventoryAddFragment extends Fragment {

    private ImageView productImageView;
    private EditText productIdEditText;
    private EditText productNameEditText;
    private EditText productDescriptionEditText;
    private EditText productPriceEditText;
    private EditText productStockEditText;
    private EditText productBatchEditText;

    private EditText productExpirationEditText;

    public InventoryAddFragment() {
        // Required empty public constructor
    }

    public static InventoryAddFragment newInstance() {
        InventoryAddFragment fragment = new InventoryAddFragment();
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_inventory, container, false);

        // Find the product information views
        productImageView = view.findViewById(R.id.product_image);
        productIdEditText = view.findViewById(R.id.product_id_textfield);
        productNameEditText = view.findViewById(R.id.product_name_textfield);
        productDescriptionEditText = view.findViewById(R.id.product_description_textarea);
        productPriceEditText = view.findViewById(R.id.product_price_textfield);
        productStockEditText = view.findViewById(R.id.product_stock_textfield);
        productBatchEditText = view.findViewById(R.id.product_batch_textfield);
        productExpirationEditText = view.findViewById(R.id.product_expiration_textfield);

        // Add a listener to the "Add Product" button
        Button addProductButton = view.findViewById(R.id.bottom_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the input fields
                String productId = productIdEditText.getText().toString();
                String productName = productNameEditText.getText().toString();
                String productDescription = productDescriptionEditText.getText().toString();
                String productPrice = productPriceEditText.getText().toString();
                String productStock = productStockEditText.getText().toString();
                String productBatch = productBatchEditText.getText().toString();
                String productExpiration = productExpirationEditText.getText().toString();

                // TODO: Add the product to the inventory

                // Clear the input fields
                productIdEditText.setText("");
                productNameEditText.setText("");
                productDescriptionEditText.setText("");
                productPriceEditText.setText("");
                productStockEditText.setText("");
                productBatchEditText.setText("");
                productDescriptionEditText.setText("");
            }
        });


        Button scannerButton = view.findViewById(R.id.scanner_button);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Scanner button
            }
        });


        productExpirationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Expiration date calendar
                /*

                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                MaterialDatePicker<Long> datePicker = builder.build();

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long dateInMillis) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dateInMillis);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        productExpirationEditText.setText(dateFormat.format(calendar.getTime()));
                    }
                });

                datePicker.show(getParentFragmentManager(), "DATE_PICKER");

                 */
            }
        });

        return view;
    }

}
