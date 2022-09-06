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
    public static GameMode gameMode = GameMode.EASY; //this is the default
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
        String selection = "";
        try {
            InputStreamReader inputReaderFile = new InputStreamReader(new FileInputStream(profileFile));
            BufferedReader readerFile = new BufferedReader(inputReaderFile);
            this.processInit();
            while (readerFile.ready()){
                profileData.add(readerFile.readLine().split("[,]"));
            }
            System.out.println("\n Select a profile to start the Game:");
            while(true) {
                for (int j=0; j<profileData.size(); j++) {
                    System.out.println(" "+(j+1)+". "+profileData.get(j)[0]);
                }
                System.out.println(" *. Add New Profile\n #. Exit");
                selection = readUserInputs.readLine();

                if (selection.equals("*")) {
                    this.createGameProfile();
                    break;
                } else if (selection.equals("#")) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    boolean breakThisLoop = false;
                    for (int i = 0; i < profileData.size(); i++) {
                        if (selection.equals(Integer.toString((i + 1)))) {
                            String[] playerProfile = profileData.get(i);
                            System.out.println("Enter Password for Profile: " + playerProfile[0]);
                            while (true) {
                                userInputs = readUserInputs.readLine();
                                if (userInputs.equals(playerProfile[1])) {
                                    this.GameIsInitialized = true;
                                    this.currentPlayer = playerProfile[0];
                                    System.out.println("\n*************************** " + playerProfile[0].toUpperCase() +
                                            " is now playing ****************************\n");
                                    breakThisLoop = true;
                                    break; //breaks this while loop
                                } else {
                                    System.out.println("Wrong Password! Enter Passwd again.");
                                    this.GameIsInitialized = false;
                                }
                            }
                            break; //breaks this for loop
                        } else {
                            if (i == profileData.size()-1) {//check if the loop has finished without a match of section
                                System.out.println("Wrong Choice. Select a profile to start the Game:");
                            }
                            this.GameIsInitialized = false;
                        }
                    }
                    if(breakThisLoop)
                        break;
                }
            }
            readerFile.close();
            System.out.println("Select Game Mode:\n 1. EASY\n 2. MEDIUM\n 3. HARD");
            selection = readUserInputs.readLine();
            switch (selection) {
                case "1" -> gameMode = GameMode.EASY;
                case "2" -> gameMode = GameMode.MEDIUM;
                case "3" -> gameMode = GameMode.HARD;
                default -> System.out.println("Wrong Selection The Default: Easy is used");
            }
        } catch (IOException | InterruptedException e) {
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
            writer1.write(userInputs+","+readUserInputs.readLine()+"\n");
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
            if (allSoldiersAreDead(Ally)){
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
            if (allSoldiersAreDead(Enemy)) {
                System.out.println("Game Ended with all Enemy Soldiers Dead");
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
            System.out.println("########################### Initializing game... ###########################");
            this.initializeGame();
            if(GameIsInitialized){
                this.runGame();
            }
        }else{
            System.out.println("No Game Profile was found. Do want to create one(1 for YES and 0 for to Exit):");
            userInputs = readUserInputs.readLine();
            while (true){
                if(userInputs.equals("1")) {
                    this.createGameProfile();
                    if(GameIsInitialized){
                        this.runGame();
                    }
                    break;
                } else if (userInputs.equals("0")) {
                    System.out.println("Exiting.....");
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
    private boolean isFileExists(File file) {
        return file.exists() && !file.isDirectory();
    }
    public static int getMaxSoldiers() {
        return maxSoldiers;
    }
    public static int getSoldierChoice() {
        return SoldierChoice;
    }
    private void processInit() throws InterruptedException {
        StringBuilder result = new StringBuilder();
        String hex = "2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d20636f" +
                "70797269676874a9626f6e6e6965202d20626f6e66616365206f74" +
                "69656e6f202d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2da";
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String tempInHex = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(tempInHex, 16);
            result.append((char) decimal);
        }
        System.out.println(result.toString());
        Thread.sleep(1200);
    }
}
