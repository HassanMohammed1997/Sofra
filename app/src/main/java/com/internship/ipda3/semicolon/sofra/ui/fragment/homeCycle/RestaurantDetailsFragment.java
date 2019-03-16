package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.ViewPagerAdapter;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.CategoryDatum;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.RestaurantDatum;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.details.RestaurantDetails;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments.CommentsFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments.FoodListFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments.RestaurantInfoFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.Constants.keys.RESTAURANT_ID;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailsFragment extends Fragment {


    @BindView(R.id.restaurant_image_view)
    ImageView restaurantImageView;
    @BindView(R.id.restaurant_name_tv)
    TextView restaurantNameTv;
    @BindView(R.id.restaurant_categories_tv)
    TextView restaurantCategoriesTv;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.min_order_tv)
    TextView minOrderTv;
    @BindView(R.id.delivery_fee_tv)
    TextView deliveryFeeTv;
    @BindView(R.id.restaurant_fragment_detials_tabs)
    TabLayout restaurantFragmentDetialsTabs;
    @BindView(R.id.restaurant_detials_view_pager)
    ViewPager restaurantDetialsViewPager;
    Unbinder unbinder;

    int res_id;

    EndPoint endPoint;


    public RestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detials, container, false);

        if (getArguments() != null) {
            res_id = getArguments().getInt(RESTAURANT_ID);
        }

        endPoint = getClient().create(EndPoint.class);
        retrieveSingleRestaurantData(res_id);


        unbinder = ButterKnife.bind(this, view);
        setupViewPager();

        removeFragment();
        return view;
    }

    private void removeFragment() {
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                FoodListFragment foodFragment = (FoodListFragment) fragment;
                getFragmentManager().beginTransaction().remove(fragment).commit();
            } catch (Exception e) {
                e.printStackTrace();

            }

            try {
                CommentsFragment commentsFragment = (CommentsFragment) fragment;
                getFragmentManager().beginTransaction().remove(fragment).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                RestaurantInfoFragment infoFragment = (RestaurantInfoFragment) fragment;
                getFragmentManager().beginTransaction().remove(fragment).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void retrieveSingleRestaurantData(int id) {
        endPoint.getRestaurantDetails(id)
                .enqueue(new Callback<RestaurantDetails>() {
                    @Override
                    public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            int status = response.body().getStatus().intValue();
                            String msg = response.body().getMsg();
                            if (status == 1) {
                                RestaurantDatum data = response.body().getData();
                                String name = data.getName();
                                Long rate = data.getRate();
                                String deliveryCost = data.getDeliveryCost();
                                String availability = data.getAvailability();
                                String photoUrl = data.getPhotoUrl();
                                String minimumCharger = data.getMinimumCharger();

                                Glide.with(getActivity()).load(photoUrl).into(restaurantImageView);
                                restaurantNameTv.setText(name);
                                ratingBar.setRating(rate.floatValue());
                                minOrderTv.setText(minimumCharger);
                                deliveryFeeTv.setText(deliveryCost);

                                List<CategoryDatum> categories = data.getCategories();
                                for (CategoryDatum datum : categories) {
                                    restaurantCategoriesTv.append(datum.getName() + ", ");
                                }

                            }

                            verbose("onResponse: success: server message: " + msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantDetails> call, Throwable t) {
                        error("onFailure: fail: failure message: " + t.getMessage());

                    }
                });


    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), getContext());
        viewPagerAdapter.addFragment(new FoodListFragment(), getString(R.string.food_list_text));
        viewPagerAdapter.addFragment(new CommentsFragment(), getString(R.string.comments_text));
        viewPagerAdapter.addFragment(new RestaurantInfoFragment(), getString(R.string.restaurant_info_text));

        restaurantDetialsViewPager.setAdapter(viewPagerAdapter);
        restaurantFragmentDetialsTabs.setupWithViewPager(restaurantDetialsViewPager, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
