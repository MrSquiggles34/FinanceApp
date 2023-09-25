import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class BillView extends Pane {

    private ListView<String> billsList;
    private int screenWidth = 960;
    private int screenHeight = 540;
    private double monthlyBills;
    private Rectangle rect, vert;
    private Label header1;
    private Button homeButton, logButton, billButton, savingsButton, enterButton, removeButton;
    private TextField textField1, textField2, textField3, textField4;
    private PieChart pieChart;
    public ListView<String> getBillsList() { return billsList; }


    public Button getEnterButton(){return enterButton;}
    public Button getHomeButton() {
        return homeButton;
    }

    public Button getLogButton() {
        return logButton;
    }

    public Button getBillButton() {
        return billButton;
    }

    public Button getSavingsButton() {
        return savingsButton;
    }
    public Button getRemoveButton(){return removeButton;}

    public TextField getTextField1() {return textField1;}
    public TextField getTextField2() {return textField2;}
    public TextField getTextField3() {return textField3;}
    public TextField getTextField4() {return textField4;}
    public BillView() {
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

        rect = new Rectangle(0, 0, screenWidth, 60);
        rect.setFill(Color.rgb(0, 172, 71));

        vert = new Rectangle(660, 0, 4, screenHeight);
        vert.setFill(Color.rgb(0, 172, 71));

        billsList = new ListView<String>();
        billsList.relocate(20, 160);
        billsList.setPrefSize(620,300);

        header1 = new Label("New BILL Entry:");
        header1.setFont(Font.font("Verdana", 12));
        header1.setTextFill(Color.GRAY);
        header1.relocate(20, 75);

        textField1 = new TextField();
        textField1.relocate(20, 100);
        textField1.setPrefSize(100, 20);
        textField1.setPromptText("Item");

        textField2 = new TextField();
        textField2.relocate(140, 100);
        textField2.setPrefSize(100, 20);
        textField2.setPromptText("Price");

        textField3 = new TextField();
        textField3.relocate(260, 100);
        textField3.setPrefSize(100, 20);
        textField3.setPromptText("Interval");

        textField4 = new TextField();
        textField4.relocate(380, 100);
        textField4.setPrefSize(100, 20);
        textField4.setPromptText("Start Date");

        enterButton = new Button("Enter");
        enterButton.relocate(540, 100);
        enterButton.setPrefSize(100, 20);

        removeButton = new Button("Remove");
        removeButton.relocate(540, 475);
        removeButton.setPrefSize(100, 20);
        //removeButton.setDisable(true);

        pieChart = new PieChart();

        pieChart.setTitle("Spending Breakdown"); // Set the title of the PieChart
        pieChart.setPrefSize(320, 320); // Set the size of the PieChart
        pieChart.relocate(650, 120);

        getChildren().addAll(rect, vert, homeButton, logButton, billButton, enterButton, removeButton, header1, textField1, textField2, textField3, textField4, billsList, pieChart);
        setPrefSize(screenWidth, screenHeight);
    }

    public void update(FinanceModel model, int selectedItem) {
        monthlyBills = model.getMonthlyBills();
        billsList.getItems().clear();
        billsList.setStyle("-fx-font: 10pt Consolas");
        System.out.println("sub");


        for (int i = model.getBillsList().size() - 1; i >= 0; i--) {
            System.out.println("added");
            billsList.getItems().add(model.getBillsList().get(i));
        }

        ObservableList<String> billsObserve = FXCollections.observableArrayList(billsList.getItems());
        billsList.setItems(billsObserve);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Needs", model.getNeeds()),
                new PieChart.Data("Wants", model.getWants()),
                new PieChart.Data("Savings", model.getSavings()),
                new PieChart.Data("Misc", model.getMisc())
        );

        pieChart.setData(pieChartData);

        double totalSpending = model.getNeeds() + model.getWants() + model.getSavings() + model.getMisc();

        for (PieChart.Data data : pieChart.getData()) {
            if (totalSpending > 0) {
                double percentage = (data.getPieValue() / totalSpending) * 100;
                data.setName(data.getName().split("\\s\\(")[0] + " (" + String.format("%.0f", percentage) + "%)");
            }
        }

        model.saveToFile("Finance.txt");
    }
}
