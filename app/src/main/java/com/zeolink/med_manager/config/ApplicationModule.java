package com.zeolink.med_manager.config;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zeolink.med_manager.utility.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by isaac on 4/16/2018.
 */
@Module
public class ApplicationModule {

    private Context mContext;

    ApplicationModule(@NonNull Context context){
        this.mContext = context;
    }

    @Provides
    @ApplicationScope
    Context provideApplicationContext(){
        return mContext;
    }
}
