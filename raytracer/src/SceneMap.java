import raytracer.SceneObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by shellariandra on 22.05.14.
 */
public class SceneMap extends JFrame
{
    private static int MAP_WIDTH = 300;
    private static int MAP_HEIGHT = 300;
    private static int X_COORDINATE = 20;
    private static int Y_COORDINATE = 20;
    private static int LENGTH_UNIT = 50;
    private static double REDUCTION_COEFFICIENT = 0.5;
    private static int color = 255;
    RenderFrame.RenderPanel renderPanel;
    private BufferedImage bufferedImage;

    public SceneMap(final RenderFrame.RenderPanel renderPanel)
    {
        this.renderPanel = renderPanel;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        JPanel panel = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g)
            {
                g.drawImage(bufferedImage, 0, 0, null);
            }
        };

        panel.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        getContentPane().add(panel);
        generateImage(renderPanel.getSceneObjects());


        MouseAdapter adepter = new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                renderPanel.changeCamerePosition((MAP_HEIGHT - Y_COORDINATE - e.getY()) * REDUCTION_COEFFICIENT,
                        (e.getX() - X_COORDINATE) * REDUCTION_COEFFICIENT);

            }
        };

        panel.addMouseListener(adepter);

        pack();
        setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
        // setResizable(false);
    }

    private boolean checkPoint(double x, double y)
    {
        if ((x >= X_COORDINATE) && (x <= X_COORDINATE + 3 * LENGTH_UNIT) &&
                (y >= MAP_HEIGHT - Y_COORDINATE - LENGTH_UNIT) && (y <= MAP_HEIGHT - Y_COORDINATE))
        {
            return true;
        }
        if ((x >= X_COORDINATE + LENGTH_UNIT) && (x <= X_COORDINATE + 2 * LENGTH_UNIT)
                && (y >= MAP_HEIGHT - Y_COORDINATE - 3 * LENGTH_UNIT) && (y <= MAP_HEIGHT - Y_COORDINATE - LENGTH_UNIT))
        {
            return true;
        }
        if ((x >= X_COORDINATE + LENGTH_UNIT) && (x <= X_COORDINATE + 3 * LENGTH_UNIT)
                && (y >=MAP_HEIGHT - Y_COORDINATE - 4 * LENGTH_UNIT) && (y <= MAP_HEIGHT - Y_COORDINATE - 3 * LENGTH_UNIT))
        {
            return  true;
        }
        return false;
    }

    private void generateImage(ArrayList<SceneObject> sceneObjects)
    {
        bufferedImage = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

        for (SceneObject object : sceneObjects)
        //for (int i = 0; i < sceneObjects.size(); ++i)
        //for (int i = 0; i < 1; ++i)
        {
            //SceneObject object = sceneObjects.get(i);
            int minX = getRealX(object.getMinY());
            int maxY = getRealY(object.getMinX());
            int maxX = getRealX(object.getMaxY());
            int minY = getRealY(object.getMaxX());

            if (object.getMinZ() == object.getMaxZ())
            {
                continue;
            }

            putLine(minX, maxY, maxX, minY);
        }

        /*
        bufferedImage = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < LENGTH_UNIT; ++i)
        {
            bufferedImage.setRGB(X_COORDINATE, MAP_HEIGHT - Y_COORDINATE - i, color);
            bufferedImage.setRGB(X_COORDINATE + 3 * LENGTH_UNIT, MAP_HEIGHT - Y_COORDINATE - i, color);
            bufferedImage.setRGB(X_COORDINATE + 3 * LENGTH_UNIT, MAP_HEIGHT - Y_COORDINATE - 3 * LENGTH_UNIT - i, color);

            bufferedImage.setRGB(X_COORDINATE + i, MAP_HEIGHT - Y_COORDINATE - LENGTH_UNIT, color);
            bufferedImage.setRGB(X_COORDINATE + 2 * LENGTH_UNIT + i, MAP_HEIGHT - Y_COORDINATE - LENGTH_UNIT, color);
            bufferedImage.setRGB(X_COORDINATE + 2 * LENGTH_UNIT + i, MAP_HEIGHT - Y_COORDINATE - 3 * LENGTH_UNIT, color);
        }

        for (int i = 0; i < 2 * LENGTH_UNIT; ++i)
        {
            bufferedImage.setRGB(X_COORDINATE + 2 * LENGTH_UNIT, MAP_HEIGHT - Y_COORDINATE - LENGTH_UNIT - i, color);

            bufferedImage.setRGB(X_COORDINATE + LENGTH_UNIT + i, MAP_HEIGHT - Y_COORDINATE - 4 * LENGTH_UNIT, color);
        }

        for (int i = 0; i < 3 * LENGTH_UNIT; ++i)
        {
            bufferedImage.setRGB(X_COORDINATE + LENGTH_UNIT, MAP_HEIGHT - Y_COORDINATE - LENGTH_UNIT - i, color);

            bufferedImage.setRGB(X_COORDINATE + i, MAP_HEIGHT - Y_COORDINATE, color);
        }

        bufferedImage.flush();
        */
    }

    private int getRealX(double x)
    {
        return (int) (Math.round(x / REDUCTION_COEFFICIENT) + X_COORDINATE);
    }

    private int getRealY(double y)
    {
        return (int) (MAP_HEIGHT - Math.round(y / REDUCTION_COEFFICIENT) - Y_COORDINATE);
    }

    private void putLine(int x1, int y1, int x2, int y2)
    {
        System.out.println("X1 " + x1);
        System.out.println("X2 " + x2);
        System.out.println("Y1 " + y1);
        System.out.println("Y2 " + y2);
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x2 >= x1) ? 1 : -1;
        int sy = (y2 >= y1) ? 1 : -1;

        if (dy <= dx)
        {
            int d = (dy / 2) - dx;
            int d1 = dy / 2;
            int d2 = (dy - dx) / 2;
            bufferedImage.setRGB(x1, y1, color);
            for (int x = x1 + sx, y = y1, i = 1; i <= dx; ++i, x += sx)
            {
                if (d > 0)
                {
                    d += d2;
                    y += sy;
                } else
                {
                    d += d1;
                }
                bufferedImage.setRGB(x, y, color);
            }
        } else
        {
            int d = (dx / 2) - dy;
            int d1 = dx / 2;
            int d2 = (dx - dy) / 2;
            bufferedImage.setRGB(x1, y1, color);
            for (int x = x1, y = y1 + sy, i = 1; i <= dy; ++i, y += sy)
            {
                if (d > 0)
                {
                    d += d2;
                    x += sx;
                } else
                {
                    d += sx;
                }
                bufferedImage.setRGB(x, y, color);
            }
        }
    }

}
