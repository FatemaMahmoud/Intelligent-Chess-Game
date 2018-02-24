
package intelligentchessgame;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author HP
 */
public class Queen extends ChessPiece{
    
    public Queen(Point index, Point listIndex, ImageIcon dark, ImageIcon white){
        super(index, listIndex, dark, white);
    }
    
    @Override
    public void set_possible_moves(ArrayList<ArrayList<ChessPiece>> arr) { 
        this.possibleMoves.clear();
        if (this.getPlace().x == -1)
            return;
        Point oldPlace = currPlace;
        King king = (King)arr.get(listIndex.x).get(7);
        //move right
        int i = 1;
        while (this.currPlace.x + i <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y), arr))
                break;
            else if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y), arr)){
                currPlace = new Point(this.currPlace.x + i, this.currPlace.y);
                if(king.isSafe(arr)){
                    currPlace = oldPlace;
                    this.possibleMoves.add(new Point(this.currPlace.x + i, this.currPlace.y));
                }
                currPlace = oldPlace;
            }
            i++;
        }
        
        //move left
        i = 1;
        while (this.currPlace.x - i >= 0 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y), arr))
                break;
            else if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y), arr)){
                currPlace = new Point(this.currPlace.x - i, this.currPlace.y);
                if(king.isSafe(arr)){
                    currPlace = oldPlace;
                    this.possibleMoves.add(new Point(this.currPlace.x - i, this.currPlace.y));
                }
                currPlace = oldPlace;
            }
            i++;
        }
        
        //move up
        i = 1;
        while (this.currPlace.y + i <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + i), arr))
                break;
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + i), arr)){
                currPlace = new Point(this.currPlace.x, this.currPlace.y + i);
                if(king.isSafe(arr)){
                    currPlace = oldPlace;
                    this.possibleMoves.add(new Point(this.currPlace.x, this.currPlace.y + i));
                }
                currPlace = oldPlace;
            }
            i++;
        }
        
        //move down
        i = 1;
        while (this.currPlace.y - i >= 0 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - i), arr))
                break;
            if (!Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - i), arr)){
                currPlace = new Point(this.currPlace.x, this.currPlace.y - i);
                if(king.isSafe(arr)){
                    currPlace = oldPlace;
                    this.possibleMoves.add(new Point(this.currPlace.x, this.currPlace.y - i));
                }
                currPlace = oldPlace;
            }
            i++;
        }
        
        //move north east
        i = 1;
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
        //move right
        int i = 1;
        while (this.currPlace.x + i <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x + i, this.currPlace.y), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x + i, this.currPlace.y));
                break;
            }
            i++;
        }
        
        //move left
        i = 1;
        while (this.currPlace.x - i >= 0 && 
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x - i, this.currPlace.y), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x - i, this.currPlace.y));
                break;
            }
            i++;
        }
        
        //move up
        i = 1;
        while (this.currPlace.y + i <= 7 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y + i), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x, this.currPlace.y + i));
                break;
            }
            i++;
        }
        
        //move down
        i = 1;
        while (this.currPlace.y - i >= 0 &&
                !Controller.friend_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - i), arr)){
            if (Controller.enemy_collision(this.listIndex, new Point(this.currPlace.x, this.currPlace.y - i), arr)){
                this.enemyPiles.add(new Point(this.currPlace.x, this.currPlace.y - i));
                break;
            }
            i++;
        }
        
        //move north east
        i = 1;
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
