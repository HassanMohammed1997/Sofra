
package com.internship.ipda3.semicolon.sofra.model.general.offers.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.RestaurantDatum;

public class OffersDatum {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("starting_at")
    @Expose
    private String startingAt;
    @SerializedName("ending_at")
    @Expose
    private String endingAt;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("available")
    @Expose
    private boolean available;
    @SerializedName("restaurant")
    @Expose
    private RestaurantDatum restaurant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartingAt() {
        return startingAt;
    }

    public void setStartingAt(String startingAt) {
        this.startingAt = startingAt;
    }

    public String getEndingAt() {
        return endingAt;
    }

    public void setEndingAt(String endingAt) {
        this.endingAt = endingAt;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public RestaurantDatum getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDatum restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Datum{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", startingAt='" + startingAt + '\'' +
                ", endingAt='" + endingAt + '\'' +
                ", photo='" + photo + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", available=" + available +
                ", restaurant=" + restaurant +
                '}';
    }
}
