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

public class SliderTest extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_test);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingItems();

        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardoingViewPager2);
        onboardingViewPager.setAdapter(onboardingAdapter);
        setLayoutOnboardingIndicators();
        setCurrentOnboardingIndicators(0);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });
        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onboardingViewPager.getCurrentItem() + 1 <onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                }else {
                   startActivity(new Intent(getApplicationContext(),SetupScreen.class));
                   finish();
                }
            }
        });
    }
    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemPayOnline = new OnboardingItem();

        itemPayOnline.setTitle("Welcome to Bachat");
        itemPayOnline.setDescription("by Team GingerBread");
        itemPayOnline.setImage(R.drawable.logo);


        OnboardingItem itemOntheway = new OnboardingItem();
        itemOntheway.setTitle("Add Expenses and Incomes");
        itemOntheway.setDescription("");
        itemOntheway.setImage(R.drawable.addexpenses);

        OnboardingItem itemEatTogether = new OnboardingItem();
        itemEatTogether.setTitle("View your transactions visually depicted ");
        itemEatTogether.setDescription("Using graphs");
        itemEatTogether.setImage(R.drawable.pieimage);

        OnboardingItem itemOntheway11 = new OnboardingItem();
        itemOntheway11.setTitle("Enter transactions in different currencies");
        itemOntheway11.setDescription("Get latest conversion rates ");
        itemOntheway11.setImage(R.drawable.currencyimage);


        OnboardingItem itemOntheway1 = new OnboardingItem();
        itemOntheway1.setTitle("Make Multiple Profiles ");
        itemOntheway1.setDescription("Keep your personal and business expenses separate");
        itemOntheway1.setImage(R.drawable.profile);


        OnboardingItem itemOntheway21 = new OnboardingItem();
        itemOntheway21.setTitle("View, Repeat and Edit Transactions ");
        itemOntheway21.setDescription("");
        itemOntheway21.setImage(R.drawable.edittransactions2);


        onboardingItems.add(itemPayOnline);
        onboardingItems.add(itemOntheway);
        onboardingItems.add(itemEatTogether);
        onboardingItems.add(itemOntheway11);
        onboardingItems.add(itemOntheway1);
        onboardingItems.add(itemOntheway21);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setLayoutOnboardingIndicators() {

        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
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

    private void setCurrentOnboardingIndicators(int index) {

        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("Start");
    } else {
        buttonOnboardingAction.setText("Next");


    }
    }
}
