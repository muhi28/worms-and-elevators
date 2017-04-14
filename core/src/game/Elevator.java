package game;

/**
 * Created by Muhi on 14.04.2017.
 */

public class Elevator {

    private static int [] elevatorFields = new int[7];

    private static boolean newfield = true;
    private static int random;



    public static void generateElevator(){

        for (int i = 0; i < 7; i++) {

            int number= (int) (Math.random() * 62+1);

            elevatorFields[i] = number;

        }
    }

    public int moveElevator(int fieldnumber){

        while(newfield){

             random = (int) (Math.random() * elevatorFields.length);

            if(random != fieldnumber){

                newfield = false;
            }

        }

        return random;
    }
    public int [] getElevatorFields(){

        return elevatorFields;
    }
}
