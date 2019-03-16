package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.FoodListAdapter;
import com.internship.ipda3.semicolon.sofra.adapter.OrdersAdapter;
import com.internship.ipda3.semicolon.sofra.model.client.order.orders.OrderDatum;
import com.internship.ipda3.semicolon.sofra.model.client.order.orders.Orders;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem.FoodItemDatum;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem.RestaurantFoodItems;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    @BindView(R.id.items_fragment_Rv_display_items)
    RecyclerView itemsFragmentRvDisplayItems;
    @BindView(R.id.items_fragment_Btn_add_item)
    Button itemFragmentBtnAddItem;
    Unbinder unbinder;
    EndPoint endPoint;
    private int id;
    private String apiToken;


    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        id = LoadIntegerData(getActivity(), USER_TYPE);
        apiToken = LoadStringData(getActivity(), API_TOKEN);


        unbinder = ButterKnife.bind(this, view);
        endPoint = getClient().create(EndPoint.class);

        setupRecyclerView();


        //TODO if user type = 1 (ordinary user), then display his/her orders and hide a new item button.
        //TODO otherwise, then display the restaurant items/foods.
        if (id == 1) {

        } else if (id == 2) {
            getRestaurantItems(1);
        }
        return view;
    }

    private void setupRecyclerView() {
        itemsFragmentRvDisplayItems.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsFragmentRvDisplayItems.setHasFixedSize(true);
    }

    private void getRestaurantItems(int page) {
        endPoint.restaurantItems(apiToken, page)
                .enqueue(new Callback<RestaurantFoodItems>() {
                    @Override
                    public void onResponse(Call<RestaurantFoodItems> call, Response<RestaurantFoodItems> response) {
                        if (response.code() == 200) {
                            long status = response.body().getStatus();
                            String msg = response.body().getMsg();
                            verbose("onResponse: get restaurant items: " + msg);
                            if (status == 1) {
                                List<FoodItemDatum> data = response.body().getData().getData();
                                FoodListAdapter foodListAdapter = new FoodListAdapter(data, getContext(), getActivity());
                                itemsFragmentRvDisplayItems.setAdapter(foodListAdapter);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantFoodItems> call, Throwable t) {
                        error("onFailure: get restaurant items: " + t.getMessage());

                    }
                });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.items_fragment_Btn_add_item)
    public void onViewClicked() {
        replace(R.id.home_activity_frame, new AddItemFragment(), getFragmentManager());


    }

    private void getCurrentOrders() {
        endPoint.getUserOrders(apiToken, "current", 1)
                .enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            long status = response.body().getStatus();
                            if (status == 1) {
                                List<OrderDatum> orderData = response.body().getData().getData();
                                OrdersAdapter adapter = new OrdersAdapter(getContext(), orderData);
                                itemsFragmentRvDisplayItems.setAdapter(adapter);
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

}
