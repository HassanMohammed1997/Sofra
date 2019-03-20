package com.internship.ipda3.semicolon.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.RestaurantDatum;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.RestaurantDetailsFragment;

import static com.internship.ipda3.semicolon.sofra.Constants.keys.RESTAURANT_ID;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.SaveData;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {


    private Context context;
    private List<RestaurantDatum> dataList = new ArrayList<>();
    private Activity activity;

    public RestaurantAdapter(Context context, Activity activity, List<RestaurantDatum> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemList = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.restaurnat_list_item, viewGroup, false);
        return new ViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RestaurantDatum data = dataList.get(position);
        setData(viewHolder, data);
        setAction(viewHolder, data);

    }

    private void setAction(ViewHolder viewHolder, final RestaurantDatum data) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Long id = data.getId();
                SaveData(activity, RESTAURANT_ID, id.intValue());
                bundle.putInt(RESTAURANT_ID, id.intValue());
                RestaurantDetailsFragment restaurantDetailsFragment = new RestaurantDetailsFragment();
                restaurantDetailsFragment.setArguments(bundle);
                replace(R.id.home_activity_frame, restaurantDetailsFragment, ((AppCompatActivity) activity).getSupportFragmentManager());
            }
        });
    }

    private void setData(ViewHolder viewHolder, RestaurantDatum data) {
        viewHolder.restaurantNameTv.setText(data.getName());
        viewHolder.deliveryFeeTv.setText(data.getDeliveryCost());
        viewHolder.minOrderTv.setText(data.getMinimumCharger());
        viewHolder.ratingBarIv.setRating(data.getRate().floatValue());
//        viewHolder.restaurantCategoriesTv.setText(data.getCategories().iterator().next().getName());
        Glide.with(context).load("http://ipda3.com/sofra/" + data.getPhoto()).into(viewHolder.restaurantImageView);


    }

    @Override
    public int getItemCount() {
        if (dataList == null)
            return 0;
        return dataList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_image_view)
        ImageView restaurantImageView;
        @BindView(R.id.restaurant_name_tv)
        TextView restaurantNameTv;
        @BindView(R.id.rating_bar)
        RatingBar ratingBarIv;
        @BindView(R.id.min_order_tv)
        TextView minOrderTv;
        @BindView(R.id.delivery_fee_tv)
        TextView deliveryFeeTv;
        @BindView(R.id.restaurant_categories_tv)
        TextView restaurantCategoriesTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
