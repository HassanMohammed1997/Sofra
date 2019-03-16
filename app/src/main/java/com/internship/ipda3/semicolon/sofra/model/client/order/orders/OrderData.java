
package com.internship.ipda3.semicolon.sofra.model.client.order.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderData {

    @SerializedName("current_page")
    private Long mCurrentPage;
    @SerializedName("data")
    private List<OrderDatum> mData;
    @SerializedName("from")
    private Long mFrom;
    @SerializedName("last_page")
    private Long mLastPage;
    @SerializedName("next_page_url")
    private String mNextPageUrl;
    @SerializedName("per_page")
    private Long mPerPage;
    @SerializedName("prev_page_url")
    private String mPrevPageUrl;
    @SerializedName("to")
    private Long mTo;
    @SerializedName("total")
    private Long mTotal;

    public Long getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(Long currentPage) {
        mCurrentPage = currentPage;
    }

    public List<OrderDatum> getData() {
        return mData;
    }

    public void setData(List<OrderDatum> data) {
        mData = data;
    }

    public Long getFrom() {
        return mFrom;
    }

    public void setFrom(Long from) {
        mFrom = from;
    }

    public Long getLastPage() {
        return mLastPage;
    }

    public void setLastPage(Long lastPage) {
        mLastPage = lastPage;
    }

    public String getNextPageUrl() {
        return mNextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        mNextPageUrl = nextPageUrl;
    }

    public Long getPerPage() {
        return mPerPage;
    }

    public void setPerPage(Long perPage) {
        mPerPage = perPage;
    }

    public String getPrevPageUrl() {
        return mPrevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        mPrevPageUrl = prevPageUrl;
    }

    public Long getTo() {
        return mTo;
    }

    public void setTo(Long to) {
        mTo = to;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "mCurrentPage=" + mCurrentPage +
                ", mData=" + mData +
                ", mFrom=" + mFrom +
                ", mLastPage=" + mLastPage +
                ", mNextPageUrl='" + mNextPageUrl + '\'' +
                ", mPerPage=" + mPerPage +
                ", mPrevPageUrl='" + mPrevPageUrl + '\'' +
                ", mTo=" + mTo +
                ", mTotal=" + mTotal +
                '}';
    }
}
