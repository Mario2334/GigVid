package com.android.gigvid.viewModel.homescreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.buygig.BuyGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.buygig.BuyGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist.TicketResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;
import com.android.gigvid.model.repository.reponseData.ListResponse;

import java.lang.ref.WeakReference;

public class TicketsViewModel extends AndroidViewModel {

    private DataRepository mDataRepository;
    private MutableLiveData<String> mText;

    public TicketsViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(new WeakReference<>(application.getApplicationContext()));
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<ListResponse<TicketResp>> getTicketList() {
        LiveData<ListResponse<TicketResp>> buyGigResp = mDataRepository.getTicketsApiCall();
        return Transformations.map(buyGigResp, new Function<ListResponse<TicketResp>, ListResponse<TicketResp>>() {
            @Override
            public ListResponse<TicketResp> apply(ListResponse<TicketResp> input) {
//                Modification to Data as per UI to be done here
                return input;
            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        clear();
    }

    private void clear() {
        mDataRepository = null;
    }
}