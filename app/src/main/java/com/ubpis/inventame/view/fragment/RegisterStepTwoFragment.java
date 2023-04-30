package com.ubpis.inventame.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Business;
import com.ubpis.inventame.data.model.UserType;
import com.ubpis.inventame.data.repository.BusinessRepository;
import com.ubpis.inventame.databinding.FragmentRegisterStepTwoBinding;
import com.ubpis.inventame.viewmodel.RegisterStepTwoViewModel;

public class RegisterStepTwoFragment extends Fragment {

    private RegisterStepTwoViewModel mViewModel;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private AlertDialog dialog;
    private FragmentRegisterStepTwoBinding binding;
    private String selectedCategory;
    private FirebaseAuth auth;

    public static RegisterStepTwoFragment newInstance() {
        return new RegisterStepTwoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
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
                        Picasso.get().load(uri).into(binding.logo);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        binding.logo.setImageBitmap(photo);
                    }
                }
        );
        mViewModel = new ViewModelProvider(this).get(RegisterStepTwoViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_step_two, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.selectedCategory = RegisterStepTwoFragmentArgs.fromBundle(getArguments()).getSelectedCategory();
        binding.backButton.setOnClickListener(this::goBack);
        binding.logoPicker.setOnClickListener(v -> showLogoPickerChoices());
        binding.registerBtn.setOnClickListener(this::register);
        setupValidationObservers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupValidationObservers() {
        mViewModel.getEmail().observe(getViewLifecycleOwner(), email -> mViewModel.checkEmail());
        mViewModel.getPassword().observe(getViewLifecycleOwner(), password -> mViewModel.checkPassword());
        mViewModel.getTradename().observe(getViewLifecycleOwner(), description -> mViewModel.checkTradename());
        mViewModel.getPasswordConfirmation().observe(getViewLifecycleOwner(), confirmation -> mViewModel.checkPasswordConfirm());
        mViewModel.getCif().observe(getViewLifecycleOwner(), cif -> mViewModel.checkCif());
        mViewModel.errors.observe(getViewLifecycleOwner(), errors -> {
            if (errors.containsKey("tradename")) {
                binding.nameTextField.setError(getString(errors.get("tradename")));
            } else {
                binding.nameTextField.setError(null);
            }
            if (errors.containsKey("email")) {
                binding.emailTextField.setError(getString(errors.get("email")));
            } else {
                binding.emailTextField.setError(null);
            }
            if (errors.containsKey("password")) {
                binding.passwordTextField.setError(getString(errors.get("password")));
            } else {
                binding.passwordTextField.setError(null);
            }
            if (errors.containsKey("passwordConfirm")) {
                binding.confirmTextField.setError(getString(errors.get("passwordConfirm")));
            } else {
                binding.confirmTextField.setError(null);
            }
            if (errors.containsKey("cif")) {
                binding.cifTextField.setError(getString(errors.get("cif")));
            } else {
                binding.cifTextField.setError(null);
            }
            binding.registerBtn.setEnabled(mViewModel.isValidForm());
        });
    }


    private void register(View view) {
        binding.registerBtn.setEnabled(false);
        auth.createUserWithEmailAndPassword(mViewModel.getEmail().getValue(), mViewModel.getPassword().getValue())
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Business newBusiness = new Business();
                        newBusiness.setId(user.getUid());
                        newBusiness.setTradename(binding.companyName.getText().toString());
                        newBusiness.setDescription(binding.companyDesc.getText().toString());
                        newBusiness.setCif(binding.companyCif.getText().toString());
                        newBusiness.setEmail(binding.companyEmail.getText().toString());
                        newBusiness.setCategory(selectedCategory);
                        newBusiness.setType(UserType.BUSINESS);
                        newBusiness.setCreatedAt(Timestamp.now());
                        if (binding.logo.getDrawable() != null) {
                            Bitmap bitmap = ((BitmapDrawable) binding.logo.getDrawable()).getBitmap();
                            BusinessRepository.getInstance().uploadLogo(user.getUid(), bitmap, o -> {
                                newBusiness.setImageUrl(o.toString());
                                BusinessRepository.getInstance().addBusiness(newBusiness).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        goToDashboard(view);
                                    } else {
                                        Log.w("Register", "Error adding document", task1.getException());
                                        binding.registerBtn.setEnabled(true);
                                    }
                                });
                            });
                        } else {
                            BusinessRepository.getInstance().addBusiness(newBusiness).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    goToDashboard(view);
                                } else {
                                    Log.w("Register", "Error adding document", task1.getException());
                                    binding.registerBtn.setEnabled(true);
                                }
                            });
                        }
                    } else {
                        Log.w("Register", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getActivity(), "Register failed",
                                Toast.LENGTH_SHORT).show();
                        binding.registerBtn.setEnabled(true);
                    }
                });
    }

    private void goBack(View view) {
        NavDirections action = RegisterStepTwoFragmentDirections.actionRegisterStepTwoToRegisterStepOne();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToDashboard(View view) {
        NavDirections action = RegisterStepTwoFragmentDirections.actionRegisterStepTwoToMainActivity();
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