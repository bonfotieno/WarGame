package com.game;

import com.game.gun.Gun;
import com.game.jet.Jet;
import com.game.tank.Tank;
import java.util.ArrayList;

public class Army {
    //private ArrayList<Gun> guns;
    //private ArrayList<Tank> tanks;
    //private ArrayList<Jet> jets;
    private ArrayList<Soldier> soldiers;
    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }
    public void setSoldiers(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }
    //public ArrayList<Jet> getJets() {return jets;}
    //public void setJets(ArrayList<Jet> jets) {this.jets = jets;}
    //public ArrayList<Tank> getTanks() {return tanks;}
    //public void setTanks(ArrayList<Tank> tanks) {this.tanks = tanks;}
    //public ArrayList<Gun> getGuns() {return guns;}
    // public void setGuns(ArrayList<Gun> guns) {this.guns = guns;}
}
