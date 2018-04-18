package com.zeolink.med_manager.datasource;

import android.net.Uri;

/**
 * Created by isaac on 4/16/2018.
 */

public interface UsersDataSource {
    interface UserProfileUpdate{
        void onSuccess();

        void onFailure();
    }
    void updateProfile(Uri uri, String displayName, UserProfileUpdate profileUpdate);
}
