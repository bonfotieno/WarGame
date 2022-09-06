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
    private boolean GameIsInitialized = false;
    private String currentPlayer;
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
        ArrayList<String[]> profileData = new ArrayList<>(); //this 2D to hold profileName and Password from the file
        String userInputs = "";
        try {
            InputStreamReader inputReaderFile = new InputStreamReader(new FileInputStream(profileFile));
            BufferedReader readerFile = new BufferedReader(inputReaderFile);
            while (readerFile.ready()){
                profileData.add(readerFile.readLine().split("[,]"));
            }
            System.out.println(" Select a profile to start the Game:");
            for (int j=0; j<profileData.size(); j++) {
                System.out.println(" "+(j+1)+". "+profileData.get(j)[0]);
            }
            System.out.println(" *. Add New Profile");
            userInputs = readUserInputs.readLine();
            if(userInputs.equals("*")){
                this.createGameProfile();
            } else{
                for (int i = 0; i < profileData.size(); i++) {
                    if (userInputs.equals(Integer.toString((i + 1)))) {
                        String[] playerProfile = profileData.get(i);
                        System.out.println("Enter Password for Profile: "+playerProfile[0]);
                        userInputs = readUserInputs.readLine();
                        if (userInputs.equals(playerProfile[1])) {
                            this.GameIsInitialized = true;
                            this.currentPlayer = playerProfile[0];
                            System.out.println("*************** "+playerProfile[0]+" is now playing ***************");
                        }else {
                            System.out.println("Wrong Password! Enter Passwd again.");
                            this.GameIsInitialized = false;
                        }
                        break;

                    }else {
                        System.out.print("Wrong Choice. ");
                        this.GameIsInitialized = false;
                    }
                }
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
    private  void runGame(){
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
    public void run() throws IOException {
        String userInputs;
        System.out.println("\n ############################### WAR GAME ##################################\n");
        if(isFileExists(profileFile) && profileFile.length() > 0){
            while (true){
                System.out.println("Initializing game...");
                this.initializeGame();
                if(GameIsInitialized){
                    this.runGame();
                    break;
                }
            }
        }else{
            System.out.println("No Game Profile was found. Do want to create one(1 for YES and 0 for to Exit):");
            while (true){
                userInputs = readUserInputs.readLine();
                if(userInputs.equals("1")) {
                    this.createGameProfile();
                    if(GameIsInitialized){
                        this.runGame();
                        break;
                    }
                } else if (userInputs.equals("0")) {
                    System.out.println("Exiting");
                    break;
                } else
                    System.out.println("Wrong Choice. Enter Choice Again:");
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
