package com.android.gigvid.model.repository.networkRepo.homeScreen;

import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.BuyGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.BuyGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HomeScreenApi {

    /*
        get list of all gigs
     */
    @GET("/dev/gig/")
    Call<List<GigListResp>> getGigList();

    /*
        Create new gig
     */
    @POST("/dev/gig/create_verify/")
    Call<CreateGigResp> createGig(@Header("authorization") String auth, @Body CreateGigReqBody createGig);

    /*
     Buy gig ticket
  */
    @POST("/dev/gig/create_payment/")
    Call<BuyGigResp> buyGig(@Header("authorization") String auth, @Body BuyGigReqBody buyGigReqBody);
}
