package com.game.tank;

enum TankModel{
    CANON,
    LONGBOW
}
public class Tank implements InterfaceTank {
    private String tankNumber;
    private TankModel model;
    private int shells;
    private int maxShells;

    public Tank(String tankNumber, int maxShells) {
        this.tankNumber = tankNumber;
        this.model = TankModel.CANON;
        this.maxShells = maxShells;
        reloadShells();
    }
    public void changeTankModel() {
        if (this.model.equals(TankModel.CANON))
            this.setModel(TankModel.LONGBOW);
        else
            this.setModel(TankModel.CANON);
    }
    public void reloadShells() {
        shells = maxShells;
    }
    public void shootShells() throws Exception {
        if (shells > 0)
        {
            if (this.model.equals(TankModel.CANON)){
                shells --;
                System.out.println("\t^");
            }
            else {
                shells -= 5;
                System.out.println("\t^^^");
            }
        }else
            throw new Exception("The Jet ran out of Shells");
    }
    public String getTankNumber() {
        return tankNumber;
    }

    public void setTankNumber(String tankNumber) {
        this.tankNumber = tankNumber;
    }

    public TankModel getModel() {
        return model;
    }

    public void setModel(TankModel model) {
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
