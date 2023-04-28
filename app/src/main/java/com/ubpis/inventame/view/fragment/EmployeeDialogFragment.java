package com.ubpis.inventame.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class EmployeeDialogFragment extends DialogFragment {

    private ImageView employeeImage;
    private String defaultAvatarPath;
    private Button deleteButton;

    private boolean isEdit;

    public static String TAG = "AddEmployeeDialog";

    public EmployeeDialogFragment(){
        this.isEdit = false;
    }

    public EmployeeDialogFragment(boolean isEdit){
        this.isEdit = isEdit;
    }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_employee_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(value -> dismiss());
        employeeImage = view.findViewById(R.id.employee_image);
        deleteButton = view.findViewById(R.id.delete_button);
        int randomInt = (int) (Math.random() * 1000000);
        this.defaultAvatarPath = "https://api.dicebear.com/6.x/notionists/png?seed=" + randomInt;
        Picasso.get().load(this.defaultAvatarPath).transform(new RoundedCornersTransformation(50, 0)).fit()
                .centerCrop().into(employeeImage);
        handleEdit();
    }

    private void handleEdit(){
        if(this.isEdit){
            deleteButton.setVisibility(View.VISIBLE);
            toolbar.setTitle(R.string.employee_edit_title);
        }
    }

}
