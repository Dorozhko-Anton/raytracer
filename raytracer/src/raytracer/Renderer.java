package raytracer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Anton on 21.05.2014.
 */
public class Renderer {
    public static BufferedImage render(RenderContext renderContext) {
        BufferedImage bufferedImage = new BufferedImage(renderContext.getImageWidth(),
                renderContext.getImageHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < renderContext.getImageWidth(); i++) {
            for (int j = 0; j < renderContext.getImageHeight(); j++) {
                //TODO: form ray

                //TODO: trace ray


                bufferedImage.setRGB(i, j, Color.white.getRGB());
            }
        }
        return bufferedImage;
    }

    public static void trace(Ray r, RenderContext renderContext, Color color) {
        SceneObject hitObject = renderContext.intersect(r, color);
        if (hitObject != null) {
            color = r.getRayColor();
        } else {
            color = renderContext.getBackgroundColor();
        }
    }
}
