package logic;

import java.util.ArrayList;

/**
 * Created by Muhi on 06.04.2017.
 */

public class Spielfeld {

    private Spielfigur spielfigur;
    private int counter = 0;

    private ArrayList<Spielfigur> spielfiguren = new ArrayList<>();

    private int spielerAnzahl = 0;

    public Spielfeld(){

        addPlayer();
    }

    public Spielfigur addPlayer(){

        spielerAnzahl += 1;
        counter += 1;

         spielfigur = new Spielfigur("Spieler1",spielerAnzahl);

        spielfiguren.add(counter,spielfigur);
        //spielfigur.setId(spielerAnzahl);

        return spielfigur;
    }

}
