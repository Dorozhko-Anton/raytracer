import raytracer.*;
import raytracer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        RenderPanel renderPanel = new RenderPanel();
        mainFrame.addCanvas(renderPanel);
        mainFrame.setFocusable(true);

        mainFrame.pack();
        mainFrame.setVisible(true);

        SceneMap sceneMap = new SceneMap(renderPanel);
    }

    class RenderPanel extends JPanel {
        private static final double DR = 3;
        private BufferedImage renderedImage;
        private Scene scene;
        private RenderContext renderContext;
        private Camera camera;

        RenderPanel() {
            scene = new Scene();
//            scene.addSceneObject(new Triangle3D(
//                    new Vector3D(-100, 130, -100),
//                    new Vector3D(100, 130, -100),
//                    new Vector3D(0, 130, 80),
//                    Color.blue,
//                    new Material(0.5, 0, 0, 0.3, 0, 0)
//            ));

            camera = new Camera(new Vector3D(0, 0, 0),
                    -Math.PI / 2, 25);
            camera.setWorldPosition(new Vector3D(0, 0, 15));

            renderContext = new RenderContext(250, 250,
                    camera,
                    scene,
                    Color.black);

            renderedImage = Renderer.render(renderContext);

            setFocusable(true);
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    super.mouseClicked(e);
                    requestFocusInWindow();
                }
            });
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    System.out.println(e.getKeyChar());
                    switch (e.getKeyChar()) {
                        case 'w':

                            //camera.rotateCamera(Math.PI / 36, 0, 0);
                            break;
                        case 's':

                            //camera.rotateCamera(-Math.PI / 36, 0, 0);
                            break;
                        case 'a':
                            camera.rotateCamera(-Math.PI / 36, 0, 0);
                            break;
                        case 'd':
                            camera.rotateCamera(Math.PI / 36, 0, 0);
                            break;
                        case 'k':
                            camera.moveCamera(new Vector3D(DR, 0, 0));
                            break;
                        case 'i':
                            camera.moveCamera(new Vector3D(-DR, 0, 0));
                            break;
                        case 'l':
                            camera.moveCamera(new Vector3D(0, -DR, 0));
                            break;
                        case 'j':
                            camera.moveCamera(new Vector3D(0, DR, 0));
                            break;
                        case 'r':
                            camera.moveCamera(new Vector3D(0, 0, DR));
                            break;
                        case 'f':
                            camera.moveCamera(new Vector3D(0, 0, -DR));
                            break;
                    }
                    System.out.println(camera.getWorldPosition());
                    renderedImage = Renderer.render(renderContext);
                    repaint();
                }
            });
        }

        public void changeCamerePosition(double x, double y)
        {
            camera.setWorldPosition(new Vector3D(x, y, 15));

            renderedImage = Renderer.render(renderContext);
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(renderedImage, 0, 0, null);
        }
    }
}
