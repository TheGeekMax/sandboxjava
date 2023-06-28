import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Sandbox extends JFrame {
    //constantes associé a la taille de la frame
    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;
    public static final int MULTIPLIER = 4;
    public static final int BRUSH_SIZE = 10;


    //variables pour le "jeu"
    public int[][] game = new int[WIDTH][HEIGHT];
    public int[][] nextGen = new int[WIDTH][HEIGHT];


    //// 0- air, 1-block, 2-sand, 3-eau
    public static int currentBlock = 2;

    //variables du pannel
    private DrawComponent canvas = new DrawComponent(this);
    public Sandbox(){
        this.setTitle("Sandbox Test (lazy-version)");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(canvas);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        //events
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println(e.getX());
                GameMaster.drawCircle(game,e.getX()/MULTIPLIER,e.getY()/MULTIPLIER-(BRUSH_SIZE),BRUSH_SIZE);
            }
        });

        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getWheelRotation() >0){
                    currentBlock = 2;
                }else{
                    currentBlock = 3;
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                GameMaster.drawCircle(game,e.getX()/MULTIPLIER,e.getY()/MULTIPLIER-(BRUSH_SIZE),BRUSH_SIZE);
            }
        });

        //on def les tableaux
        for(int i =0; i < WIDTH; i ++){
            for(int j =0; j < HEIGHT; j ++){
                game[i][j] = 0;
                nextGen[i][j] = 0;
            }
        }
        //GameMaster.drawCircle(game,WIDTH/2,HEIGHT/2,15);
        mainLoop();
    }

    private void mainLoop(){
        while(true){
            canvas.repaint();
        }
    }
    public static void main(String[] args) {
        new Sandbox();
    }
}