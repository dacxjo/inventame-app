package com.ubpis.inventame.view.fragment;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ubpis.inventame.R;

import java.util.concurrent.TimeUnit;

public class SoftPOSPaymentFragment extends DialogFragment {
    public static String TAG = "SoftPOSPaymentFragment";
    private double total;
    private TextView quantityToCharge;
    private View readCard1, readCard2, readCard3, readCard4;
    private ImageView imageView;
    public SoftPOSPaymentFragment(double total) {
       this.total = total;
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

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soft_pos_payment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quantityToCharge = view.findViewById(R.id.quantityToCharge);

        String quantityTextView = String.format("%.2f€", total);
        quantityTextView.replace(".", ",");
        quantityToCharge.setText(quantityTextView);

        SpannableString spannableString =  new SpannableString(quantityToCharge.getText().toString());
        spannableString.setSpan(new RelativeSizeSpan(1.1875f), 0, quantityTextView.indexOf(","), 0);
        quantityToCharge.setText(spannableString);

        readCard1 = view.findViewById(R.id.readCard1);
        readCard2 = view.findViewById(R.id.readCard2);
        readCard3 = view.findViewById(R.id.readCard3);
        readCard4 = view.findViewById(R.id.readCard4);
        imageView = view.findViewById(R.id.imageView);

        ValueAnimator animator1 = ValueAnimator.ofFloat(0.5f, 1f);
        animator1.setDuration(1000); // Duración de la animación en milisegundos (2 segundos en este ejemplo)

        ValueAnimator animator2 = ValueAnimator.ofFloat(0.5f, 1f);
        animator2.setDuration(1000); // Duración de la animación en milisegundos (2 segundos en este ejemplo)

        ValueAnimator animator3 = ValueAnimator.ofFloat(0.5f, 1f);
        animator3.setDuration(1000); // Duración de la animación en milisegundos (2 segundos en este ejemplo)

        ValueAnimator animator4 = ValueAnimator.ofFloat(0.5f, 1f);
        animator4.setDuration(1000); // Duración de la animación en milisegundos (2 segundos en este ejemplo)
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                readCard1.getBackground().setAlpha((int) (alpha * 255));
                animator2.start();
            }
        });
        animator1.start();

        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                readCard2.getBackground().setAlpha((int) (alpha * 255));
                animator3.start();
            }
        });

        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                readCard3.getBackground().setAlpha((int) (alpha * 255));
                animator4.start();
            }
        });

        animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                readCard4.getBackground().setAlpha((int) (alpha * 255));
            }
        });
    }
}