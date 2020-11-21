package com.example.Bachat;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TransactionHistory_old extends Fragment {
    View view;
    //private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ShowAllExpenseFragment showallexpensefragment=new ShowAllExpenseFragment();
    private ShowAllEarningFragment showallincomefragment= new ShowAllEarningFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_history_old, container, false);

        //getActivity().setTitle("Transaction History");

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        ViewPagerAdapter  adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.AddFragment(showallexpensefragment,"Show expense");
        adapter.AddFragment(showallincomefragment,"Show income");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}

