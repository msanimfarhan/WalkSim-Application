import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * An AWT Frame class for holding and displaying a WalkCanvas.
 * @author Alex Brandt; abrandt@dal.ca
 */
public class WalkFrame extends Frame {

    public static final int DEFAULT_SIZE = 500;
    public static final int DEFAULT_SCALE = 15;
    public static final int DEFAULT_PADDING = 20;

    private WalkCanvas canvas;

    public WalkFrame() {
        this(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SCALE);
    }

    public WalkFrame(int width, int height, int scale) {
        Coordinate canvasPadding = new Coordinate(DEFAULT_PADDING, DEFAULT_PADDING);
        this.setTitle("Random Walk Visualizer");
        //this.setSize(width,height);
//        this.setLayout(new FlowLayout());

        this.canvas = new WalkCanvas(canvasPadding, scale);
//        this.canvas.setSize(this.canvasWidth, this.canvasHeight);
        this.add(this.canvas);
        this.pack();
        this.setResizable(false);

        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        });

        this.setVisible(true);
    }

    public void setPath (ArrayList<Coordinate> path) {
        this.canvas.prepareCanvas(path);
        this.pack();
        for (Coordinate coord : path) {
            this.canvas.addPoint((coord));
        }
    }

    public void animatePath(ArrayList<Coordinate> coords, int stepDuration) {
        this.canvas.prepareCanvas(coords);
        this.pack();
        if (coords.size() < 2) {
            return;
        }

        int N = coords.size();
        for (int i = 0; i < N; ++i) {
            this.canvas.addPoint((coords.get(i)));
        }
        this.canvas.startAnimation(stepDuration);
    }

    @Override
    public void repaint() {

    }


}

