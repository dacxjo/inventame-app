package com.ubpis.inventame.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private EmployeeCardAdapter employeeCardAdapter, resultsCardAdapter;
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

        // Product List RecyclerView elements
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
        final Observer<ArrayList<Employee>> observerEmployees = employees -> {
            if (viewModel.getEmployees().getValue().isEmpty()) {
                binding.emptyState.setVisibility(View.VISIBLE);
                binding.employeeList.setVisibility(View.GONE);
            } else {
                binding.emptyState.setVisibility(View.GONE);
                binding.employeeList.setVisibility(View.VISIBLE);
            }
            employeeCardAdapter.notifyDataSetChanged();
        };
        viewModel.getEmployees().observe(this.getViewLifecycleOwner(), observerEmployees);
        viewModel.loadEmployeesFromRepository(FirebaseAuth.getInstance().getCurrentUser().getUid());
        // SearchView elements
        LinearLayoutManager managerResults = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );
        DividerItemDecoration dividerItemDecorationResults = new DividerItemDecoration(binding.employeeSearchResults.getContext(),
                managerResults.getOrientation());
        binding.employeeSearchResults.addItemDecoration(dividerItemDecorationResults);
        binding.employeeSearchResults.setLayoutManager(managerResults);
        resultsCardAdapter = new EmployeeCardAdapter(viewModel.getResults().getValue());
        resultsCardAdapter.setOnClickCardListener(this::showEditEmployeeDialog);
        binding.employeeSearchResults.setAdapter(resultsCardAdapter);
        binding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                resultsCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.searchEmployees(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                resultsCardAdapter.notifyDataSetChanged();
            }
        });
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