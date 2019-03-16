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
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.debug;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestPasswordFragment extends Fragment {


    @BindView(R.id.code_edit_text)
    EditText codeEditText;
    @BindView(R.id.new_password_edit_text)
    EditText newPasswordEditText;
    @BindView(R.id.confirm_new_password_edit_text)
    EditText confirmNewPasswordEditText;
    Unbinder unbinder;

    int id;

    EndPoint endPoint;

    public RestPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rest_password, container, false);
        unbinder = ButterKnife.bind(this, view);


        id = LoadIntegerData(getActivity(), USER_TYPE);

        endPoint = getClient().create(EndPoint.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.change_password_button)
    public void onViewClicked() {
        String code = getTextFromEditText(codeEditText);
        String newPass = getTextFromEditText(newPasswordEditText);
        String confirmPass = getTextFromEditText(confirmNewPasswordEditText);

        createNewPassword(id, code, newPass, confirmPass);


    }

    private void createNewPassword(int id, String code, String newPass, String confirmPass) {
        if (id == 1) {
            endPoint.userNewPassword(code, newPass, newPass)
                    .enqueue(new Callback<NewPassword>() {
                        @Override
                        public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                            if (response.code() == 200) {
                                String msg = response.body().getMsg();
                                long status = response.body().getStatus();
                                if (status == 1) {
                                    showToast(getContext(), msg);
                                } else {
                                    showToast(getContext(), msg);
                                }
                            } else {
                                debug("response message: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<NewPassword> call, Throwable t) {
                            error("onFailure: create new password: user: " + t.getMessage());

                        }
                    });

        }else{
            endPoint.restaurantNewPassword(code, newPass, confirmPass)
                    .enqueue(new Callback<NewPassword>() {
                        @Override
                        public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                            if (response.code() == 200){
                                String msg = response.body().getMsg();
                                long status = response.body().getStatus();
                                if (status == 1){
                                    showToast(getContext(), msg);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<NewPassword> call, Throwable t) {

                        }
                    });
        }
    }
}
