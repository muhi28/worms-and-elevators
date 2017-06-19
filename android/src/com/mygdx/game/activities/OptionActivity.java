package com.mygdx.game.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.mygdx.game.R;

/**
 * The type Option activity.
 */
public class OptionActivity extends Activity {

    private Switch mySwitch;
    private Intent intent;
    private boolean test = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        mySwitch = (Switch) findViewById(R.id.mySwitch);
        mySwitch.setChecked(true);

        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                test = isChecked;
            }
        });

    }

    /**
     * On click back button.
     *
     * @param v the v
     */
    public void onClickBackButton(View v) {

        intent = new Intent(this, MainMenu.class);
        intent.putExtra("music", test);

        startActivity(intent);
    }

}
