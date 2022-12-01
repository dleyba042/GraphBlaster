package main;

import ds.MazeGraph;
import gui.MyLine;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tester extends Application
{

    public static final int MAX_CHOICES = 300;
    public static final int START_DIM_OF_WINDOW = 660;
    Stage window;
    private static final int WINDOW_DEFAULT_WIDTH = 750;
    private static final int START_DIMS_OF_GRAPH = 12;

    private static final ComboBox<Integer> COMBO_BOX = initComboBox();

    private static final Button BUTTON = initButton();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Devin's Badass Graph");
        initWindow(START_DIMS_OF_GRAPH,primaryStage);
        primaryStage.show();
    }

    public static Scene fromGrid(MazeGraph graph,int dims, int height, int width, int padding, Stage primaryStage)
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(padding,padding,padding,padding));
        gridPane.setVgap(0);
        gridPane.setHgap(0);

        int wholeRowWidth = width - (padding* 2) ;
        int wholeColHeight = height - (padding * 2) ;
        int widthCol = wholeRowWidth / dims ;
        int heightRow = wholeColHeight / dims;

        drawGrid(wholeRowWidth,wholeColHeight ,widthCol,heightRow,gridPane,dims);
        createMaze(graph,wholeColHeight,heightRow,wholeRowWidth,widthCol,dims,gridPane);

        BUTTON.setOnAction( (ActionEvent event) ->
                {
                    if(COMBO_BOX.getValue() == null)
                    {
                        primaryStage.setScene(fromGrid(new MazeGraph(dims, dims),dims, height,width, padding
                                ,primaryStage));
                    }
                    else
                    {
                        initWindow(COMBO_BOX.getValue(),primaryStage);
                    }

                });
        gridPane.add(BUTTON,0,2);
        gridPane.add(COMBO_BOX,1,2);

        return new Scene(gridPane,width,height);

    }


    /**
     * Weird thing about the translateY for the sides of the square is that it seems to start in the middle and
     * spread out that's why the translate y is 10 apart
     * @return
     */

    public static void knockDownWalls(boolean left, boolean right, boolean top, boolean bottom,
                                      double translateX, double translateY, GridPane graph, int wallWidth, int wallHeight)
    {
        if(top)
        {
            graph.getChildren().add(makeLineFromBuilder(0,0,wallWidth,0,translateX,translateY,true));
        }

        if(bottom)
        {
            graph.getChildren().add(makeLineFromBuilder(0,0,wallWidth,0,translateX,translateY + wallHeight,true));
        }
        if(left)
        {
            graph.getChildren().add(makeLineFromBuilder(0,0,0,wallHeight,translateX,translateY,true));
        }
        if(right)
        {
            graph.getChildren().add(makeLineFromBuilder(0,0,0,wallHeight,translateX + wallWidth,translateY,true));
        }
    }

    public static void drawGrid(int wholeRowWidth, int wholeColHeight,int widthCol, int heightRow,
                                GridPane gridPane, int numRows)
    {
        int counter = 0;

        for(int x = 0, y=0 ; numRows >= counter; x+= widthCol, y+= heightRow)
        {
            gridPane.getChildren().addAll(makeLineFromBuilder(x,0,x,wholeColHeight,x,0,false));
            gridPane.getChildren().addAll(makeLineFromBuilder(0, y ,wholeRowWidth,y,0,y,false));
            counter++;
        }
    }

    /**
     * Makes sure that the rows and colums always fit evenly in the windo by adjusting dimensions as needed
     *
     * @param rowsCols
     * @return
     */
    public static int findIdealDim(int rowsCols)
    {
        int startDim = START_DIM_OF_WINDOW; // mess with this to adjust dimensions
        while (startDim % rowsCols != 0)
        {
            startDim--;
        }
        return startDim ;
    }

    public static MyLine makeLineFromBuilder(double startX, double startY, double endX, double endY, double translateX,
                                             double translateY, boolean knockingDown)
    {
        return MyLine.builder()
                .startX(startX)
                .startY(startY)
                .endX(endX)
                .endY(endY)
                .translateX(translateX)
                .translateY(translateY)
                .knockingDown(knockingDown)
                .build();
    }

    public static void initWindow(int dim, Stage primaryStage)
    {
        int idealDimensions =  findIdealDim(dim);
        int padding = (WINDOW_DEFAULT_WIDTH- idealDimensions) / 2; //to be able to keep the main window pretty big
        idealDimensions+= (padding * 2);
        primaryStage.setScene(fromGrid(new MazeGraph(dim,dim),dim,idealDimensions,idealDimensions , padding,
                primaryStage));
    }

    /**
     * Knocks down all walls in the current graph
     * @param graph
     * @param wholeColHeight
     * @param heightRow
     * @param wholeRowWidth
     * @param widthCol
     * @param dims
     * @param gridPane
     */

    public static void createMaze(MazeGraph graph, int wholeColHeight, int heightRow,int wholeRowWidth, int widthCol,
                                  int dims, GridPane gridPane)
    {
        int mazeParser = 0;

        LinkedList<Integer>[] adjacencyLists = graph.getAdjacencyLists();

        for(int y = 0 ; y< wholeColHeight && mazeParser<adjacencyLists.length; y+= heightRow)
        {
            for(int x = 0; x< wholeRowWidth && mazeParser<adjacencyLists.length; x+= widthCol)
            {
                knockDownWalls(adjacencyLists[mazeParser].contains(mazeParser - 1),
                        adjacencyLists[mazeParser].contains(mazeParser + 1),
                        adjacencyLists[mazeParser].contains(mazeParser - dims),
                        adjacencyLists[mazeParser].contains(mazeParser + dims),
                        x, y,gridPane,widthCol,heightRow);

                mazeParser++;
            }
        }
    }

    public static ComboBox<Integer> initComboBox()
    {
        List<Integer> choices = new ArrayList<>();

        for(int i = 2; i< MAX_CHOICES; i++ )
        {
            choices.add(i);
        }

        ComboBox<Integer> comboBox = new ComboBox<>(FXCollections.observableList(choices));
        comboBox.setPadding(new Insets(10,10,10,10));
        return  comboBox;
    }

    public static Button initButton()
    {
        Button button = new Button();
        button.setPadding(new Insets(10,10,10,10));
        button.setText("Generate a new Graph??");
        return button;
    }

}

