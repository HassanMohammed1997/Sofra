package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.FoodListAdapter;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem.FoodItemDatum;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem.RestaurantFoodItems;
import com.internship.ipda3.semicolon.sofra.model.restaurent.items.delete.Delete;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations.AddItemFragment;
import com.internship.ipda3.semicolon.sofra.util.SwipeHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.ITEM_ID;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.RESTAURANT_ID;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.convertStringToRequestBody;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.SaveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodListFragment extends Fragment {


    @BindView(R.id.food_list_fragment_rv_food_list)
    RecyclerView foodListFragmentRvFoodList;
    Unbinder unbinder;

    List<FoodItemDatum> data;

    String apiToken;

    FoodListAdapter adapter;

    EndPoint endPoint;
    private int userType;

    public FoodListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        int restaurantId = LoadIntegerData(getActivity(), RESTAURANT_ID);
        apiToken = LoadStringData(getActivity(), API_TOKEN);

        unbinder = ButterKnife.bind(this, view);
        setupFoodListRecycler();


        endPoint = getClient().create(EndPoint.class);
        retrieveFoodList(restaurantId);


        userType = LoadIntegerData(getActivity(), USER_TYPE);


        return view;
    }


    private void retrieveFoodList(int restaurantId) {
        endPoint.getFoodItems(restaurantId, 1)
                .enqueue(new Callback<RestaurantFoodItems>() {
                    @Override
                    public void onResponse(Call<RestaurantFoodItems> call, Response<RestaurantFoodItems> response) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            String msg = response.body().getMsg();
                            int status = response.body().getStatus().intValue();
                            if (status == 1) {
                                data = response.body().getData().getData();
                                adapter = new FoodListAdapter(data, getContext(), getActivity());
                                foodListFragmentRvFoodList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }

                            verbose("onResponse: success: server message: " + msg);

                        } else {
                            verbose(String.format("onResponse: response code: %d, response raw: %s", response.code(), response.raw()));
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantFoodItems> call, Throwable t) {
                        error("onFailure; failure message: " + t.getMessage());

                    }
                });

    }

    private void setupFoodListRecycler() {
        foodListFragmentRvFoodList.setLayoutManager(new LinearLayoutManager(getContext()));
        foodListFragmentRvFoodList.setHasFixedSize(true);


        if (userType == 2){
            SwipeHelper swipeHelper = new SwipeHelper(getContext(), foodListFragmentRvFoodList) {
                @Override
                public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                    underlayButtons.add(new UnderlayButton(
                            getString(R.string.edit_food_item_string),
                            R.drawable.ic_mode_edit_black_24dp,
                            getResources().getColor(R.color.colorGreen),
                            new UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    //edit action
                                    //get the item id that will be modified and save it in shared preference.
                                    int itemId = adapter.getId(pos);
                                    SaveData(getActivity(), ITEM_ID, itemId);

                                    //replace add item fragment
                                    replace(R.id.home_activity_frame, new AddItemFragment(), getFragmentManager());
                                }
                            }));

                    underlayButtons.add(new UnderlayButton(getString(R.string.delete),
                            R.drawable.ic_delete_black_24dp,
                            getResources().getColor(R.color.colorRed),
                            new UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    //delete action
                                    int itemId = adapter.getId(pos);
                                    showDeleteDialog(itemId, pos);


                                }
                            }));

                }
            };


        }




    }

    private void showDeleteDialog(final int itemId, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.delete_confirm))
                .setTitle(getString(R.string.delete))
                .setIcon(getResources().getDrawable(R.drawable.ic_delete_black_24dp))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.delete_button_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete
                        RequestBody itemIdBody = convertStringToRequestBody(String.valueOf(itemId));
                        endPoint.deleteItem(apiToken, itemIdBody)
                                .enqueue(new Callback<Delete>() {
                                    @Override
                                    public void onResponse(Call<Delete> call, Response<Delete> response) {
                                        if (response.code() == 200) {
                                            String msg = response.body().getMsg();
                                            showToast(getContext(), msg);
                                            adapter.removeItem(pos);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Delete> call, Throwable t) {

                                    }
                                });

                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
