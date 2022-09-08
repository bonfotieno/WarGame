package com.game.controller;

enum Weapons{
    Gun,
    Tank,
    Jet
}
public class ChooseWeaponThread extends Thread{
    Weapons weapon = Weapons.Gun; // gun is ste to be the default choice
    @Override
    public void run() {
        while(!WarGameWorld.GameIsTerminated) {
            System.out.println();
        }
    }
}
