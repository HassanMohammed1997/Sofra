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
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.local.database.Cart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    private List<Cart> dataList = new ArrayList<>();
    private Context context;
    private Activity activity;

    public CartAdapter(List<Cart> dataList, Context context, Activity activity) {
        this.dataList = dataList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cart_items_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = dataList.get(position);
        setData(holder, cart);

    }

    private void setData(final ViewHolder holder, final Cart cart) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int quantity = Integer.parseInt(cart.getNumber());
                        if (cart.getPrice() != null) {
                            verbose("Price: " + cart.getPrice());
                            holder.cartItemListTvPrice.setText(String.format("%s%s", cart.getPrice(), context.getString(R.string.reyal)));
                        }




                        holder.cartItemListBtnCounter.setNumber(String.valueOf(quantity));
                        Glide.with(context)
                                .load(cart.getPhotoUrl())
                                .into(holder.cartItemListIvDisplayImage);

                        holder.cartItemListTvName.setText(cart.getName());


                    }
                });

            }
        }).start();

        //Total price = quantity * price
    }

    @Override
    public int getItemCount() {
        if (dataList == null)
            return 0;

        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cart_item_list_iv_display_image)
        ImageView cartItemListIvDisplayImage;
        @BindView(R.id.cart_item_list_tv_name)
        TextView cartItemListTvName;
        @BindView(R.id.cart_item_list_btn_counter)
        ElegantNumberButton cartItemListBtnCounter;
        @BindView(R.id.price_text)
        TextView priceText;
        @BindView(R.id.cart_item_list_tv_total_price)
        TextView cartItemListTvTotalPrice;
        @BindView(R.id.cart_item_list_tv_price)
        TextView cartItemListTvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
