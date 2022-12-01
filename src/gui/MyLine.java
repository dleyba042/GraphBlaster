package gui;

import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MyLine extends TilePane{

    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double translateX;
    private double translateY;
    private boolean knockingDown;

    public MyLine(double startX, double startY, double endX, double endY, double translateX, double translateY,
                  boolean knockingDown)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.translateX = translateX;
        this.translateY = translateY;
        this.knockingDown = knockingDown;
    }

    public MyLine createLine()
    {
        Line line = new Line(startX, startY, endX, endY);
        if (knockingDown)
        {
            line.setStroke(Color.WHITE);
        }
        setTranslateX(translateX);
        setTranslateY(translateY);
        getChildren().add(line);
        return this;
    }

    public static MyLineBuilder builder()
    {
        return new MyLineBuilder();
    }


    public static class MyLineBuilder
    {
        private double startX;
        private double startY;
        private double endX;
        private double endY;
        private double translateX;
        private double translateY;
        private boolean knockingDown;

        public MyLineBuilder(){};

        public MyLineBuilder startX(double startX)
        {
            this.startX = startX;
            return this;
        }
        public MyLineBuilder startY(double startY)
        {
            this.startY = startY;
            return this;
        }
        public MyLineBuilder endX(double endX)
        {
            this.endX = endX;
            return this;
        }
        public MyLineBuilder endY(double endY)
        {
            this.endY =endY;
            return this;
        }

        public MyLineBuilder translateX(double translateX)
        {
            this.translateX = translateX;
            return this;
        }
        public MyLineBuilder translateY(double translateY)
        {
            this.translateY = translateY;
            return this;
        }
        public MyLineBuilder knockingDown(boolean knockingDown)
        {
            this.knockingDown = knockingDown;
            return this;
        }

        public MyLine build()
        {
            MyLine line = new MyLine(startX,startY,endX,endY,translateX,translateY,knockingDown);
            return line.createLine();
        }

    }



}