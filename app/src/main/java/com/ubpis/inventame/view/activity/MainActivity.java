package com.ubpis.inventame.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.UserType;
import com.ubpis.inventame.view.fragment.EmployeeDialogFragment;
import com.ubpis.inventame.view.fragment.ProductDialogFragment;
import com.ubpis.inventame.view.fragment.SaleDialogFragment;

public class MainActivity extends AppCompatActivity {

    ExtendedFloatingActionButton fab;
    NavigationBarView bottomNav;
    NavHostFragment navHostFragment;
    FirebaseAuth auth;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        fab = findViewById(R.id.extended_fab);
        fab.setExtended(false);
        bottomNav = findViewById(R.id.bottom_navigation);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(this::setupFab);
        NavigationUI.setupWithNavController(bottomNav, navController);
        navController.addOnDestinationChangedListener((controller, navDestination, bundle) -> {
            // If user is not logged in (all fragments of this scope), go to startup
            if (auth.getCurrentUser() == null) {
                this.goToStartup(controller);
            }
        });
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userType = sharedPref.getString("userType", UserType.BUSINESS.toString());
        new Toast(this).makeText(this, userType, Toast.LENGTH_SHORT).show();
        if (userType.equals(UserType.EMPLOYEE.toString())) {
            bottomNav.getMenu().removeItem(R.id.employeesFragment);
        }

    }

    private void setupFab(@NonNull NavController navController, @NonNull NavDestination destination, @Nullable Bundle bundle) {
        if (destination.getId() == R.id.homeFragment || destination.getId() == R.id.notificationsFragment) {
            fab.hide();
        } else if (destination.getId() == R.id.inventoryFragment) {
            showFabNicely();
            fab.postDelayed(() -> {
                fab.shrink();
                fab.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_20));
            }, 200);
            fab.setOnClickListener(view -> new ProductDialogFragment().show(
                    navHostFragment.getParentFragmentManager(), ProductDialogFragment.TAG));
        } else if (destination.getId() == R.id.employeesFragment) {
            showFabNicely();
            fab.postDelayed(() -> {
                fab.shrink();
                fab.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_20));
            }, 200);
            fab.setOnClickListener(view -> new EmployeeDialogFragment().show(
                    navHostFragment.getParentFragmentManager(), EmployeeDialogFragment.TAG));
        } else if (destination.getId() == R.id.salesFragment) {
            showFabNicely();
            fab.postDelayed(() -> {
                fab.extend();
                fab.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_shopping_cart_24));
            }, 200);
            fab.setOnClickListener(view -> new SaleDialogFragment().show(
                    navHostFragment.getParentFragmentManager(), SaleDialogFragment.TAG));
        } else {
            fab.postDelayed(() -> fab.shrink(), 200);
            showFabNicely();
        }
    }

    private void showFabNicely() {
        if (!fab.isShown()) {
            fab.postDelayed(() -> fab.show(), 200);
        } else {
            fab.hide();
            fab.postDelayed(() -> fab.show(), 200);
        }
    }

    private void goToStartup(NavController controller) {
        NavDirections action = OnboardActivityDirections.actionGlobalOnBoardActivity();
        controller.navigate(action);
    }

}


