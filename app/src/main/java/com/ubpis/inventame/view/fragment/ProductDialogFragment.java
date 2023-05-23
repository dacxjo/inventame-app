package com.ubpis.inventame.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
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
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.data.repository.ProductRepository;
import com.ubpis.inventame.databinding.FragmentProductFormBinding;
import com.ubpis.inventame.viewmodel.InventoryViewModel;

import java.util.Date;

public class ProductDialogFragment extends DialogFragment {

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    private ActivityResultLauncher<ScanOptions> barcodeLauncher;
    private AlertDialog dialog;
    public static String TAG = "AddProductDialog";
    private boolean isEdit;
    private FragmentProductFormBinding binding;
    private InventoryViewModel viewModel;
    private FirebaseAuth auth;
    private Product product;
    private Drawable originalDrawable;


    public ProductDialogFragment() {
        this.isEdit = false;
        this.product = null;
    }

    public ProductDialogFragment(boolean isEdit, Product product) {
        this.isEdit = isEdit;
        this.product = product;
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
        auth = FirebaseAuth.getInstance();
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isEdit){
            viewModel = new ViewModelProvider(requireParentFragment()).get(InventoryViewModel.class);
        }else{
            viewModel = new ViewModelProvider(this.requireActivity()).get(InventoryViewModel.class);
        }
        binding.setViewModel(viewModel);
        viewModel.setSelected(product);
        binding.productPicker.setOnClickListener(v -> showProductPickerChoices());
        binding.scannerButton.setOnClickListener(v -> launchScanner());
        binding.productExpirationTextfield.setOnClickListener(v -> showDatePicker());
        binding.deleteButton.setOnClickListener(value -> deleteProduct());
        setupToolbar();
        handleEdit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() == null) {
                        Toast.makeText(getActivity(), R.string.common_cancelled, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.scanned, result.getContents()), Toast.LENGTH_LONG).show();
                        binding.productCodeTextfield.setText(result.getContents());
                    }
                });

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_form, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        Picasso .get().load(R.drawable.placeholder).into(binding.product);
        return binding.getRoot();
    }

    private void launchScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt(getString(R.string.scan_label));
        barcodeLauncher.launch(options);
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText(R.string.select_date)
                        .setNegativeButtonText(R.string.common_cancel)
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

        datePicker.addOnPositiveButtonClickListener(selectedDate -> {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String dateString = sdf.format(new Date(selectedDate));
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

    private void handleEdit() {
        if (this.isEdit) {
            binding.deleteButton.setVisibility(View.VISIBLE);
            binding.divider.setVisibility(View.GONE);
            binding.scannerButton.setVisibility(View.GONE);
            binding.toolbar.setTitle(R.string.inventory_edit_title);

            if(this.product != null && this.product.getImageUrl() != null && !this.product.getImageUrl().isEmpty()){
                Picasso .get().load(this.product.getImageUrl()).error(R.drawable.placeholder).into(binding.product);
                originalDrawable = binding.product.getDrawable();
            }
        }
    }

    private void addProduct() {
        String businessId = auth.getCurrentUser().getUid();
        Product product = new Product();
        product.setBarcode(binding.productCodeTextfield.getText().toString());
        product.setPrice(Float.parseFloat(binding.productPriceTextfield.getText().toString()));
        product.setDescription(binding.productDescriptionTextarea.getText().toString());
        product.setStock(Integer.parseInt(binding.productStockTextfield.getText().toString()));
        product.setName(binding.productNameTextfield.getText().toString());
        product.setBatch(binding.productBatchTextfield.getText().toString());
        product.setBusinessId(businessId);
        product.setExpired(false);
        product.setExpirationDate(binding.productExpirationTextfield.getText().toString());
        product.setCreatedAt(Timestamp.now());
        if (binding.product.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) binding.product.getDrawable()).getBitmap();
            ProductRepository.getInstance().uploadPicture(businessId + product.getBarcode(), bitmap, o -> {
                product.setImageUrl(o.toString());
                viewModel.addProduct(product);
                dismiss();
            });
        } else {
            Log.i("ProductFormFragment", "No image selected");
            product.setImageUrl("");
            viewModel.addProduct(product);
            dismiss();
        }

    }

    private void updateProduct(){
        if (binding.product.getDrawable() != null && binding.product.getDrawable() != originalDrawable) {
            Bitmap bitmap = ((BitmapDrawable) binding.product.getDrawable()).getBitmap();
            ProductRepository.getInstance().uploadPicture(viewModel.selected.getValue().getBusinessId() + product.getBarcode(), bitmap, o -> {
                product.setImageUrl(o.toString());
                viewModel.updateProduct(viewModel.selected.getValue());
                dismiss();
            });
        } else {
            product.setImageUrl("");
            viewModel.updateProduct(viewModel.selected.getValue());
            dismiss();
        }
    }

    private void deleteProduct(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext())
                .setTitle(R.string.delete_product_confirm)
                .setMessage(R.string.common_delete_disclaimer)
                .setPositiveButton(R.string.common_delete, (dialog, which) -> {
                    viewModel.deleteProduct(viewModel.selected.getValue().getId());
                    dismiss();
                })
                .setNegativeButton(R.string.common_cancel, null);
        builder.create().show();


    }

    private void setupToolbar(){
        binding.toolbar.setNavigationOnClickListener(value -> dismiss());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_save:
                    if(this.isEdit){
                        updateProduct();
                    }else{
                        addProduct();
                    }
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        viewModel.setSelected(null);
        viewModel.loadProductsFromRepository(auth.getCurrentUser().getUid());
    }


}
