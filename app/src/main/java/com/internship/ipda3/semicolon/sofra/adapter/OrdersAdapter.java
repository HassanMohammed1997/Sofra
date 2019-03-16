package com.internship.ipda3.semicolon.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.client.order.orders.OrderDatum;
import com.internship.ipda3.semicolon.sofra.model.client.order.orders.OrderItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.hideView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {


    private Context context;
    private List<OrderDatum> dataList = new ArrayList<>();

    public OrdersAdapter(Context context, List<OrderDatum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_order_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        OrderDatum orderItem = dataList.get(i);
        setData(viewHolder, orderItem);

    }

    private void setData(ViewHolder viewHolder, OrderDatum orderItem) {
        List<OrderItem> items = orderItem.getItems();
        String state = orderItem.getState();
        if (state.equals("complete") | state.equals("delivered") | state.equals("accepted") | state.equals("rejected")) {
            hideView(viewHolder.orderListBtnOrderReceipt);
            hideView(viewHolder.orderListBtnOrderReject);
        }
        for (OrderItem item : items) {
            String name = item.getName();
            String orderId = item.getPivot().getOrderId();
            String price = item.getPivot().getPrice();
            String photoUrl = item.getPhotoUrl();


            viewHolder.orderListTvDisplayName.setText(name);
            viewHolder.orderListTvDisplayOrderNumber.setText(String.format("%s%s", context.getString(R.string.order_id_string), orderId));
            viewHolder.orderListTvDisplayPrice.setText(String.format("%s%s", context.getString(R.string.price_text), price));
            Glide.with(context).load(photoUrl).into(viewHolder.orderListIvOrderImage);


        }
        viewHolder.orderListTvDisplayDeliveryCost.setText(String.format("%s%s", context.getString(R.string.delivery_fee_text), orderItem.getRestaurant().getDeliveryCost()));
        viewHolder.orderListTvDisplayTotal.setText(String.format("%s%s", context.getString(R.string.total_price), orderItem.getTotal()));


    }

    @Override
    public int getItemCount() {
        if (dataList == null)
            return 0;

        return dataList.size();
    }

    @OnClick({R.id.order_list_btn_order_receipt, R.id.order_list_btn_order_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_list_btn_order_receipt:
                break;
            case R.id.order_list_btn_order_reject:
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_list_iv_order_image)
        ImageView orderListIvOrderImage;
        @BindView(R.id.order_list_tv_display_name)
        TextView orderListTvDisplayName;
        @BindView(R.id.order_list_tv_display_price)
        TextView orderListTvDisplayPrice;
        @BindView(R.id.order_list_tv_display_delivery_cost)
        TextView orderListTvDisplayDeliveryCost;
        @BindView(R.id.order_list_tv_display_total)
        TextView orderListTvDisplayTotal;
        @BindView(R.id.order_list_tv_display_order_number)
        TextView orderListTvDisplayOrderNumber;
        @BindView(R.id.order_list_btn_order_receipt)
        Button orderListBtnOrderReceipt;
        @BindView(R.id.order_list_btn_order_reject)
        Button orderListBtnOrderReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
