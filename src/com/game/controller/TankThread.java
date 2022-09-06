package com.game.controller;

import com.game.Army;

public class TankThread extends Thread{

    @Override
    public void run() {
        if (WarGameWorld.getSoldierChoice() % 2 == 0) {
            // ally
        }else{
            //enemy
        }
    }
}
