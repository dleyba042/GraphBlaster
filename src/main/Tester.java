package main;

import ds.MazeGraph;
import gui.MyLine;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static gui.Utilities.*;

public class Tester extends Application
{

    private static final int START_DIMS_OF_GRAPH = 12;

    private static final List<ColumnConstraints> CONSTRAINTS = initColumnConstraints();

    private static final Button DFS_BUTTON = initDFS();

    private static final Button BFS_BUTTON = initBFS();

    private static final ComboBox<Integer> COMBO_BOX = initComboBox();
    private static final Button BUTTON = initButton();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Devin's Badass Maze");

        BUTTON.setOnAction( (ActionEvent event) ->
        {
            if(COMBO_BOX.getValue() == null)
            {
                primaryStage.setScene(setupScene(START_DIMS_OF_GRAPH));
            }
            else
            {
                primaryStage.setScene(setupScene(COMBO_BOX.getValue()));
            }

        });

        primaryStage.setScene(setupScene(START_DIMS_OF_GRAPH));
        primaryStage.show();
    }

    public static Scene fromGrid(MazeGraph graph,int dims, int height, int width, int padding)
    {

         GridPane maze = new GridPane();
         maze.setPadding(new Insets(padding,padding,padding,padding));
         maze.addColumn(3);
         maze.addColumn(4);
         maze.addColumn(5);
         maze.getColumnConstraints().addAll(CONSTRAINTS);

        int wholeRowWidth = width - (padding* 2) ;
        int wholeColHeight = height - (padding * 2) ;
        int widthCol = wholeRowWidth / dims ;
        int heightRow = wholeColHeight / dims;

        drawGrid(wholeRowWidth,wholeColHeight ,widthCol,heightRow,dims,maze);
        createMaze(graph,wholeColHeight,heightRow,wholeRowWidth,widthCol,dims,maze);

        maze.add(BUTTON,0,2);
        maze.add(DFS_BUTTON,3,2);
        maze.add(BFS_BUTTON,4,2);
        maze.add(COMBO_BOX,1,2);

        return new Scene(maze,width,height);
    }


    /**
     * Weird thing about the translateY for the sides of the square is that it seems to start in the middle and
     * spread out that's why the translate y is 10 apart
     * @return
     */

    public static void knockDownWalls(boolean left, boolean right, boolean top, boolean bottom,
                                      double translateX, double translateY, int wallWidth, int wallHeight, GridPane maze)
    {
        if(top)
        {
            maze.getChildren().add(makeLineFromBuilder(0,0,wallWidth,0,translateX,translateY,true));
        }

        if(bottom)
        {
            maze.getChildren().add(makeLineFromBuilder(0,0,wallWidth,0,translateX,translateY + wallHeight,true));
        }
        if(left)
        {
            maze.getChildren().add(makeLineFromBuilder(0,0,0,wallHeight,translateX,translateY,true));
        }
        if(right)
        {
            maze.getChildren().add(makeLineFromBuilder(0,0,0,wallHeight,translateX + wallWidth,translateY,true));
        }
    }

    public static void drawGrid(int wholeRowWidth, int wholeColHeight,int widthCol, int heightRow, int numRows, GridPane maze)
    {
        int counter = 0;

        for(int x = 0, y=0 ; numRows >= counter; x+= widthCol, y+= heightRow)
        {
            maze.getChildren().addAll(makeLineFromBuilder(x,0,x,wholeColHeight,x,0,false));
            maze.getChildren().addAll(makeLineFromBuilder(0, y ,wholeRowWidth,y,0,y,false));
            counter++;
        }
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



    /**
     * Knocks down all walls in the current graph
     * @param graph
     * @param wholeColHeight
     * @param heightRow
     * @param wholeRowWidth
     * @param widthCol
     * @param dims
     */

    public static void createMaze(MazeGraph graph, int wholeColHeight, int heightRow,int wholeRowWidth, int widthCol,
                                  int dims, GridPane maze)
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
                        x, y,widthCol,heightRow,maze);

                mazeParser++;
            }
        }
    }

    public static Scene setupScene(int dims)
    {
        HashMap<String,Integer> startVals = initWindow(dims);
        int idealDim = startVals.get("idealDim");

        return fromGrid(new MazeGraph(dims,dims),dims
                ,idealDim,idealDim,startVals.get("padding"));

    }

}

