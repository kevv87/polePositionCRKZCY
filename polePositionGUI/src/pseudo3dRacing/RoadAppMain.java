package pseudo3dRacing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class RoadAppMain extends JFrame {

    private static final int D_W=1500;
    private static final int D_H=1200;
    DrawPanel drawPanel = new DrawPanel();
    ArrayList<Line> lines = new ArrayList<Line>();

    int width=1600;
    int height = 768;
    int pos=0;
    int playerX=0;


    int N;
    int roadW = 2000;
    int segL=200;
    double camD=0.84;

    public RoadAppMain(){

        // Aqui se genera el mapa
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



        //add(drawPanel);
    }

    class Keychecker extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){
            char ch = event.getKeyChar();
            System.out.println(event.getKeyCode());
            boolean draw = false;
            if(event.getKeyCode() == KeyEvent.VK_UP){
                pos+=200;
                draw=true;
                //drawPanel.repaint();
            }if(event.getKeyCode() == KeyEvent.VK_DOWN){
                pos-=200;
                draw=true;
                //drawPanel.repaint();
            } if(event.getKeyCode() == KeyEvent.VK_RIGHT){
                playerX +=200;
                draw=true;
                //drawPanel.repaint();
            } if (event.getKeyCode() == KeyEvent.VK_LEFT){
                playerX-=200;
                draw=true;
                //drawPanel.repaint();
            }

            if(draw){
                drawPanel.repaint();
            }
        }
    }

    private class DrawPanel extends JPanel{
        public DrawPanel() {
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

            drawValues(g);
        }

        private void drawValues(Graphics g){
            //drawQuad(g,Color.green, 500, 500, 200, 500,300,100);
            // ######################## draw road ############################## //
            int startPos = pos/segL;
            double x=0, dx= 0;

            for(int n=startPos; n<startPos+300;n++){
                Line l = lines.get(n%N);
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

                drawQuad(g,grass,0, (int) p.Y, width, 0, (int)l.Y, width);
                drawQuad(g, rumble, (int)p.X, (int) p.Y, (int)(p.W*1.2),(int)l.X,(int)l.Y, (int)(l.W*1.2));
                drawQuad(g, road, (int)p.X, (int)p.Y, (int)p.W, (int)l.X,(int)l.Y, (int)l.W);

            }
            Graphics skyG=g;
            skyG.setColor(Color.blue);
            skyG.fillRect(0,0,D_W, 387);
        }

        void drawQuad(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2){
            Graphics graphics =g;
            int[] xpoints = {x1-w1,x2-w2,x2+w2,x1+w1};
            int[] yPoints = {y1,y2,y2,y1};
            int nPoints = 4;
            graphics.setColor(c);
            graphics.fillPolygon(xpoints, yPoints, nPoints);
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
        void project(int camX, int camY, int camZ){
            scale = camD/(z-camZ);
            X=(1+scale*(x-camX))*width/2;
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
