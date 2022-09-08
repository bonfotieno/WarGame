package com.game.controller;

public class TankThread extends Thread{
    @Override
    public void run() {
        while(!WarGameWorld.GameIsTerminated) {
            System.out.println();
        }
    }
}
