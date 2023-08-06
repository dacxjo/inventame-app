package com.ubpis.inventame.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.ubpis.inventame.R;

public class OnboardActivity extends AppCompatActivity {


    private NavHostFragment navHostFragment;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        auth = FirebaseAuth.getInstance();
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, navDestination, bundle) -> {
            // If user is logged in (all fragments of this scope), go to Dashboard
            if (auth.getCurrentUser() != null) {
                this.goToDashboard(controller);
            }
        });
    }

    private void goToDashboard(NavController controller) {
        NavDirections action = MainActivityDirections.actionGlobalMainActivity();
        controller.navigate(action);
    }
}