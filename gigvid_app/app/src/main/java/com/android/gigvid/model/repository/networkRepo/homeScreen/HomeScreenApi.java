package com.android.gigvid.model.repository.networkRepo.homeScreen;

import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.profile.BankDetailsReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.buygig.BuyGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.buygig.BuyGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.creategig.CreateGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.creategig.CreateGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist.TicketResp;

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

    /*
     get list of tickets
  */
    @GET("dev/gig/list_tickets/")
    Call<List<TicketResp>> getTicketList(@Header("authorization") String auth);

    /*
   get gigs hosted by me
*/
    @GET("dev/gig/my_gigs/")
    Call<List<GigListResp>> getMyGigsList(@Header("authorization") String auth);

    @POST("/dev/user/bank_account/")
    Call<String> addBankDetails(@Header("authorization") String auth, @Body BankDetailsReqBody bankDetailsReqBody);
}
