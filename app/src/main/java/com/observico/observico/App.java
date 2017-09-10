package com.observico.observico;

import android.app.Application;

/**
 * Created by Георгий on 10.09.2017.
 */

public class App extends Application {
        private static App instance;

        public static App get() {
            return instance;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            instance = this;
        }
}
