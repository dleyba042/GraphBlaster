package main;

import ds.MazeGraph;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Tester extends Application
{

    Stage window;
    private static final int WINDOW_DEFAULT_WIDTH = 750;
    public static void main(String[] args)
    {
        launch(args);
        //Looks like we only have to do a good union up to the last one
        //probably because once we've gotten to it, it has no valid options left
    }

    @Override
    public void start(Stage primaryStage)
    {
        //Create original graph

        //TODO yep the math formula has to be a height that is evenly divisible by both row and column
        //and you just add 40 to it to represent the padding!!
        //for this one I did 44 * 17 = 748(The even divisor) + 40 = 788

        int rows = 16;
        int cols = 16;

        int idealDimensions =  findIdealDim(rows);
        int padding = (WINDOW_DEFAULT_WIDTH- idealDimensions) / 2; //to be able to keep the main window pretty big
        idealDimensions+= (padding * 2);

        window = primaryStage;
        window.setTitle("Devin's Badass Graph");
        window.setScene(fromGrid(new MazeGraph(rows,cols),rows,cols, idealDimensions,idealDimensions , padding, window));
        window.show();
    }

    public static class MyLine extends TilePane
    {
        public  MyLine(double startX, double startY, double endX, double endY, double translateX, double translateY, boolean knockindDown)
        {
            Line line = new Line(startX,startY,endX,endY);
            if(knockindDown)
            {
                line.setStroke(Color.WHITE);
            }
            setTranslateX(translateX);
            setTranslateY(translateY);
            getChildren().add(line);
        }
    }


    public static Scene fromGrid(MazeGraph graph,int rows, int cols, int height, int width, int padding, Stage primaryStage)
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(padding,padding,padding,padding));
        gridPane.setVgap(0);
        gridPane.setHgap(0);

        int wholeRowWidth = width - (padding* 2) ;
        int wholeColHeight = height - (padding * 2) ;
        int widthCol = wholeRowWidth / cols ;
        int heightRow = wholeColHeight / rows;

        drawGrid(0,wholeRowWidth,wholeColHeight ,widthCol,heightRow,gridPane,rows);

        /**
        Break down all the walls here
         */

        int mazeParser = 0;
        LinkedList<Integer>[] adjacencyLists = graph.getAdjacencyLists();

        for(int y = 0 ; y< wholeColHeight; y+= heightRow)
        {
            for(int x = 0; x< wholeRowWidth; x+= widthCol)
            {

                //Test to knock down each left wall
                knockDownWalls(adjacencyLists[mazeParser].contains(mazeParser - 1),
                        adjacencyLists[mazeParser].contains(mazeParser + 1),
                        adjacencyLists[mazeParser].contains(mazeParser - cols),
                        adjacencyLists[mazeParser].contains(mazeParser + cols),
                        x, y,gridPane,widthCol,heightRow);

                mazeParser++;
            }
        }

        Button button = new Button();
        button.setPadding(new Insets(10,10,10,10));
        button.setText("Generate a new Graph??");
        button.setOnAction( (ActionEvent event) ->
                {
                    primaryStage.setScene(fromGrid(new MazeGraph(rows,cols),rows,cols, height,width, padding,primaryStage));
                });
        gridPane.add(button,0,2);

        //Testing this now need to parse in graph
        // knockDownWalls(true,true,true,true,widthCol, heightRow ,gridPane,widthCol,heightRow);

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
            graph.getChildren().add(new MyLine(0,0,wallWidth,0,translateX,translateY,true));
        }

        if(bottom)
        {
            graph.getChildren().add(new MyLine(0,0,wallWidth,0,translateX,translateY + wallHeight,true));
        }
        if(left)
        {
            graph.getChildren().add(new MyLine(0,0,0,wallHeight,translateX,translateY,true));
        }
        if(right)
        {
            graph.getChildren().add(new MyLine(0,0,0,wallHeight,translateX + wallWidth,translateY,true));
        }
    }

    public static void drawGrid(int leftCol, int wholeRowWidth, int wholeColHeight,int widthCol, int heightRow, GridPane gridPane, int numRows)
    {
        int counter = 0;

        for(int x = leftCol, y=0 ; numRows >= counter; x+= widthCol, y+= heightRow)
        {
            gridPane.getChildren().addAll(new MyLine(x, 0,x,wholeColHeight,x,0,false));
            gridPane.getChildren().addAll(new MyLine(0, y ,wholeRowWidth,y,0,y,false));
            counter++;
        }
    }

    public static int findIdealDim(int rowsCols)
    {
        int startDim = 660; // mess with this to adjust dimensions


        while (startDim % rowsCols != 0)
        {
            startDim--;
        }
        System.out.println(startDim);

        return startDim ;
    }

}

