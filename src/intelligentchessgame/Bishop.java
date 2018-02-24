package intelligentchessgame;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class Bishop extends ChessPiece{
    
    public Bishop(Point place, Point listIndex, ImageIcon dark, ImageIcon white){
        super(place, listIndex, dark, white); 
    }

    @Override
    public void set_possible_moves(ArrayList<ArrayList<ChessPiece>> arr) {
        this.possibleMoves.clear();
        if (this.getPlace().x == -1)
            return;
        Point oldPlace = currPlace;
        King king = (King)arr.get(listIndex.x).get(7);
        //move north east
        int i = 1;
        while (this.currPlace.x + i <= 7 && this.currPlace.y - i >= 0 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y - i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y - i), arr))
                break;
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y - i), arr)){
                currPlace = new Point(this.currPlace.x + i, this.currPlace.y - i);
                if(king.isSafe(arr)){
                    currPlace = oldPlace;
                    this.possibleMoves.add(new Point(this.currPlace.x + i, this.currPlace.y - i));
                }
                currPlace = oldPlace;
            }
            i++;
        }
        
        //move north west
        i = 1;
        while (this.currPlace.x - i >= 0 && this.currPlace.y - i >= 0 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y - i), arr)){    
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y - i), arr))
                break;
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y - i), arr)){
                currPlace = new Point(this.currPlace.x - i, this.currPlace.y - i);
                if(king.isSafe(arr)){
                    currPlace = oldPlace;
                    this.possibleMoves.add(new Point(this.currPlace.x - i, this.currPlace.y - i));
                }
                currPlace = oldPlace;
            }
            i++;
        }
        
        //move south east
        i = 1;
        while (this.currPlace.x + i <= 7 && this.currPlace.y + i <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y + i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y + i), arr))
                break;
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y + i), arr)){
                currPlace = new Point(this.currPlace.x + i, this.currPlace.y + i);
                if(king.isSafe(arr)){
                    currPlace = oldPlace;
                    this.possibleMoves.add(new Point(this.currPlace.x + i, this.currPlace.y + i));
                }
                currPlace = oldPlace;
            }
            i++;
        }
        
        //move south west
        i = 1;
        while (this.currPlace.x - i >= 0 && this.currPlace.y + i <= 7 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y + i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y + i), arr))
                break;
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y + i), arr)){
                currPlace = new Point(this.currPlace.x - i, this.currPlace.y + i);
                if(king.isSafe(arr)){
                    currPlace = oldPlace;
                    this.possibleMoves.add(new Point(this.currPlace.x - i, this.currPlace.y + i));
                }
                currPlace = oldPlace;
            }
            i++;
        }
        set_enemy_attacks(arr);
        
    }

    @Override
    public void set_enemy_attacks(ArrayList<ArrayList<ChessPiece>> arr) {
        this.enemyPiles.clear();
        if (this.getPlace().x == -1)
            return;
        //move north east
        int i = 1;
        while (this.currPlace.x + i <= 7 && this.currPlace.y - i >= 0 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y - i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y - i), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x + i, this.currPlace.y - i));
                break;
            }
            i++;
        }
        
        //move north west
        i = 1;
        while (this.currPlace.x - i >= 0 && this.currPlace.y - i >= 0 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y - i), arr)){    
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y - i), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x - i, this.currPlace.y - i));
                break;
            }
            i++;
        }
        
        //move south east
        i = 1;
        while (this.currPlace.x + i <= 7 && this.currPlace.y + i <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y + i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y + i), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x + i, this.currPlace.y + i));
                break;
            }
            i++;
        }
        
        //move south west
        i = 1;
        while (this.currPlace.x - i >= 0 && this.currPlace.y + i <= 7 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y + i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y + i), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x - i, this.currPlace.y + i));
                break;
            }
            i++;
        }
        
    }
}
