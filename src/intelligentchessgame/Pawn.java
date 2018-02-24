package intelligentchessgame;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Pawn extends ChessPiece {

    protected boolean enPassant_black;
    protected boolean enPassant_white;

    public Pawn(Point place, Point listIndex, ImageIcon dark, ImageIcon white) {
        super(place, listIndex, dark, white);
        enPassant_black = false;
        enPassant_white = false;
    }

    @Override
    public void setPlace(Point place) {
        super.setPlace(place);
        if (this.currPlace.y == 1 && this.listIndex.x == 0 && place.y == 3) {
            enPassant_black = true;
        } else {
            enPassant_black = false;
        }
        
    }

    @Override
    public void set_possible_moves(ArrayList<ArrayList<ChessPiece>> arr) {
        this.possibleMoves.clear();

        if (this.getPlace().x == -1)
            return;
        Point oldPlace = this.currPlace;
        King king = (King)arr.get(listIndex.x).get(7);
        Point p;
        if (listIndex.x == 0) {   // Black pawn
            p = new Point(currPlace.x, currPlace.y + 1);
            if (p.y <= 7 && !Controller.friend_collision(listIndex, p, arr) && !Controller.enemy_collision(listIndex, p, arr)) {
                currPlace = p;
                if(king.isSafe(arr)){
                    currPlace=oldPlace;
                    this.possibleMoves.add(new Point(p));
                }
                currPlace = oldPlace;
                p.y++;
                if (this.currPlace.y == 1 && !Controller.friend_collision(listIndex, p, arr) && !Controller.enemy_collision(listIndex, p, arr)) {   //First move
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;
                }
                p.y--;
            }
        } else if (listIndex.x == 1) {
            // White pawn
            enPassant_white = false;
            p = new Point(currPlace.x, currPlace.y - 1);
            if (p.y >= 0 && !Controller.friend_collision(listIndex, p, arr) && !Controller.enemy_collision(listIndex, p, arr)) {
                currPlace = p;
                if(king.isSafe(arr)){
                    currPlace=oldPlace;
                    this.possibleMoves.add(new Point(p));
                }
                currPlace = oldPlace;
                p.y--;
                if (this.currPlace.y == 6 && !Controller.friend_collision(listIndex, p, arr) && !Controller.enemy_collision(listIndex, p, arr)) {   //First move
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                        enPassant_white = true;
                    }
                    currPlace = oldPlace;
                }
                p.y++;
            }
        }
        set_enemy_attacks(arr);
    }

    
    public boolean isIsolated(ArrayList<ArrayList<ChessPiece>> arr){
        if(this.currPlace.x==-1){
            return false;
        }
        Point p = new Point();
        for(int k=-1; k<2; k++){
            for(int j=-1; j<2; j++){
                if(!(k == 0 && j == 0) && this.currPlace.x+k>=0 && this.currPlace.x+k<=7 && this.currPlace.y+j>=0 && this.currPlace.y+j<=7){
                    p.x=this.currPlace.x+k;
                    p.y=this.currPlace.y+j;
                    for (int i = 8; i < 16; i++)
                        if ( arr.get(this.listIndex.x).get(i).currPlace.equals(p))
                            return false;
                }
            }
        }
        return true;
    }
    public boolean isDoubled(ArrayList<ArrayList<ChessPiece>> arr){
        Point p = new Point();
        if(this.currPlace.x==-1){
            return false;
        }
        for(int i=8; i<16; i++){
            if(arr.get(this.listIndex.x).get(i) instanceof Pawn){
                p.x = currPlace.x;
                p.y = currPlace.y+1;
                if(p.y<=7 && arr.get(this.listIndex.x).get(i).currPlace.equals(p))
                    return true;
                p.y = p.y-2;
                if(p.y>=0 && arr.get(this.listIndex.x).get(i).currPlace.equals(p))
                    return true;
                }
        }
        return false;
    }
    
    public boolean isBlocked(ArrayList<ArrayList<ChessPiece>> arr){
        Point p = new Point();
        if(this.currPlace.x==-1){
            return false;
        }
        for(int i=0; i<16; i++){
                p.x = currPlace.x;
                if(this.listIndex.x == 0){
                    p.y = currPlace.y+1;
                    if(p.y<=7 && arr.get(1-this.listIndex.x).get(i).currPlace.equals(p))
                        return true;
                }
                else{
                    p.y = p.y-2;
                    if(p.y>=0 && arr.get(1-this.listIndex.x).get(i).currPlace.equals(p))
                        return true;
                }
        }
        return false;
    }


        @Override
    public void set_enemy_attacks(ArrayList<ArrayList<ChessPiece>> arr) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.enemyPiles.clear();
        this.specialMoves.clear();
        if (this.getPlace().x == -1)
            return;
       
        Point p;
        if (listIndex.x == 0) {   // Black pawn
            p = new Point(currPlace.x+1, currPlace.y + 1);
            if (p.y <= 7 && p.x <= 7 && !Controller.friend_collision(listIndex, p, arr)) {
                if (Controller.enemy_collision(listIndex, p, arr)) {
                    this.enemyPiles.add(new Point(p));
                } else if (Controller.adjacent_pawn(listIndex, new Point(currPlace.x + 1, currPlace.y), arr)) {
                    this.specialMoves.add(new Point(p));
                }
            }
            p.x = p.x - 2;
            if (p.y <= 7 && p.x >= 0 && !Controller.friend_collision(listIndex, p, arr)) {
                if (Controller.enemy_collision(listIndex, p, arr)) {
                    this.enemyPiles.add(new Point(p));
                } else if (Controller.adjacent_pawn(listIndex, new Point(currPlace.x - 1, currPlace.y), arr)) {
                    this.specialMoves.add(new Point(p));
                }
            }
        } else if (listIndex.x == 1) {  // White pawn
            enPassant_white = false;
            p = new Point(currPlace.x+1, currPlace.y - 1);
            if (p.y >= 0 && p.x <= 7 && !Controller.friend_collision(listIndex, p, arr)) {
                if (Controller.enemy_collision(listIndex, p, arr)) {
                    this.enemyPiles.add(new Point(p));
                } else if (Controller.adjacent_pawn(listIndex, new Point(currPlace.x + 1, currPlace.y), arr)) {
                    this.specialMoves.add(new Point(p));
                }
            }
            p.x = p.x - 2;
            if (p.y >= 0 && p.x >= 0 && !Controller.friend_collision(listIndex, p, arr)) {
                if (Controller.enemy_collision(listIndex, p, arr)) {
                    this.enemyPiles.add(new Point(p));
                } else if (Controller.adjacent_pawn(listIndex, new Point(currPlace.x - 1, currPlace.y), arr)) {
                    this.specialMoves.add(new Point(p));
                }
            }
        }
    }       
}

