import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * An awt canvas for rendering a walk path on a lattice grid.
 * @author Alex Brandt; abrandt@dal.ca
 */
public class WalkCanvas extends Canvas {

    private ArrayList<Coordinate> drawCoords;
    private ArrayList<Color> drawColors;
    private boolean shouldDrawBGGrid = true;
    private boolean drawBackgroundGrid;
    private int scale;
    private Coordinate padding;
    private Coordinate centerPt;
    private int lastIdxPainted;

    private Timer timer;

    public WalkCanvas(Coordinate padding, int scale) {
        super();
        drawCoords = new ArrayList<>();
        drawColors = new ArrayList<>();
        this.scale = scale;
        this.padding = padding;
        this.drawBackgroundGrid = shouldDrawBGGrid;
        this.lastIdxPainted = 0;
    }

    @Override
    public void paint(Graphics g) {
        if (drawBackgroundGrid) {
            this.drawGrid(g);
        }
        //AWT may trigger a paint while timer is running. If so, reset timer paint index.
        lastIdxPainted = 0;

        if (timer != null && !timer.isRunning()) {
            this.connectTheDots(g);
        }
    }

    @Override
    public void update(Graphics g) {
        super.update(g); //clears contents of this component
        drawBackgroundGrid = shouldDrawBGGrid;
        lastIdxPainted = 0;
    }

    public void paintNewPoints() {
        Graphics g = this.getGraphics();
        int N = Math.min(drawCoords.size(), lastIdxPainted + 2);
        for (int i = lastIdxPainted+1; i < N; ++i) {
            _drawLineSegment(g, i);
        }
        lastIdxPainted = N-1;

        if (lastIdxPainted+1 == drawCoords.size()) {
            timer.stop();
        }
    }

    private void connectTheDots(Graphics graphics) {
        if (drawCoords.size() < 2) {
            return;
        }

        for (int i = 1; i < drawCoords.size(); ++i) {
            _drawLineSegment(graphics, i);
        }
    }

    private Color _getLineSegmentColor(int i) {
        int r = 0;
        int g = 0;
        int b = 0;
        int x = 50; //1/4 of the period and the maximum value
        int colorscale = 4;
        int tg = i % (4*x);
        int tb = (i + 3*x) % (4*x); //phase shift 3/4 of a period ahead

        if (tg >= 2*x) {
            g = Math.max(0, x - (tg % (2*x)));
        } else {
            g = Math.min(x, tg % (2*x));
        }
        if (tb >= 2*x) {
            b = Math.max(0, x - (tb % (2*x)));
        } else {
            b = Math.min(x, tb % (2*x));
        }
        return new Color(r*colorscale, g*colorscale, b*colorscale);
    }


    //Draw [i-1,i]
    private void _drawLineSegment(Graphics graphics, int i) {
        if (i < 0 || drawCoords.size() < i) {
            return;
        }

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(drawColors.get(i));
        Coordinate prevPt = drawCoords.get(i-1);
        Coordinate curPt = drawCoords.get(i);
        g2.drawLine(prevPt.x, prevPt.y, curPt.x, curPt.y);
    }

    public void drawGrid(Graphics g) {
        g.setColor(new Color(200, 200, 200));
        int w = this.getWidth() - padding.x;
        int h = this.getHeight() - padding.y;
        for (int i = padding.x; i <= w; i += scale) {
            g.drawLine(i, padding.y, i, h);
        }
        for (int i = padding.y; i <= h; i += scale) {
            g.drawLine(padding.x, i, w, i);
        }
    }

    public void addPoint(Coordinate pt) {
        this.drawCoords.add(_centeredCoordsToWindowCoords(pt));
        this.drawColors.add(_getLineSegmentColor(drawCoords.size()-1));
    }

    public void clearPoints() {
        this.drawCoords.clear();
        this.drawColors.clear();
        this.lastIdxPainted = 0;
    }

    private Coordinate _centeredCoordsToWindowCoords(Coordinate coord) {
        Coordinate ret = coord.scale(this.scale);
        ret.y = ret.y*-1; //need to flip y around since, Window coords has positive y-axis pointing down
        ret = ret.add(this.centerPt);

        return ret;
    }

    private int _findPathExtends(ArrayList<Coordinate> path) {
        int max = 0;
        int ax, ay;
        for (Coordinate coord : path) {
            ax = Math.abs(coord.x);
            ay = Math.abs(coord.y);
            if (ax > max) {
                max = ax;
            }
            if (ay > max) {
                max = ay;
            }
        }
        return max;
    }

    public void prepareCanvas(ArrayList<Coordinate> path) {
        int max = _findPathExtends(path);
        max *= this.scale;

        int maxPadding = Math.max(padding.x, padding.y);
        max += maxPadding;
        max *= 2; //to center the path on canvas

        if (max < this.getMinimumSize().width) {
            max = this.getMinimumSize().width;
        }
        if (max < this.getMinimumSize().height) {
            max = this.getMinimumSize().height;
        }

        while ((max-2*maxPadding) % scale != 0) {
            ++max;
        }

        Dimension screenDim = this.getToolkit().getScreenSize();
        int screenSize = Math.min(screenDim.width, screenDim.height);
        if (max > screenSize) {
            //If extents are too big for screen, adjust scale and try again.
            prepareCanvas(path, this.padding, this.scale/2);
            return;
        }

        this.setSize(max, max);
        this.centerPt = new Coordinate(max/2, max/2);

        this.clearPoints();

    }

    public void prepareCanvas(ArrayList<Coordinate> path, Coordinate padding, int scale) {
        this.padding = padding;
        this.scale = scale;
        prepareCanvas(path);
    }

    public void startAnimation(int stepDuration) {
        timer = new Timer(stepDuration, null);
        WalkCanvas localCanvas = this;
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localCanvas.paintNewPoints();
            }
        });
        lastIdxPainted = 0;
        timer.start();
    }

}

