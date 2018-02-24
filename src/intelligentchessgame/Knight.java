
package intelligentchessgame;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class Knight extends ChessPiece{
    
    public Knight(Point place, Point listIndex, ImageIcon dark, ImageIcon white){
        super(place, listIndex, dark, white); 
    }
    
    @Override
    public void set_possible_moves(ArrayList<ArrayList<ChessPiece>> arr) {
        this.possibleMoves.clear();
        if (this.getPlace().x == -1)
            return;
        Point oldPlace = currPlace;
        King king = (King)arr.get(listIndex.x).get(7);
        Point p = new Point();
        if (this.currPlace.x + 2 <= 7){
            
            if (this.currPlace.y + 1 <= 7){
                p.x = this.currPlace.x + 2;
                p.y = this.currPlace.y + 1;
                
                if(!Controller.enemy_collision(listIndex, p, arr) && !Controller.friend_collision(this.listIndex, p, arr) && p != this.currPlace){
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;   
                }
            }
            if (this.currPlace.y - 1 >= 0){
                p.x = this.currPlace.x + 2;
                p.y = this.currPlace.y - 1;
                if(!Controller.enemy_collision(listIndex, p, arr) && !Controller.friend_collision(this.listIndex, p, arr) && p != this.currPlace){
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;   
                }
            }
        }
        if (this.currPlace.x - 2 >= 0){
            if (this.currPlace.y + 1 <= 7){
                p.x = this.currPlace.x - 2;
                p.y = this.currPlace.y + 1;
                if(!Controller.enemy_collision(listIndex, p, arr) && !Controller.friend_collision(this.listIndex, p, arr) && p != this.currPlace){
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;   
                }
            }
            if (this.currPlace.y - 1 >= 0){
                p.x = this.currPlace.x - 2;
                p.y = this.currPlace.y - 1;
                if(!Controller.enemy_collision(listIndex, p, arr) && !Controller.friend_collision(this.listIndex, p, arr) && p != this.currPlace){
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;   
                }
            } 
        }
        if (this.currPlace.y + 2 <= 7){
            if (this.currPlace.x + 1 <= 7){
                p.x = this.currPlace.x + 1;
                p.y = this.currPlace.y + 2;
                if(!Controller.enemy_collision(listIndex, p, arr) && !Controller.friend_collision(this.listIndex, p, arr) && p != this.currPlace){
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;   
                }        
            }
            if (this.currPlace.x - 1 >= 0){
                p.x = this.currPlace.x - 1;
                p.y = this.currPlace.y + 2;
                if(!Controller.enemy_collision(listIndex, p, arr) && !Controller.friend_collision(this.listIndex, p, arr) && p != this.currPlace){
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;   
                }
            }     
        }
         if (this.currPlace.y - 2 >= 0){
            if (this.currPlace.x + 1 <= 7){
                p.x = this.currPlace.x + 1;
                p.y = this.currPlace.y - 2;
                if(!Controller.enemy_collision(listIndex, p, arr) && !Controller.friend_collision(this.listIndex, p, arr) && p != this.currPlace){
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;   
                }
            }
            if (this.currPlace.x - 1 >= 0){
                p.x = this.currPlace.x - 1;
                p.y = this.currPlace.y - 2;
                if(!Controller.enemy_collision(listIndex, p, arr) && !Controller.friend_collision(this.listIndex, p, arr) && p != this.currPlace){
                    currPlace = p;
                    if(king.isSafe(arr)){
                        currPlace=oldPlace;
                        this.possibleMoves.add(new Point(p));
                    }
                    currPlace = oldPlace;   
                }
            }     
        }
         set_enemy_attacks(arr);
    }

    @Override
    public void set_enemy_attacks(ArrayList<ArrayList<ChessPiece>> arr) {
        this.enemyPiles.clear();
        if (this.getPlace().x == -1)
            return;
        Point p = new Point();
        if (this.currPlace.x + 2 <= 7){
            
            if (this.currPlace.y + 1 <= 7){
                p.x = this.currPlace.x + 2;
                p.y = this.currPlace.y + 1;
                
                if(Controller.enemy_collision(listIndex, p, arr))
                    this.enemyPiles.add(new Point(p.x, p.y));
            }
            if (this.currPlace.y - 1 >= 0){
                p.x = this.currPlace.x + 2;
                p.y = this.currPlace.y - 1;
                if(Controller.enemy_collision(listIndex, p, arr))
                    this.enemyPiles.add(new Point(p.x, p.y));
            }
        }
        if (this.currPlace.x - 2 >= 0){
            if (this.currPlace.y + 1 <= 7){
                p.x = this.currPlace.x - 2;
                p.y = this.currPlace.y + 1;
                if(Controller.enemy_collision(listIndex, p, arr))
                    this.enemyPiles.add(new Point(p.x, p.y));
            }
            if (this.currPlace.y - 1 >= 0){
                p.x = this.currPlace.x - 2;
                p.y = this.currPlace.y - 1;
                if(Controller.enemy_collision(listIndex, p, arr))
                    this.enemyPiles.add(new Point(p.x, p.y));
            } 
        }
        if (this.currPlace.y + 2 <= 7){
            if (this.currPlace.x + 1 <= 7){
                p.x = this.currPlace.x + 1;
                p.y = this.currPlace.y + 2;
                if(Controller.enemy_collision(listIndex, p, arr))
                    this.enemyPiles.add(new Point(p.x, p.y));
            }
            if (this.currPlace.x - 1 >= 0){
                p.x = this.currPlace.x - 1;
                p.y = this.currPlace.y + 2;
                if(Controller.enemy_collision(listIndex, p, arr))
                    this.enemyPiles.add(new Point(p.x, p.y));
            }     
        }
         if (this.currPlace.y - 2 >= 0){
            if (this.currPlace.x + 1 <= 7){
                p.x = this.currPlace.x + 1;
                p.y = this.currPlace.y - 2;
                if(Controller.enemy_collision(listIndex, p, arr))
                    this.enemyPiles.add(new Point(p.x, p.y));
            }
            if (this.currPlace.x - 1 >= 0){
                p.x = this.currPlace.x - 1;
                p.y = this.currPlace.y - 2;
                if(Controller.enemy_collision(listIndex, p, arr))
                    this.enemyPiles.add(new Point(p.x, p.y));
            }     
        }
    }
    
}
