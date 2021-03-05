package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class Main extends Application {
    @FXML Canvas canvas;
    private static double[] avgHousingPricesByYear = { 247381.0,264171.4,287715.3,294736.1, 308431.4,322635.9,340253.0,363153.7};
    private static double[] avgCommercialPricesByYear = { 1121585.3,1219479.5,1246354.2,1295364.8, 1335932.6,1472362.0,1583521.9,1613246.3};

    private static String[] ageGroups = { "18-25", "26-35", "36-45", "46-55", "56-65", "65+"};
    private static int[] purchasesByAgeGroup = { 648, 1021, 2453, 3173, 1868, 2247};
    private static Color[] pieColours = { Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM};

    private void draw(Group group){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setLineWidth(2.0);
        gc.setFill(Color.RED);

        drawBarChart(gc, avgHousingPricesByYear, avgCommercialPricesByYear);
        drawPieChart(gc, ageGroups, purchasesByAgeGroup, pieColours);
    }

    private void drawBarChart(GraphicsContext gc, double[] data1, double[] data2){
        double height = canvas.getHeight();
        double width = canvas.getWidth();
        double barWidth = 10;
        double offset = 5;
        double margin = 30;

        double max = Math.max(max(data1), max(data2));
        double scale = height/max;

        for(int i=0; i<data1.length; i++){
            gc.setFill(Color.RED);
            gc.fillRect(margin+ i*2*barWidth+offset*i, height-data1[i]*scale+margin, barWidth, data1[i]*scale);
            gc.setFill(Color.BLUE);
            gc.fillRect(margin+ i*2*barWidth+barWidth+offset*i, height-data2[i]*scale+margin, barWidth, data2[i]*scale);
        }

    }

    private void drawPieChart(GraphicsContext gc, String[] labels, int[] data, Color[] colors){
        int dataTotal = sum(data);
        double startAngle=0;
        double height = 300;
        double width = 300;
        int posx = 300;
        int posy = 150;

        for(int i=0; i<labels.length; i++){
            double percent = (double)data[i]/dataTotal;
            double angle = 360*percent;

            gc.setFill(colors[i]);
            gc.fillArc(posx,posy,height/2,width/2,startAngle,angle, ArcType.ROUND);

            startAngle += angle;
        }

    }

    private int sum(int[] array){
        int sum = 0;
        for(int item:array){
            sum += item;
        }
        return sum;
    }

    private double max(double[] array){
        double max = 0;
        for (double v : array) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        canvas = new Canvas(900,300);


        Group root = new Group();
        root.getChildren().add(canvas);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        draw(root);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
