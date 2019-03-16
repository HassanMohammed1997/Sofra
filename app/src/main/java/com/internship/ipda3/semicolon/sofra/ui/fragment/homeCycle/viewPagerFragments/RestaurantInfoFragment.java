package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.RestaurantDatum;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.details.RestaurantDetails;
import com.internship.ipda3.semicolon.sofra.model.restaurent.changeState.ChangeState;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.RESTAURANT_ID;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.convertStringToRequestBody;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.setText;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantInfoFragment extends Fragment {


    @BindView(R.id.restaurant_info_fragment_tv_status)
    TextView restaurantInfoFragmentTvStatus;
    @BindView(R.id.restaurant_info_tv_city)
    TextView restaurantInfoTvCity;
    @BindView(R.id.restaurant_info_fragment_tv_region)
    TextView restaurantInfoFragmentTvRegion;
    @BindView(R.id.restaurant_info_fragment_tv_min)
    TextView restaurantInfoFragmentTvMin;
    @BindView(R.id.restaurant_info_fragment_tv_delivery_cost)
    TextView restaurantInfoFragmentTvDeliveryCost;
    Unbinder unbinder;

    int res_id;
    String state;

    EndPoint endPoint;
    @BindView(R.id.state_switch)
    Switch stateSwitch;
    @BindView(R.id.restaurant_info_fragment_btn_save)
    Button restaurantInfoFragmentBtnSave;
    private int userType;
    private String apiToken;

    public RestaurantInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);
        userType = LoadIntegerData(getActivity(), USER_TYPE);
        apiToken = LoadStringData(getActivity(), API_TOKEN);

        endPoint = getClient().create(EndPoint.class);
        res_id = LoadIntegerData(getActivity(), RESTAURANT_ID);
        displayRestaurantInfo();

        unbinder = ButterKnife.bind(this, view);
        if (userType == 1) {
            stateSwitch.setVisibility(View.GONE);
            restaurantInfoFragmentBtnSave.setVisibility(View.GONE);
        }


        return view;
    }


    private void displayRestaurantInfo() {
        if (res_id == 0) {
            return;
        }

        endPoint.getRestaurantDetails(res_id)
                .enqueue(new Callback<RestaurantDetails>() {
                    @Override
                    public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                        if (response.code() == 200) {
                            long status = response.body().getStatus();
                            String msg = response.body().getMsg();
                            if (status == 1) {
                                RestaurantDatum data = response.body().getData();
                                String availability = data.getAvailability();
                                if (availability.equals("open"))
                                    stateSwitch.setChecked(true);
                                else
                                    stateSwitch.setChecked(false);
                                setText(availability, restaurantInfoFragmentTvStatus);
                                setText(data.getMinimumCharger(), restaurantInfoFragmentTvMin);
                                setText(data.getDeliveryCost(), restaurantInfoFragmentTvDeliveryCost);
                                setText(data.getRegion().getName(), restaurantInfoFragmentTvRegion);
                                setText(data.getRegion().getCity().getName(), restaurantInfoTvCity);

                            }

                            verbose("onResponse: server message: " + msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantDetails> call, Throwable t) {
                        error("onFailure: response failure message: " + t.getMessage());

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.restaurant_info_fragment_btn_save)
    public void onViewClicked() {
        String state = getSwitchState();
        RequestBody stateBody = convertStringToRequestBody(state);
        RequestBody apiTokenBody = convertStringToRequestBody(apiToken);

        endPoint.changeRestaurantState(stateBody, apiTokenBody)
                .enqueue(new Callback<ChangeState>() {
                    @Override
                    public void onResponse(Call<ChangeState> call, Response<ChangeState> response) {
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            if (response.body().getStatus() == 1) {
                                verbose("onResponse: change state: " + msg);

                            }


                        } else {
                            verbose("onResponse: response raw: " + response.raw());
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangeState> call, Throwable t) {
                        error("onFailure: " + t.getMessage());

                    }
                });
    }

    private String getSwitchState() {
        String state = null;
        if (stateSwitch.getVisibility() == View.VISIBLE) {
            if (stateSwitch.isChecked()) {
                state = "open";
            } else {
                state = "closed";
            }
        }

        return state;
    }
}
