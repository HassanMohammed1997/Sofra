package com.internship.ipda3.semicolon.sofra.service;

import com.internship.ipda3.semicolon.sofra.model.client.car.add.AddItemToCart;
import com.internship.ipda3.semicolon.sofra.model.client.order.orders.Orders;
import com.internship.ipda3.semicolon.sofra.model.client.userCycle.login.UserLogin;
import com.internship.ipda3.semicolon.sofra.model.client.userCycle.password.NewPassword;
import com.internship.ipda3.semicolon.sofra.model.client.userCycle.register.UserRegister;
import com.internship.ipda3.semicolon.sofra.model.general.categories.Catagory;
import com.internship.ipda3.semicolon.sofra.model.general.city.City;
import com.internship.ipda3.semicolon.sofra.model.general.region.Region;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.Restaurant;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.details.RestaurantDetails;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem.RestaurantFoodItems;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.reviews.RestaurantReviews;
import com.internship.ipda3.semicolon.sofra.model.restaurent.changeState.ChangeState;
import com.internship.ipda3.semicolon.sofra.model.restaurent.items.delete.Delete;
import com.internship.ipda3.semicolon.sofra.model.restaurent.items.newItem.NewItem;
import com.internship.ipda3.semicolon.sofra.model.restaurent.login.RestaurantLogin;
import com.internship.ipda3.semicolon.sofra.model.restaurent.register.RestaurantRegister;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EndPoint {

    @POST("client/login")
    @FormUrlEncoded
    Call<UserLogin> userLogin(@Field("email") String email,
                              @Field("password") String password);

    @POST("client/register")
    @FormUrlEncoded
    Call<UserRegister> userRegister(@Field("name")
                                            String name,
                                    @Field("email")
                                            String email,
                                    @Field("password")
                                            String password,
                                    @Field("password_confirmation")
                                            String passwordConfirmation,
                                    @Field("phone")
                                            String phone,
                                    @Field("address")
                                            String address,
                                    @Field("region_id")
                                            int region_id);

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestaurantLogin> restaurentLogin(@Field("email")
                                                  String email,
                                          @Field("password")
                                                  String password);

    @POST("restaurant/register")
    @Multipart
    Call<RestaurantRegister> restaurentRegister(@Part("name")
                                                        RequestBody name,
                                                @Part("email")
                                                        RequestBody email,
                                                @Part("password")
                                                        RequestBody password,
                                                @Part("password_confirmation") RequestBody passwordConfirmation,
                                                @Part("phone")
                                                        RequestBody phone,
                                                @Part("whatsapp")
                                                        RequestBody whatsapp,
                                                @Part("region_id")
                                                        RequestBody regionId,
                                                @Part("categories[0]")
                                                        List<RequestBody> categories,
                                                @Part("delivery_period")
                                                        RequestBody deliveryPeriod,
                                                @Part("delivery_cost")
                                                        RequestBody deliveryCost,
                                                @Part("minimum_charger")
                                                        RequestBody minimumCharges,
                                                @Part MultipartBody.Part photo,
                                                @Part("availability")
                                                        RequestBody availability);

    @GET("cities")
    Call<City> getCities();

    @GET("regions")
    Call<Region> getRegions(@Query("city_id")
                                    int cityId);

    @GET("categories")
    Call<Catagory> getCategories();

    @POST("client/reset-password")
    @FormUrlEncoded
    Call<NewPassword> userRestPassword(@Field("email")
                                               String email);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<NewPassword> restaurantRestPassword(@Field("email")
                                                     String email);

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<NewPassword> restaurantNewPassword(@Field("code")
                                                    String code,
                                            @Field("password")
                                                    String password,
                                            @Field("password_confirmation")
                                                    String passwordConfirmation);

    @POST("client/new-password")
    @FormUrlEncoded
    Call<NewPassword> userNewPassword(@Field("code")
                                              String code,
                                      @Field("password")
                                              String password,
                                      @Field("password_confirmation")
                                              String passwordConfirmation);

    @GET("restaurants")
    Call<Restaurant> getRestaurantList(@Query("page") int page);

    @GET("restaurant")
    Call<RestaurantDetails> getRestaurantDetails(@Query("restaurant_id")
                                                         int restaurantId);

    @GET("items")
    Call<RestaurantFoodItems> getFoodItems(@Query("restaurant_id")
                                                   int restaurantId,
                                           @Query("page")
                                                   int page);

    @GET("restaurant/reviews")
    Call<RestaurantReviews> getRestaurantReviews(@Query("api_token")
                                                         String apiToken,
                                                 @Query("restaurant_id")
                                                         int restaurantId,
                                                 @Query("page")
                                                         int page);

    @POST("restaurant/review")
    @FormUrlEncoded
    Call<RestaurantReviews> newReview(@Field("rate")
                                              float rate,
                                      @Field("comment")
                                              String comment,
                                      @Field("restaurant_id")
                                              int resId,
                                      @Field("api_token")
                                              String apiToken);

    @GET("restaurant/my-items")
    Call<RestaurantFoodItems> restaurantItems(@Query("api_token")
                                                      String apiToken,
                                              @Query("page")
                                                      int page);

    @POST("restaurant/new-item")
    @Multipart
    Call<NewItem> addNewItem(@Part("description")
                                     RequestBody description,
                             @Part("price")
                                     RequestBody price,
                             @Part("preparing_time")
                                     RequestBody preparingTime,
                             @Part("name")
                                     RequestBody name,
                             @Part MultipartBody.Part photo,
                             @Part("api_token")
                                     RequestBody apiToken);

    @GET("client/my-orders")
    Call<Orders> getUserOrders(@Query("api_token")
                                       String apiToken,
                               @Query("state")
                                       String state,
                               @Query("page")
                                       int page);

    @POST("restaurant/update-item")
    @Multipart
    Call<NewItem> updateItem(@Query("api_token")
                                     String api_token,
                             @Part("description")
                                     RequestBody description,
                             @Part("price")
                                     RequestBody price,
                             @Part("preparing_time")
                                     RequestBody preparingTime,
                             @Part("name")
                                     RequestBody name,
                             @Part MultipartBody.Part photo,
                             @Part("item_id")
                                     RequestBody itemId);

    @POST("restaurant/change-state")
    @Multipart
    Call<ChangeState> changeRestaurantState(@Part("state") RequestBody state, @Part("api_token") RequestBody apiToken);

    @POST("restaurant/delete-item")
    @Multipart
    Call<Delete> deleteItem(@Query("api_token")
                                    String apiToken,
                            @Part("item_id")
                                    RequestBody itemId);

  




}
