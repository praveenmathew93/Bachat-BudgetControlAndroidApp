package com.example.Bachat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TransactionsPagerAdapter extends FragmentStateAdapter {

    public TransactionsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new ShowAllEarningFragment();
            default:
                return new ShowAllExpenseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
