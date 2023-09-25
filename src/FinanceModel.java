import javafx.scene.chart.PieChart;

import java.io.Serializable;
import java.util.*;
import java.io.*;

public class FinanceModel implements Serializable {
    private double balance, savings, prevWeek, prevMonth, monthlyBills, monthlyLogs, needs, wants, earned, misc;
    private String currentPage = "home";
    private String thanNormal;
    private ArrayList<String> billsList, logsList;
    private ArrayList<PieChart.Data> pieChartData;
    public FinanceModel(String initName) {
        balance = 0.0;
        savings = 0.0;
        prevWeek = 0.0;
        prevMonth = 0.0;
        monthlyBills = 0.0;
        monthlyLogs = 0.0;
        needs = 0.0;
        wants = 0.0;
        earned = 0.0;
        misc = 0.0;

        thanNormal = "less";
        currentPage = "home";
        billsList = new ArrayList<>();
        logsList = new ArrayList<>();
        pieChartData = new ArrayList<>();
    }

    public ArrayList<String> getBillsList(){return billsList;}
    public ArrayList<String> getLogsList(){return logsList;}
    public double getBalance(){return balance;}
    public void setBalance(double newBalance){this.balance = newBalance;}
    public double getSavings(){return savings;}
    public void setSavings(double newSavings){this.savings = newSavings;}
    public double getPrevMonth() {return prevMonth;}
    public double getPrevWeek() {return prevWeek;}
    public double getMonthlyBills() {return monthlyBills;}
    public double getMonthlyLogs() {return monthlyLogs;}
    public void setMonthlyBills(double monthlyBills){this.monthlyBills = monthlyBills;}
    public String getThanNormal() {return thanNormal;}
    public String getCurrentPage(){return currentPage;}
    public double getNeeds(){return needs;}
    public void setNeeds(double newNeeds){this.needs = newNeeds;}
    public double getWants(){return wants;}
    public void setWants(double newWants){this.wants = newWants;}
    public double getEarned(){return earned;}
    public void setEarned(double newEarned){this.earned = newEarned;}
    public double getMisc(){return misc;}
    public void setMisc(double newMisc){this.misc = newMisc;}
    public static FinanceModel createModel(){
        FinanceModel model1 = new FinanceModel("Andy's finances");
        return model1;
    }

    public boolean saveToFile(String filename) {
        try {
            ObjectOutputStream outFile;

            // Construct the full path to the file in the current directory
            String fullPath = System.getProperty("user.dir") + File.separator + filename;

            // Open file
            outFile = new ObjectOutputStream(new FileOutputStream(fullPath));

            // Write object
            outFile.writeObject(this);

            // Close file
            outFile.close();

            return true;
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot open file for writing");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.err.println("Error: Cannot write to file");
            e.printStackTrace();
            return false;
        }
    }


    public static FinanceModel loadFromFile(String filename)
    {
        try {
            FinanceModel newElectronicStore;
            ObjectInputStream inFile;

            //open file
            inFile = new ObjectInputStream(new FileInputStream(filename));

            //read object

            newElectronicStore = (FinanceModel) inFile.readObject();

            //close file
            inFile.close();
            return newElectronicStore;
        } catch (ClassNotFoundException e) { //additional exception to handle
            System.out.println("Error: Object's class does not match");
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot open file for writing");
            return null;
        } catch (IOException e) {
            System.out.println("Error: Cannot read from file");
            return null;
        }
    }
}
