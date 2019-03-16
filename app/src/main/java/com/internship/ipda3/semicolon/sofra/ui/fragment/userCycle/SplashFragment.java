package com.internship.ipda3.semicolon.sofra.ui.fragment.userCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.ui.activity.HomeActivity;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.RestaurantFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.intent;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.SaveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    Unbinder unbinder;
    private Bundle mBundle;
    private RestaurantFragment mRestaurantFragment;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        unbinder = ButterKnife.bind(this, view);
        mBundle = new Bundle();
        mRestaurantFragment = new RestaurantFragment() ;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.order_food_button, R.id.buy_food_button, R.id.twitter_image, R.id.instagram_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_food_button:
                //replace restaurant list fragment
                intent(getContext(), HomeActivity.class);
                SaveData(getActivity(), USER_TYPE, 1);
                break;
            case R.id.buy_food_button:
                //replace register fragment
                replace(R.id.frame, new LoginFragment(), getFragmentManager());
//                intent(getActivity(), HomeActivity.class);
                SaveData(getActivity(), USER_TYPE, 2);
                break;
            case R.id.twitter_image:
                break;
            case R.id.instagram_image:
                break;
        }
    }
}
