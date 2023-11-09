
package dba_p2;

/**
 *
 * @author ana
 */
public class Coordinates {
    int x;
    int y;
    //int visitados;
    
    Coordinates(){
        x = 0;
        y = 0;
    //    visitados = 0;
    }
    
    Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    //    visitados = 0;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Coordinates) {
            Coordinates otroPunto = (Coordinates) obj;
            return x == otroPunto.x && y == otroPunto.y;
        }
        return false;
    }

    // public static void main(String args[]) {
    //     Coordinates c1 = new Coordinates(1,3);
    //     Coordinates c2 = new Coordinates(1,3);
    //     Coordinates c3 = new Coordinates(1,6);
    //     System.out.println(c1.equals(c2));
    //     System.out.println(c1.equals(c3));
    // }
}
