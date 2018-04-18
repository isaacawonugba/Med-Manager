package com.zeolink.med_manager.config;

import android.content.Context;
import com.zeolink.med_manager.utility.ApplicationScope;
import dagger.Component;

/**
 * Created by isaac on 4/16/2018.
 */

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context exposeContext();
}
