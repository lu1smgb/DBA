
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coordinates otroPunto = (Coordinates) obj;
        return x == otroPunto.x && y == otroPunto.y;
    }
}
