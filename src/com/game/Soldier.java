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
        this.tank = new Tank("T127", 1000);
        this.jet = new Jet();
    }
    public boolean isAlive() {
        return alive;
    }
    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public boolean gunHasBullets() {
        if (this.gun.getBullets() > 0)
            return true;
        else
            return false;
    }
    public void shootBullets() {
        if (this.alive) {
            System.out.println(this.militaryId + " shooting");
            this.gun.shootBullets();
        }
    }
    public void shot() {
        this.alive = false;
        System.out.println(this.militaryId + " just died");
    }
    public void changeShootingMode() {
        this.gun.changeShootingMode();
    }
    public void shootShells(){
        this.tank.shootShells();
    }
    public void changeShellModel(){
        this.tank.changeTankModel();
    }
    public void jetFiring(){
        this.jet.fly();
        this.jet.fire();
    }
    public void changeJetType(){
        this.jet.chooseJetType();
    }
}
