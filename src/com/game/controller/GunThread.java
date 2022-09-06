package com.game.controller;

import com.game.Army;
import java.util.Random;

public class GunThread extends Thread {
    private void SoldiersShooting(Army army){
        for (int k = 0; k < WarGameWorld.getMaxSoldiers(); k ++) {
            int soldierIndex = new Random().nextInt(army.getSoldiers().size());
            army.getSoldiers().get(soldierIndex).shootBullets();
        }
    }
    private void SoldiersDying(Army army){
        for (int k = 0; k < WarGameWorld.getMaxSoldiers(); k ++) {
            int soldierIndex = new Random().nextInt(army.getSoldiers().size() );
            if (WarGameWorld.getSoldierChoice() <= 9 && army.getSoldiers().get(soldierIndex).isAlive())
                army.getSoldiers().get(soldierIndex).shot();
        }
    }
    @Override
    public void run() {
        if (WarGameWorld.getSoldierChoice() % 2 == 0){
            this.SoldiersShooting(WarGameWorld.Enemy);
            this.SoldiersDying(WarGameWorld.Ally);
        }else {
            this.SoldiersShooting(WarGameWorld.Ally);
            this.SoldiersDying(WarGameWorld.Enemy);
        }
    }
}
