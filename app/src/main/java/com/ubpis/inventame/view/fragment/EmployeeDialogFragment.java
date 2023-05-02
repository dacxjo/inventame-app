package com.ubpis.inventame.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Employee;
import com.ubpis.inventame.data.model.UserType;
import com.ubpis.inventame.databinding.FragmentEmployeeFormBinding;
import com.ubpis.inventame.viewmodel.EmployeeViewModel;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class EmployeeDialogFragment extends DialogFragment {

    private String defaultAvatarPath;
    private Employee employee;
    private FragmentEmployeeFormBinding binding;
    private EmployeeViewModel viewModel;
    private boolean isEdit;
    private FirebaseAuth auth;
    private FirebaseAuth authHelper;
    public static String TAG = "AddEmployeeDialog";

    public EmployeeDialogFragment(){
        this.isEdit = false;
        this.employee = null;
    }

    public EmployeeDialogFragment(boolean isEdit, Employee employee){
        this.isEdit = isEdit;
        this.employee = new Employee(employee);
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
        auth = FirebaseAuth.getInstance();
        // Necessary to make a user for employees
        // TODO: Use ENV vars to this values
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://inventame-2c44f.firebaseio.com")
                .setApiKey("AIzaSyBrT_mlSj_wHv3qOGQHRd8dG9tEGLLFv4s")
                .setApplicationId("inventame-2c44f").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getContext(), firebaseOptions, "FirebaseApp2");
            authHelper = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            authHelper = FirebaseAuth.getInstance(FirebaseApp.getInstance("FirebaseApp2"));
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_form, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isEdit){
            viewModel = new ViewModelProvider(requireParentFragment()).get(EmployeeViewModel.class);
        }else{
            viewModel = new ViewModelProvider(this.requireActivity()).get(EmployeeViewModel.class);
        }
        viewModel.setSelected(this.employee);

        int randomInt = (int) (Math.random() * 1000000);
        this.defaultAvatarPath = "https://api.dicebear.com/6.x/notionists/png?seed=" + randomInt;

        handleEdit();
        setupToolbar();

        binding.deleteButton.setOnClickListener(value -> deleteEmployee());
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
        viewModel.loadEmployeesFromRepository(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    private void addEmployee(){
        authHelper.createUserWithEmailAndPassword(binding.employeeEmail.getText().toString(), binding.employeePassword.getText().toString()).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String businessId = auth.getCurrentUser().getUid();
                Employee employee = new Employee();
                employee.setId(task.getResult().getUser().getUid());
                employee.setName(binding.employeeName.getText().toString());
                employee.setLastname(binding.employeeLastname.getText().toString());
                employee.setEmail(binding.employeeEmail.getText().toString());
                employee.setDocumentNumber(binding.employeeDocument.getText().toString());
                employee.setType(UserType.EMPLOYEE);
                employee.setBusinessId(businessId);
                employee.setImageUrl(this.defaultAvatarPath);
                employee.setCreatedAt(Timestamp.now());
                viewModel.addEmployee(employee);
                authHelper.signOut();
                dismiss();
            }
        });

    }

    private void updateEmployee(){
        viewModel.updateEmployee(viewModel.selected.getValue());
        dismiss();
    }

    private void deleteEmployee(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext())
                .setTitle("¿Borrar empleado?")
                .setMessage("Esta acción no se puede deshacer")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteEmployee(viewModel.selected.getValue().getId());
                        dismiss();
                    }
                })
                .setNegativeButton("Cancelar", null);
         builder.create().show();


    }

    private void setupToolbar(){
        binding.setViewModel(viewModel);
        binding.toolbar.setNavigationOnClickListener(value -> dismiss());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_save:
                    if(this.isEdit){
                        updateEmployee();
                    }else{
                        addEmployee();
                    }
                    return true;
                default:
                    return false;
            }
        });
    }

    private void handleEdit(){
        String avatar = this.isEdit && !viewModel.selected.getValue().getImageUrl().isEmpty() ? viewModel.selected.getValue().getImageUrl() : this.defaultAvatarPath;
        Picasso.get().load(avatar).transform(new RoundedCornersTransformation(50, 0)).fit()
                .centerCrop().into(binding.employeeImage);
        if(this.isEdit){
            binding.deleteButton.setVisibility(View.VISIBLE);
            binding.toolbar.setTitle(R.string.employee_edit_title);;
        }
    }


}
