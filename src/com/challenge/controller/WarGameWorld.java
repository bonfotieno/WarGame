package com.challenge.controller;

import com.challenge.Army;
import com.challenge.Soldier;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WarGameWorld {
    private static int maxSoldiers = 5;
    private Army ally;
    private Army enemy;
    public WarGameWorld() {
        this.setupGame();
    }

    private void setupGame() {
        // Create 2 armies (Ally and Enemy)
        ally = new Army();
        enemy = new Army();
        ArrayList<Soldier> allySoldiers = new ArrayList<>(maxSoldiers);
        ArrayList<Soldier> enemySoldiers = new ArrayList<>(maxSoldiers);
        for (int k = 0; k < maxSoldiers; k ++) {
            allySoldiers.add(new Soldier("ALLY_00" + k));
            enemySoldiers.add(new Soldier("ENEMY_00" + k));
        }
        ally.setSoldiers(allySoldiers);
        enemy.setSoldiers(enemySoldiers);
    }

    private boolean allSoldiersAreDead(Army army) {
        boolean flagDead = true;
        for (int k = 0; k < army.getSoldiers().size(); k ++) {
            flagDead = !army.getSoldiers().get(k).isAlive();
            if (!flagDead)
                break;
        }
        return flagDead;
    }

    private boolean noWeaponHasBullets(Army army) {
        boolean bulletsDepleted = true;
        for (int k = 0; k < army.getSoldiers().size(); k ++) {
            bulletsDepleted = !army.getSoldiers().get(k).gunHasBullets();
            if (!bulletsDepleted)
                break;
        }
        return bulletsDepleted;
    }
    private void runGame() {
        // randomize enemy or ally
        int choice = new Random().nextInt(10);
        if (choice % 2 == 0)
        {
            // enemy
            for (int k = 0; k < maxSoldiers; k ++) {
                int soldierIndex = new Random().nextInt(enemy.getSoldiers().size() );
                enemy.getSoldiers().get(soldierIndex).shootBullets();
            }
            // ally
            for (int k = 0; k < maxSoldiers; k ++) {
                int soldierIndex = new Random().nextInt(ally.getSoldiers().size() );
                choice = new Random().nextInt(10);
                if (choice % 2 == 0 && ally.getSoldiers().get(soldierIndex).isAlive())
                    ally.getSoldiers().get(soldierIndex).shot();
            }
        }
        else
        {
            for (int k = 0; k < maxSoldiers; k ++) {
                int soldierIndex = new Random().nextInt(ally.getSoldiers().size() );
                ally.getSoldiers().get(soldierIndex).shootBullets();
            }
            // ally
            for (int k = 0; k < maxSoldiers; k ++) {
                int soldierIndex = new Random().nextInt(enemy.getSoldiers().size() );
                choice = new Random().nextInt(10);
                if (choice % 2 == 0 && enemy.getSoldiers().get(soldierIndex).isAlive())
                    enemy.getSoldiers().get(soldierIndex).shot();
            }
        }
    }
    public void run() throws InterruptedException {
        // - Setup the game [Soldiers, Army (Ally, Enemy), Weapon Arsenal]
        // - Run the game [ Soldiers shoot at enemy, Control Weapons + Arsenal ]
        // - Control the game. Determine, when the game ends...
        // [1 - All soldiers are dead,
        // [2 - No weapon has bullets
        while (true) {
            if (allSoldiersAreDead(ally) && allSoldiersAreDead(enemy)){
                System.out.println("""
                        \nAll your soldiers are dead.
                        Enter 0 to exit or to continue playing enter New number of soldiers:"""
                );
                Scanner scan = new Scanner(System.in);
                maxSoldiers = scan.nextInt();
                if (maxSoldiers != 0){
                    setupGame();
                }else
                    break;

            }else {
                this.runGame();
            }
            if (noWeaponHasBullets(ally) && noWeaponHasBullets(enemy)){
                System.out.println("Bullets are over. Exiting...");
                break;
            }
            Thread.sleep(100);
        }
    }
}
