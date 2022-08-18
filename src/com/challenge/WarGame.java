package com.challenge;

import java.util.Random;
import java.util.Scanner;

public class WarGame {
    private static Soldier[] createSoldiers() {
        Soldier[] soldiers = new Soldier[1000];
        for (int k = 0; k < 1000; k ++) {
            Soldier soldier = new Soldier("MIL_ID_000747", 'A');
            soldiers[k] = soldier;
        }
        return soldiers;
    }
    public static void main(String[] args) throws InterruptedException {
        Army army = new Army();
        army.setSoldiers(createSoldiers());
        int weapon;
        System.out.println("*********Start Game***********.\nChoose a weapon:\n1.Gun\n2.Tank\n3.Jet");
        Scanner scan = new Scanner(System.in);
        weapon = scan.nextInt();
        while (true) {
            Soldier soldier = new Soldier("MIL_ID_000747", 'A');
            Thread.sleep(2000);
            int i = 0;
            while (i <= 10000) {
                if (weapon == 1) {
                    soldier.shootBullets();
                    if (new Random().nextInt() % 2 == 0)
                        soldier.changeShootingMode();
                }else if (weapon == 2) {
                    soldier.shootShells();
                    if (new Random().nextInt() % 2 == 0)
                        soldier.changeShellModel();
                } else if (weapon == 3) {
                    soldier.jetFiring();
                    if (new Random().nextInt() % 2 == 0)
                        soldier.changeJetType();
                }
                else{
                    System.out.println("No such weapon!!\nGame is ending...");
                    Thread.sleep(1000);
                    break;
                }
                i += 1000;
            }
        }
    }
}
