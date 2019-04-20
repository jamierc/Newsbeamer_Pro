package com.jrcdesign.newsbeamer_pro;

import android.app.Application;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        AdColonyAppOptions options = new AdColonyAppOptions()
                .setGDPRConsentString("1")
                .setGDPRRequired(true);
        AdColony.configure(this, options, "appc38b8f26510d42738a", "vzec1b9b55e74a4d239d");
    }
}
