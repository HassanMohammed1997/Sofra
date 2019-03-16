package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.FoodListAdapter;
import com.internship.ipda3.semicolon.sofra.adapter.RestaurantAdapter;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.Restaurant;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.RestaurantDatum;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem.FoodItemDatum;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem.RestaurantFoodItems;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {


    @BindView(R.id.restaurant_fragment_rv_restaurant)
    RecyclerView restaurantFragmentRvRestaurant;
    Unbinder unbinder;
    EndPoint endPoint;
    private int id;
    private String apiToken;


    public RestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();

        id = LoadIntegerData(getActivity(), USER_TYPE);
        apiToken = LoadStringData(getActivity(), API_TOKEN);
        endPoint = getClient().create(EndPoint.class);

        if (id == 1){
            getRestaurantList(1);
        }else if (id == 2){
            getCurrentRestaurantItem(1);
        }

        return view;
    }

    private void getCurrentRestaurantItem(int page) {
        endPoint.restaurantItems(apiToken, page)
                .enqueue(new Callback<RestaurantFoodItems>() {
                    @Override
                    public void onResponse(Call<RestaurantFoodItems> call, Response<RestaurantFoodItems> response) {
                        if (response.code() == 200){
                            String msg = response.body().getMsg();
                            long status = response.body().getStatus();

                            if (status == 1){
                                List<FoodItemDatum> data = response.body().getData().getData();
                                FoodListAdapter foodListAdapter = new FoodListAdapter(data, getContext(), getActivity());
                                restaurantFragmentRvRestaurant.setAdapter(foodListAdapter);


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantFoodItems> call, Throwable t) {
                        error("onFailure: get restaurant items: " + t.getMessage());

                    }
                });

    }

    private void getRestaurantList(int page) {
        endPoint.getRestaurantList(page)
                .enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                        if (response.code() == 200) {
                            long status = response.body().getStatus();
                            String msg = response.body().getMsg();
                            if (status == 1) {
                                verbose("onResponse: get restaurant list: " + msg);
                                List<RestaurantDatum> restaurantDataList = response.body().getData().getData();
                                RestaurantAdapter adapter = new RestaurantAdapter(getContext(), getActivity(), restaurantDataList);
                                restaurantFragmentRvRestaurant.setAdapter(adapter);

                            } else {
                                verbose("onResponse: get restaurant list: " + msg);
                            }
                        } else {
                            verbose("onResponse: get restaurant list: response raw: " + response.raw());
                        }
                    }

                    @Override
                    public void onFailure(Call<Restaurant> call, Throwable t) {
                        error("onFailure: get restaurant list: " + t.getMessage());

                    }
                });
    }

    private void setupRecyclerView() {
        restaurantFragmentRvRestaurant.setHasFixedSize(true);
        restaurantFragmentRvRestaurant.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
