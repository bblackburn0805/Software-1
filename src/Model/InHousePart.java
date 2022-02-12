/*

        Brandon Blackburn
        03/27/20
        C482 - Software 1

 */


package Model;


public class InHousePart extends Part{
    private int machineId;

    public void setMachineID(int machineID){this.machineId = machineID;}
    public int getMachineID(){return machineId;}


    public InHousePart(int id, String name, double price, int stock, int min, int max){
        super(id, name, price, stock, min, max);
        this.machineId = getMachineID();
    }

}
