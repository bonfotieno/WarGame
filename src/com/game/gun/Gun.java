package com.game.gun;

public class Gun {
    int bullets;
    int maxBullets;
    char shootingMode; // S for single, A for automatic 5 bullets (per shot)
    public void reloadBullets() {
        bullets = maxBullets;
    }

    public char getShootingMode() {
        return shootingMode;
    }

    public void setShootingMode(char shootingMode) {
        this.shootingMode = shootingMode;
    }

    public void changeShootingMode() {
        if (shootingMode == 'S')
            this.setShootingMode('A');
        else
            this.setShootingMode('S');
    }

    public Gun() {
        this.shootingMode = 'S';
        this.maxBullets = 10;
        reloadBullets();
    }
    public void shootBullets() throws Exception {
        if (bullets > 0)
        {
            if (shootingMode == 'S'){
                bullets --;
                System.out.println("\t"+"-");
            }
            else {
                bullets -= 5;
                System.out.println("-----");
            }
        }else
            throw new Exception("bullets are depleted");
    }
    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    public int getMaxBullets() {
        return maxBullets;
    }

    public void setMaxBullets(int maxBullets) {
        this.maxBullets = maxBullets;
    }
}
