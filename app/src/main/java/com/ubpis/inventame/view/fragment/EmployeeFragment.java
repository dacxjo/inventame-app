package com.ubpis.inventame.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.faltenreich.skeletonlayout.Skeleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchView;
import com.google.firebase.auth.FirebaseAuth;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Employee;
import com.ubpis.inventame.databinding.FragmentEmployeeBinding;
import com.ubpis.inventame.view.adapter.EmployeeCardAdapter;
import com.ubpis.inventame.viewmodel.EmployeeViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private EmployeeCardAdapter employeeCardAdapter;
    private FragmentEmployeeBinding binding;
    private Skeleton skeleton;

    public static EmployeeFragment newInstance() {
        return new EmployeeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        LinearLayoutManager manager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.employeeList.getContext(),
                manager.getOrientation());
        binding.employeeList.addItemDecoration(dividerItemDecoration);

        binding.employeeList.setLayoutManager(manager);
        employeeCardAdapter = new EmployeeCardAdapter(
                viewModel.getEmployees().getValue()
        );
        employeeCardAdapter.setOnClickCardListener(this::showEditEmployeeDialog);
        binding.employeeList.setAdapter(employeeCardAdapter);
        /*viewModel.getIsLoading().observe(this.getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                skeleton.showSkeleton();
            } else {
                skeleton.showOriginal();
            }
        });
        skeleton = SkeletonLayoutUtils.applySkeleton(binding.employeeList, R.layout.user_card_layout);
        skeleton.setMaskCornerRadius(40f);*/
        final Observer<ArrayList<Employee>> observerEmployees = employees -> {
            if (viewModel.getEmployees().getValue().isEmpty()) {
                binding.emptyState.setVisibility(View.VISIBLE);
                binding.employeeList.setVisibility(View.GONE);
            } else {
                binding.emptyState.setVisibility(View.GONE);
                binding.employeeList.setVisibility(View.VISIBLE);
            }
            //employeeCardAdapter.setEmployees(employees); // Hacer un set con filtrados
            employeeCardAdapter.notifyDataSetChanged();
        };
        // Sustituir observer de employees por search query.
        // viewModel.getSearchResults().observe(this.getViewLifecycleOwner(), observerSearchResults);
        viewModel.getEmployees().observe(this.getViewLifecycleOwner(), observerEmployees); // ELIMINAR
        viewModel.loadEmployeesFromRepository(FirebaseAuth.getInstance().getCurrentUser().getUid());
        this.setupBottomNavigationTransition();
    }

    private void showEditEmployeeDialog(Employee employee) {
         new EmployeeDialogFragment(true, employee).show(
                getChildFragmentManager(), EmployeeDialogFragment.TAG);
    }

    private void setupBottomNavigationTransition(){
        SearchView searchView = binding.searchView;
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        ExtendedFloatingActionButton fab = getActivity().findViewById(R.id.extended_fab);
        searchView.addTransitionListener((searchView1, previousState, newState) -> {
            if (newState == SearchView.TransitionState.SHOWING) {
                bottomNav.animate().translationY(bottomNav.getHeight()).setDuration(300).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        bottomNav.setVisibility(View.GONE);
                    }
                });
                fab.hide();
            } else if (newState == SearchView.TransitionState.HIDDEN) {
                bottomNav.setVisibility(View.VISIBLE);
                bottomNav.animate().translationY(0).setDuration(300).setListener(null);
                fab.show();
            }
        });
    }
}