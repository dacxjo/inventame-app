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
import androidx.appcompat.widget.Toolbar;

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.divider.MaterialDivider;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.databinding.FragmentProductFormBinding;


import java.util.Date;

public class ProductDialogFragment extends DialogFragment {

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private AlertDialog dialog;
    public static String TAG = "AddProductDialog";
    private boolean isEdit;
    private FragmentProductFormBinding binding;


    public ProductDialogFragment() {
        this.isEdit = false;
    }

    public ProductDialogFragment(boolean isEdit) {
        this.isEdit = isEdit;
    }


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
        binding.productPicker.setOnClickListener(v -> showProductPickerChoices());
        binding.scannerButton.setOnClickListener(v -> launchScanner());
        binding.productExpirationTextfield.setOnClickListener(v -> showDatePicker());
        binding.toolbar.setNavigationOnClickListener(v -> dismiss());
        handleEdit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentProductFormBinding.inflate(inflater, container, false);
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
                        Picasso.get().load(uri).into(binding.product);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        binding.product.setImageBitmap(photo);
                    }
                }
        );

        return binding.getRoot();
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

        datePicker.addOnPositiveButtonClickListener(selectedDate -> {
            // Convert the selected date to a formatted string
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String dateString = sdf.format(new Date(selectedDate));
            // Set the selected date string to the textfield
            binding.productExpirationTextfield.setText(dateString);
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

    private void handleEdit(){
        if(this.isEdit){
            binding.deleteButton.setVisibility(View.VISIBLE);
            binding.divider.setVisibility(View.GONE);
            binding.scannerButton.setVisibility(View.GONE);
            binding.toolbar.setTitle(R.string.inventory_edit_title);
        }
    }

}
