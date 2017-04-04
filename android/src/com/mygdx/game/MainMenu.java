package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Muhi on 03.04.2017.
 */

public class MainMenu extends Activity {


    private Intent intent;
   // private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        //button = (Button)findViewById(R.id.instr_button);

        //onChangetoInstruction(button);
    }

   /* public void onChangetoInstruction(Button button){

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                intent = new Intent(MainMenu.this,Instruction.class);
                startActivity(intent);
            }
        });

    }*/

   public void onButtonClickCreateInstr(View v){

       intent = new Intent(this, Instruction.class);
        startActivity(intent);
   }

   public void onClickChangeToCharacterSelect(View v){

       intent = new Intent(this, CharacterSelect.class);
       startActivity(intent);
   }

}
