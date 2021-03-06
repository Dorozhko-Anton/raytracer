package raytracer;

import java.awt.*;

/**
 * Created by Anton on 21.05.2014.
 */
public class RenderContext {
    private int imageWidth;
    private int imageHeight;

    private Camera camera;
    private Scene scene;
    private Color backgroundColor;

    public RenderContext(int imageWidth, int imageHeight, Camera camera, Scene scene, Color backgroundColor) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.camera = camera;
        this.scene = scene;
        this.backgroundColor = backgroundColor;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public Camera getCamera() {
        return camera;
    }

    public Scene getScene() {
        return scene;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

}
