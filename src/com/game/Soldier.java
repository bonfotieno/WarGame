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
            System.out.println("\t"+this.militaryId + " shooting");
            try {
                this.gun.shootBullets();
            } catch (Exception e) {
                System.out.println("\t###### BULLETS are DEPLETED for: "+this.militaryId);
            }
        }
    }
    public void shootShells(){
        this.tank.shootShells();
    }
    public void jetFiring(){
        this.jet.fire();
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
    public void setGun(Gun gun) {
        this.gun = gun;
    }
}
