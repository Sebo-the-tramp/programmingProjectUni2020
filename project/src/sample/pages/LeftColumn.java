package sample.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;

//import classes
import sample.functions.FunctionDefinition;

public class LeftColumn extends VBox{

    CenterColumn centerColumn;
    ArrayList<Button> arrayBtn = new ArrayList<Button>();

    ArrayList<FunctionDefinition> funcDefList;
    ArrayList<String> historyList;

    Label title, historyTitle;

    Button showHistory;


    public LeftColumn(Stage primaryStage, ArrayList<FunctionDefinition> _funcDefList, ArrayList<String> _historyList, CenterColumn centerColumn, RightColumn rightColumn){
        funcDefList = _funcDefList;
        historyList = _historyList;

        this.setStyle("-fx-background-color: #2b2b2b");
        this.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        this.prefHeightProperty().bind(primaryStage.heightProperty().multiply(1));
        this.setSpacing(20);


        //right column to compute the saved history from here
        title = new Label("Select a function");
        title.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        title.setAlignment(Pos.CENTER);
        title.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.10));
        title.setTextFill(Color.WHITE);
        title.setFont(new Font(24));

        //setAlignment(Pos.CENTER);
        ToggleGroup toggleGroupButton = new ToggleGroup();
        VBox buttonsList = new VBox();
        buttonsList.setAlignment(Pos.CENTER);
        buttonsList.setSpacing(15);

        //create a button for each function
        for (FunctionDefinition element: funcDefList) {
            ToggleButton btn = new ToggleButton(element.getName());
            btn.setId(element.getId());
            btn.setOnAction(centerColumn::changeSelection);
            btn.setMinHeight(30);
            btn.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
            btn.setAlignment(Pos.CENTER);
            buttonsList.getChildren().add(btn);
            btn.setToggleGroup(toggleGroupButton);
        }

        historyTitle = new Label("History");
        historyTitle.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        historyTitle.setAlignment(Pos.CENTER);
        historyTitle.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.10));
        historyTitle.setTextFill(Color.WHITE);
        historyTitle.setFont(new Font(24));

        //making a good division line between the two main sections
        HBox line = new HBox();
        line.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));
        line.setMinHeight(2);
        line.setMaxWidth(280);
        line.setStyle("-fx-background-color: white");
        line.setAlignment(Pos.CENTER);

        //centering the line
        VBox lineContainer = new VBox();
        lineContainer.setAlignment(Pos.CENTER);
        lineContainer.getChildren().addAll(line);

        //creating the button to open the history window
        showHistory = new Button("Show history");
        showHistory.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));
        showHistory.setOnAction(this::openHistoryWindow);

        //adding a VBox container
        VBox historyContainer = new VBox();
        historyContainer.setAlignment(Pos.CENTER);
        historyContainer.getChildren().add(showHistory);

        getChildren().addAll(title, buttonsList, lineContainer, historyTitle, historyContainer);

    }

    //######################--OTHER WINDOW--############################

    //create a new window with inside the history of functions
    private void openHistoryWindow(ActionEvent actionEvent) {

        StackPane root = new StackPane();
        VBox historyBox = new VBox();

        ObservableList data = FXCollections.observableArrayList();
        ListView functionsList = new ListView(data);

        historyBox.maxWidth(600);

        if(historyList.size() != 0){
            for (String s: historyList) {

                Label funcDescription = new Label(s);
                funcDescription.setAlignment(Pos.CENTER);
                funcDescription.setFont(new Font(20));
                funcDescription.setTextFill(Color.WHITE);
                funcDescription.setWrapText(true);
                funcDescription.setMaxWidth(600);

                HBox listItem = new HBox();
                listItem.maxWidth(550);
                listItem.getChildren().add(funcDescription);
                listItem.setPadding(new Insets(10,0,10,0));
                listItem.setStyle("-fx-background-color: #2b2b2b");
                data.add(s);
            }
        }else{
            Label funcDescription = new Label("No operation has been computed");
            funcDescription.setFont(new Font(20));
            data.add("No operation has been computed");
        }


        root.getChildren().add(functionsList);

        Stage stage = new Stage();
        stage.setTitle("Operation's history");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 600, 500, Color.web("#2b2b2b")));
        stage.show();
    }

}
