/*

        Brandon Blackburn
        03/27/20
        C482 - Software 1

 */

package Model;

public class OutsourcedPart extends Part{
    private String companyName;

    public String getCompanyName() {return companyName;}
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public OutsourcedPart(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        this.companyName = getCompanyName();
    }
}