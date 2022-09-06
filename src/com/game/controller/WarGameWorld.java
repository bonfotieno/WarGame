package com.game.controller;

import com.game.Army;
import com.game.Soldier;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WarGameWorld {
    private static int maxSoldiers = 10;
    public static int SoldierChoice;
    public static Army Ally;
    public static Army Enemy;
    private final File dataFile = new File("game_data.data");
    private final File profileFile = new File("game_profile.data");
    private final InputStreamReader inputReader = new InputStreamReader(System.in);
    private final BufferedReader readUserInputs = new BufferedReader(inputReader);
    public WarGameWorld() {this.setupGame();}
    private void setupGame() {
        // Create 2 armies (Ally and Enemy)
        Ally = new Army();
        Enemy = new Army();
        ArrayList<Soldier> allySoldiers = new ArrayList<>(maxSoldiers);
        ArrayList<Soldier> enemySoldiers = new ArrayList<>(maxSoldiers);
        for (int k = 0; k < maxSoldiers; k ++) {
            allySoldiers.add(new Soldier("ALLY_00" + k));
            enemySoldiers.add(new Soldier("ENEMY_00" + k));
        }
        Ally.setSoldiers(allySoldiers);
        Enemy.setSoldiers(enemySoldiers);
    }

    private void GameThreadHandler() {
        GunThread t1 = new GunThread();
        TankThread t2 = new TankThread();
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    private void initializeGame(){
        ArrayList<String> profileData = new ArrayList<>();
        try {
            InputStreamReader inputReaderFile = new InputStreamReader(new FileInputStream(profileFile));
            BufferedReader readerFile = new BufferedReader(inputReaderFile);
            while (readerFile.ready()){
                profileData.add(readerFile.readLine());
            }
            System.out.println(" Select a profile to start the Game:");
            for (int j=0; j<profileData.size(); j++) {
                System.out.println(" "+(j+1)+". "+profileData.get(j));
            }
            readerFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void createGameProfile(){
        //reading user inputs
        String userInputs;
        try {
            OutputStreamWriter outputWriter = new OutputStreamWriter(new FileOutputStream(profileFile, true));
            BufferedWriter writer1 = new BufferedWriter(outputWriter);
            System.out.println("Create a new game profile:\nEnter Username:");
            userInputs = readUserInputs.readLine();
            System.out.println("Enter a Password:");
            writer1.write(userInputs+","+readUserInputs.readLine());
            writer1.flush();
            writer1.close();
            System.out.println("Profile created successfully.\n 1. Continue\n 0. Exit");
            userInputs = readUserInputs.readLine();
            if(userInputs.equals("1")){
                this.initializeGame();
            }else
                System.out.println("Exiting...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void run() throws IOException {
        String userInputs;
        System.out.println("\n  ############################ WAR GAME ############################\n");
        if(isFileExists(profileFile) && profileFile.length() > 0){
            this.initializeGame();
        }else{
            System.out.println("No Game Profile was found. Do want to create one(1 for YES and 0 for to Exit):");
            userInputs = readUserInputs.readLine();
            if(userInputs.equals("1")) {
                this.createGameProfile();
            } else if (userInputs.equals("0")) {
                System.out.println("Exiting");
            } else
                System.out.println("Wrong Choice. Game is Terminating");
        }
        //below to be in the run game, flagGameIsInitialized=
        while (true) {
            SoldierChoice = new Random().nextInt(10); // randomize enemy or ally choice
            if (allSoldiersAreDead(Ally) && allSoldiersAreDead(Enemy)){
                System.out.println("All your soldiers are dead. Enter 0 to exit" +
                        " or to continue playing enter New number of soldiers:");
                Scanner scan = new Scanner(System.in);
                maxSoldiers = scan.nextInt();
                if (maxSoldiers != 0){
                    setupGame();
                }else
                    break;

            }else {
                this.GameThreadHandler();
            }
            if (noWeaponHasBullets(Ally) && noWeaponHasBullets(Enemy)){
                System.out.println("Bullets are over. Exiting...");
                break;
            }
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
    public static boolean isFileExists(File file) {
        return file.exists() && !file.isDirectory();
    }
    public static int getMaxSoldiers() {
        return maxSoldiers;
    }
    public static int getSoldierChoice() {
        return SoldierChoice;
    }
}
