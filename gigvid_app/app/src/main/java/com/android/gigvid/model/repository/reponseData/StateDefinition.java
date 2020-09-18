package com.android.gigvid.model.repository.reponseData;

import androidx.annotation.IntDef;

/**
 * Created by Kavya P S on 18/09/20.
 */
public class StateDefinition {
    @IntDef({State.LOADING, State.COMPLETED, State.ERROR})
    public @interface State {
        int LOADING = 0;
        int COMPLETED = 21;
        int ERROR = 42;
    }

    @IntDef({ErrorState.INTERNAL_SERVER_ERROR})
    public @interface ErrorState {
        int INTERNAL_SERVER_ERROR = 500;
    }

}