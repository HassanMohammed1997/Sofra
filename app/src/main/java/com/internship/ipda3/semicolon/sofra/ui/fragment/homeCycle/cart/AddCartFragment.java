package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.cart;


import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.local.database.Cart;
import com.internship.ipda3.semicolon.sofra.local.database.CartDatabase;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;
import com.internship.ipda3.semicolon.sofra.ui.fragment.userCycle.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.internship.ipda3.semicolon.sofra.Constants.Database.CART_DB_NAME;
import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.ITEM_ID;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.FOOD_DESC;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.FOOD_NAME;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.FOOD_PRICE;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.PHOTO_URL;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.PREPARING_TIME;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCartFragment extends Fragment {


    @BindView(R.id.add_cart_fragment_iv_display_food_image)
    ImageView addCartFragmentIvDisplayFoodImage;
    @BindView(R.id.add_cart_fragment_tv_display_name)
    TextView addCartFragmentTvDisplayName;
    @BindView(R.id.add_cart_fragment_tv_display_details)
    TextView addCartFragmentTvDisplayDetails;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.add_cart_fragment_tv_price)
    TextView addCartFragmentTvPrice;
    @BindView(R.id.add_cart_fragment_tv_duration_time)
    TextView addCartFragmentTvDurationTime;
    @BindView(R.id.special_order_tv)
    TextView specialOrderTv;
    @BindView(R.id.add_cart_fragment_et_special_order)
    EditText addCartFragmentEtSpecialOrder;
    @BindView(R.id.add_cart_fragment_btn_counter)
    ElegantNumberButton addCartFragmentBtnCounter;
    @BindView(R.id.add_cart_fragment_btn_add)
    Button addCartFragmentBtnAdd;
    Unbinder unbinder;

    EndPoint endPoint;
    private String apiToken;
    private String foodDesc;
    private String photoUrl;
    private String foodPrice;
    private String preparingTime;
    private String foodName;

    private CartDatabase cartDatabase;


    public AddCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_cart, container, false);

        getDataFromSharedPref();
        initDatabase();

        unbinder = ButterKnife.bind(this, view);
        displayItemData();


        return view;
    }

    private void displayItemData() {
        Glide.with(getContext())
                .load(photoUrl).into(addCartFragmentIvDisplayFoodImage);

        addCartFragmentTvDisplayDetails.setText(foodDesc);
        addCartFragmentTvDisplayName.setText(foodName);
        addCartFragmentTvDurationTime.setText(preparingTime);
        addCartFragmentTvPrice.setText(foodPrice);
    }

    private void getDataFromSharedPref() {
        endPoint = getClient().create(EndPoint.class);
        apiToken = LoadStringData(getActivity(), API_TOKEN);
        foodDesc = LoadStringData(getActivity(), FOOD_DESC);
        photoUrl = LoadStringData(getActivity(), PHOTO_URL);
        foodPrice = LoadStringData(getActivity(), FOOD_PRICE);
        preparingTime = LoadStringData(getActivity(), PREPARING_TIME);
        foodName = LoadStringData(getActivity(), FOOD_NAME);

    }

    private void initDatabase() {
        cartDatabase = Room.databaseBuilder(getContext(), CartDatabase.class, CART_DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_cart_fragment_btn_add)
    public void onViewClicked() {
        if (apiToken == null) {
            loginDialog();
            return;
        }

        final int itemId = LoadIntegerData(getActivity(), ITEM_ID);
        final String quantity = addCartFragmentBtnCounter.getNumber();
        final String note = addCartFragmentEtSpecialOrder.getText().toString();

        saveDataToDatabase(itemId, quantity, note);





    }

    private void loginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.log_in_text))
                .setMessage(getString(R.string.you_must_login));
        builder.setPositiveButton(getString(R.string.login_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                replace(R.id.home_activity_frame, new LoginFragment(), getFragmentManager());
                dialog.dismiss();
            }
        }).setNegativeButton(getString(R.string.dismiss), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveDataToDatabase(final int itemId, final String quantity, final String note) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cart newCart = new Cart();
                newCart.setNumber(quantity);
                newCart.setSpecialOrder(note);
                newCart.setItemId(itemId);
                newCart.setName(foodName);
                newCart.setPrice(foodPrice);
                newCart.setPhotoUrl(photoUrl);
                cartDatabase.daoAccess()
                        .addToCart(newCart);
                verbose("add to cart: items: " + newCart.toString());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        showToast(getContext(), getString(R.string.add_to_cart));
                        replace(R.id.home_activity_frame, new CartListFragment(), getFragmentManager());


                    }
                });

            }
        }).start();
    }

}
