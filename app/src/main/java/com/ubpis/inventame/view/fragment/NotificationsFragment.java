package com.ubpis.inventame.view.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.search.SearchView;
import com.ubpis.inventame.R;
import com.ubpis.inventame.view.adapter.NotificationCardAdapter;
import com.ubpis.inventame.viewmodel.NotificationsViewModel;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel ViewModel;
    private RecyclerView nList;
    private NotificationCardAdapter notificationCardAdapter;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        nList = view.findViewById(R.id.notificationList);
        LinearLayoutManager manager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(nList.getContext(),
                manager.getOrientation());
        nList.addItemDecoration(dividerItemDecoration);

        nList.setLayoutManager(manager);
        notificationCardAdapter = new NotificationCardAdapter(
                ViewModel.getUsers().getValue()
        );
        nList.setAdapter(notificationCardAdapter);
    }
}