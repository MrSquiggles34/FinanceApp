import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

import static java.lang.Math.round;


public class FinanceView extends Pane{


    private String currentView = "home";
    private int screenWidth = 960;
    private int screenHeight = 540;
    private double bal, sav, prevWeek, prevMonth, needs, wants, savings, misc;
    private String thanNormal = "LESS" ;

    // Define components
    private Label header1, header2, header3, header4, header5, header6, header7;
    private Rectangle rect, vert;
    private Button homeButton, logButton, billButton, savingsButton;
    private PieChart pieChart;
    public String getCurrentView(){return currentView;}


    public void setCurrentView(String newView){this.currentView = newView;}
    public Button getHomeButton(){return homeButton;}
    public Button getLogButton() {return logButton;}
    public Button getBillButton() {return billButton;}
    public Button getSavingsButton() {return savingsButton;}


    public FinanceView(){

        // Create Label components
        header1 = new Label("Welcome Back, Andy!");
        header1.setFont(Font.font("Merryweather", FontWeight.BOLD, FontPosture.ITALIC, 60));
        header1.relocate(20, 60);

        header2 = new Label("Balance:               $" + bal);
        header2.setFont(Font.font("Verdana", 32));
        header2.setTextFill(Color.GRAY);
        header2.relocate(140, 250);

        header3 = new Label("Savings:               $" + sav);
        header3.setFont(Font.font("Verdana", 32));
        header3.setTextFill(Color.GRAY);
        header3.relocate(140, 300);

//        header4 = new Label("You spent $" + prevWeek + " last week");
//        header4.setFont(Font.font("Verdana", 16));
//        header4.setTextFill(Color.GRAY);
//        header4.relocate(200, 480);
//
//        header5 = new Label("You spent $" + prevMonth + " last month");
//        header5.setFont(Font.font("Verdana", 24));
//        header5.relocate(160, 500);

//        header6 = new Label("You are spending ");
//        header6.setFont(Font.font("Verdana", 16));
//        header6.relocate(740, 80);

//        header7 = new Label( thanNormal + " THAN NORMAL");
//        header7.setFont(Font.font("Verdana", 26));
//        header7.relocate(680, 95);


        rect = new Rectangle(0, 0, screenWidth, 60);
        rect.setFill(Color.rgb(0, 172, 71));

        vert = new Rectangle(660, 0, 4, screenHeight);
        vert.setFill(Color.rgb(0, 172, 71));

        // Create Buttons
        homeButton = new Button("HOME");
        homeButton.relocate(20, 20);
        homeButton.setPrefSize(100, 20);

        logButton = new Button("LOGS");
        logButton.relocate(160, 20);
        logButton.setPrefSize(100, 20);

        billButton = new Button("BILLS");
        billButton.relocate(300, 20);
        billButton.setPrefSize(100, 20);

//        savingsButton = new Button("SAVINGS");
//        savingsButton.relocate(440, 20);
//        savingsButton.setPrefSize(100, 20);

        pieChart = new PieChart();

        pieChart.setTitle("Spending Breakdown"); // Set the title of the PieChart
        pieChart.setPrefSize(320, 320); // Set the size of the PieChart
        pieChart.relocate(650, 120);
        getChildren().add(pieChart);




        getChildren().addAll(rect, header1, header2, header3, homeButton, logButton, billButton,vert);
        setPrefSize(screenWidth, screenHeight);
    }

    public void update(FinanceModel model, int selectedItem)
    {
        bal = (double) round(model.getBalance() * 100) /100;



        needs = model.getNeeds();
        wants = model.getWants();
        savings = model.getSavings();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Needs", needs),
                new PieChart.Data("Wants", wants),
                new PieChart.Data("Savings", savings),
                new PieChart.Data("Misc", misc)
        );

        pieChart.setData(pieChartData);

        double totalSpending = needs + wants + savings + misc;

        for (PieChart.Data data : pieChart.getData()) {
            if (totalSpending > 0) {
                double percentage = (data.getPieValue() / totalSpending) * 100;
                data.setName(data.getName().split("\\s\\(")[0] + " (" + String.format("%.0f", percentage) + "%)");
            }
        }

        header2.setText("Balance:               $" + bal);
        sav = (double) round(model.getSavings() * 100) /100;
        header3.setText("Savings:               $" + sav);
        prevWeek = model.getPrevWeek();
        prevMonth = model.getPrevMonth();
        thanNormal = model.getThanNormal();
    }
}

