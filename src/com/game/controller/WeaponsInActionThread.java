package com.game.controller;

import com.game.Army;
import java.util.Random;

public class WeaponsInActionThread extends Thread {
    private void SoldiersShooting(Army army){
        for (int k = 0; k < WarGameWorld.maxSoldiers; k ++) {
            int soldierIndex = new Random().nextInt(army.getSoldiers().size());
            int random = new Random().nextInt(20);
            if (random%10==0 && army.getSoldiers().get(soldierIndex).isAlive()) {
                if (ChooseWeaponThread.weapon == Weapons.Gun) {
                    army.getSoldiers().get(soldierIndex).shootBullets();
                } else if (ChooseWeaponThread.weapon == Weapons.Tank) {
                    army.getSoldiers().get(soldierIndex).shootShells();
                } else if (ChooseWeaponThread.weapon == Weapons.Jet) {
                    army.getSoldiers().get(soldierIndex).jetFiring();
                }
            }
        }
    }
    private void SoldiersDying(Army army){
        GameMode gameMode = WarGameWorld.gameMode;
        int range = 0;
        int possibleNumberOfKills;
        for (int k = 0; k < WarGameWorld.maxSoldiers; k ++) {
            int soldierIndex = new Random().nextInt(army.getSoldiers().size());
            int random = new Random().nextInt(81);
            if (ChooseWeaponThread.weapon == Weapons.Jet || ChooseWeaponThread.weapon == Weapons.Tank) {
                possibleNumberOfKills = 10;
            } else
                possibleNumberOfKills = 1;
            for (int i = 1; i <= possibleNumberOfKills; i++) {
                if (army == WarGameWorld.Ally) {
                    if (gameMode == GameMode.EASY) {
                        range = 21;
                    } else if (gameMode == GameMode.MEDIUM) {
                        range = 41;
                    } else if (gameMode == GameMode.HARD) {
                        range = 81;
                    }
                    if (random % 10 == 0 && WarGameWorld.SoldierChoice <= range && army.getSoldiers().get(soldierIndex).isAlive()) {
                        army.getSoldiers().get(soldierIndex).shot();
                    }
                } else {
                    if (random % 10 == 0 && army.getSoldiers().get(soldierIndex).isAlive())
                        army.getSoldiers().get(soldierIndex).shot();
                }
            }
        }
    }
    @Override
    public void run() {
        while (!WarGameWorld.GameIsTerminated) {
            if (WarGameWorld.SoldierChoice % 2 == 0) {
                this.SoldiersShooting(WarGameWorld.Enemy);
                this.SoldiersDying(WarGameWorld.Ally);
            } else {
                this.SoldiersShooting(WarGameWorld.Ally);
                this.SoldiersDying(WarGameWorld.Enemy);
            }
        }
    }
}
