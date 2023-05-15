package com.ubpis.inventame.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.*;

import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.CartItem;
import com.ubpis.inventame.data.model.Sale;
import com.ubpis.inventame.view.adapter.SalesCardAdapter;
import android.view.Menu;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SalesFragment extends Fragment implements OnMenuItemClickListener, OnClickListener {
    private static final int ICON_MARGIN = 8;
    private RecyclerView saleCardList;
    private SalesCardAdapter salesCardAdapter;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<Sale> saleCards;
    private MenuItem filterButton;
    private HorizontalScrollView scrollViewFilterChip;
    private ChipGroup filterChipGroup;
    private Chip filterChipSaleType;
    private Chip filterChipDate;
    private Chip filterChipPrice;
    private Chip filterChipHighlightType;
    private PopupMenu popupMenuFilterSaleType;
    private PopupMenu popupMenuFilterDate;
    private PopupMenu popupMenuFilterPrice;
    private PopupMenu popupMenuFilterHighlightType;
    private Context wrapper;
    private double total = 0;

    public static SalesFragment newInstance() {
        return new SalesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saleCardList = view.findViewById(R.id.saleCardList);
        coordinatorLayout = view.findViewById(R.id.sales_container);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        saleCardList.setLayoutManager(manager);
        saleCards = new ArrayList<>();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("https://images.hola.com/imagenes/seleccion/20220301205438/google-nest-hub-guia/1-185-257/google-nest-hub-m.jpg", "Google Nest Hub", 1,99.00f));
        cartItems.add(new CartItem("https://cdn.businessinsider.es/sites/navi.axelspringer.es/public/media/image/2022/02/reloj-cocina-2620297.jpg", "Clock", 3, 45.00f));
        cartItems.add(new CartItem("https://www.powerplanetonline.com/cdnassets/yeelight_crystal_pendant_light_lampara_de_techo_inteligente_09_ad_l.jpg", "Pendant Lamp", 2, 200.00f));
        cartItems.add(new CartItem("https://lh3.googleusercontent.com/cewixHQrBsI-iviE4qPNPLppaYuNTccxIBTi9v2XusjhRvp-UdBpOAYr78exyrJPM5lyFjWHnEQFBSUyJuSSCd3sI-UGN67G8Nbi=s2048", "Google Pixelbook Go", 1, 600.00f));


        for (CartItem cartItem : cartItems) {
            total += cartItem.getTotalPrice();
        }
        saleCards.add(new Sale(cartItems, total));
        saleCards.add(new Sale(cartItems, total));
        saleCards.add(new Sale(cartItems, total));
        saleCards.add(new Sale(cartItems, total));
        saleCards.add(new Sale(cartItems, total));

        TextView numSales = view.findViewById(R.id.numSales);
        TextView sortType = view.findViewById(R.id.sortType);

        numSales.setText(String.format(numSales.getText().toString(), saleCards.size()));
        sortType.setText(String.format(sortType.getText().toString(), "Recently Bought"));

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
            }
            else if(newState == SearchView.TransitionState.HIDDEN){
                bottomNav.setVisibility(View.VISIBLE);
                bottomNav.animate().translationY(0).setDuration(300).setListener(null);
                fab.show();
            }
        });

        salesCardAdapter = new SalesCardAdapter(saleCards, coordinatorLayout, fab);
        saleCardList.setAdapter(salesCardAdapter);
        salesCardAdapter.setOnClickCardListener(this::showInfoSaleDialog);

        /** filter setup */

        //setup filter button of SearchBar
        SearchBar searchBar = view.findViewById(R.id.search_bar);
        Menu menu = searchBar.getMenu();
        filterButton = menu.findItem(R.id.filterButton);
        filterButton.setOnMenuItemClickListener(item -> showFilterOptions());

        //setup HorizontalScrowView for the chip group
        scrollViewFilterChip = view.findViewById(R.id.scrollViewFilterChip);

        //setup all chip filters
        filterChipSaleType = view.findViewById(R.id.filterChipSaleType);
        filterChipDate = view.findViewById(R.id.filterChipDate);
        filterChipPrice = view.findViewById(R.id.filterChipPrice);
        filterChipHighlightType = view.findViewById(R.id.filterChipHighlightType);

        //setup Popup Menus of all the chip filters
        wrapper = new ContextThemeWrapper(getContext(), R.style.FilterPopupMenu);

        popupMenuFilterSaleType = new PopupMenu(wrapper, filterChipSaleType);
        popupMenuFilterDate = new PopupMenu(wrapper, filterChipDate);
        popupMenuFilterPrice = new PopupMenu(wrapper, filterChipPrice);
        popupMenuFilterHighlightType = new PopupMenu(wrapper, filterChipHighlightType);

        popupMenuFilterSaleType.getMenuInflater().inflate(R.menu.filter_sale_type_menu, popupMenuFilterSaleType.getMenu());
        popupMenuFilterDate.getMenuInflater().inflate(R.menu.filter_date_menu, popupMenuFilterDate.getMenu());
        popupMenuFilterPrice.getMenuInflater().inflate(R.menu.filter_price_menu, popupMenuFilterPrice.getMenu());
        popupMenuFilterHighlightType.getMenuInflater().inflate(R.menu.filter_highlight_type_menu, popupMenuFilterHighlightType.getMenu());

        //setup chip listeners
        filterChipSaleType.setOnClickListener(this);
        filterChipDate.setOnClickListener(this);
        filterChipPrice.setOnClickListener(this);
        filterChipHighlightType.setOnClickListener(this);

        //setup popup menu listeners
        popupMenuFilterSaleType.setOnMenuItemClickListener(this);
        popupMenuFilterDate.setOnMenuItemClickListener(this);
        popupMenuFilterPrice.setOnMenuItemClickListener(this);
        popupMenuFilterHighlightType.setOnMenuItemClickListener(this);
    }
    private void showInfoSaleDialog(int position){
        new SaleDialogFragment(saleCards.get(position), total).show(getChildFragmentManager(), SaleDialogFragment.TAG);
    }

    private boolean showFilterOptions(){
        if (scrollViewFilterChip.getVisibility() == View.VISIBLE){
            filterButton.setIcon(R.drawable.ic_filter_list_24);
            scrollViewFilterChip.setVisibility(View.GONE);
        }
        else {
            filterButton.setIcon(R.drawable.ic_filter_list_off_24);
            scrollViewFilterChip.setVisibility(View.VISIBLE);
        }

        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterChipSaleType:
                filterChipSaleType.setChecked(false);
                filterChipSaleType.setCheckable(false);
                filterChipSaleType.setOnCloseIconClickListener(null);
                filterChipSaleType.setChipText(getString(R.string.text_filter_sale_type));

                popupMenuFilterSaleType.show();
                filterChipSaleType.setCloseIcon(getResources().getDrawable(R.drawable.ic_arrow_drop_up_24));
                break;
            case R.id.filterChipDate:
                filterChipDate.setChecked(false);
                filterChipDate.setCheckable(false);
                filterChipDate.setOnCloseIconClickListener(null);
                filterChipDate.setChipText(getString(R.string.text_filter_date));

                popupMenuFilterDate.show();
                filterChipDate.setCloseIcon(getResources().getDrawable(R.drawable.ic_arrow_drop_up_24));
                break;
            case R.id.filterChipPrice:
                filterChipPrice.setChecked(false);
                filterChipPrice.setCheckable(false);
                filterChipPrice.setOnCloseIconClickListener(null);
                filterChipPrice.setChipText(getString(R.string.text_filter_price));

                popupMenuFilterPrice.show();
                filterChipPrice.setCloseIcon(getResources().getDrawable(R.drawable.ic_arrow_drop_up_24));
                break;
            case R.id.filterChipHighlightType:
                filterChipHighlightType.setChecked(false);
                filterChipHighlightType.setCheckable(false);
                filterChipHighlightType.setOnCloseIconClickListener(null);
                filterChipHighlightType.setChipText(getString(R.string.text_filter_highlight_type));

                popupMenuFilterHighlightType.show();
                filterChipHighlightType.setCloseIcon(getResources().getDrawable(R.drawable.ic_arrow_drop_up_24));
                break;
            default:
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Drawable checkIcon = getResources().getDrawable(R.drawable.ic_check_24);
        Drawable cancelIcon = getResources().getDrawable(R.drawable.ic_cancel_24);
        Drawable arrowDropDownIcon = getResources().getDrawable(R.drawable.ic_arrow_drop_down_24);

        switch (item.getItemId()) {
            case R.id.allSaleTypes:
                clickChipSetup(filterChipSaleType, getString(R.string.text_filter_sale_type),false, arrowDropDownIcon);
                return true;
            case R.id.automatic:
                clickChipSetup(filterChipSaleType, item.getTitle().toString(), true, cancelIcon);
                filterChipSaleType.setOnCloseIconClickListener(v -> resetFilterOption(v));
                return true;

            case R.id.manual:
                clickChipSetup(filterChipSaleType, item.getTitle().toString(), true, cancelIcon);
                filterChipSaleType.setOnCloseIconClickListener(v -> resetFilterOption(v));
                return true;

            case R.id.allSaleHighlights:
                clickChipSetup(filterChipHighlightType, getString(R.string.text_filter_sale_type),false, arrowDropDownIcon);
                return true;

            case R.id.favourite:
                clickChipSetup(filterChipHighlightType, item.getTitle().toString(), true, cancelIcon);
                filterChipSaleType.setOnCloseIconClickListener(v -> resetFilterOption(v));
                return true;

            case R.id.noFavourite:
                clickChipSetup(filterChipHighlightType, item.getTitle().toString(), true, cancelIcon);
                filterChipSaleType.setOnCloseIconClickListener(v -> resetFilterOption(v));
                return true;

            default:
                return false;
        }
    }

    private void clickChipSetup(Chip chip, String filterNameOption, boolean isChecked, Drawable icon) {
        if (isChecked) {
            chip.setCheckable(isChecked);
            chip.setChecked(isChecked);
        }
        else {
            chip.setChecked(isChecked);
            chip.setCheckable(isChecked);
        }
        chip.setCloseIcon(icon);
        chip.setChipText(filterNameOption);
    }

    private void resetFilterOption(View v){
        Drawable arrowDropDownIcon = getResources().getDrawable(R.drawable.ic_arrow_drop_down_24);

        switch (v.getId()) {
            case R.id.filterChipSaleType:
                clickChipSetup(filterChipSaleType, getString(R.string.text_filter_sale_type), false, arrowDropDownIcon);
                filterChipSaleType.setOnCloseIconClickListener(null);
                break;
            case R.id.filterChipDate:
                clickChipSetup(filterChipDate, getString(R.string.text_filter_date), false, arrowDropDownIcon);
                filterChipDate.setOnCloseIconClickListener(null);
                break;
            case R.id.filterChipPrice:
                clickChipSetup(filterChipPrice, getString(R.string.text_filter_price), false, arrowDropDownIcon);
                filterChipPrice.setOnCloseIconClickListener(null);
                break;
            case R.id.filterChipHighlightType:
                clickChipSetup(filterChipHighlightType, getString(R.string.text_filter_highlight_type), false, arrowDropDownIcon);
                filterChipHighlightType.setOnCloseIconClickListener(null);
                break;
            default:
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    @SuppressWarnings("RestrictTo")
    private void showMenu(View v, @MenuRes int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        // Inflating the Popup using xml file
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        // There is no public API to make icons show on menus.
        // IF you need the icons to show this works however it's discouraged to rely on library only
        // APIs since they might disappear in future versions.

        if (popup.getMenu() instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
            //noinspection RestrictedApi
            menuBuilder.setOptionalIconsVisible(true);
            //noinspection RestrictedApi
            for (MenuItem item : menuBuilder.getVisibleItems()) {
                int iconMarginPx = (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP, ICON_MARGIN, getResources().getDisplayMetrics());

                if (item.getIcon() != null) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        item.setIcon(new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0));
                    } else {
                        item.setIcon(
                                new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0) {
                                    @Override
                                    public int getIntrinsicWidth() {
                                        return getIntrinsicHeight() + iconMarginPx + iconMarginPx;
                                    }
                                });
                    }
                }
            }
        }
        popup.show();
    }
}