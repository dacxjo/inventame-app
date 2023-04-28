package com.ubpis.inventame.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.viewmodel.RegisterStepTwoViewModel;

public class RegisterStepTwoFragment extends Fragment {

    private RegisterStepTwoViewModel mViewModel;
    private Button backButton, registerBtn;
    private MaterialCardView logoPicker;
    private ImageView logo;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private AlertDialog dialog;

    public static RegisterStepTwoFragment newInstance() {
        return new RegisterStepTwoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

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
                        Picasso.get().load(uri).into(logo);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        logo.setImageBitmap(photo);
                    }
                }
        );
        return inflater.inflate(R.layout.fragment_register_step_two, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterStepTwoViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String selectedCategory = RegisterStepTwoFragmentArgs.fromBundle(getArguments()).getSelectedCategory();
        backButton = view.findViewById(R.id.back_button);
        registerBtn = view.findViewById(R.id.registerBtn);
        logoPicker = view.findViewById(R.id.logoPicker);
        logo = view.findViewById(R.id.logo);
        registerBtn.setOnClickListener(this::goToDashboard);
        backButton.setOnClickListener(this::goBack);
        logoPicker.setOnClickListener(v -> showLogoPickerChoices());
    }


    private void goBack(View view) {
        NavDirections action = RegisterStepTwoFragmentDirections.actionRegisterStepTwoToRegisterStepOne();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToDashboard(View view) {
        NavDirections action = RegisterStepTwoFragmentDirections.actionRegisterStepTwoToDemoActivity();
        Navigation.findNavController(view).navigate(action);
    }


    private void showLogoPickerChoices() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this.getContext());
        builder.setTitle(R.string.register_add_logo);
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