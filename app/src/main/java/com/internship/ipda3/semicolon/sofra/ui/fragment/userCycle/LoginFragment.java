package com.internship.ipda3.semicolon.sofra.ui.fragment.userCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.client.userCycle.login.UserLogin;
import com.internship.ipda3.semicolon.sofra.model.restaurent.login.RestaurantLogin;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;
import com.internship.ipda3.semicolon.sofra.ui.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.RESTAURANT_ID;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.intent;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.isNetworkAvailable;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.SaveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.pass_edit_text)
    EditText passEditText;
    Unbinder unbinder;
    int id;
    private RegisterFragment mRegisterFagment;
    private Bundle mBundle;
    private EndPoint mEndPoint;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        unbinder = ButterKnife.bind(this, view);

        id = LoadIntegerData(getActivity(), USER_TYPE);
        verbose("user type: " + id);

        mRegisterFagment = new RegisterFragment();
        mBundle = new Bundle();
        mEndPoint = getClient().create(EndPoint.class);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_button, R.id.forget_pass_text, R.id.register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (isNetworkAvailable(getContext())) {
                    login(id);
                } else {
                    showToast(getContext(), getString(R.string.no_internet_connection));
                }
                break;
            case R.id.forget_pass_text:
                //replace forget password fragment
                replace(R.id.frame, new ForgetPasswordFragment(), getFragmentManager());
                break;
            case R.id.register_button:
                //replace register fragment with restaurant id.
                mBundle.putInt(USER_TYPE, id);
                mRegisterFagment.setArguments(mBundle);
                replace(R.id.frame, mRegisterFagment, getFragmentManager());
                break;
        }
    }

    private void login(int id) {
        String email = emailEditText.getText().toString();
        String password = passEditText.getText().toString();

        if (id == 1) {
            userLogin(email, password);
        } else if (id == 2) {
            restaurantLogin(email, password);

        }


    }

    private void restaurantLogin(String email, String password) {
        mEndPoint.restaurentLogin(email, password)
                .enqueue(new Callback<RestaurantLogin>() {
                    @Override
                    public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                        verbose("onResponse: response raw: " + response.raw());
                        if (response.code() == 200) {
                            long status = response.body().getStatus();
                            String msg = response.body().getMsg();
                            if (status == 1) {
                                showToast(getContext(), msg);
                                //Now, I save restaurant api token using shared preference...
                                String apiToken = response.body().getData().getApiToken();
                                int id = response.body().getData().getUser().getId().intValue();
                                SaveData(getActivity(), API_TOKEN, apiToken);
                                SaveData(getActivity(), RESTAURANT_ID, id);
                                //save other restaurant data...using shared preference.
                                //move to restaurant items...using intent

                                intent(getContext(), HomeActivity.class);


                            } else {
                                showToast(getContext(), msg);
                            }
                        } else {
                            error("response message error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantLogin> call, Throwable t) {
                        error("onFailure: " + t.getMessage());

                    }
                });
    }

    private void userLogin(String email, String password) {
        mEndPoint.userLogin(email, password)
                .enqueue(new Callback<UserLogin>() {
                    @Override
                    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                        verbose("onResponse: response request: " + response.raw());
                        if (response.code() == 200) {
                            long status = response.body().getStatus();
                            String msg = response.body().getMsg();
                            if (status == 1) {
                                showToast(getContext(), msg);
                                //Now, I save user api token.
                                String apiToken = response.body().getData().getApiToken();
                                SaveData(getActivity(), API_TOKEN, apiToken);

                                intent(getContext(), HomeActivity.class);
                                //I will save other user data..... using shared preference.
                                //move to restaurant items....using intent
                            } else {
                                showToast(getContext(), msg);
                            }
                        } else {
                            error(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLogin> call, Throwable t) {
                        error("onFailure: " + t.getMessage());

                    }
                });
    }


}
