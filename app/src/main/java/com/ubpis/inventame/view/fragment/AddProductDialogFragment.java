package com.ubpis.inventame.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;


import java.util.Date;

public class AddProductDialogFragment extends DialogFragment {

    private MaterialCardView productPicker;
    private ImageView productImage;
    private Button scannerButton;
    private EditText productIdEditText;
    private EditText productNameEditText;
    private EditText productDescriptionEditText;
    private EditText productPriceEditText;
    private EditText productStockEditText;
    private EditText productBatchEditText;
    private EditText productExpirationEditText;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private AlertDialog dialog;

    public static String TAG = "AddProductDialog";

    private Toolbar toolbar;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(value -> dismiss());

        productPicker = view.findViewById(R.id.productPicker);
        productImage = view.findViewById(R.id.product_image);
        scannerButton = view.findViewById(R.id.scanner_button);

        productIdEditText = view.findViewById(R.id.product_id_textfield);
        productNameEditText = view.findViewById(R.id.product_name_textfield);
        productDescriptionEditText = view.findViewById(R.id.product_description_textarea);
        productPriceEditText = view.findViewById(R.id.product_price_textfield);
        productStockEditText = view.findViewById(R.id.product_stock_textfield);
        productBatchEditText = view.findViewById(R.id.product_batch_textfield);
        productExpirationEditText = view.findViewById(R.id.product_expiration_textfield);

        productPicker.setOnClickListener(v -> showProductPickerChoices());
        scannerButton.setOnClickListener(v -> launchScanner());
        productExpirationEditText.setOnClickListener(v -> showDatePicker());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        dialog.dismiss();
                        launchCameraIntent();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Camera permission denied.", Toast.LENGTH_SHORT).show();
                    }
                });

        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        Picasso.get().load(uri).into(productImage);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        productImage.setImageBitmap(photo);
                    }
                }
        );

        return inflater.inflate(R.layout.fragment_add_inventory, container, false);
    }

    private void launchScanner() {
        // TODO: Scanner button
        System.out.println("Escanner activated!!!");
    }

    private void showDatePicker(){
        MaterialDatePicker<Long> datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                // Convert the selected date to a formatted string
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String dateString = sdf.format(new Date(selectedDate));

                // Set the selected date string to the textfield
                productExpirationEditText.setText(dateString);
            }
        });

        datePicker.show(getParentFragmentManager(), "Expiration Date");
    }

    private void showProductPickerChoices() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this.getContext());
        builder.setTitle(R.string.inventory_add_title);
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_add_logo, null);
        builder.setView(view);
        Button takePhotoButton = view.findViewById(R.id.take_photo_button);
        Button choosePhotoButton = view.findViewById(R.id.choose_photo_button);
        builder.setNegativeButton(R.string.common_cancel, (dialog, which) -> {
            dialog.dismiss();
        });
        dialog = builder.create();
        choosePhotoButton.setOnClickListener(this::choosePicture);
        takePhotoButton.setOnClickListener(this::takePhoto);
        dialog.show();
    }


    private void choosePicture(View view) {
        dialog.dismiss();
        ActivityResultContracts.PickVisualMedia.VisualMediaType mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE;
        pickMedia.launch(new PickVisualMediaRequest.Builder().setMediaType(mediaType).build());
    }

    private void takePhoto(View view) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            dialog.dismiss();
            launchCameraIntent();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void launchCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureLauncher.launch(takePictureIntent);
    }

}
