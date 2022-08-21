package com.challenge;

import com.challenge.gun.Gun;
import com.challenge.jet.Jet;
import com.challenge.tank.Tank;

public class Soldier {
    private Gun gun;
    private Tank tank;
    private Jet jet;
    private String militaryId;
    private char type; // A or E
    private Than tankModel;
    public Soldier(String militaryId, char type) {
        this.militaryId = militaryId;
        this.type = type;
        this.gun = new Gun();
        this.tank = new Tank("T127", 1000);
        this.jet = new Jet();
        this.shellModel = tank.getModel();
    }
    public void shootBullets() {
        this.gun.shootBullets();
    }
    public void changeShootingMode() {
        this.gun.changeShootingMode();
    }
    public void shootShells(){
        this.tank.shootShells();
    }
    public void changeShellModel(){
        if (this.shellModel.equals("canon"))
            this.tank.setModel("long bow");
        else
            this.tank.setModel("canon");
    }
    public void jetFiring(){
        this.jet.fly();
        this.jet.fire();
    }
    public void changeJetType(){
        this.jet.chooseJetType();
    }
}
