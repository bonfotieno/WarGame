package com.game.jet;

import java.util.Scanner;

public class Jet {
    private String jetType; // heavy fighter||fighter bomber||Interceptor
    private int missiles;
    private int maxMissiles;
    private int bullets;
    private int maxBullets;
    private int missileIntercepts;
    private int maxMissileIntercept;
    public Jet() {
        this.setJetType("heavy fighter");
        this.setMaxMissiles(500);
        this.setMaxBullets(500);
        this.setMaxMissileIntercept(500);
        reloadMissiles();
        reloadBullets();
        reloadMissileIntercept();
    }
    public void reloadBullets() {
        bullets = maxBullets;
    }
    public void reloadMissiles() {
        missiles = maxMissiles;
    }
    public void reloadMissileIntercept() {missileIntercepts = maxMissileIntercept;}
    public void chooseJetType(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose Jet Type\n 1 - heavy fighter, 2 - fighter bomber, 3 - Interceptor");
        int jetChoice = scan.nextInt();
        if (jetChoice==1) {
            this.setJetType("heavy fighter");
        }
        else if (jetChoice==2) {
            this.setJetType("fighter bomber");
        }
        else if (jetChoice==3){
            this.setJetType("Interceptor");
        }
    }
    public void fire() throws Exception {
        if (bullets > 0)
        {
            switch (this.jetType) {
                case "heavy fighter": {
                    bullets -= 10;
                    System.out.println("\t>>");
                    break;
                }
                case "fighter bomber": {
                    missiles--;
                    System.out.println("\t>>>");
                    break;
                }
                case "Interceptor": {
                    missileIntercepts--;
                    System.out.println("\t<<");
                    break;
                }
            }
        }else
            throw new Exception("The Jet ran out of bullets");
    }
    public void setJetType(String jetType) {
        this.jetType = jetType;
    }
    public void setMaxMissiles(int maxMissiles) {
        this.maxMissiles = maxMissiles;
    }
    public void setMaxMissileIntercept(int maxMissileIntercept) {
        this.maxMissileIntercept = maxMissileIntercept;
    }
    public void setMaxBullets(int maxBullets) {
        this.maxBullets = maxBullets;
    }
    public String getJetType() {
        return jetType;
    }
    public int getMissiles() {
        return missiles;
    }
    public void setMissiles(int missiles) {
        this.missiles = missiles;
    }
    public int getMaxMissiles() {
        return maxMissiles;
    }
    public int getBullets() {
        return bullets;
    }
    public int getMaxBullets() {
        return maxBullets;
    }
    public int getMissileIntercepts() {
        return missileIntercepts;
    }
    public int getMaxMissileIntercept() {
        return maxMissileIntercept;
    }
}

//public void fly(){System.out.println("The fighter jet is up and flying!!!");}