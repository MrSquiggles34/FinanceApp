import java.lang.*;
import java.io.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Double.parseDouble;

public class FinanceApp extends Application {
    FinanceModel model;
    public FinanceApp() {
        // Get the current directory where the JAR or class file is located
        String currentDirectory = System.getProperty("user.dir");

        // Construct the path to Finance.txt in the current directory
        File dataFile = new File(currentDirectory, "Finance.txt");

        if (dataFile.exists()) {
            // Load data from the file and create a FinanceModel instance
            model = FinanceModel.loadFromFile(dataFile.getAbsolutePath());
        } else {
            // Create a new FinanceModel instance if the file doesn't exist
            model = FinanceModel.createModel();
        }
    }



    public void start(Stage primaryStage) {

        Pane aPane = new Pane();

        // Create view
        FinanceView view = new FinanceView();
        LogView log = new LogView();
        BillView bill = new BillView();

        aPane.getChildren().add(view);

        // Handle button events
        view.getHomeButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });
        view.getLogButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(model.getCurrentPage().equals("home")) {
                    view.setCurrentView("logs");
                    aPane.getChildren().add(log);
                    aPane.getChildren().remove(view);
                    log.update(model, 0);
                }
            }
        });
        // PARSE THESEEEEEEE -----------------------------------
        view.getBillButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(model.getCurrentPage().equals("home")) {
                    view.setCurrentView("logs");
                    aPane.getChildren().add(bill);
                    aPane.getChildren().remove(view);
                    view.getBillButton().setDisable(false);
                    bill.update(model, 0);
                }
            }
        });

        log.getHomeButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aPane.getChildren().add(view);
                aPane.getChildren().remove(log);
                view.update(model, 0);
            }
        });

        log.getBillButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aPane.getChildren().add(bill);
                aPane.getChildren().remove(log);
                bill.update(model, 0);
            }
        });

        log.getEnterButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(!log.getTextField1().getText().isEmpty() && !log.getTextField2().getText().isEmpty() && log.getComboBox().getValue() != null){
                    String string = String.format("%" + (-22) + "s" + "%" + (-22) + "s" + "%" + (-22) + "s" + "%" + (-12) + "s", log.getTextField1().getText(), log.getTextField2().getText(), log.getComboBox().getValue(), log.getTextField4().getText()).replace(" ", "_").replace("_", " ");
                    model.getLogsList().add(string);

                    log.update(model, 0);
                    if(log.getComboBox().getValue() == "Needs")
                    {
                        model.setBalance(model.getBalance() - parseDouble(log.getTextField2().getText()));
                        model.setNeeds(model.getNeeds() + parseDouble(log.getTextField2().getText()));
                    }

                    else if(log.getComboBox().getValue() == "Wants")
                    {
                        model.setBalance(model.getBalance() - parseDouble(log.getTextField2().getText()));
                        model.setWants(model.getWants() + parseDouble(log.getTextField2().getText()));
                    }
                    else if(log.getComboBox().getValue() == "Earned")
                    {
                        model.setBalance(model.getBalance() + parseDouble(log.getTextField2().getText()));
                        model.setEarned(model.getEarned() + parseDouble(log.getTextField2().getText()));
                    }
                    else if(log.getComboBox().getValue() == "Savings")
                    {
                        model.setSavings(model.getSavings() + parseDouble(log.getTextField2().getText()));
                        model.setBalance(model.getBalance() - parseDouble(log.getTextField2().getText()));
                    }
                    else if(log.getComboBox().getValue() == "" || log.getComboBox().getValue() == "Misc")
                    {
                        model.setMisc(model.getMisc() + parseDouble(log.getTextField2().getText()));
                        model.setBalance(model.getBalance() - parseDouble(log.getTextField2().getText()));
                    }

                    log.getTextField1().clear();
                    log.getTextField2().clear();
                    log.getTextField4().clear();
                }
            }
        });

        log.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                // Remove the selected item in the cart.
                System.out.println(log.getLogsList().getSelectionModel().getSelectedItem());
                int index = log.getLogsList().getSelectionModel().getSelectedIndex();
                log.getLogsList().getItems().removeAll(log.getLogsList().getSelectionModel().getSelectedItems());
                model.getLogsList().remove(index);
                //bill.getRemoveButton().setDisable(true);

            }
        });

        bill.getEnterButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(!bill.getTextField1().getText().isEmpty() && !bill.getTextField2().getText().isEmpty() && !bill.getTextField3().getText().isEmpty()) {
                    String string = String.format("%" + (-22) + "s" + "%" + (-22) + "s" + "%" + (-22) + "s" + "%" + (-12) + "s", bill.getTextField1().getText(), bill.getTextField2().getText(), bill.getTextField3().getText(), bill.getTextField4().getText()).replace(" ", "_").replace("_", " ");

                    model.getBillsList().add(string);
                    bill.getTextField1().clear();
                    bill.getTextField2().clear();
                    bill.getTextField3().clear();
                    bill.getTextField4().clear();
                    bill.update(model, 0);
                }
            }
        });

        bill.getHomeButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aPane.getChildren().add(view);
                aPane.getChildren().remove(bill);
                view.update(model, 0);
            }
        });

        bill.getLogButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aPane.getChildren().add(log);
                aPane.getChildren().remove(bill);
                log.update(model, 0);
            }
        });

        bill.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent actionEvent) {
               // Remove the selected item in the cart.
               System.out.println(bill.getBillsList().getSelectionModel().getSelectedItem());
               int index = bill.getBillsList().getSelectionModel().getSelectedIndex();
               bill.getBillsList().getItems().removeAll(bill.getBillsList().getSelectionModel().getSelectedItems());
               model.getBillsList().remove(index);
               //bill.getRemoveButton().setDisable(true);

           }
        });


                // Update View
        primaryStage.setTitle("Andy's Finances");
        primaryStage.setResizable(true);


        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
        view.update(model, 0);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
