package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.ViewPagerAdapter;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.userOrderViewPager.CurrentOrdersFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.userOrderViewPager.PreviousOrdersFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserOrderFragment extends Fragment {


    @BindView(R.id.user_order_fragment_tabs_orders)
    TabLayout userOrderFragmentTabsOrders;
    @BindView(R.id.user_order_fragment_vp_show_orders)
    ViewPager userOrderFragmentVpShowOrders;
    Unbinder unbinder;

    public UserOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_order, container, false);

        unbinder = ButterKnife.bind(this, view);
        setupViewPager();
        return view;
    }

    private void setupViewPager() {
        //create new adapter from view pager adapter.
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), getContext());
        //add fragments and its title
        viewPagerAdapter.addFragment(new CurrentOrdersFragment(), getString(R.string.current_order_string));
        viewPagerAdapter.addFragment(new PreviousOrdersFragment(), getString(R.string.previous_order_string));
        userOrderFragmentVpShowOrders.setAdapter(viewPagerAdapter);

        //setup tabs layout with view pager.
        userOrderFragmentTabsOrders.setupWithViewPager(userOrderFragmentVpShowOrders);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
