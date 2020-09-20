package com.android.gigvid.model.repository.reponseData;

import androidx.annotation.IntDef;

/**
 * Created by Kavya P S on 18/09/20.
 */
public class StateDefinition {
    @IntDef({State.LOADING, State.COMPLETED, State.ERROR})
    public @interface State {
        int LOADING = 0;
        int COMPLETED = 1;
        int ERROR = 2;
    }

    @IntDef({ErrorState.INTERNAL_SERVER_ERROR, ErrorState.NO_INTERNET_ERROR})
    public @interface ErrorState {
        int INTERNAL_SERVER_ERROR = 500;
        int NO_INTERNET_ERROR = 1;
    }

}