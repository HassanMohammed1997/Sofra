package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations.offers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.OffersAdapter;
import com.internship.ipda3.semicolon.sofra.model.general.offers.list.Offers;
import com.internship.ipda3.semicolon.sofra.model.general.offers.list.OffersDatum;
import com.internship.ipda3.semicolon.sofra.model.restaurent.offer.list.OfferList;
import com.internship.ipda3.semicolon.sofra.model.restaurent.offer.list.OfferListData;
import com.internship.ipda3.semicolon.sofra.model.restaurent.offer.list.OfferListDatum;
import com.internship.ipda3.semicolon.sofra.service.EndPoint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.hideView;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersListFragment extends Fragment {


    @BindView(R.id.offer_list_fragment_rv_displayOffers)
    RecyclerView offerListFragmentRvDisplayOffers;
    Unbinder unbinder;
    @BindView(R.id.offers_list_fragment_btn_newOffer)
    Button offersListFragmentBtnNewOffer;
    int userType;

    private OffersAdapter offersAdapter;
    private List<OffersDatum> offersData;

    private EndPoint endPoint;
    private String apiToken;

    public OffersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers_list, container, false);


        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        getDataFromShared();
        hideViews();


        endPoint = getClient().create(EndPoint.class);
        if (userType == 1) {
            getListOfOffers(1);
        } else if (userType == 2) {
            restaurantOffer(1);

        }
        return view;
    }

    private void restaurantOffer(int i) {
        endPoint.getRestaurantOffer(apiToken, i)
                .enqueue(new Callback<OfferList>() {
                    @Override
                    public void onResponse(Call<OfferList> call, Response<OfferList> response) {
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            long status = response.body().getStatus();
                            if (status == 1) {
                                OfferListData data = response.body().getData();
                                offersData = data.getData();
                                offersAdapter.notifyDataSetChanged();

                            }

                            verbose("onResponse: " + msg);
                        }

                    }

                    @Override
                    public void onFailure(Call<OfferList> call, Throwable t) {
                        error("onFailure: " + t.getMessage());

                    }
                });
    }

    private void hideViews() {
        if (userType == 1) {
            hideView(offersListFragmentBtnNewOffer);
        }
    }

    private void getDataFromShared() {
        userType = LoadIntegerData(getActivity(), USER_TYPE);
        apiToken = LoadStringData(getActivity(), API_TOKEN);
    }

    private void getListOfOffers(int page) {
        endPoint.getOffers(page)
                .enqueue(new Callback<Offers>() {
                    @Override
                    public void onResponse(Call<Offers> call, Response<Offers> response) {
                        if (response.code() == 200) {
                            int status = response.body().getStatus();
                            String msg = response.body().getMsg();
                            if (status == 1) {
                                offersData = response.body().getData().getData();
                                offersAdapter.notifyDataSetChanged();
                            }


                            verbose("onResponse: get offers: server msg: " + msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<Offers> call, Throwable t) {
                        error("onFailure: getOffers: failure msg: " + t.getMessage());

                    }
                });
    }

    private void setupRecyclerView() {
        offerListFragmentRvDisplayOffers.setLayoutManager(new LinearLayoutManager(getContext()));
        offerListFragmentRvDisplayOffers.setHasFixedSize(true);
        offersAdapter = new OffersAdapter(offersData, getContext(), getActivity());
        offerListFragmentRvDisplayOffers.setAdapter(offersAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.offers_list_fragment_btn_newOffer)
    public void onViewClicked() {
        replace(R.id.home_activity_frame, new AddOfferFragment(), getFragmentManager());
    }
}
