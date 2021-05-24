package com.p3lb.cafex;


import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.utilities.Printing;

public class ApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Printooth.INSTANCE.init(this);
    }


}