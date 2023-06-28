import javax.swing.*;
import java.awt.*;

public class DrawComponent extends JPanel {
    private Sandbox instance;

    public DrawComponent(Sandbox instance){
        super();
        this.instance = instance;
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(Sandbox.WIDTH*Sandbox.MULTIPLIER,Sandbox.HEIGHT*Sandbox.MULTIPLIER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int longeur = 0;
        int currentid = instance.game[0][0];

        for(int j = 0; j < Sandbox.HEIGHT; j++){
            for(int i = 0; i < Sandbox.WIDTH; i++){
                if(instance.game[i][j] == currentid){
                    longeur ++;
                }else{
                    switch (currentid){
                        case 0:
                            g2d.setColor(Color.cyan);
                            break;
                        case 1:
                            g2d.setColor(Color.gray);
                            break;
                        case 2:
                            g2d.setColor(Color.yellow);
                            break;
                        case 3:
                            g2d.setColor(Color.blue);
                            break;

                    }

                    g2d.fillRect((i-longeur)*Sandbox.MULTIPLIER,j*Sandbox.MULTIPLIER,longeur*Sandbox.MULTIPLIER,Sandbox.MULTIPLIER);

                    currentid = instance.game[i][j];
                    longeur = 1;
                }
            }
            if(longeur > 0){
                switch (currentid){
                    case 0:
                        g2d.setColor(Color.cyan);
                        break;
                    case 1:
                        g2d.setColor(Color.gray);
                        break;
                    case 2:
                        g2d.setColor(Color.yellow);
                        break;
                    case 3:
                        g2d.setColor(Color.blue);
                        break;

                }

                g2d.fillRect((Sandbox.WIDTH-longeur)*Sandbox.MULTIPLIER,j*Sandbox.MULTIPLIER,longeur*Sandbox.MULTIPLIER,Sandbox.MULTIPLIER);
            }
            if(j < Sandbox.HEIGHT-1){
                longeur =0;
                currentid = instance.game[0][j+1];
            }
        }
        GameMaster.Step(instance.game,instance.nextGen);
    }
}
