package pseudo3dRacing;

import GameObjects.Static;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class RoadAppMain extends JFrame {

    private static final int D_W=1500;
    private static final int D_H=1200;
    ArrayList<Line> lines = new ArrayList<Line>();

    int width=1600;
    int height = 768;
    double pos=0;
    double playerX=0;

    boolean up;
    boolean down;
    boolean left;
    boolean right;


    int N;
    int roadW = 2000;
    int segL=200;
    double camD=0.84;

    public RoadAppMain(){

        // Aqui se genera el mapa
        ArrayList<Static> objetos = new ArrayList<Static>();
        objetos.add(new Static("hueco", 35000, 100));
        DrawPanel drawPanel = new DrawPanel(objetos);
        for(int i=0;i<1600;i++){
            Line line = new Line();
            line.z = i*segL;

            if(i>200 && i<700){
                line.curve=0.5;
                //playerX-=200;
            }
            lines.add(line);
        }

        N=lines.size();
        add(drawPanel);
        addKeyListener(new Keychecker());
        //pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(up){
                        pos+=0.001;
                        if(pos>200*segL && pos<700*segL){
                            playerX -=0.0001;
                        }
                    }
                    if(down){
                        pos-=0.001;
                    }
                    if(left){
                        playerX+=0.0005;
                    }
                    if(right){
                        playerX-=0.0005;
                    }
                    drawPanel.repaint();
                }

            }
        }).start();



        //add(drawPanel);
    }

    class Keychecker extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){
            if(event.getKeyCode() == KeyEvent.VK_UP){
                up = true;
                //drawPanel.repaint();
            }
            if(event.getKeyCode() == KeyEvent.VK_DOWN){
                down = true;
                //drawPanel.repaint();
            }
            if(event.getKeyCode() == KeyEvent.VK_RIGHT){
                right = true;
                //drawPanel.repaint();
            }
            if (event.getKeyCode() == KeyEvent.VK_LEFT){
                left = true;
                //drawPanel.repaint();
            }
            /*if(draw){
                drawPanel.repaint();
            }*/
        }

        @Override
        public void keyReleased(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_UP){
                up = false;
                //drawPanel.repaint();
            }
            if(event.getKeyCode() == KeyEvent.VK_DOWN){
                down = false;
                //drawPanel.repaint();
            }
            if(event.getKeyCode() == KeyEvent.VK_RIGHT){
                right = false;
                //drawPanel.repaint();
            }
            if (event.getKeyCode() == KeyEvent.VK_LEFT){
                left = false;
                //drawPanel.repaint();
            }
        }
    }

    private class DrawPanel extends JPanel{

        ArrayList<Static> objetos;

        public DrawPanel(ArrayList<Static> objetos) {
            this.objetos = objetos;
            InputMap inputMap = getInputMap();
            ActionMap actionMap = getActionMap();
            //this.setPreferredSize(getPrefferredSize());
            //addKeyListener(new Keychecker());
            /*
            String VK_UP = "VK_UP";
            KeyStroke WVK_UP = KeyStroke.getKeyStroke(KeyEvent.VK_U,0);
            inputMap.put(WVK_UP, VK_UP);
            actionMap.put(WVK_UP, new AbstractAction(){
                @Override
                public void actionPerformed(ActionEvent e){
                    pos +=200;
                    drawPanel.repaint();
                    System.out.println('a');
                }
            });
            String VK_DOWN = "VK_DOWN";
            KeyStroke WVK_DOWN = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0);
            inputMap.put(WVK_DOWN, VK_DOWN);
            actionMap.put(WVK_DOWN, new AbstractAction(){
                @Override
                public void actionPerformed(ActionEvent e){
                    pos -=200;
                    drawPanel.repaint();
                }
            });*/
        }

        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            System.out.println("Pos: " + pos + " posx " + playerX);
            try {
                drawValues(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void drawValues(Graphics g) throws IOException {
            //drawQuad(g,Color.green, 500, 500, 200, 500,300,100);
            // ######################## draw road ############################## //
            int startPos = (int) (pos/segL);
            double x=0, dx= 0;

            for(int n=startPos; n<startPos+300;n++){
                Line l = lines.get(n%N);

                //System.out.println("Pos: " + pos + " posx: " +playerX );
                //System.out.println(l.z);
                if(playerX>roadW+100){
                    playerX = roadW+100;
                }else if(playerX<-roadW-100){
                    playerX = -roadW-100;
                }
                l.project(playerX-(int)x, 1500, pos);



                x+=dx;
                dx+=l.curve;

                Color grass = ((n/2)%2)==0? new Color(16,200,16):new Color(0,154,0);
                Color rumble = ((n/2)%2)==0? new Color(255,255,255):new Color(255,0,0);
                Color road = Color.black;

                Line p=null;
                if(n==0){
                    p=l;
                }else{
                    p=lines.get((n-1) % N);
                }
                //System.out.println(l.z);
                for (Static objeto:objetos
                ) {
                    if(objeto.getPosM() == l.z && objeto.getPosM() - pos <33000){
                        drawImg(g, "hueco", (int)p.X,(int)p.Y,(int)p.W,(int)l.x,(int)l.Y,(int)l.W, objeto, pos, 1500, playerX-(int)x);
                    }
                }
                drawQuad(g,grass,0, (int) p.Y, width, 0, (int)l.Y, width);
                drawQuad(g, rumble, (int)p.X, (int) p.Y, (int)(p.W*1.2),(int)l.X,(int)l.Y, (int)(l.W*1.2));
                drawQuad(g, road, (int)p.X, (int)p.Y, (int)p.W, (int)l.X,(int)l.Y, (int)l.W);

            }
            Graphics skyG=g;
            skyG.setColor(Color.blue);
            skyG.fillRect(0,0,D_W, 395);
            Image imagen = ImageIO.read(getClass().getResource("/resources/images/car1.png")).getScaledInstance(275,225,Image.SCALE_DEFAULT);
            g.drawImage(imagen, width/2 -imagen.getWidth(this)/2, height-imagen.getHeight(this)/2, this);
        }

        void drawQuad(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2){
            Graphics graphics =g;
            int[] xpoints = {x1-w1,x2-w2,x2+w2,x1+w1};
            int[] yPoints = {y1,y2,y2,y1};
            int nPoints = 4;
            graphics.setColor(c);
            graphics.fillPolygon(xpoints, yPoints, nPoints);
        }

        void drawImg(Graphics g, String type, int x1, int y1, int w1, int x2, int y2, int w2, Static objeto, double camZ, int camY, double camX){
            Image imagen = null;
            double scale = camD/(objeto.getPosM()-camZ);
            double X=(1+scale*((x2)-camX))*width/2;
            try {
                if (type.equals("hueco")) {
                    imagen = ImageIO.read(getClass().getResource("/resources/images/obs.png")).getScaledInstance(w1/3,w1/3,Image.SCALE_DEFAULT);
                }
            }catch(IOException e){
                System.out.println(e);
            }
            //System.out.println(X);
            //System.out.println(camD/objeto.getPosM());
            //System.out.println("1.Scale: "+scale+ " camD: " + camD+ "z: "+objeto.getPosM() + "camz: " + camZ);
            g.drawImage(imagen, (int)(X),y2,this);
        }

        public Dimension getPreferredSize(){
            return new Dimension(D_W, D_H);
        }
    }

    private class Line{
        double x,y,z;
        double X,Y,W;
        double scale, curve;

        public Line(){
            curve = x=y=z=0;
        }

        // World to screen
        void project(double camX, int camY, double camZ){
            scale = camD/(z-camZ);
            X=(1+scale*(x-camX))*width/2;
            //System.out.println(X);
            //System.out.println("1.Scale: "+scale+ " camD: " + camD+ "z: "+z + "camz: " + camZ);
            Y=(1-scale*(y-camY))*height/2;
            W=scale*roadW*width/2;
        }
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RoadAppMain();
            }
        });

    }
}
