package com.challenge;

public class Tank {
    private String tankNumber;
    private String model;
    private int shells;
    private int maxShells;

    public Tank(String tankNumber, int maxShells) {
        this.tankNumber = tankNumber;
        this.model = "canon";
        this.shells = maxShells;
        reloadShells();
    }

    public void reloadShells() {
        shells = maxShells;
    }

    public void shootShells() {
        if (shells > 0)
        {
            if (this.model == "canon"){
                shells --;
                System.out.println("-");
            }
            else {
                shells -= 5;
                System.out.println("--");
            }
        }
    }
    public String getTankNumber() {
        return tankNumber;
    }

    public void setTankNumber(String tankNumber) {
        this.tankNumber = tankNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getShells() {
        return shells;
    }

    public void setShells(int shells) {
        this.shells = shells;
    }

    public int getMaxShells() {
        return maxShells;
    }

    public void setMaxShells(int maxShells) {
        this.maxShells = maxShells;
    }
}
