package com.game.controller;

import com.game.Army;

public class TankThread extends Thread{

    @Override
    public void run() {
        while(!WarGameWorld.GameIsTerminated) {
            if (WarGameWorld.SoldierChoice % 2 == 0) {
                // ally
            } else {
                //enemy
            }
        }
    }
}
