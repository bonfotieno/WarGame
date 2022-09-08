package com.game.controller;

import java.util.Random;

enum Weapons{
    Gun,
    Tank,
    Jet
}
public class ChooseWeaponThread extends Thread{
    public static Weapons weapon = Weapons.Gun; // gun is ste to be the default choice
    int random;
    @Override
    public void run() {
        while(!WarGameWorld.GameIsTerminated) {
            random = new Random().nextInt(50);
            if (random%2==0) {
                weapon = Weapons.Gun;
            }else{
                if (random%5==0) {
                    weapon = Weapons.Tank;
                } else if (random % 7 == 0) {
                    weapon = Weapons.Jet;
                }
            }
        }
    }
}
