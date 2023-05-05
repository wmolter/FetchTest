package com.molter.fetchtest;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.HTTP;

/**
 * Interface used by Retrofit to make a REST GET function handle
 */
public interface FetchWebData {
    @GET("/hiring.json")
    Call<List<TestListItem>> GetFetchData();
}
