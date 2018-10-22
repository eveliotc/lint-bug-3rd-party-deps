package evel.io.lintbug;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.facebook.fresco.animation.bitmap.BitmapAnimationBackend;

public class MainActivity extends Activity {

    BitmapAnimationBackend bitmapAnimationBackend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmapAnimationBackend = new BitmapAnimationBackend(null, null, null, null, null
                , null);
    }
}
