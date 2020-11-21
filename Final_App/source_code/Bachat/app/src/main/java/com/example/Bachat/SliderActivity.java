package com.example.Bachat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
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

public class SliderActivity extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;

    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_activity);
        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);


        setupOnboardingItems();
        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardoingViewPager);
         onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnBoardingIndicators();
        setCurrentOnboardingIndicator(0);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardingViewPager.getCurrentItem()+ 1 <onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+ 1);
                } else {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }

            }
        });
    }

    private void setupOnboardingItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();
        OnboardingItem itemPayOnline = new OnboardingItem();

        itemPayOnline.setTitle("Welcome to Bachat App");
        itemPayOnline.setDescription("Track your expenses and make smart decisions");
        itemPayOnline.setImage(R.drawable.logo);


        OnboardingItem itemOntheway = new OnboardingItem();
        itemOntheway.setTitle("ADD EXPENSES AND INCOME ");
        itemOntheway.setDescription("Press MINUS to add expenses and PLUS to add income ");
        itemOntheway.setImage(R.drawable.logo);

        OnboardingItem itemEatTogether = new OnboardingItem();
        itemEatTogether.setTitle("We make it easier for you");
        itemEatTogether.setDescription("You can use graphs and smart ananlytical tools ");
        itemEatTogether.setImage(R.drawable.moneydepo);

        OnboardingItem itemOntheway11 = new OnboardingItem();
        itemOntheway11.setTitle("NEW ");
        itemOntheway11.setDescription("Press MINUS to add expenses and PLUS to add income ");
        itemOntheway11.setImage(R.drawable.extra_income_icon);


        OnboardingItem itemOntheway1 = new OnboardingItem();
        itemOntheway1.setTitle("NEW ");
        itemOntheway1.setDescription("5th screen ");
        itemOntheway1.setImage(R.drawable.others);


        OnboardingItem itemOntheway21 = new OnboardingItem();
        itemOntheway21.setTitle("6th ");
        itemOntheway21.setDescription("screen ");
        itemOntheway21.setImage(R.drawable.onboarding_indicator_active);

        OnboardingItem itemOntheway2 = new OnboardingItem();
        itemOntheway2.setTitle("7th ");
        itemOntheway2.setDescription(" screen ");
        itemOntheway2.setImage(R.drawable.onboarding_indicator_active);

        onboardingItems.add(itemPayOnline);
        onboardingItems.add(itemOntheway);
        onboardingItems.add(itemEatTogether);
        onboardingItems.add(itemOntheway11);
        onboardingItems.add(itemOntheway1);
        onboardingItems.add(itemOntheway21);
        onboardingItems.add(itemOntheway2);

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
            buttonOnboardingAction.setText("Start");
        }else{
            buttonOnboardingAction.setText("Next");
        }



    }

}
