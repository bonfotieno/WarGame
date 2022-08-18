package com.challenge;

import java.util.Objects;

public class Soldier {
    private Gun gun;
    private Tank tank;
    private Jet jet;
    private String militaryId;
    private char type; // A or E
    private final String shellModel;
    public Soldier(String militaryId, char type) {
        this.militaryId = militaryId;
        this.type = type;
        this.gun = new Gun();
        this.tank = new Tank("T127", 100);
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
        if (Objects.equals(this.shellModel, "canon"))
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
