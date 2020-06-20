package pseudo3dRacing;

import GameObjects.Car;
import GameObjects.Static;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;


/**
 * * Clase inicial, se encarga de ejecutar el juego.
 * *
 * * @author Kevin Zeledon
 * * @version 1.0
 * * @since  06/19/2020
 * */
public class RoadAppMain extends JFrame {

    private static final int D_W=1500; // Ancho de pantalla
    private static final int D_H=1200; // Alto de pantala
    ArrayList<Line> lines = new ArrayList<Line>();  // Lista de todas las de la carretera

    int width=1600;// Ancho dibujable
    int height = 768;  // Alto dibujable
    double pos=0;  // Posicion de la camara
    double playerX=0;  // Posicion en x de la camara

    // Variables que guardan la tecla que se está presionando
    boolean up;
    boolean down;
    boolean left;
    boolean right;


    Car myCar;  // Mi carro

    int N; // Cantidad de lineas
    int roadW = 2000;  // Ancho de la calle.
    int segL=200; // Largo de cada segmento de la carretera
    double camD=0.84;  // Profundidad de la camara

    // Constructor
    public RoadAppMain(){

        // Aqui se genera el mapa
        ArrayList<Static> objetos = new ArrayList<Static>();

        // Add static objects
        objetos.add(new Static("hueco", 35000, 1000));
        objetos.add(new Static("palm",35000, roadW+500));

        myCar = new Car(0);
        DrawPanel drawPanel = new DrawPanel(objetos);

        // Agregando los segmentos
        for(int i=0;i<1600;i++){
            Line line = new Line();
            line.z = i*segL;

            // Agregando curvas
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

        // Hilo de inputs
        new Thread(new Runnable() {

            double time = 0;
            double ptime = 0;

            @Override
            public void run() {
                while(true){
                    if(time == ptime) {
                        time = System.nanoTime();
                       // System.out.println(ptime);
                        //System.out.println(time);
                    }else if((time-ptime)/1000000000 > 1){
                        drawPanel.addSecond();
                        time = System.nanoTime();
                        ptime = time;
                    }else{
                        time = System.nanoTime();
                    }
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

    }

    /**
     * * Clase encargada de escuchar los inputs del usuario en el
     * teclado
     * @author Kevin Zeledon
     * @version 1.0
     * @since  06/19/2020
     * */
    class Keychecker extends KeyAdapter{

        // Eventos a iniciar cuando se presionan teclas
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
            if(event.getKeyCode() == KeyEvent.VK_LEFT){
                right = true;
                myCar.right();
                //drawPanel.repaint();
            }
            if (event.getKeyCode() == KeyEvent.VK_RIGHT){
                left = true;
                myCar.left();
                //drawPanel.repaint();
            }
            /*if(draw)
                drawPanel.repaint();
            }*/
        }

        // Eventos a iniciar cuando se sueltan teclas
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
            if(event.getKeyCode() == KeyEvent.VK_LEFT){
                right = false;
                myCar.normal();
                //drawPanel.repaint();
            }
            if (event.getKeyCode() == KeyEvent.VK_RIGHT){
                left = false;
                myCar.normal();
                //drawPanel.repaint();
            }
        }
    }

    /**
     * Canvas personalizado en el cual se dibuja all.
     *
     * @author Kevin Zeledon
     * @version 1.0
     * @since  06/19/2020
     * */
    private class DrawPanel extends JPanel{

        ArrayList<Static> objetos;  // Objetos estaticos a dibujar
        double tiempo=0;

        // Constructor de la clase
        public DrawPanel(ArrayList<Static> objetos) {
            this.objetos = objetos;
        }

        /**
         * Metodo que se llama automaticamente para pintar los componentes del grafico
         * @return Nada
         * @param g Graphics en el cual se va a dibujar
         */
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            //System.out.println("Pos: " + pos + " posx " + playerX);
            try {
                drawValues(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Metodo especializado para dibujar en el canvas
         *
         * @return nada
         * @param g Graphics en el cual se va a dibujar
         * @throws IOException
         */
        private void drawValues(Graphics g) throws IOException {
            //drawQuad(g,Color.green, 500, 500, 200, 500,300,100);
            // ######################## draw road ############################## //
            int startPos = (int) (pos/segL);
            double x=0, dx= 0;

            ArrayList<Static> pendingDraw = new ArrayList<>();

            for(int n=startPos; n<startPos+300;n++){
                Line l = lines.get(n%N);

                //System.out.println("Pos: " + pos + " posx: " +playerX );
                //System.out.println(l.z);

                //todo: Hacer verificacion de

                // Se mueve el carro a un lado segun la curva
                if(playerX>roadW+100){
                    playerX = roadW+100;
                }else if(playerX<-roadW-100){
                    playerX = -roadW-100;
                }

                l.project(playerX-(int)x, 1500, pos);

                x+=dx;
                dx+=l.curve;

                // Colores de la carretera
                Color grass = ((n/2)%2)==0? new Color(16,200,16):new Color(0,154,0);
                Color rumble = ((n/2)%2)==0? new Color(255,255,255):new Color(255,0,0);
                Color road = Color.black;

                Line p=null;
                if(n==0){
                    p=l;
                }else{
                    p=lines.get((n-1) % N);
                }

                // Recorriendo la lista de objetos para pintarlos
                for (Static objeto:objetos
                ) {
                    if(objeto.getPosM() == l.z && objeto.getPosM() - pos <33000){
                        pendingDraw.add(new Static(g, objeto.type, (int)p.x,(int)p.Y,(int)p.W,playerX-(int)x, pos, objeto.getPosM(), objeto.getPosX()));
                        //System.out.println(l.X);
                        //drawImg(g, "hueco", (int)l.x,(int)l.Y,(int)l.W, objeto,pos,playerX-(int)x);
                    }
                }

                //Dibujando el juego.
                drawQuad(g,grass,0, (int) p.Y, width, 0, (int)l.Y, width);
                drawQuad(g, rumble, (int)p.X, (int) p.Y, (int)(p.W*1.2),(int)l.X,(int)l.Y, (int)(l.W*1.2));
                drawQuad(g, road, (int)p.X, (int)p.Y, (int)p.W, (int)l.X,(int)l.Y, (int)l.W);

            }

            // Recorriendo la lista de objetos pendientes a dibujar y dibujando
            for (Static objeto:pendingDraw
                 ) {
                //System.out.println(objeto.x);
                drawImg(g,objeto.type,objeto.x,objeto.y, objeto.w, objeto, objeto.camZ, objeto.camX);
            }

            //Dibujando cielo
            Graphics skyG=g;
            skyG.setColor(Color.blue);
            skyG.fillRect(0,0,D_W, 395);

            // Draw my car
            Image imagen = ImageIO.read(getClass().getResource(myCar.imagen)).getScaledInstance(275,200,Image.SCALE_DEFAULT);
            g.drawImage(imagen, width/2 -imagen.getWidth(this)/2, height-imagen.getHeight(this)/2, this); // Draw car
        }


        /**
         * Metodo para dibujar un segmento del piso
         *
         * @return Nada
         *
         * @param g Grafico en el cual se dibuja todo
         * @param c Color de lo que se va a dibujar
         * @param x1 La coordenada en x de la primera esquina
         * @param y1 La coordenada en y de la primera esquina
         * @param w1 ancho del segmento anterior
         * @param x2 La coordenada en x de la segunda esquina
         * @param y2 La coodenarada en y de la segunda esquina
         * @param w2 ancho del segmento actual
         */
        void drawQuad(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2){
            Graphics graphics =g;
            int[] xpoints = {x1-w1,x2-w2,x2+w2,x1+w1};
            int[] yPoints = {y1,y2,y2,y1};
            int nPoints = 4;
            graphics.setColor(c);
            graphics.fillPolygon(xpoints, yPoints, nPoints);
        }

        /**
         * Metodo para dibujar una imagen
         *
         * @return nada
         *
          * @param g Grafico en el cual se dibuja
         * @param type Tipo de imagen a dibujar
         * @param x1 Coordenada en x en el mundo donde se va a dibujar
         * @param y1 Coordenada en y en el mundo donde se va a dibujar
         * @param w1 Dimensiones nuevas de la imagen
         * @param objeto Objeto que se va a dibujar/
         * @param camZ Posicion de la camara en z
         * @param camX Posicion de la camara en x
         */

        void drawImg(Graphics g, String type, int x1, int y1, int w1, Static objeto, double camZ, double camX){
            Image imagen = null;
            double scale = camD/(objeto.getPosM()-camZ);
            double X=(1+scale*(x1+objeto.getPosX()-camX))*width/2;

            try {
                /*
                if (type.equals("hueco")) {
                    imagen = ImageIO.read(getClass().getResource("/resources/images/obs.png")).getScaledInstance(w1/2,w1/2,Image.SCALE_DEFAULT);
                }*/
                imagen = ImageIO.read(getClass().getResource(objeto.imagen)).getScaledInstance(w1/4,w1/4, Image.SCALE_DEFAULT);
            }catch(IOException e){
                System.out.println(e);
            }
            //System.out.println(X);
            //System.out.println(camD/objeto.getPosM());
            //System.out.println("1.Scale: "+scale+ " camD: " + camD+ "z: "+objeto.getPosM() + "camz: " + camZ);
            g.drawImage(imagen, (int)(X),y1-imagen.getHeight(this),this);
            //System.out.println("Scale: "+scale+" x: "+x1+" camX: "+ camX+" camD: "+camD);
        }

        // Metodo que establece el tamanno de la pantalla

        /**
         * Metodo que establece el tamanno de la pantalla
         *
         * @return Dimension
         */
        public Dimension getPreferredSize(){
            return new Dimension(D_W, D_H);
        }

        /**
         * Funcion para agregar un segundo al contador
         *
         * @return nada
         */
        public void addSecond() {
            System.out.println((tiempo+=1));
        }
    }

    /**
     * Segmento del piso
     *
     * @author Kevin Zeledon
     * @version 1.0
     * @since  06/19/2020
     *
     */
    private class Line{
        double x,y,z;  // Posiciones en el mundo
        double X,Y,W;  // Posiciones en pantalla
        double scale, curve;  // Valores de transformacion

        //Constructor
        public Line(){
            curve = x=y=z=0;
        }

        // Projecting world to screen

        /**
         * Metodo para proyectar del mundo a la pantalla
         *
         * @return nada
         * @param camX Camara en x
         * @param camY Camara en y.
         * @param camZ Camara en z
         */
        void project(double camX, int camY, double camZ){
            scale = camD/(z-camZ);
            X=(1+scale*(x-camX))*width/2;
            //System.out.println(X);
            //System.out.println("1.Scale: "+scale+ " camD: " + camD+ "z: "+z + "camz: " + camZ);
            Y=(1-scale*(y-camY))*height/2;
            W=scale*roadW*width/2;
        }
    }

    //Metodo principal
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RoadAppMain();
            }
        });

    }
}
