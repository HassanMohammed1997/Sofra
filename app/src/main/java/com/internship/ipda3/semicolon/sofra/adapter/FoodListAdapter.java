package com.internship.ipda3.semicolon.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem.FoodItemDatum;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.cart.AddCartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.ITEM_ID;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.FOOD_DESC;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.FOOD_NAME;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.FOOD_PRICE;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.PHOTO_URL;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.PREPARING_TIME;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.SaveData;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    final int EMPTY_VIEW = 1000;
    private List<FoodItemDatum> dataList = new ArrayList<>();
    private Context context;
    private Activity activity;

    int userType;

    public FoodListAdapter(List<FoodItemDatum> dataList, Context context, Activity activity) {
        this.dataList = dataList;
        this.context = context;
        this.activity = activity;
        userType = LoadIntegerData(activity, USER_TYPE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FoodItemDatum foodItemDatum = dataList.get(i);
        setData(viewHolder, foodItemDatum);
        setAction(viewHolder, foodItemDatum);

    }

    private void setAction(ViewHolder viewHolder, final FoodItemDatum foodItemDatum) {
        if (userType == 1){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = foodItemDatum.getId().intValue();
                    String description = foodItemDatum.getDescription();
                    String photoUrl = foodItemDatum.getPhotoUrl();
                    String price = foodItemDatum.getPrice();
                    String preparingTime = foodItemDatum.getPreparingTime();
                    String name = foodItemDatum.getName();
                    SaveData(activity, ITEM_ID, id);
                    SaveData(activity, FOOD_DESC, description);
                    SaveData(activity, PHOTO_URL, photoUrl);
                    SaveData(activity, FOOD_PRICE, price);
                    SaveData(activity, PREPARING_TIME, preparingTime);
                    SaveData(activity, FOOD_NAME, name);


                    replace(R.id.home_activity_frame, new AddCartFragment(),
                            ((AppCompatActivity)activity).getSupportFragmentManager());

                }
            });

        }
    }

    private void setData(ViewHolder viewHolder, FoodItemDatum datum) {
        viewHolder.foodItemNameText.setText(datum.getName());
        Glide.with(context).load(datum.getPhotoUrl()).into(viewHolder.restaurantImageView);
        viewHolder.foodPriceTextView.setText(datum.getPrice());
        viewHolder.foodItemDetailsTextView.setText(datum.getDescription());


    }

    @Override
    public int getItemCount() {
        if (dataList == null)
            return 0;

        return dataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_image_view)
        ImageView restaurantImageView;
        @BindView(R.id.food_item_name_text)
        TextView foodItemNameText;
        @BindView(R.id.food_item_details_text_view)
        TextView foodItemDetailsTextView;
        @BindView(R.id.food_price_text_view)
        TextView foodPriceTextView;
        @BindView(R.id.foreground)
        public RelativeLayout foreground;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public List<FoodItemDatum> getDataList() {
        return dataList;
    }

    public int getId(int pos){
        return dataList.get(pos).getId().intValue();
    }


}
