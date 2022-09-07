package com.game.controller;

import java.util.Random;

public class GenerateSoldierChoice extends Thread{
    @Override
    public void run() {
        while(!WarGameWorld.GameIsTerminated){
            WarGameWorld.SoldierChoice = new Random().nextInt(20);
        }
    }
}
