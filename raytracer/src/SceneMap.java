import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
        generateImage();


        MouseAdapter adepter = new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (checkPoint(e.getX(), e.getY()))
                {
                    //TODO repaint scene
                    renderPanel.changeCamerePosition(e.getX() * REDUCTION_COEFFICIENT, e.getY() * REDUCTION_COEFFICIENT);
                }
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

    private void generateImage()
    {
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
    }
}
