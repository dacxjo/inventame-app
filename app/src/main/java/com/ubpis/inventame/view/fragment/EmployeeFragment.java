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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchView;
import com.ubpis.inventame.R;
import com.ubpis.inventame.view.adapter.UserCardAdapter;
import com.ubpis.inventame.viewmodel.EmployeeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private RecyclerView employeeList;
    private UserCardAdapter userCardAdapter;

    public static EmployeeFragment newInstance() {
        return new EmployeeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        employeeList = view.findViewById(R.id.employeeCardRv);
        LinearLayoutManager manager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(employeeList.getContext(),
                manager.getOrientation());
        employeeList.addItemDecoration(dividerItemDecoration);

        employeeList.setLayoutManager(manager);
        userCardAdapter = new UserCardAdapter(
                viewModel.getUsers().getValue()
        );
        userCardAdapter.setOnClickCardListener(this::showEditEmployeeDialog);
        employeeList.setAdapter(userCardAdapter);

        SearchView searchView = requireView().findViewById(R.id.search_view);
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

    private void showEditEmployeeDialog(int position) {
        new EmployeeDialogFragment(true).show(
                getChildFragmentManager(), EmployeeDialogFragment.TAG);
    }
}