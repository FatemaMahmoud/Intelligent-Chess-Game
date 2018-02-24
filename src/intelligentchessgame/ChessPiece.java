
package intelligentchessgame;

import java.awt.Point;
import java.io.Serializable;
import javax.swing.ImageIcon;
import java.util.ArrayList;



abstract public class ChessPiece implements Serializable {
    
    protected Point currPlace;
    protected Point listIndex;
    protected ArrayList<Point> possibleMoves;
    protected ArrayList<Point> enemyPiles;
    protected ArrayList<Point> specialMoves;
    protected ArrayList<Point> nextMoves;
    protected ImageIcon dark;
    protected ImageIcon white;
    
    public ChessPiece(Point place, Point listIndex, ImageIcon dark, ImageIcon white){
        this.currPlace = place;
        this.listIndex = listIndex;
        this.dark = dark;
        this.white = white;
        this.possibleMoves = new ArrayList<>();        
        this.enemyPiles = new ArrayList<>();
        this.specialMoves = new ArrayList<>();
        this.nextMoves = new ArrayList<>();
    }
    
    public Point getPlace(){
        return this.currPlace;
    }
    
    public void setPlace(Point place){
        this.currPlace = place;
        //this.set_possible_moves();
    }
    
    public boolean valid_move(Point p){
        return this.possibleMoves.contains(p);
    }
    
    public ImageIcon getDark(){
        return this.dark;
    }
    
    public ImageIcon getWhite(){
        return this.white;
    }
    
    abstract public void set_possible_moves(ArrayList<ArrayList<ChessPiece>> arr);
    abstract public void set_enemy_attacks(ArrayList<ArrayList<ChessPiece>> arr);
    
    public ArrayList<Point> get_possible_moves(){
        ArrayList<Point> moves = new ArrayList();
        for (int i = 0 ; i < this.possibleMoves.size() ; i++){
            moves.add(new Point(this.possibleMoves.get(i).x, this.possibleMoves.get(i).y));
        }
        for (int i = 0 ; i < this.specialMoves.size() ; i++){
            moves.add(new Point(this.specialMoves.get(i).x, this.specialMoves.get(i).y));
        }
        return moves;
    }
    
    public ArrayList<Point> get_enemy_piles(){
        ArrayList<Point> moves = new ArrayList();
        
        for (int i = 0 ; i < this.enemyPiles.size() ; i++){
            moves.add(new Point(this.enemyPiles.get(i).x, this.enemyPiles.get(i).y));
        }
        return moves;
       
    }
    
    
    
}