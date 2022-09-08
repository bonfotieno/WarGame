package com.game.controller;

public class ChooseWeaponThread extends Thread{
    @Override
    public void run() {
        while(!WarGameWorld.GameIsTerminated) {
            System.out.println();
        }
    }
}
