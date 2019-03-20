package com.internship.ipda3.semicolon.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.general.offers.list.OffersDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private List<OffersDatum> dataList = new ArrayList<>();
    private Context context;
    private Activity activity;

    public OffersAdapter(List<OffersDatum> dataList, Context context, Activity activity) {
        this.dataList = dataList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.offers_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        OffersDatum offersDatum = dataList.get(i);
        setData(holder, offersDatum);
        setAction(holder, offersDatum);

    }

    private void setAction(ViewHolder holder, OffersDatum offersDatum) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setData(ViewHolder holder, OffersDatum offersDatum) {
        holder.offersListItemTvDuration.setText(offersDatum.getEndingAt());
        holder.offersListItemTvFoodName.setText(offersDatum.getName());
        holder.offersListItemTvPrice.setText(offersDatum.getPrice());
        holder.offersListItemTvRestaurantName.setText(offersDatum.getName());
        Glide.with(context)
                .load("http://ipda3.com/sofra/" + offersDatum.getPhoto())
                .into(holder.offersListItemIvFoodPhoto);

    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.offers_list_item_iv_foodPhoto)
        ImageView offersListItemIvFoodPhoto;
        @BindView(R.id.offers_list_item_tv_foodName)
        TextView offersListItemTvFoodName;
        @BindView(R.id.offers_list_item_tv_restaurantName)
        TextView offersListItemTvRestaurantName;
        @BindView(R.id.offers_list_item_tv_duration)
        TextView offersListItemTvDuration;
        @BindView(R.id.offers_list_item_tv_price)
        TextView offersListItemTvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
