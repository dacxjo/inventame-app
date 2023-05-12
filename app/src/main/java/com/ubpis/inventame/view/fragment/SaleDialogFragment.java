package com.ubpis.inventame.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.CartItem;
import com.ubpis.inventame.data.model.Sale;
import com.ubpis.inventame.view.adapter.SaleCardAdapter;
import com.ubpis.inventame.view.adapter.SalesCardAdapter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class SaleDialogFragment extends DialogFragment {

    public static String TAG = "SaleProductDialog";
    private Sale sale;
    private Toolbar toolbar;
    private Menu menuToolbar;
    private MenuItem menuItemDownload;
    private SaleCardAdapter saleCardAdapter;
    private RecyclerView saleCartList;
    private TextView infoSale, totalPrice, subtotalPrice, IVAPrice, headlineIVA;
    private ArrayList<CartItem> itemsList;
    private double total;

    public SaleDialogFragment(Sale sale, double total) {
        this.sale = sale;
        this.total = total;
        this.itemsList = sale.getList();
    }
    public SaleDialogFragment() {
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(value -> dismiss());

        menuToolbar = toolbar.getMenu();
        menuItemDownload = menuToolbar.findItem(R.id.download);
        menuItemDownload.setOnMenuItemClickListener(menuItem -> downloadSaleCSV());

        infoSale = view.findViewById(R.id.infoSale);
        infoSale.setLineSpacing(0f,1.5f);
        totalPrice = view.findViewById(R.id.totalPrice);
        subtotalPrice = view.findViewById(R.id.subtotalPrice);
        IVAPrice = view.findViewById(R.id.IVAPrice);
        headlineIVA = view.findViewById(R.id.headlineIVA);

        saleCartList = view.findViewById(R.id.saleCartList);

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        MaterialDividerItemDecoration dividerItemDecoration = new MaterialDividerItemDecoration(saleCartList.getContext(),
                manager.getOrientation());
        saleCartList.addItemDecoration(dividerItemDecoration);

        saleCartList.setLayoutManager(manager);

        saleCardAdapter = new SaleCardAdapter(itemsList);
        saleCartList.setAdapter(saleCardAdapter);

        //set ID sale
        toolbar.setTitle(String.format(toolbar.getTitle().toString(), sale.getUuid().substring(0,8)));

        //set information sale
        Date dateSale = sale.getDate();
        Instant instant = dateSale.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = localDateTime.format(formatter);

        infoSale.setText(String.format(infoSale.getText().toString(), "Nombre", formattedDate));

        //set all prices
        totalPrice.setText(String.format("%.2f", total) + "€");
        subtotalPrice.setText(String.format("%.2f", total/1.21) + "€");
        IVAPrice.setText(String.format("%.2f", total - (total/1.21)) + "€");

        //set IVA
        headlineIVA.setText(String.format(headlineIVA.getText().toString(), 21));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_sale_dialog, container, false);
    }

    private boolean downloadSaleCSV(){

        return true;
    }
}
