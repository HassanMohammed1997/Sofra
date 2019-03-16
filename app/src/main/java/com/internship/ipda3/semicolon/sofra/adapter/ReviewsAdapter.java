package com.internship.ipda3.semicolon.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.reviews.ReviewsDatum;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments.CommentsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<ReviewsDatum> dataList = new ArrayList<>();
    private Context context;

    public ReviewsAdapter(List<ReviewsDatum> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reviews_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ReviewsDatum reviewsDatum = dataList.get(i);
        setData(viewHolder, reviewsDatum);
        setAction(viewHolder, reviewsDatum);

    }

    private void setAction(ViewHolder viewHolder, ReviewsDatum reviewsDatum) {
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    private void setData(ViewHolder viewHolder, ReviewsDatum reviewsDatum) {
        viewHolder.commentsTextView.setText(reviewsDatum.getComment());
        viewHolder.dateTextView.setText(reviewsDatum.getCreatedAt());
        viewHolder.ratingBar.setRating(Float.parseFloat(reviewsDatum.getRate()));
        viewHolder.userNameTextView.setText(reviewsDatum.getClient().getName());
    }

    @Override
    public int getItemCount() {
        if (dataList == null)
            return 0;
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_name_text_view)
        TextView userNameTextView;
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;
        @BindView(R.id.date_text_view)
        TextView dateTextView;
        @BindView(R.id.comments_text_view)
        TextView commentsTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
