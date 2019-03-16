package com.internship.ipda3.semicolon.sofra.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments.CommentsFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments.FoodListFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments.RestaurantInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Context context;
    List<Fragment> fragments = new ArrayList<>();
    List<String> fragmentTitle = new ArrayList<>();



    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int pos) {
        return fragments.get(pos);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return fragmentTitle.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        fragmentTitle.add(title);
    }
}
