package com.challenge;

public class Soldier {
    private Gun gun;
    private Tank tank;
    private String militaryId;
    private char type; // A or E
    private final String shellModel;
    public Soldier(String militaryId, char type) {
        this.militaryId = militaryId;
        this.type = type;
        this.gun = new Gun();
        this.tank = new Tank("T127", 100);
        this.shellModel = tank.getModel();
    }
    public void shootBullets() {
        //System.out.println(this.militaryId + " shooting");
        this.gun.shootBullets();
    }
    public void shootShells(){
        this.tank.shootShells();
    }
    public void changeShootingMode() {
        this.gun.changeShootingMode();
    }
    public void changeShellModel(){
        if (this.shellModel == "canon")
            this.tank.setModel("long bow");
        else
            this.tank.setModel("canon");
    }
}
