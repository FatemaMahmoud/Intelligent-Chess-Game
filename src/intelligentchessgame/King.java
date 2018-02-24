/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intelligentchessgame;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class King extends ChessPiece {
    
    private boolean check;
    
    
    public King(Point place, Point listIndex, ImageIcon dark, ImageIcon white){
        super(place, listIndex, dark, white); 
    }

    @Override
    public void set_possible_moves(ArrayList<ArrayList<ChessPiece>> arr) {
        this.possibleMoves.clear();
        if (this.getPlace().x == -1)
            return;
        //move right
        if(this.currPlace.x + 1 <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y), arr)){
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y), arr)){
                this.possibleMoves.add(new Point(this.currPlace.x + 1, this.currPlace.y));
            }
        }
        
        //move left
        if(this.currPlace.x - 1 >= 0 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y), arr)){
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y), arr)){
                this.possibleMoves.add(new Point(this.currPlace.x - 1, this.currPlace.y));                
            }
        }
        
        //move up
        if(this.currPlace.y + 1 <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + 1), arr)){
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + 1), arr)){
                this.possibleMoves.add(new Point(this.currPlace.x, this.currPlace.y + 1));                
            }
        }
        
        //move down
        if(this.currPlace.y - 1 >= 0 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - 1), arr)){
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - 1), arr)){
                this.possibleMoves.add(new Point(this.currPlace.x, this.currPlace.y - 1));
            }
        }
        
        //move north east
        if(this.currPlace.x + 1 <= 7 && this.currPlace.y - 1 >= 0 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y - 1), arr)){
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y - 1), arr)){
                this.possibleMoves.add(new Point(this.currPlace.x + 1, this.currPlace.y - 1));
            }
        }
        
        //move north west
        if(this.currPlace.x - 1 >= 0 && this.currPlace.y - 1 >= 0 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y - 1), arr)){    
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y - 1), arr)){
                this.possibleMoves.add(new Point(this.currPlace.x - 1, this.currPlace.y - 1));
            }
        }
        
        //move south east
        if(this.currPlace.x + 1 <= 7 && this.currPlace.y + 1 <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y + 1), arr)){
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y + 1), arr)){
                this.possibleMoves.add(new Point(this.currPlace.x + 1, this.currPlace.y + 1));
            }
        }
        
        //move south west
        if(this.currPlace.x - 1 >= 0 && this.currPlace.y + 1 <= 7 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y + 1), arr)){
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y + 1), arr)){
                this.possibleMoves.add(new Point(this.currPlace.x - 1, this.currPlace.y + 1));
            }
        }
        set_enemy_attacks(arr);
    } 
    
    @Override
    public void set_enemy_attacks(ArrayList<ArrayList<ChessPiece>> arr) {
        this.enemyPiles.clear();
        if (this.getPlace().x == -1)
            return;
        //move right
        if(this.currPlace.x + 1 <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x + 1, this.currPlace.y));
            }
        }
        
        //move left
        if(this.currPlace.x - 1 >= 0 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x - 1, this.currPlace.y));
            }
        }
        
        //move up
        if(this.currPlace.y + 1 <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + 1), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + 1), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x, this.currPlace.y + 1));
            }
        }
        
        //move down
        if(this.currPlace.y - 1 >= 0 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - 1), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - 1), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x, this.currPlace.y - 1));
            }
        }
        
        //move north east
        if(this.currPlace.x + 1 <= 7 && this.currPlace.y - 1 >= 0 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y - 1), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y - 1), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x + 1, this.currPlace.y - 1));
            }
        }
        
        //move north west
        if(this.currPlace.x - 1 >= 0 && this.currPlace.y - 1 >= 0 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y - 1), arr)){    
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y - 1), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x - 1, this.currPlace.y - 1));
            }
        }
        
        //move south east
        if(this.currPlace.x + 1 <= 7 && this.currPlace.y + 1 <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y + 1), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + 1, this.currPlace.y + 1), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x + 1, this.currPlace.y + 1));
            }
        }
        
        //move south west
        if(this.currPlace.x - 1 >= 0 && this.currPlace.y + 1 <= 7 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y + 1), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - 1, this.currPlace.y + 1), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x - 1, this.currPlace.y + 1));
            }
        }
    }
    public boolean isSafe(ArrayList<ArrayList<ChessPiece>> arr){
        int EnemeyIndex = 1-listIndex.x;
        for(int i=0; i<16; i++){
            arr.get(EnemeyIndex).get(i).set_enemy_attacks(arr);
            if(arr.get(EnemeyIndex).get(i).enemyPiles.contains(currPlace))
                return false;
        }
        return true;
    }
}
