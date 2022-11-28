package main;

import ds.MazeGraph;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Tester extends Application
{

    Stage window;
    private static final int WINDOW_PADDING = 20;
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

        int rows = 50;
        int cols = 50;

        MazeGraph graph = new MazeGraph(4,4);

        int idealWidth =  ((rows * 10) % 800 > 0) ? 800 : 600;
        int idealHeight = ((rows * 10) % 800 > 0) ? 800 : 600;

        window = primaryStage;
        window.setTitle("Devin's Badass Graph");
        window.setScene(fromGrid(graph,rows,cols, idealHeight,idealWidth));
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


    public static Scene fromGrid(MazeGraph graph,int rows, int cols, int height, int width)
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(WINDOW_PADDING,WINDOW_PADDING,WINDOW_PADDING,WINDOW_PADDING));
        gridPane.setVgap(0);
        gridPane.setHgap(0);

        /*
        //TODO may be usefull later
        int widthCol = (width - (WINDOW_PADDING * 2)) / cols;
        int heightRow = (height - (WINDOW_PADDING * 2) - HEIGHT_ADJUSTER) / rows;
        int bottomRow = heightRow * (rows - 1);

         */

       // System.out.println("Width = " + widthCol);
       // System.out.println("Height = " + heightRow);

        /*
        //fill in the rest
        for(int x = leftCol ; x<width-heightRow; x+= widthCol)
        {
            for(int y = 0; y<height-heightRow - HEIGHT_ADJUSTER; y+= heightRow)
            {
                gridPane.getChildren().addAll(drawGrid(x, y,leftCol,bottomRow, widthCol,heightRow));
            }

        }

         */

        int wholeRowWidth = width - (WINDOW_PADDING * 2) - 20;
        int wholeColHeight = height - (WINDOW_PADDING * 2) -20 ;
        int widthCol = wholeRowWidth / cols ;
        int heightRow = wholeColHeight / rows;

        drawGrid(0,wholeRowWidth,wholeColHeight ,widthCol,heightRow,gridPane,rows);


        //Testing this now need to parse in graph
        //knockDownWalls(true,true,true,true,widthCol * 2, heightRow * 3,gridPane,widthCol,heightRow);


        return new Scene(gridPane,width,height);

    }


    /**
     * Weird thing about the translateY for the sides of the square is that it seems to start in the middle and
     * spread out that's why the translate y is 10 apart
     * @return
     */
/*
    public static List<Node> drawGrid(int xCoord, int yCoord,int leftCol, int bottomRow, int width, int height)
    {
            List<Node> square = new ArrayList<>();

            if(xCoord == leftCol) //Draw far left col
            {
                square.add(new MyLine(0,0,0,height,xCoord, yCoord + (height/2),false));
            }
            if(yCoord == bottomRow) //draw Bottom row
            {
                square.add(new MyLine(0,height,width,height,xCoord, yCoord + height,false));
            }
            //all other rows
            square.add(new MyLine(0,0,width,0,xCoord,yCoord,false));
            //all other cols
            square.add(new MyLine(0,0,0,height,xCoord + width,yCoord+ (height/2),false));

        return square;
    }

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
            graph.getChildren().add(new MyLine(0,0,0,wallHeight,translateX,translateY + (wallHeight/2),true));
        }
        if(right)
        {
            graph.getChildren().add(new MyLine(0,0,0,wallHeight,translateX + wallWidth,translateY + (wallHeight/2),true));
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
        gridPane.getChildren().addAll(new MyLine(0, wholeColHeight,0,wholeColHeight,wholeRowWidth,wholeColHeight,false));
    }

}

