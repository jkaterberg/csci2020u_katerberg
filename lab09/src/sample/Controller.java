package sample;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Controller implements Initializable {
    public Canvas drawingThing;

    private ArrayList<Float> downloadStockPrices(String stock) throws IOException, ParserConfigurationException, SAXException {
        //Data constraints
        String period1 = Long.toString(new GregorianCalendar(2010,Calendar.JANUARY,1).getTime().getTime()/1000L);
        String period2 = Long.toString(new GregorianCalendar(2021,Calendar.DECEMBER,31).getTime().getTime()/1000L);
        String interval = "1mo";
        String events = "history";
        String includeAdjustedClose = "true";
        //Fetch the data
        String sURL = "https://query1.finance.yahoo.com/v7/finance/download/"+stock
                                                                                +"?period1="+period1
                                                                                +"&period2="+period2
                                                                                +"&interval="+interval
                                                                                +"&events="+events
                                                                                +"&includeAdjustedClose="+includeAdjustedClose;

        //setup the connection
        URL netURL = new URL(sURL);
        URLConnection conn = netURL.openConnection();
        conn.setDoOutput(false);
        conn.setDoInput(true);

        //pull in the data
        Scanner scanner = new Scanner(conn.getInputStream());
        ArrayList<Float> closing = new ArrayList<>();

        //get rid of the headers
        scanner.next();
        scanner.next();
        //parse the data
        while(scanner.hasNext()){
            String[] nextLine = scanner.next().split(",");
            closing.add(Float.parseFloat(nextLine[4]));
        }
        return closing;
    }

    private void drawLinePlot(GraphicsContext gc, ArrayList<Float> stock1, ArrayList<Float> stock2){
        int height = 800;
        int width = 800;

        gc.strokeLine(0,0,0,height);
        gc.strokeLine(0,height,width,height);

        int points = Math.max(stock1.size(),stock2.size());
        float xspace = (float)width/points;
        float yspace = (float)height/Math.max(Collections.max(stock1), Collections.max(stock2));

        plotLine(gc, stock1, height, yspace, xspace, Color.BLUE);
        plotLine(gc, stock2, height, yspace, xspace, Color.ORANGE);
    }

    private void plotLine(GraphicsContext gc, ArrayList<Float> stock, int height, float yspace, float xspace, Color color){
        System.out.println(yspace);

        gc.setStroke(color);
        for(int i=0; i<stock.size()-1; i++){
            gc.strokeLine(i*xspace, height-stock.get(i)*yspace, (i+1)*xspace, height-stock.get(i+1)*yspace);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GraphicsContext gc = drawingThing.getGraphicsContext2D();
        ArrayList<Float> stock1 = null;
        ArrayList<Float> stock2 = null;
        try {
            stock1 = downloadStockPrices("AAPL");
            stock2 = downloadStockPrices("GOOG");

            System.out.println(stock1);
            System.out.println(stock2);

            drawLinePlot(gc, stock1, stock2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
