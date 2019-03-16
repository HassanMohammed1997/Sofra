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

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.isNetworkAvailable;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentOrdersFragment extends Fragment {


    @BindView(R.id.current_order_fragment_rv_show_orders)
    RecyclerView currentOrderFragmentRvShowOrders;
    Unbinder unbinder;

    EndPoint endPoint;
    private String apiToken;

    public CurrentOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_orders, container, false);

        apiToken = LoadStringData(getActivity(), API_TOKEN);


        unbinder = ButterKnife.bind(this, view);
        endPoint = getClient().create(EndPoint.class);
        setupRecyclerView();

        if (isNetworkAvailable(getContext())) {
            getCurrentOrders();
        } else {
            showToast(getContext(), getString(R.string.no_internet_connection));
        }
        return view;
    }

    private void getCurrentOrders() {

        endPoint.getUserOrders("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB", "completed", 1)
                .enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            long status = response.body().getStatus();
                            if (status == 1) {
                                List<OrderDatum> orderData = response.body().getData().getData();
                                OrdersAdapter adapter = new OrdersAdapter(getContext(), orderData);
                                currentOrderFragmentRvShowOrders.setAdapter(adapter);
                            }

                            verbose("onResponse: get users order: current orders: " + msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        error("onFailure: get user orders: current orders: failure: " + t.getMessage());

                    }
                });

    }


    private void setupRecyclerView() {
        currentOrderFragmentRvShowOrders.setHasFixedSize(true);
        currentOrderFragmentRvShowOrders.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
