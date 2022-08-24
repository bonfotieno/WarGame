package com.challenge;

import com.challenge.gun.Gun;
import com.challenge.tank.Tank;

import java.util.ArrayList;

public class Army {
    private ArrayList<Soldier> soldiers;
    private ArrayList<Gun> guns;
    private ArrayList<Tank> tanks;
    //private Jet[] jets;

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public ArrayList<Gun> getGuns() {
        return guns;
    }

    public void setGuns(ArrayList<Gun> guns) {
        this.guns = guns;
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    public void setTanks(ArrayList<Tank> tanks) {
        this.tanks = tanks;
    }

    //public Jet[] getJets() {return jets;}

    //public void setJets(Jet[] jets) {this.jets = jets;}
}
