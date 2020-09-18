package com.android.gigvid.model.repository.networkRepo.homeScreen;

import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeScreenApi {

    /**
     * List of upcoming gigs
     * @return
     */
    @GET("/dev/gig/")
    Call<List<GigListResp>> getGigList();
}
