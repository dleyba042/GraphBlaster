package gui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utilities
{
    public static final int MAX_CHOICES = 300;
    public static final int START_DIM_OF_WINDOW = 660;
    private static final int WINDOW_DEFAULT_WIDTH = 750;

    public static Button initDFS()
    {
        Button button = new Button();
        button.setPadding(new Insets(10,10,10,10));
        button.setText("DFS??");
        button.setAlignment(Pos.BASELINE_LEFT);
        return button;
    }

    public static Button initBFS()
    {
        Button button = new Button();
        button.setPadding(new Insets(10,10,10,10));
        button.setText("BFS??");
        button.setAlignment(Pos.BASELINE_LEFT);
        return button;
    }

    public static List<ColumnConstraints> initColumnConstraints()
    {
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(15);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20);
        ColumnConstraints col5 = new ColumnConstraints();
        col4.setPercentWidth(20);

        return List.of(col1,col2,col3,col4,col5);
    }

    public static Button initButton()
    {
        Button button = new Button();
        button.setPadding(new Insets(10,10,10,10));
        button.setText("Generate a new Graph??");
        return button;
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

    public static HashMap<String,Integer> initWindow(int dim)
    {
        HashMap<String,Integer> map = new HashMap<>();
        int idealDimensions =  findIdealDim(dim);
        int padding = (WINDOW_DEFAULT_WIDTH- idealDimensions) / 2; //to be able to keep the main window pretty big
        map.put("dim",dim);
        map.put("padding",padding);
        idealDimensions+= (padding * 2);
        map.put("idealDim",idealDimensions);
        return map;
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
}
