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
    }

    public void drawBackground(){
        gc.setFill(Color.GREEN);
        gc.fillRect(0,size/2,size,size);

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
        gc.strokeLine(size/4,size,size/2 - size/10 +30, size/2 + 1);
        gc.strokeLine(3*size/4,size,size/2 + size/10 - 30, size/2 + 1);

        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0,0,size,size/2);

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
