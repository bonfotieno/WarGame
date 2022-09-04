package com.game.controller;

import com.game.Army;
import java.util.Random;

public class SoldierShootingThread extends Thread {
    private final Army ally;
    private final Army enemy;
    public SoldierShootingThread(Army ally, Army enemy)
    {
        this.ally = ally;
        this.enemy = enemy;
    }
    @Override
    public void run() {
        if (WarGameWorld.getSoldierChoice() % 2 == 0){
            // enemy
            for (int k = 0; k < WarGameWorld.getMaxSoldiers(); k ++) {
                int soldierIndex = new Random().nextInt(enemy.getSoldiers().size());
                enemy.getSoldiers().get(soldierIndex).shootBullets();
            }
        }else {
            for (int k = 0; k < WarGameWorld.getMaxSoldiers(); k ++) {
                int soldierIndex = new Random().nextInt(ally.getSoldiers().size() );
                ally.getSoldiers().get(soldierIndex).shootBullets();
            }
        }
    }
}
