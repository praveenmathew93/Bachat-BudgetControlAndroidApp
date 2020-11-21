package com.example.Bachat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Slider10 extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;

    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpviewpager);
        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);


        setupOnboardingItems();
        final ViewPager2 onboardViewPager = findViewById(R.id.onboardoingViewPager1);
        onboardViewPager.setAdapter(onboardingAdapter);

        setupOnBoardingIndicators();
        setCurrentOnboardingIndicator(0);
        onboardViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardViewPager.getCurrentItem()+ 1 <onboardingAdapter.getItemCount()) {
                    onboardViewPager.setCurrentItem(onboardViewPager.getCurrentItem()+ 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), HelpMenuActivity.class));
                    finish();
                }

            }
        });
    }

    private void setupOnboardingItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();
        OnboardingItem itemPayOnline = new OnboardingItem();
        itemPayOnline.setTitle("The currency that you choose while adding a transaction will be converted to your default currency");
        itemPayOnline.setDescription("");
        itemPayOnline.setImage(R.drawable.currencyconversion);


        OnboardingItem itemOntheway = new OnboardingItem();
        itemOntheway.setTitle("If your device is not connected to the Internet then the application will be taking offline conversion rates");
        itemOntheway.setDescription("");
        itemOntheway.setImage(R.drawable.edittransactions);




        onboardingItems.add(itemPayOnline);
        onboardingItems.add(itemOntheway);


        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnBoardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i< childCount; i++){
            ImageView imageView = (ImageView)layoutOnboardingIndicators.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            }else { imageView.setImageDrawable(
                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
            );

            }

        }
        if(index == onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Back");
        }else{
            buttonOnboardingAction.setText("Next");
        }



    }

}

