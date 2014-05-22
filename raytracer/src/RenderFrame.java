import raytracer.*;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Anton on 22.05.2014.
 */
public class RenderFrame {
    /**
     * Ctor that manages adding menu items, actions, canvas to the mainFrame.
     * May be I should do Init() method instead.
     */
    public RenderFrame() {
        final MainFrame mainFrame = new MainFrame("Лабораторная работа №4");

        AbstractAction contacts = new AbstractAction("Контакты", MainFrame.createImageIcon("/images/contacts.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(mainFrame, "name: Dorozhko Anton \n mail: dorozhko.a@gmail.com\n ");
            }
        };
        contacts.putValue(AbstractAction.SHORT_DESCRIPTION, "Как со мной связаться ^_^");
        mainFrame.addAction(contacts);
        mainFrame.getAboutMenu().add(contacts);

        AbstractAction aboutAction = new AbstractAction("О программе", MainFrame.createImageIcon("/images/help.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO: fill
            }
        };
        aboutAction.putValue(AbstractAction.SHORT_DESCRIPTION, "Кэп сообщает, что это краткое описание задания");
        mainFrame.addAction(aboutAction);
        mainFrame.getAboutMenu().add(aboutAction);

        AbstractAction exitAction = new AbstractAction("Выход", MainFrame.createImageIcon("/images/cross.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        mainFrame.addAction(exitAction);

        mainFrame.getFileMenu().add(exitAction);

        mainFrame.addCanvas(new RenderPanel());
        mainFrame.setFocusable(true);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    class RenderPanel extends JPanel {
        private BufferedImage renderedImage;
        private Scene scene;
        private RenderContext renderContext;
        private Camera camera;

        RenderPanel() {
            scene = new Scene();
            scene.addSceneObject(new Triangle3D(
                    new Vector3D(-700, -700, -130),
                    new Vector3D(700, -700, -130),
                    new Vector3D(0, 400, -130),
                    Color.blue,
                    new Material(0, 0, 0, 0.6, 0, 0)
            ));
            camera = new Camera(new Vector3D(0, 500, 0),
                    -Math.PI/2, 0, Math.PI, 320);

            renderContext = new RenderContext(256, 256,
                    camera,
                    scene,
                    Color.yellow);

            renderedImage = Renderer.render(renderContext);

            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    switch (e.getKeyChar()) {
                        case 'a':
                            System.out.println("a");
                            camera.setAlZ(camera.getAlZ() + Math.PI / 36);
                            break;
                        case 'd':
                            System.out.println("d");
                            camera.setAlZ(camera.getAlZ() - Math.PI / 36);
                            break;
                    }
                    renderedImage = Renderer.render(renderContext);
                    repaint();
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(renderedImage, 0, 0, null);
        }
    }
}
