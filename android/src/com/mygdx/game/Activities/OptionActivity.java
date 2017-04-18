package com.mygdx.game.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.mygdx.game.R;

public class OptionActivity extends Activity {

    private Switch mySwitch;
    private Intent intent;
    private boolean test=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        mySwitch = (Switch) findViewById(R.id.mySwitch);
        mySwitch.setChecked(true);

        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

              /*  if(isChecked){
                  //  switchStatus.setText("Switch is currently ON");


                }else{
                   // switchStatus.setText("Switch is currently OFF");
                    }*/

                test=isChecked;



            }
        });

    }
    public void onClickBackButton(View v){

        intent = new Intent(this,MainMenu.class);
        intent.putExtra("music", test);

        startActivity(intent);
    }

}
