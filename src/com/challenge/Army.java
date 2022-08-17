package com.challenge;

public class Army {
    private Soldier[] soldiers;
    private Gun[] guns;
    private Tank[] tanks;
    //private Jet[] jets;

    public Soldier[] getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(Soldier[] soldiers) {
        this.soldiers = soldiers;
    }

    public Gun[] getGuns() {
        return guns;
    }

    public void setGuns(Gun[] guns) {
        this.guns = guns;
    }

    public Tank[] getTanks() {
        return tanks;
    }

    public void setTanks(Tank[] tanks) {
        this.tanks = tanks;
    }

    //public Jet[] getJets() {return jets;}

    //public void setJets(Jet[] jets) {this.jets = jets;}
}
