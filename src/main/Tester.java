package main;

import ds.MazeGraph;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Tester extends Application
{

    Stage window;
    private static final int WINDOW_PADDING = 20;
    private static final int HEIGHT_ADJUSTER = 80;

    public static void main(String[] args)
    {
        launch(args);
        //Looks like we only have to do a good union up to the last one
        //probably because once we've gotten to it, it has no valid options left
        //TODO test the maze graph here -> seems to work well enough
    }

    @Override
    public void start(Stage primaryStage)
    {
        //Create original graph

        MazeGraph graph = new MazeGraph(4,4);
        window = primaryStage;
        window.setTitle("Devin's Badass Graph");
        window.setScene(fromGrid(graph,7,7, 600,600));
        window.show();
    }

    public static class MyLine extends Group
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



    public static class MyNode extends StackPane
    {
        public MyNode(double x, double y, double width, double height)
        {
            Rectangle rec =new Rectangle(width,height);
            rec.setStroke(Color.BLACK);
            rec.setFill(Color.ORANGE);

            setTranslateX(x);
            setTranslateY(y);

            getChildren().addAll(rec);
        }

    }

    public static Scene devinTry()
    {
        Group root = new Group();

        for(int x = 0 ; x<400; x+= 20)
        {
            for(int y = 0; y<400; y+= 20)
            {
                Node myNode = new MyNode(x, y, 20, 20);
                root.getChildren().add(myNode);
            }
        }
        return new Scene(root, 400,400);
    }

    public static Scene fromGrid(MazeGraph graph,int rows, int cols, int height, int width)
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(WINDOW_PADDING,WINDOW_PADDING,WINDOW_PADDING,WINDOW_PADDING));
        gridPane.setVgap(10);
        gridPane.setHgap(10);


        int leftCol = 0;
        int widthCol = (width - (WINDOW_PADDING * 2)) / cols;
        int heightRow = (height - (WINDOW_PADDING * 2) - HEIGHT_ADJUSTER) / rows;
        int bottomRow = heightRow * (rows - 1);

        System.out.println("Width = " + widthCol);
        System.out.println("Height = " + heightRow);

        //fill in the rest
        for(int x = leftCol ; x<width-heightRow; x+= widthCol)
        {
            for(int y = 0; y<height-heightRow - HEIGHT_ADJUSTER; y+= heightRow)
            {
                gridPane.getChildren().addAll(drawGrid(x, y,leftCol,bottomRow, widthCol,heightRow));
            }
        }

        //Testing this now need to parse in graph
        knockDownWalls(true,true,true,true,widthCol * 2, heightRow * 3,gridPane,widthCol,heightRow);


        return new Scene(gridPane,width,height);

    }

    /**
     * Weird thing about the translateY for the sides of the square is that it seems to start in the middle and
     * spread out that's why the translate y is 10 apart
     * @param xCoord
     * @param yCoord
     * @return
     */

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

}

