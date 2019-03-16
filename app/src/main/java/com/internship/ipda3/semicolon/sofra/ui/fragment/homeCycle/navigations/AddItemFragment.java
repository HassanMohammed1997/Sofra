package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.restaurent.items.newItem.NewItem;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;

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
import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.ITEM_ID;
import static com.internship.ipda3.semicolon.sofra.util.FileUtils.getPath;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.convertFileToMultipart;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.convertStringToRequestBody;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.getTextFromEditText;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.initEndPointInterface;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.isNetworkAvailable;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment {

    private static final int WRITE_EXTERNAL_STORAGE = 1001;
    private static final int REQUEST_GALLERY = 1000;
    @BindView(R.id.items_name_et)
    EditText itemsNameEt;
    @BindView(R.id.items_description_et)
    EditText itemsDescriptionEt;
    @BindView(R.id.items_price_et)
    EditText itemsPriceEt;
    @BindView(R.id.item_duration_time_et)
    EditText itemDurationTimeEt;
    @BindView(R.id.item_image_iv)
    ImageView itemImageIv;
    Unbinder unbinder;
    private EndPoint endPoint;
    private Uri imageUri;
    private MultipartBody.Part imagePart;
    private String apiToken;
    private int itemId;


    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        apiToken = LoadStringData(getActivity(), API_TOKEN);
        itemId = LoadIntegerData(getActivity(), ITEM_ID);
        verbose("item id: " + itemId);

        if (itemId != 0) {
            getItemData();
        }

        endPoint = initEndPointInterface();


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void getItemData() {
        //TODO using database to store the item data.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.item_image_iv, R.id.add_item_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_image_iv:
                openGallery();
                break;
            case R.id.add_item_btn:
                if (isNetworkAvailable(getContext())) {
                    addNewItem();
                } else {
                    showToast(getContext(), getString(R.string.no_internet_connection));
                }
                break;
        }
    }

    private void addNewItem() {
        String name = getTextFromEditText(itemsNameEt);
        String description = getTextFromEditText(itemsDescriptionEt);
        String durationTime = getTextFromEditText(itemDurationTimeEt);
        String price = getTextFromEditText(itemsPriceEt);

        RequestBody namePart = convertStringToRequestBody(name);
        RequestBody descriptionPart = convertStringToRequestBody(description);
        RequestBody durationPart = convertStringToRequestBody(durationTime);
        RequestBody pricePart = convertStringToRequestBody(price);
        RequestBody apiTokenPart = convertStringToRequestBody(apiToken);
        RequestBody itemIdPart = convertStringToRequestBody(String.valueOf(itemId));

        if (itemId == 0) {
            endPoint.addNewItem(descriptionPart, pricePart, durationPart, namePart, imagePart, apiTokenPart)
                    .enqueue(new Callback<NewItem>() {
                        @Override
                        public void onResponse(Call<NewItem> call, Response<NewItem> response) {
                            if (response.code() == 200) {
                                String msg = response.body().getMsg();
                                showToast(getContext(), msg);
                                long status = response.body().getStatus();
                                if (status == 1) {
                                    verbose("onResponse: success");
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<NewItem> call, Throwable t) {
                            verbose("onFailure: " + t.getMessage());

                        }
                    });
        } else {
            endPoint.updateItem(apiToken, descriptionPart, pricePart,
                    durationPart, namePart, imagePart, itemIdPart)
                    .enqueue(new Callback<NewItem>() {
                        @Override
                        public void onResponse(Call<NewItem> call, Response<NewItem> response) {
                            if (response.code() == 200) {
                                String msg = response.body().getMsg();
                                showToast(getContext(), msg);
                            } else {
                                showToast(getContext(), getString(R.string.something_went_error));
                                verbose("onResponse: update current item: response raw message: " + response.raw());

                            }
                        }

                        @Override
                        public void onFailure(Call<NewItem> call, Throwable t) {
                            error("onFailure: update current item: failed: " + t.getMessage());

                        }
                    });
        }


    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_GALLERY);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            itemImageIv.setImageURI(imageUri);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                checkPermission();
            }

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


            verbose("image part: " + imagePart);
        }
    }
}
