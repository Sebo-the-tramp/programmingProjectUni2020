package sample.pages;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Main;
import sample.functions.FunctionDefinition;
import sample.functions.Functions;

import java.util.ArrayList;
import java.util.Arrays;


public class RightColumn extends VBox{

    //creating the input field
    private TextField inputValue = new TextField();

    //label result
    private TextArea result = new TextArea("");
    private ArrayList<String> history;

    //plotterGraph
    private VBox plotterContainer = new VBox();
    private VBox resultContainer = new VBox();

    //chart variable
    final static NumberAxis xAxis = new NumberAxis();
    final static NumberAxis yAxis = new NumberAxis();
    final static LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);


    public RightColumn(Stage primaryStage,  ArrayList<String> _history){
        this.history = _history;

        this.setStyle("-fx-background-color: #2b2b2b");
        this.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.40));
        this.prefHeightProperty().bind(primaryStage.heightProperty().multiply(1));
        this.setSpacing(20);

        //label title
        Label resultLabel = new Label("Result:");
        resultLabel.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        resultLabel.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.10));
        resultLabel.setTextFill(Color.WHITE);
        resultLabel.setFont(new Font(23));
        resultLabel.setAlignment(Pos.CENTER);

        //TextArea result:
        result.setFont(new Font(20));
        result.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));
        result.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.10));
        result.setWrapText(true);
        result.setEditable(false);
        result.setMaxWidth(400);

        //making a good division line between the two main sections
        HBox line = new HBox();
        line.setMinHeight(2);
        line.setMaxWidth(380);
        line.setStyle("-fx-background-color: white");
        line.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.30));
        line.setAlignment(Pos.TOP_CENTER);

        resultContainer.setAlignment(Pos.CENTER);
        resultContainer.getChildren().addAll(resultLabel,result);
        resultContainer.setVisible(false);
        resultContainer.setAlignment(Pos.CENTER);
        resultContainer.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25));

        //creating the button for plotting the graph
        Button plotter = new Button("Plot");
        plotter.setAlignment(Pos.CENTER);
        plotter.setOnAction(this::createGraph);
        plotter.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.15));

        //description of input label
        Label descriptionInputValue = new Label("Numbers of points to be plotted on the graph");
        descriptionInputValue.setTextFill(Color.WHITE);
        descriptionInputValue.setPadding(new Insets(0,0,0,20));

        //creating a box that contains input and description
        HBox inputContainer = new HBox();
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.getChildren().addAll(inputValue, descriptionInputValue);
        inputContainer.setPadding(new Insets(0,0,10,0));

        //creating the VBox for the graph, inputs and button
        plotterContainer.getChildren().addAll(lineChart, inputContainer, plotter);
        plotterContainer.setAlignment(Pos.CENTER);
        plotterContainer.setVisible(false);
        plotterContainer.setSpacing(10);

        this.getChildren().addAll(resultContainer, plotterContainer);

    }

    private void createGraph(ActionEvent actionEvent) {

        FunctionDefinition func = FunctionDefinition.findFunction(lineChart.getId());

        if (func.isHasGraph()) {
            plotterContainer.setVisible(true);
            if (Main.checkInput(inputValue.getText(), 0)) {

                int input = Integer.parseInt(inputValue.getText());

                ArrayList<Integer> arrayInputs = new ArrayList<>();
                arrayInputs.add(input);
                computeFunction(lineChart.getId(), arrayInputs, false);
            }

        }else{
            plotterContainer.setVisible(false);
        }

    }

    public static void setGraph(String nameFunction, String title){
        lineChart.setId(nameFunction);
        lineChart.setTitle(title);
    }

    public void computeFunction(String nameFunction, ArrayList<Integer> inputs, boolean updateInput) {

        //show the result
        resultContainer.setVisible(true);

        ArrayList<Integer> results = new ArrayList<>();

        lineChart.setId(nameFunction);
        lineChart.autosize();
        lineChart.getData().clear();
        lineChart.setLegendVisible(false);


        switch (nameFunction)
        {
            case "fx_func_1":
                results = Functions.func_1(inputs.get(0));
                break;
            case "fx_func_2":
                results.add(Functions.func_2(inputs.get(0), inputs.get(1)));
                break;
            case "fx_func_3":
                //adding the result to the proper variable
                results.add(Functions.func_3(inputs.get(0)));
                //creating the chart
                lineChart.setTitle("Number of prime numbers");
                XYChart.Series seriesFunc3 = new XYChart.Series();
                for(int i = 1; i <= inputs.get(0); i++){
                    seriesFunc3.getData().add(new XYChart.Data(i, Functions.func_3(i)));
                }
                lineChart.getData().add(seriesFunc3);

                break;
            case "fx_func_4":
                results.add(Functions.func_4(inputs.get(0)));

                //creating the chart
                lineChart.setTitle("Sieve of Eratosthenes");
                XYChart.Series seriesFunc4 = new XYChart.Series();
                for(int i = 1; i <= inputs.get(0); i++){
                    seriesFunc4.getData().add(new XYChart.Data(i, Functions.func_4(i)));
                }
                lineChart.getData().add(seriesFunc4);

                break;
            case "fx_func_5":
                results = Functions.func_5(inputs.get(0));
                break;
            case "fx_func_6":
                results.add(Functions.func_6(inputs.get(0), inputs.get(1)));
                break;
            case "fx_func_7":
                results = Functions.func_7(inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3));
                break;
            case "fx_func_8":
                //using the approximative approach if the input is too large

                XYChart.Series seriesFunc8 = new XYChart.Series();
                lineChart.setTitle("Partition function");
                if(inputs.get(0) > 60){
                    results.add(Functions.func_8(inputs.get(0)));

                    for(int i = 1; i <= inputs.get(0); i++){
                        seriesFunc8.getData().add(new XYChart.Data(i, Functions.func_8(i)));
                    }

                }//using the recursive approach until the input is too big
                else{
                    results.add(Functions.func_8_b(inputs.get(0),inputs.get(0)));

                    for(int i = 1; i < inputs.get(0); i++){
                        seriesFunc8.getData().add(new XYChart.Data(i, Functions.func_8_b(i,i)));
                    }
                }
                lineChart.getData().add(seriesFunc8);

                break;
        }

        //if the result must be updated otherwise only the graph will change
        if(updateInput) {
            //showing the result into the apposite space
            Object[] arrayResults = results.toArray();
            String resultString = Arrays.toString(arrayResults);
            if (arrayResults.length != 1) {
                result.setText(resultString);
            } else {
                result.setText(resultString.substring(1, resultString.length() - 1));
            }

            //creating the string to be added to the history list
            Object[] arrayInputs = inputs.toArray();
            String stringHistory = "(" + FunctionDefinition.getFunctionNameFromId(nameFunction) + "); input -->  " + Arrays.toString(arrayInputs) + "; result --> " + Arrays.toString(arrayResults);
            history.add(stringHistory);
        }
    }

    public void showGraph(String nameFunction){
        //setting visible if the function has a graph
        result.setText("");
        plotterContainer.setVisible(FunctionDefinition.findFunction(nameFunction).isHasGraph());
    }
}
