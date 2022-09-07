package com.game.controller;

import com.game.Army;
import com.game.Soldier;

import java.io.*;
import java.util.ArrayList;

public class WarGameWorld {
    public static Integer maxSoldiers = 10;
    public static int SoldierChoice;
    public static Army Ally;
    public static Army Enemy;
    private boolean GameIsInitialized = false;
    public static boolean GameIsTerminated = false;
    private String currentPlayer;
    public static GameMode gameMode = GameMode.EASY; //this is the default
    private final File dataFile = new File("game_data.data");
    private final File profileFile = new File("game_profile.data");
    private final InputStreamReader inputReader = new InputStreamReader(System.in);
    private final BufferedReader readUserInputs = new BufferedReader(inputReader);
    private OutputStreamWriter outputWriterFile;
    private InputStreamReader inputReaderFile;
    private BufferedWriter dataFileWriter;
    private BufferedReader dataFileReader;
    public WarGameWorld() {
        try {
            this.inputReaderFile = new InputStreamReader(new FileInputStream(this.dataFile));
            this.outputWriterFile = new OutputStreamWriter(new FileOutputStream(this.dataFile, true));
            this.dataFileWriter = new BufferedWriter(this.outputWriterFile);
            this.dataFileReader = new BufferedReader(this.inputReaderFile);
            if(this.dataFile.length()==0){
                this.dataFileWriter.write("");
            }
        } catch (IOException e) {}
    }
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
        GenerateSoldierChoice t0 = new GenerateSoldierChoice();
        GunThread t1 = new GunThread();
        TankThread t2 = new TankThread();
        Thread GameThread= new Thread(new Runnable() {
            @Override
            public void run() {
                runGame();
            }
        });
        t0.start();
        t1.start();
        t2.start();
        GameThread.start();
        try {
            t1.join();
            t2.join();
            t0.join();
            GameThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    private void initializeGame(){
        ArrayList<String[]> profileData = new ArrayList<>(); //this 2D array to hold profileName and Password from the file
        String userInputs = "";
        String selection = "";
        try {
            InputStreamReader inputReaderFile = new InputStreamReader(new FileInputStream(profileFile));
            BufferedReader readerFile = new BufferedReader(inputReaderFile);
            this.processInit();
            while (readerFile.ready()){
                profileData.add(readerFile.readLine().split("[,]"));
            }
            System.out.println("\n Choose a profile to start the Game:");
            boolean breakThisLoop = false;
            while(true) {
                for (int j=0; j<profileData.size(); j++) {
                    System.out.println(" "+(j+1)+". "+profileData.get(j)[0]);
                }
                System.out.println(" *. Add New Profile\n #. Exit");
                selection = readUserInputs.readLine();

                if (selection.equals("*")) {
                    this.createGameProfile();
                    break;
                }
                else if (selection.equals("#")) {
                    System.out.println("Exiting...");
                    break;
                }
                else {
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
                                            " is now Playing ****************************\n");
                                    this.selectGameMode();
                                    this.inputMaxSoldiers();
                                    this.setupGame();
                                    System.out.println("\n*************************** Enjoy Your Game ****************************\n");
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
                }
                if(breakThisLoop)
                    break;
            }
        readerFile.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private  void inputMaxSoldiers() throws IOException {
        System.out.println("Enter Max Number of Soldiers:");
        maxSoldiers = Integer.parseInt(readUserInputs.readLine());
    }
    private void selectGameMode() throws IOException {
        System.out.println("Select Game Mode:\n 1. EASY\n 2. MEDIUM\n 3. HARD");
        String selection = readUserInputs.readLine();
        switch (selection) {
            case "1" -> gameMode = GameMode.EASY;
            case "2" -> gameMode = GameMode.MEDIUM;
            case "3" -> gameMode = GameMode.HARD;
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
                System.out.println("########################### Initializing game... ###########################");
                this.initializeGame();
            }else
                System.out.println("Exiting...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void runGame(){
        while (true) {
            if (allSoldiersAreDead(Ally)){
                GameIsTerminated = true;
                System.out.println("Game Ended with all Ally Soldiers Dead");
                break;
            }
            if (allSoldiersAreDead(Enemy)) {
                GameIsTerminated = true;
                System.out.println("Game Ended with all Enemy Soldiers Dead");
                break;
            }
            if (noWeaponHasBullets(Ally) && noWeaponHasBullets(Enemy)){
                System.out.println("Bullets are over. Exiting...");
                break;
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
                this.GameThreadHandler();
            }
        }else{
            System.out.println("No Game Profile was found. Do want to create one(1 for YES and 0 for to Exit):");
            userInputs = readUserInputs.readLine();
            while (true){
                if(userInputs.equals("1")) {
                    this.createGameProfile();
                    if(GameIsInitialized){
                        this.GameThreadHandler();
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
        Thread.sleep(700);
    }
}
