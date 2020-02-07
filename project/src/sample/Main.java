package sample;


//importing libraries
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;

//importing classes

import sample.functions.FunctionDefinition;
import sample.pages.CenterColumn;
import sample.pages.LeftColumn;
import sample.pages.RightColumn;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group root = new Group();

        ArrayList<String> history = new ArrayList<>();

        //initializing the list
        FunctionDefinition.createList();

        RightColumn rightColumn = new RightColumn(primaryStage, history);
        CenterColumn centerColumn = new CenterColumn(primaryStage, FunctionDefinition.getFunctionList(), rightColumn);
        LeftColumn leftColumn = new LeftColumn(primaryStage, FunctionDefinition.getFunctionList(), history, centerColumn, rightColumn);

        HBox group = new HBox();
        group.getChildren().addAll(leftColumn, centerColumn, rightColumn);

        root.getChildren().add(group);
        root.getStylesheets().add(Main.class.getResource("lineChart.css").toExternalForm());

        primaryStage.setTitle("fx calculator");
        primaryStage.setScene(new Scene(root, 1400, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    //#####################--METHODS USED IN ALL PROGRAM--##################


    public static boolean checkInput(String text, int constraint){
        boolean checked = true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error in the inputs");

        if(!text.isEmpty()){
            if(isInteger(text)){
                int input = Integer.parseInt(text);

                if(constraint != -2) {
                	if(input < constraint){
                        alert.setContentText("The input does not satisfies the constraints");
                        checked = false;
                    }
                }else {
                	if(input == 0) {
                		
                		alert.setContentText("The input does not satisfies the constraints");
                        checked = false;
                	}
                }
                
            }else{
                checked = false;
                alert.setContentText("The input is not an integer");
            }
        }else{
            checked = false;
            alert.setContentText("The input is empty");
        }


        if(!checked){
            alert.show();
        }

        return checked;
    }

    //checking if the input is an integer or not
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
