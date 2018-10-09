package OVP;

import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.zero.zero.timetable.R;

public class Display {

    public static void ViewOVP(WebView TimeTable){
        String OVP_Link = "";
        final boolean Interaction_enabler = false;

        TimeTable.loadUrl(OVP_Link);

        TimeTable.setInitialScale(100);
        TimeTable.getSettings().setBuiltInZoomControls(Interaction_enabler);

        TimeTable.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !Interaction_enabler;
            }
        });
    }
}
