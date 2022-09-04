package com.game.controller;

import com.game.Army;
import java.util.Random;

public class SoldierShotThread extends Thread{
    private final Army ally;
    private final Army enemy;
    public SoldierShotThread(Army ally, Army enemy)
    {
        this.ally = ally;
        this.enemy = enemy;
    }
    @Override
    public void run() {
        if (WarGameWorld.getSoldierChoice() % 2 == 0) {
            // ally
            for (int k = 0; k < WarGameWorld.getMaxSoldiers(); k ++) {
                int soldierIndex = new Random().nextInt(ally.getSoldiers().size() );
                WarGameWorld.SoldierChoice = new Random().nextInt(10);
                if (WarGameWorld.SoldierChoice % 2 == 0 && ally.getSoldiers().get(soldierIndex).isAlive())
                    ally.getSoldiers().get(soldierIndex).shot();
            }
        }else{
            //enemy
            for (int k = 0; k < WarGameWorld.getMaxSoldiers(); k ++) {
                int soldierIndex = new Random().nextInt(enemy.getSoldiers().size() );
                WarGameWorld.SoldierChoice = new Random().nextInt(10);
                if (WarGameWorld.SoldierChoice % 2 == 0 && enemy.getSoldiers().get(soldierIndex).isAlive())
                    enemy.getSoldiers().get(soldierIndex).shot();
            }
        }
    }
}
