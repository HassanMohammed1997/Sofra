package com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.viewPagerFragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.adapter.ReviewsAdapter;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.reviews.RestaurantReviews;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.reviews.ReviewsDatum;
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
import static com.internship.ipda3.semicolon.sofra.Constants.keys.RESTAURANT_ID;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.service.RetrofitClient.getClient;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.getTextFromEditText;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.showToast;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.error;
import static com.internship.ipda3.semicolon.sofra.util.LogUtil.verbose;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {


    @BindView(R.id.comments_fragments_RV_reviews)
    RecyclerView commentsFragmentsRVReviews;
    Unbinder unbinder;

    EndPoint endPoint;
    String apiToken = "";
    int restaurantId;
    @BindView(R.id.comment_layout)
    LinearLayout commentLayout;
    private int userId;


    public CommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        unbinder = ButterKnife.bind(this, view);

        apiToken = LoadStringData(getActivity(), API_TOKEN);
        restaurantId = LoadIntegerData(getActivity(), RESTAURANT_ID);
        userId = LoadIntegerData(getActivity(), USER_TYPE);

        if (userId == 2)
            commentLayout.setVisibility(View.GONE);




        endPoint = getClient().create(EndPoint.class);
        setupReviewsRecyclerView();
        retrieveComments();
        return view;
    }


    private void retrieveComments() {
        endPoint.getRestaurantReviews(apiToken, restaurantId, 1)
                .enqueue(new Callback<RestaurantReviews>() {
                    @Override
                    public void onResponse(Call<RestaurantReviews> call, Response<RestaurantReviews> response) {
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            int status = response.body().getStatus().intValue();
                            if (status == 1) {
                                List<ReviewsDatum> data = response.body().getData().getData();
                                ReviewsAdapter adapter = new ReviewsAdapter(data, getContext());
                                commentsFragmentsRVReviews.setAdapter(adapter);
                            }

                            verbose("onResponse: success: server message: getRestaurantReviews: " + msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantReviews> call, Throwable t) {
                        error("onFailure: failure message: " + t.getMessage());

                    }
                });
    }

    private void setupReviewsRecyclerView() {
        commentsFragmentsRVReviews.setHasFixedSize(true);
        commentsFragmentsRVReviews.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.comments_fragment_btn_add_comment)
    public void onViewClicked() {
        showRatingDialog();

    }

    public void showRatingDialog() {
        Dialog rateDialog = new Dialog(getContext());
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.rating_custom_dialog, null);
        rateDialog.setContentView(view);
        rateDialog.show();

        final RatingBar ratingBar = view.findViewById(R.id.rating_bar_dialog);
        Button commentButton = view.findViewById(R.id.comment_button);
        final EditText commentText = view.findViewById(R.id.comment_edit_text);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                String comment = getTextFromEditText(commentText);
                newComment(rating, comment);
            }
        });
    }

    private void newComment(float rating, String comment) {
        if (apiToken == null) {
            showToast(getContext(), getString(R.string.you_must_login));
            return;
        }

        endPoint.newReview(rating, comment, restaurantId, apiToken)
                .enqueue(new Callback<RestaurantReviews>() {
                    @Override
                    public void onResponse(Call<RestaurantReviews> call, Response<RestaurantReviews> response) {
                        if (response.code() == 200) {
                            String msg = response.body().getMsg();
                            int status = response.body().getStatus().intValue();
                            verbose("onResponse: post new review: " + msg);

                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantReviews> call, Throwable t) {
                        error("onFailure: post new review: response message" + t.getMessage());

                    }
                });


    }
}
