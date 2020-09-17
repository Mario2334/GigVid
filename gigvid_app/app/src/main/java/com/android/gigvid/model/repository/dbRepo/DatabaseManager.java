package com.android.gigvid.model.repository.dbRepo;


/**
 * Created by Kavya P S on 18/09/20.
 */
public class DatabaseManager {
    private static DatabaseManager INSTANCE;

    public static DatabaseManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager();
        }
        return INSTANCE;
    }
}
