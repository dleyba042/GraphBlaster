package experiments;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Experimental
{

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

    public static class MyNode extends GridPane
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
}
