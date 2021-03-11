package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    @FXML private Canvas graph;
    @FXML private Canvas legend;
    @FXML public GraphicsContext gc;
    private static Color[] colors = { Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM};

    public void initGraphics(Canvas canvas){
        gc = canvas.getGraphicsContext2D();
    }

    private int sum(Integer[] array){
        int total=0;
        for(int i:array){
            total+=i;
        }
        return total;
    }

    private void drawPieChart(GraphicsContext gc, String[] labels, Integer[] data, Color[] colors){
        int dataTotal = sum(data);
        double startAngle=0;
        double height = 500;
        double width = 500;
        int posx = 300;
        int posy = 0;
        int rectHeight = 50;
        int rectWidth = 100;
        int legendSeparator = 20;

        gc.setStroke(Color.BLACK);
        //Draw the Legend
        for(int i=0; i<labels.length; i++){
            gc.setFill(colors[i]);
            gc.fillRect(0, i*(rectHeight+legendSeparator), rectWidth, rectHeight);
            gc.strokeText(labels[i], rectWidth+legendSeparator, i*(rectHeight+legendSeparator)+(float)rectHeight/2);
        }

        //Draw the Chart
        for(int i=0; i<labels.length; i++){
            double percent = (double)data[i]/dataTotal;
            double angle = 360*percent;

            gc.setFill(colors[i]);
            gc.fillArc(posx,posy,height/2,width/2,startAngle,angle, ArcType.ROUND);

            startAngle += angle;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Read in the data
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Data File");
        File dataFile = fileChooser.showOpenDialog(null);

        Scanner sc = null;
        HashMap<String, Integer> freq = null;
        try {
            sc = new Scanner(dataFile).useDelimiter(",");
            freq = new HashMap<>();
            int i=0;
            //Parse the data
            while(sc.hasNext()){
                String next = sc.next();
                if(i==5){
                    if(freq.containsKey(next)){
                        freq.put(next, freq.get(next)+1);
                    }else{
                        freq.put(next, 1);
                    }
                }
                i=(i+1)%6;
            }
            System.out.println(freq);
        } catch (FileNotFoundException e) {
            sc.close();
            e.printStackTrace();
        }

        //Display the data
        if(null != freq) {
            initGraphics(graph);
            String[] labels = freq.keySet().toArray(new String[0]);
            Integer[] data = freq.values().toArray(new Integer[0]);
            drawPieChart(gc,labels,data,colors);
        }


    }
}
