package com.internship.ipda3.semicolon.sofra.ui.fragment.userCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.client.userCycle.password.NewPassword;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.getTextFromEditText;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends Fragment {


    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    Unbinder unbinder;
    int id;
    private EndPoint mEndPoint;

    private Bundle mBundle;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        unbinder = ButterKnife.bind(this, view);


        id = LoadIntegerData(getActivity(), USER_TYPE);
        verbose("user type: " + id);

        mEndPoint = getClient().create(EndPoint.class);
        mBundle = new Bundle();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.send_code_button)
    public void onViewClicked() {
        String email = getTextFromEditText(emailEditText);
        restPassword(id, email);


    }

    private void restPassword(final int id, String email) {
        if (id == 1) {
            mEndPoint.userRestPassword(email)
                    .enqueue(new Callback<NewPassword>() {
                        @Override
                        public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                            if (response.code() == 200) {
                                long status = response.body().getStatus();
                                String msg = response.body().getMsg();
                                if (status == 1) {
                                    showToast(getContext(), msg);
                                    mBundle.putInt(USER_TYPE, id);
                                    RestPasswordFragment restPasswordFragment = new RestPasswordFragment();
                                    restPasswordFragment.setArguments(mBundle);
                                    replace(R.id.frame, restPasswordFragment, getFragmentManager());
                                } else {
                                    showToast(getContext(), msg);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<NewPassword> call, Throwable t) {
                            error("onFailure: rest password: user: " + t.getMessage());

                        }
                    });

        } else {
            mEndPoint.restaurantRestPassword(email)
                    .enqueue(new Callback<NewPassword>() {
                        @Override
                        public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                            if (response.code() == 200) {
                                long status = response.body().getStatus();
                                String msg = response.body().getMsg();
                                if (status == 1) {
                                    replace(R.id.frame, new RestPasswordFragment(), getFragmentManager());
                                    showToast(getContext(), msg);
                                } else {
                                    showToast(getContext(), msg);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<NewPassword> call, Throwable t) {
                            error("onFailure: rest password: restaurant " + t.getMessage());

                        }
                    });
        }
    }
}
