package com.zeolink.med_manager.datasource;

import android.net.Uri;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by isaac on 4/16/2018.
 */

public class UsersRepository implements UsersDataSource{

    private UsersRemoteDataSource mUsersRemoteDataSource;

    @Inject
    UsersRepository(@NonNull UsersRemoteDataSource usersRemoteDataSource){
        this.mUsersRemoteDataSource = usersRemoteDataSource;
    }

    @Override
    public void updateProfile(Uri uri, String displayName, UserProfileUpdate profileUpdate) {
        mUsersRemoteDataSource.updateProfile(uri,displayName,profileUpdate);
    }
}
