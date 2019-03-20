package com.internship.ipda3.semicolon.sofra.ui.fragment.userCycle;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.client.userCycle.login.UserData;
import com.internship.ipda3.semicolon.sofra.model.client.userCycle.register.UserRegister;
import com.internship.ipda3.semicolon.sofra.model.general.categories.Catagory;
import com.internship.ipda3.semicolon.sofra.model.general.city.City;
import com.internship.ipda3.semicolon.sofra.model.general.city.CityDatum;
import com.internship.ipda3.semicolon.sofra.model.general.region.Region;
import com.internship.ipda3.semicolon.sofra.model.general.region.RegionDatum;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.CategoryDatum;
import com.internship.ipda3.semicolon.sofra.model.restaurent.register.RestaurantRegister;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;
import com.internship.ipda3.semicolon.sofra.util.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.FileUtils.getPath;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.convertFileToMultipart;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.convertStringToRequestBody;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.getTextFromEditText;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.hideView;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showView;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.SaveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private static final int REQUEST_GALLERY = 1000;
    private static final int WRITE_EXTERNAL_STORAGE = 1001;
    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.city_spinner)
    Spinner citySpinner;
    @BindView(R.id.state_spinner)
    Spinner stateSpinner;
    @BindView(R.id.description_edit_text)
    EditText descriptionEditText;
    @BindView(R.id.pass_edit_text)
    EditText passEditText;
    @BindView(R.id.repeat_pass_edit_text)
    EditText repeatPassEditText;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.register_layout)
    RelativeLayout registerLayout;
    @BindView(R.id.categories_spinner)
    MultiSelectionSpinner categoriesSpinner;
    @BindView(R.id.minimum_order_edit_text)
    EditText minimumOrderEditText;
    @BindView(R.id.delivery_fee_edit_text)
    EditText deliveryFeeEditText;
    @BindView(R.id.delivery_data_check_box)
    CheckBox deliveryDataCheckBox;
    @BindView(R.id.whats_edit_text)
    EditText whatsEditText;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.register_button)
    Button registerButton;
    @BindView(R.id.continue_layout)
    RelativeLayout continueLayout;
    @BindView(R.id.restaurant_image)
    ImageView restaurantImage;
    @BindView(R.id.progress_layout)
    FrameLayout progressLayout;

    List<CategoryDatum> data;

    List<RequestBody> categories = new ArrayList<>();


    EndPoint endPoint;
    MultipartBody.Part imagePart;

    int selectedRegionId;

    Unbinder unbinder;
    int id;


    String name = "";
    String email = "";
    String password = "";
    String repeatedPassword = "";
    String phone = "";

    Uri imageUri;
    @BindView(R.id.user_phone_edit_text)
    EditText userPhoneEditText;
    @BindView(R.id.restaurant_phone_edit_text)
    EditText restaurantPhoneEditText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);


        if (getArguments() != null) {
            id = getArguments().getInt(USER_TYPE);
            verbose("user type: " + id);
        }


        unbinder = ButterKnife.bind(this, view);

        endPoint = getClient().create(EndPoint.class);

        setSpinnerData();


        if (id == 1) {
//            hideView(continueButton);
            hideView(continueLayout);
            nextButton.setText(getString(R.string.register_button_text));
        } else {
            hideView(descriptionEditText);
            hideView(continueLayout);
            nextButton.setText(getString(R.string.next));
            nameEditText.setHint(getString(R.string.restaurant_name_text));
        }


        return view;
    }

    private void setSpinnerData() {
        endPoint.getCategories()
                .enqueue(new Callback<Catagory>() {
                    @Override
                    public void onResponse(Call<Catagory> call, Response<Catagory> response) {
                        List<String> categoryType = new ArrayList<>();
                        final List<Long> categoryId = new ArrayList<>();

                        data = response.body().getData();

                        categoryId.add(0L);
                        categoryType.add(getString(R.string.category_text));

                        if (response.code() == 200) {
                            long status = response.body().getStatus();
                            if (status == 1) {
                                List<CategoryDatum> categoryList = response.body().getData();
                                for (int i = 0; i < categoryList.size(); i++) {
                                    String categoryName = categoryList.get(i).getName();
                                    long categorisId = categoryList.get(i).getId();
                                    categoryType.add(categoryName);
                                    categoryId.add(categorisId);

                                }

                     /*           ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                                        android.R.layout.simple_spinner_item, categoryType);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

                                categoriesSpinner.setItems(categoryType);


                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Catagory> call, Throwable t) {

                    }
                });

        endPoint.getCities()
                .enqueue(new Callback<City>() {
                    @Override
                    public void onResponse(Call<City> call, Response<City> response) {
                        List<String> cities = new ArrayList<>();
                        final List<Integer> citiesId = new ArrayList<>();

                        cities.add(getString(R.string.city_string));
                        citiesId.add(0);
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            long status = response.body().getStatus();
                            if (status == 1) {
                                List<CityDatum> citiesList = response.body().getData().getData();
                                for (int i = 0; i < citiesList.size(); i++) {
                                    String cityName = citiesList.get(i).getName();
                                    cities.add(cityName);

                                    int cityId = citiesList.get(i).getId();
                                    citiesId.add(cityId);
                                }

                                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.simple_spinner_item, cities);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                citySpinner.setAdapter(spinnerAdapter);

                                citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (position != 0) {
                                            int selectedCityId = citiesId.get(position);
                                            setRegionSpinner(selectedCityId);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } else {
                                verbose("onResponse: getCities() " + msg);
                            }
                        } else {
                            verbose("response message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<City> call, Throwable t) {
                        error("onFailure: " + t.getMessage());

                    }
                });
    }

    private void setRegionSpinner(int selectedCityId) {
        endPoint.getRegions(selectedCityId)
                .enqueue(new Callback<Region>() {
                    @Override
                    public void onResponse(Call<Region> call, Response<Region> response) {
                        List<String> regionsName = new ArrayList<>();
                        final List<Integer> regionsId = new ArrayList<>();
                        regionsId.add(0);
                        regionsName.add(getString(R.string.region_text));

                        if (response.code() == 200) {
                            int status = response.body().getStatus();

                            if (status == 1) {
                                List<RegionDatum> regionList = response.body().getData().getData();
                                for (int i = 0; i < regionList.size(); i++) {
                                    String regionName = regionList.get(i).getName();
                                    regionsName.add(regionName);

                                    int regionId = regionList.get(i).getId();
                                    regionsId.add(regionId);


                                }

                                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.simple_spinner_item, regionsName);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                stateSpinner.setAdapter(spinnerAdapter);

                                stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (position != 0) {
                                            selectedRegionId = regionsId.get(position);
                                            verbose("selected region id: " + selectedRegionId);

                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Region> call, Throwable t) {
                        error("onFailure: " + t.getMessage());

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.next_button, R.id.register_button, R.id.restaurant_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next_button:
                if (id == 1) {
                    getDataFromEditText();
                    showView(progressLayout);
                    newRegister(id);
                } else {
                    hideView(registerLayout);
                    showView(continueLayout);
                }
                break;

            case R.id.register_button:
                //get data from views.
                getDataFromEditText();
                newRegister(id);
                showView(progressLayout);
                hideView(registerLayout);
                break;
            case R.id.restaurant_image:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_GALLERY);
                }
                break;
        }
    }

    private void getDataFromEditText() {
        name = getTextFromEditText(nameEditText);
        email = getTextFromEditText(emailEditText);
        password = getTextFromEditText(passEditText);
        repeatedPassword = getTextFromEditText(repeatPassEditText);
        phone = getTextFromEditText(userPhoneEditText);


    }

    private void newRegister(int id) {
        if (id == 1) {
            endPoint.userRegister(name, email, password, repeatedPassword, phone,
                    "حى الجامعة", selectedRegionId).enqueue(new Callback<UserRegister>() {
                @Override
                public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                    if (response.code() == 200) {
                        long status = response.body().getStatus();
                        String msg = response.body().getMsg();
                        if (status == 1) {
                            hideView(progressLayout);
                            showToast(getContext(), msg);
                            UserData data = response.body().getData();
                            SaveData(getActivity(), API_TOKEN, data.getApiToken());
                            verbose("onResponse: user register: " + data.toString());
                        } else {
                            showToast(getContext(), msg);
                            hideView(progressLayout);
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserRegister> call, Throwable t) {
                    error("onFailure: user register: " + t.getMessage());
                    hideView(progressLayout);

                }
            });

        } else {
            restaurantRegister();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null) {

            imageUri = data.getData();
            restaurantImage.setImageURI(imageUri);


            checkPermission();


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
        } else {
            String path = getPath(getContext(), imageUri);
            imagePart = convertFileToMultipart(path, "photo");


        }
    }

    private void restaurantRegister() {
        hideView(continueLayout);
        RequestBody restaurantName = convertStringToRequestBody(name);
        RequestBody restaurantEmail = convertStringToRequestBody(email);

        RequestBody restaurantPass = convertStringToRequestBody(password);
        RequestBody rPass = convertStringToRequestBody(repeatedPassword);

        RequestBody phoneNumber = convertStringToRequestBody(getTextFromEditText(restaurantPhoneEditText));
        RequestBody whatsApp = convertStringToRequestBody(getTextFromEditText(whatsEditText));

        List<String> selectedStrings = categoriesSpinner.getSelectedStrings();
        RequestBody regionId = convertStringToRequestBody(String.valueOf(selectedRegionId));


        categories.add(convertStringToRequestBody("1"));

        RequestBody deliveryPeriod = convertStringToRequestBody("30");
        RequestBody deliveryCost = convertStringToRequestBody(getTextFromEditText(deliveryFeeEditText));
        RequestBody minimumCharger = convertStringToRequestBody(getTextFromEditText(minimumOrderEditText));

        RequestBody availability = convertStringToRequestBody("open");


        endPoint.restaurentRegister(restaurantName, restaurantEmail, restaurantPass, rPass, phoneNumber, whatsApp,
                regionId, categories, deliveryPeriod, deliveryCost, minimumCharger, imagePart, availability)
                .enqueue(new Callback<RestaurantRegister>() {
                    @Override
                    public void onResponse(Call<RestaurantRegister> call, Response<RestaurantRegister> response) {
                        verbose("onResponse: response raw: " + response.raw());
                        if (response.code() == 200) {
                            long status = response.body().getStatus();
                            String msg = response.body().getMsg();
                            if (status == 1) {
                                showToast(getContext(), msg);
                                hideView(progressLayout);
                                showView(continueLayout);

                            } else {
                                showToast(getContext(), msg);
                                hideView(progressLayout);
                                showView(continueLayout);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantRegister> call, Throwable t) {
                        error("onFailure: " + t.getMessage());

                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    String path = getPath(getContext(), imageUri);
                    imagePart = convertFileToMultipart(path, "image");
                    verbose("image part: : " + imagePart);
                }

                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
