package com.game.controller;

import com.game.Army;

public class TankThread extends Thread{
    @Override
    public void run() {
        while(!WarGameWorld.GameIsTerminated) {
            System.out.println();
        }
    }
}
