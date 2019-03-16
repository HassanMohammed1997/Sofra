package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.userOrderViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.OrdersAdapter;
import com.internship.ipda3.semicolon.sofra.model.client.order.orders.OrderDatum;
import com.internship.ipda3.semicolon.sofra.model.client.order.orders.Orders;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.initEndPointInterface;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.isNetworkAvailable;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousOrdersFragment extends Fragment {


    @BindView(R.id.previous_order_fragment_rv_display_current_orders)
    RecyclerView previousOrderFragmentRvDisplayCurrentOrders;
    Unbinder unbinder;
    EndPoint endPoint;

    public PreviousOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_orders, container, false);

        endPoint = initEndPointInterface();

        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        if (isNetworkAvailable(getContext())) {
            getPreviousOrder(1);
        }

        return view;
    }

    private void getPreviousOrder(int page) {
        endPoint.getUserOrders("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB", "current", page)
                .enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            long status = response.body().getStatus();
                            verbose("onResponse: get current user orders: server message: " + msg);
                            if (status == 1) {
                                List<OrderDatum> orderData = response.body().getData().getData();
                                OrdersAdapter ordersAdapter = new OrdersAdapter(getContext(), orderData);
                                previousOrderFragmentRvDisplayCurrentOrders.setAdapter(ordersAdapter);

                            }
                        } else {
                            verbose("onResponse: response raw: " + response.raw());
                        }
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        error("onFailure: server failure message: " + t.getMessage());

                    }
                });

    }

    private void setupRecyclerView() {
        previousOrderFragmentRvDisplayCurrentOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        previousOrderFragmentRvDisplayCurrentOrders.setHasFixedSize(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
