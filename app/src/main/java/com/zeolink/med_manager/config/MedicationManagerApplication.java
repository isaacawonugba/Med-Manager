package com.zeolink.med_manager.config;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by andronicus on 3/23/2018.
 */
public class MedicationManagerApplication extends Application{

    private static ApplicationComponent mApplicationComponent;
	
    @Override
    public void onCreate() {
        super.onCreate();
		
        /*
        * Enable firebase offline capability
        * */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        /*
        * Initialize application component
        * */
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
	
    public static ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }
}
