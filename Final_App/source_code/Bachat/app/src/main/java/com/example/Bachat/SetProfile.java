/*package com.example.Bachat;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SetProfile extends Activity {
        PopupWindow popUp;
        boolean click = true;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            popUp = new PopupWindow(this);
            LinearLayout layout = new LinearLayout(this);
            LinearLayout mainLayout = new LinearLayout(this);
            TextView tv = new TextView(this);
            Button but = new Button(this);
            but.setText("Click Me");
            but.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click) {
                        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                        popUp.update(50, 50, 300, 80);
                        click = false;
                    } else {
                        popUp.dismiss();
                        click = true;
                    }
                }
            });

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            layout.setOrientation(LinearLayout.VERTICAL);
            tv.setText("Hi this is a sample text for popup window");
            layout.addView(tv, params);
            popUp.setContentView(layout);
            // popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
            mainLayout.addView(but, params);
            setContentView(mainLayout);
        }
    }

}*/