package com.game;

import com.game.gun.Gun;
import com.game.jet.Jet;
import com.game.tank.Tank;

public class Soldier {
    private Gun gun;
    private Tank tank;
    private Jet jet;
    private boolean alive;
    private String militaryId;
    public Soldier(String militaryId) {
        this.militaryId = militaryId;
        this.alive = true;
        this.gun = new Gun();
        this.tank = new Tank("T127", 100);
        this.jet = new Jet();
    }
    public boolean isAlive() {
        return alive;
    }
    public boolean gunHasBullets() {
        if (this.gun.getBullets() > 0)
            return true;
        else
            return false;
    }
    public void shot() {
        this.alive = false;
        System.out.println("\t"+this.militaryId + " just died");
    }
    public void shootBullets() {
        if (this.alive) {
            try {
                this.gun.shootBullets();
                System.out.println("\t"+this.militaryId + " shooting");
            } catch (Exception e) {
                System.out.println("\t###### "+this.militaryId+
                        " tried Shooting but BULLETS are DEPLETED in his gun");
            }
        }
    }
    public void shootShells(){
        if (this.alive) {
            try {
                this.tank.shootShells();
                System.out.println("\t"+this.militaryId + " shot a Shell");
            } catch (Exception e) {
                System.out.println("\t###### "+this.militaryId+
                        " tried Shooting but SHELLS are DEPLETED in the Tank");
            }
        }
    }
    public void jetFiring(){
        if (this.alive) {
            try {
                this.jet.fire();
                System.out.println("\t"+this.militaryId + " is Shooting from a Jet");
            } catch (Exception e) {
                System.out.println("\t###### "+this.militaryId+
                        " tried Shooting from a Jet BULLETS are DEPLETED in the Jet");
            }
        }
    }
    public void changeJetType(){
        this.jet.chooseJetType();
    }
    public void changeGunShootingMode() {
        this.gun.changeShootingMode();
    }
    public void changeTankModel(){
        this.tank.changeTankModel();
    }
}
