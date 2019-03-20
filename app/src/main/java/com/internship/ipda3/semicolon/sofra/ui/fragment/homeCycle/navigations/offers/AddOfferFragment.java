package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations.offers;


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
import com.internship.ipda3.semicolon.sofra.model.restaurent.offer.newOffer.NewOffer;
import com.internship.ipda3.semicolon.sofra.model.restaurent.offer.newOffer.NewOfferData;
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
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.FileUtils.getPath;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.convertFileToMultipart;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.convertStringToRequestBody;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.getTextFromEditText;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOfferFragment extends Fragment {


    private static final int REQUEST_GALLERY = 1000;
    private static final int WRITE_EXTERNAL_STORAGE = 2000;
    @BindView(R.id.add_offer_fragment_et_offerName)
    EditText addOfferFragmentEtOfferName;
    @BindView(R.id.add_offer_fragment_et_offerDesc)
    EditText addOfferFragmentEtOfferDesc;
    @BindView(R.id.add_offer_fragment_et_offerPrice)
    EditText addOfferFragmentEtOfferPrice;
    @BindView(R.id.add_offer_fragment_et_from)
    EditText addOfferFragmentEtFrom;
    @BindView(R.id.add_offer_fragment_et_to)
    EditText addOfferFragmentEtTo;
    @BindView(R.id.add_offer_fragment_iv_offerImage)
    ImageView addOfferFragmentIvOfferImage;
    Unbinder unbinder;
    private Uri imageUri;
    private MultipartBody.Part imagePart;

    private EndPoint endPoint;


    public AddOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_offer, container, false);

        unbinder = ButterKnife.bind(this, view);
        endPoint = getClient().create(EndPoint.class);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void convertToRequestBody(String price, String desc, String name, String endingAt, String startingFrom) {
        RequestBody pricePart = convertStringToRequestBody(price);
        RequestBody descPart = convertStringToRequestBody(desc);
        RequestBody namePart = convertStringToRequestBody(name);
        RequestBody endingAtPart = convertStringToRequestBody(endingAt);
        RequestBody startingFromPart = convertStringToRequestBody(startingFrom);

        String apiToken = LoadStringData(getActivity(), API_TOKEN);
        RequestBody apiTokenPart = convertStringToRequestBody(apiToken);

        uploadData(pricePart, descPart, namePart, endingAtPart, startingFromPart, apiTokenPart);

    }

    private void uploadData(RequestBody apiTokenPart, RequestBody pricePart, RequestBody descPart, RequestBody namePart,
                            RequestBody endingAtPart, RequestBody startingFromPart) {

        endPoint.newOffer(descPart, pricePart, startingFromPart, namePart, imagePart, endingAtPart, apiTokenPart)
                .enqueue(new Callback<NewOffer>() {
                    @Override
                    public void onResponse(Call<NewOffer> call, Response<NewOffer> response) {
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            long status = response.body().getStatus();
                            if (status == 1) {
                                NewOfferData data = response.body().getData();
                                verbose("onResponse: success: data: " + msg);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NewOffer> call, Throwable t) {
                        error("onFailure: " + call.request().url());

                    }
                });
    }


    @OnClick({R.id.add_offer_fragment_iv_offerImage, R.id.add_offer_fragment_btn_addOffer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_offer_fragment_iv_offerImage:
                photoDialog();
                break;
            case R.id.add_offer_fragment_btn_addOffer:
                addOfferClick();
                break;
        }
    }

    private void photoDialog() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_GALLERY);
        }
    }

    private void addOfferClick() {
        String price = getTextFromEditText(addOfferFragmentEtOfferPrice);
        String desc = getTextFromEditText(addOfferFragmentEtOfferDesc);
        String name = getTextFromEditText(addOfferFragmentEtOfferName);
        String endingAt = getTextFromEditText(addOfferFragmentEtTo);
        String startingFrom = getTextFromEditText(addOfferFragmentEtFrom);

        convertToRequestBody(price, desc, name, endingAt, startingFrom);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null) {

            imageUri = data.getData();
            addOfferFragmentIvOfferImage.setImageURI(imageUri);


            getPhotoFullPath();


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getPhotoFullPath() {
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
