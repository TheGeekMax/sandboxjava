import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameMaster {
    //fonctions intermediaire
    private static int abs(int x){
        return (x < 0)?-x:x;
    }

    private static int distSqared(int x1, int y1, int x2, int y2){
        return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
    }

    private static boolean isFree(int x, int y, int[][] game, int[][] nextGen){
        if(x < 0 || x >= Sandbox.HEIGHT || y < 0 ||y >= Sandbox.HEIGHT){
            return false;
        }
        return (game[x][y] == 0) && (nextGen[x][y] == 0);
    }

    private static void moveTo(int oldx, int oldy,int x, int y, int id, int[][] game, int[][] nextGen){
        game[oldx][oldy] = 0;
        nextGen[x][y] = id;
    }

    private static void exchange(int x1, int y1,int x2, int y2, int[][] game, int[][] nextGen,int id1, int id2){
        nextGen[x1][y1] = id2;
        nextGen[x2][y2] = id1;
        game[x1][y1] = 0;
        game[x2][y2] = 0;
    }

    static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static void drawCircle(int[][] game, int x, int y, int r){
        for(int i = Math.min(0,x-r); i <= Math.max(Sandbox.WIDTH-1,x+r); i++){
            for(int j = Math.min(0,x-r); j <= Math.max(Sandbox.HEIGHT-1,x+r); j++){
                if(distSqared(x,y,i,j) < r*r){
                    game[i][j] = Sandbox.currentBlock;
                }
            }
        }
    }

    public static void Step(int[][] game, int[][] nextGen){
        //on def un tableau de longeur WIDTH
        int[] rdm = new int[Sandbox.WIDTH];
        for(int i = 0 ; i < Sandbox.WIDTH;i++){
            rdm[i]=i;
        }

        for(int j = Sandbox.HEIGHT - 1; j >= 0; j--) {
            shuffleArray(rdm);
            for (int i = 0; i < Sandbox.WIDTH; i++) {
                int x = rdm[i];
                switch (game[x][j]) {
                    case 2:
                        //sable
                        if(j+1 < Sandbox.HEIGHT && (game[x][j+1] == 3 || nextGen[x][j+1] == 3)){
                            exchange(x,j,x,j+1,game,nextGen,2,3);
                        }else if (isFree(x, j + 1, game, nextGen)) {
                            moveTo(x, j, x, j + 1, 2, game, nextGen);
                        } else if (isFree(x + 1, j + 1, game, nextGen) && isFree(x - 1, j + 1, game, nextGen)) {
                            if (Math.random() > 0.5) {
                                moveTo(x, j, x + 1, j + 1, 2, game, nextGen);
                            } else {
                                moveTo(x, j, x - 1, j + 1, 2, game, nextGen);
                            }
                        } else if (isFree(x + 1, j + 1, game, nextGen)) {
                            moveTo(x, j, x + 1, j + 1, 2, game, nextGen);
                        } else if (isFree(x - 1, j + 1, game, nextGen)) {
                            moveTo(x, j, x - 1, j + 1, 2, game, nextGen);
                        } else {
                            moveTo(x, j, x, j, 2, game, nextGen);
                        }

                        break;
                    case 3:
                        //eau
                        if (isFree(x, j + 1, game, nextGen)) {
                            //System.out.println("BAS EAU BAB");
                            moveTo(x, j, x, j + 1, 3, game, nextGen);
                        } else if (isFree(x + 1, j, game, nextGen) && isFree(x - 1, j, game, nextGen)) {
                            //System.out.println("RAND EAU BAB");
                            if (Math.random() > 0.5) {
                                moveTo(x, j, x + 1, j, 3, game, nextGen);
                            } else {
                                moveTo(x, j, x - 1, j, 3, game, nextGen);
                            }
                        } else if (isFree(x + 1, j, game, nextGen)) {
                            //System.out.println("DROITE EAU BAB");
                            moveTo(x, j, x + 1, j, 3, game, nextGen);
                        } else if (isFree(x - 1, j, game, nextGen)) {
                            //System.out.println("GAUCHE EAU BAB");
                            moveTo(x, j, x - 1, j, 3, game, nextGen);
                        } else {
                            //System.out.println("STABLE EAU BAB");
                            moveTo(x, j, x, j, 3, game, nextGen);
                        }
                        break;

                }
            }
        }

        //on remplace nextgen par game
        for(int i = 0; i < Sandbox.WIDTH; i++){
            for(int j = Sandbox.HEIGHT-1; j >= 0 ; j--) {
                game[i][j] = nextGen[i][j];
                nextGen[i][j] = 0;
            }
        }
    }
}
