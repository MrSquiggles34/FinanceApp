import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class LogView extends Pane {
    private ListView<String> logsList;
    private ComboBox comboBox;
    private PieChart pieChart;
    private int screenWidth = 960;
    private int screenHeight = 540;
    private double logs;
    private Rectangle rect, vert;
    private Label header1;
    private Button homeButton, logButton, billButton, savingsButton, enterButton, removeButton;
    private TextField textField1, textField2, textField3, textField4;
    public ListView<String> getLogsList() { return logsList; }
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
    public Button getRemoveButton(){return removeButton;}

    public Button getSavingsButton() {
        return savingsButton;
    }
    public ComboBox getComboBox(){return comboBox;}
    public TextField getTextField1() {return textField1;}
    public TextField getTextField2() {return textField2;}
    public TextField getTextField3() {return textField3;}
    public TextField getTextField4() {return textField4;}

    public LogView() {

        homeButton = new Button("HOME");
        homeButton.relocate(20, 20);
        homeButton.setPrefSize(100, 20);

        logButton = new Button("LOGS");
        logButton.relocate(160, 20);
        logButton.setPrefSize(100, 20);

        billButton = new Button("BILLS");
        billButton.relocate(300, 20);
        billButton.setPrefSize(100, 20);

        savingsButton = new Button("SAVINGS");
        savingsButton.relocate(440, 20);
        savingsButton.setPrefSize(100, 20);

        rect = new Rectangle(0, 0, screenWidth, 60);
        rect.setFill(Color.rgb(0, 172, 71));

        vert = new Rectangle(660, 0, 4, screenHeight);
        vert.setFill(Color.rgb(0, 172, 71));

        logsList = new ListView<String>();
        logsList.relocate(20, 160);
        logsList.setPrefSize(620,300);

        header1 = new Label("New PURCHASE LOG Entry:");
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

        /*
        textField3 = new TextField();
        textField3.relocate(260, 100);
        textField3.setPrefSize(100, 20);
        textField3.setPromptText("Interval");
*/

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

        comboBox = new ComboBox();
        comboBox.getItems().addAll(
                "Earned",
                "Savings",
                "Needs",
                "Wants",
                "Misc"
        );
        comboBox.relocate(260, 100);
        comboBox.setPrefSize(100, 20);

        getChildren().addAll(rect, vert, homeButton, logButton, billButton, enterButton, removeButton, textField1, textField2, textField4, header1, logsList, comboBox,pieChart);
        setPrefSize(screenWidth, screenHeight);
    }

    public void update(FinanceModel model, int selectedItem) {
        logs = model.getMonthlyLogs();
        logsList.getItems().clear();
        logsList.setStyle("-fx-font: 10pt Consolas");
        System.out.println("sub");

        for (int i = model.getLogsList().size() - 1; i >= 0; i--) {
            System.out.println("added");
            logsList.getItems().add(model.getLogsList().get(i));
        }

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

        ObservableList<String> logsObserve = FXCollections.observableArrayList(logsList.getItems());
        logsList.setItems(logsObserve);

        model.saveToFile("Finance.txt");
    }
}
