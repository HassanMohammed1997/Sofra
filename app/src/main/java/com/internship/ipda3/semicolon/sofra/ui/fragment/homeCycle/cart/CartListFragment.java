package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.cart;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.ipda3.semicolon.sofra.Constants;
import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.CartAdapter;
import com.internship.ipda3.semicolon.sofra.local.database.Cart;
import com.internship.ipda3.semicolon.sofra.local.database.CartDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {


    @BindView(R.id.cart_list_fragment_rv_allCarts)
    RecyclerView cartListFragmentRvAllCarts;
    Unbinder unbinder;
    List<Cart> cartList = new ArrayList<>();
    CartAdapter adapter;

    CartDatabase cartDatabase;
    @BindView(R.id.cart_list_fragment_tv_total)
    TextView cartListFragmentTvTotal;

    public CartListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);

        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        intiDatabase();
        getCartItemsFromDatabase();

        return view;
    }

    private void setupRecyclerView() {
        cartListFragmentRvAllCarts.setHasFixedSize(true);
        cartListFragmentRvAllCarts.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void intiDatabase() {
        cartDatabase = Room.databaseBuilder(getContext(), CartDatabase.class, Constants.Database.CART_DB_NAME)
                .fallbackToDestructiveMigration()
                .build();


    }

    private void getCartItemsFromDatabase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Cart> cart = cartDatabase.daoAccess().getCart();

                adapter = new CartAdapter(cart, getContext(), getActivity());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cartListFragmentRvAllCarts.setAdapter(adapter);

                    }
                });

            }
        }).start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.cart_list_fragment_btn_complete_order, R.id.cart_list_fragment_btn_add_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cart_list_fragment_btn_complete_order:
                completeOrder();
                break;
            case R.id.cart_list_fragment_btn_add_more:
                break;
        }
    }

    private void completeOrder() {

    }
}
