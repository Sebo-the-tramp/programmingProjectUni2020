package sample.pages;

//import libraries
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

//import classes
import sample.Main;
import sample.functions.FunctionDefinition;


public class CenterColumn extends VBox{

    private Label title = new Label("<--Select from there");
    private Label name = new Label("");
    private Label description = new Label("");

    private HBox buttonBox = new HBox();
    private Button clear = new Button("clear");
    private Button compute = new Button("compute");
    private VBox inputGroup = new VBox();
    private RightColumn rightColumn;
    private String nameFunction;

    ArrayList<FunctionDefinition> funcDefList;
    ArrayList<Integer> inputValues = new ArrayList<>();

    public CenterColumn(Stage primaryStage, ArrayList<FunctionDefinition> _funcDefList, RightColumn _rightColumn){
        //assigning the list of function to a local variable
        funcDefList = _funcDefList;
        rightColumn = _rightColumn;

        //setting some style to the vBOX
        this.setStyle("-fx-background-color: #2b2b2b");
        this.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        this.prefHeightProperty().bind(primaryStage.heightProperty().multiply(1));
        this.setSpacing(20);

        //modifying the title
        title.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        title.setAlignment(Pos.CENTER);
        title.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.10));
        title.setTextFill(Color.WHITE);
        title.setFont(new Font(24));

        //modifying the name
        name.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        name.setAlignment(Pos.CENTER);
        name.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.10));
        name.setTextFill(Color.WHITE);
        name.setFont(new Font(20));

        //modifying the description
        description.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        description.setAlignment(Pos.CENTER);
        description.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.10));
        description.setTextFill(Color.WHITE);
        description.setFont(new Font(14));
        description.setWrapText(true);

        //setting property to buttons
        clear.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.15));
        clear.setOnAction(this::clearInputs);
        clear.setVisible(false);

        //setting property to buttons
        compute.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.15));
        compute.setOnAction(this::checkInputs);
        compute.setVisible(false);

        //adding the buttons to a HBOX container
        buttonBox.getChildren().addAll(clear,compute);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setSpacing(15);this.setSpacing(15);

        this.getChildren().addAll(title, name, description, inputGroup, buttonBox);

    }


    //change dynamically the number and description of the inputs based on the function chosen
    protected void changeSelection(ActionEvent actionEvent){

        //only needed the first time
        clear.setVisible(true);
        compute.setVisible(true);

        //delete all inputs
        inputGroup.getChildren().clear();

        //get the event and cast it to the highest sub-class object needed
        Node eventNode = (Node)actionEvent.getSource();

        //find the function that has been called
        FunctionDefinition function = FunctionDefinition.findFunction(eventNode.getId());

        //make the graph appear or disappear
        rightColumn.showGraph(function.getId());

        //get the id of the function
        nameFunction = eventNode.getId();

        name.setText(function.getName());
        description.setText(function.getDescription());

        //setting the title
        if(function.getNInput().length > 1){
            title.setText("Fill the inputs");
        }else{
            title.setText("Fill the input");
        }

        for (int i = 0; i < 4; i++){

        }

        //generate n inputs as many as requested by the function
        for(int i = 0; i < 4; i++){
            Label label = new Label("");
            label.setTextFill(Color.WHITE);

            VBox col1 = new VBox();
            col1.setId("col_input_1");
            VBox col2 = new VBox();

            TextField textField = new TextField();
            textField.setId("textField_" + i);
            textField.setVisible(false);

            if(function.getNInput().length > i){
                label.setId((function.getNInput()[i]) + "");
                textField.setVisible(true);
                
                if(function.getNInput()[i] == -1){
                	label.setText(function.getLabel()[i] + " >= 0");
                }else if(function.getNInput()[i] == -2){
                    label.setText(function.getLabel()[i] + " can not be 0");
                }else{
                	label.setText(function.getLabel()[i] + " > " + function.getNInput()[i]);
                }
            }

            col1.getChildren().add(textField);
            col2.getChildren().add(label);

            HBox container = new HBox();
            container.getChildren().addAll(col1,col2);
            container.setPadding(new Insets(10,0,10,0));
            container.setSpacing(10);

            //change graph id and title in order to understand which function to plot
            RightColumn.setGraph(nameFunction, function.getName());

            //add the HBOX to the list
            inputGroup.getChildren().addAll(container);
        }

        //setting the id of the button to know which function to perform
        compute.setId(function.getId());

    }


    //##################OTHER METHODS#######################

    //clear all the inputs
    private void clearInputs(ActionEvent actionEvent) {

        //clearing every input
        //looping through the HBOX (into the Vbox) components to get to the input text fields
        for (Node a: inputGroup.getChildren()) {

            //looping trough the columns
            for (Node b: ((HBox) a).getChildren() ){
                VBox column = (VBox) b;

                //if the column correspond to the input ones:
                if(column.getId() == "col_input_1"){
                    for (Node c: column.getChildren()) {
                        TextField textField = (TextField) c;
                        textField.setText("");
                    }
                }
            }
        }
    }


    private void checkInputs(ActionEvent actionEvent){

        
        //every time this function is called the values must be resetted
        inputValues.clear();

        boolean checked = true;

        for (Node a : inputGroup.getChildren()) {

            List container = ((HBox) a).getChildren();

            VBox vbox_0 = (VBox) container.get(0);
            VBox vbox_1 = (VBox) container.get(1);

            TextField textField = (TextField) vbox_0.getChildren().get(0);
            Label label = (Label) vbox_1.getChildren().get(0);

            //if the input is visible check the input
            if(textField.isVisible()){
                if(checked && Main.checkInput(textField.getText(), Integer.parseInt(label.getId()))){
                    int input = Integer.parseInt(textField.getText());
                    inputValues.add(input);
                }else{
                    checked = false;
                }
            }
        }

        if(checked){
            //ok calling
            rightColumn.computeFunction(nameFunction, inputValues, true);
        }

    }
}
