import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameScreen {

    public GraphicsContext gc;
    private final float size;
    private final Canvas canvas;

    public GameScreen(float size, Canvas canvas) {
        this.size = size;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        drawAll();
    }

    public void drawLines(){
        gc.setStroke(Color.web("#FFFFFF",0.6));
        gc.setLineWidth(10);
        for(float i = size/2 + 10; i<size; i+=30){
            gc.strokeLine(0,i, size, i);
        }
    }
    // Funcion utilizada para dibujar las lineas que simulan el movimiento, con un displacement
    // dy para que se mueva las lineas un poco para abajo.
    // E: dy, displacement en y, S: void, R: none
    public void drawLines(double dy){
        gc.setStroke(Color.web("#FFFFFF",0.2));
        gc.setLineWidth(10);
        for(float i = size/2 + 10; i<size; i+=50){
            if(i+dy < size){
                //gc.strokeLine(0,i+dy, size, i+dy);
                gc.setStroke(Color.WHITE);
                gc.strokeLine(size/2, i+dy, size/2, i+dy+5);
            }else{
                //gc.strokeLine(0,i+dy-size/2, size, i+dy-size/2);
                gc.setStroke(Color.WHITE);
                gc.strokeLine(size/2, i+dy-size/2, size/2, i+dy-size/2+5);
            }
            gc.setStroke(Color.web("#FFFFFF",0.2));
            gc.setLineWidth(10);
        }

    }

    public void drawStreet(){
        gc.setStroke(Color.RED);
        gc.setLineWidth(20);
        gc.strokeLine(0,size,size/2 - size/10, size/2 + 1);
        gc.strokeLine(size,size,size/2 + size/10, size/2 + 1);

        gc.setFill(Color.GRAY);
        gc.setStroke(Color.TRANSPARENT);
        gc.fillPolygon(new double[]{0, size / 2 - size / 10, size / 2 + size / 10, size},
                new double[]{size,size/2, size/2, size}, 4);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        //gc.strokeLine(size/2,size,size/2, size/2 + 1);
        //gc.strokeLine(3*size/4,size,size/2 + size/10 - 30, size/2 + 1);
    }

    public void drawBackground(){
        // Cielo
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0,0,size,size/2);

        // Tierra
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.TRANSPARENT);
        gc.fillPolygon(new double[]{0, size/2 - size/10 - 12, 0}, new double[]{size/2, size/2, size-12}, 3);
        gc.fillPolygon(new double[]{size/2 + size/10 + 12, size, size}, new double[]{size/2, size/2, size-12}, 3);

    }

    public void drawAll(){
        drawStreet();
        drawLines();
        drawBackground();
    }


    public GraphicsContext getGc() {
        return gc;
    }

    public float getSize() {
        return size;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
