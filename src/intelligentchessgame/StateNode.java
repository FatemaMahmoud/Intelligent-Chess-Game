
package intelligentchessgame;

import java.awt.Point;
import java.util.ArrayList;

public class StateNode {
    
    private ArrayList<ArrayList<Point>> gameState;
    private ArrayList<ArrayList<ArrayList<Point>>> nextStates;
    private ArrayList<ArrayList<ArrayList<Point>>> enemyCollision;
    private ArrayList<ArrayList<ArrayList<Point>>> enemyNextStates;
    private ArrayList<ArrayList<ArrayList<Point>>> enemyLose;
    private int clr;
    private boolean explored;
    private double utility;
    private int succIndex;
    private boolean leaf;
    
    public StateNode(ArrayList<ArrayList<Point>> gameState, int clr){
        this.gameState = gameState;
        this.nextStates = new ArrayList<>();
        this.enemyCollision = new ArrayList<>();
        this.enemyNextStates = new ArrayList<>();
        this.enemyLose = new ArrayList<>();
        this.clr = clr;
       // this.set_enemy_moves();
        this.set_next_states();
        
        this.explored = false;
        this.leaf = false;
        
    }
    
    public void set_succ_index(int x){
        this.succIndex = x;
    }
    
    public int get_succ_index(){
        return this.succIndex;
    }
    
    public void set_explored(){
        explored = true;
    }
    
    public boolean get_explored(){
        return this.explored;
    }
    public boolean get_leaf(){
       return leaf;
    }
    
    public void set_utility(double u){
        this.utility= u;
        this.leaf = true;
    }
    
    public double get_utility(){
        return this.utility;
    }
    
    public ArrayList<ArrayList<Point>> get_gameState(){
        return this.gameState;
    }
    
    public void set_dummy(){
        for (int i = 0 ; i < 2 ; i++){
            for (int j = 0 ; j < Controller.dummyState.get(i).size() ; j++){
                Controller.dummyState.get(i).get(j).setPlace(new Point(this.gameState.get(i).get(j).x, this.gameState.get(i).get(j).y));
            }
        }
        for (int i = 0 ; i < 2 ; i++){
            for (int j = 0 ; j < Controller.dummyState.get(i).size() ; j++){
                Controller.dummyState.get(i).get(j).set_possible_moves(Controller.dummyState);
                Controller.dummyState.get(i).get(j).set_enemy_attacks(Controller.dummyState);
            }
        }
    }
    
    private ArrayList<ArrayList<Point>> deep_copy(){
        ArrayList<ArrayList<Point>>  ns = new ArrayList <>();
        for (int i = 0 ; i < 2 ; i++){
            ns.add(new ArrayList<>());
            for (int j = 0 ; j < gameState.get(i).size() ; j++){
                ns.get(i).add(new Point(gameState.get(i).get(j).x, gameState.get(i).get(j).y));
            }
        }
        return ns;
    }
    
    private void set_next_states(){
        ArrayList<ArrayList<Point>>  ns;
        ArrayList<ArrayList<Point>>  ns1;
        ArrayList<Point> moves;
        ArrayList<Point> moves1;
        this.set_dummy();
        for (int j = 0 ; j < Controller.dummyState.get(clr).size() ; j++){
            moves = Controller.dummyState.get(clr).get(j).get_possible_moves();
            moves1 = Controller.dummyState.get(1-clr).get(j).get_possible_moves();
            for (int k = 0 ; k < moves.size() ; k++){
                ns = deep_copy();
                ns.get(clr).set(j, new Point(moves.get(k).x, moves.get(k).y));
                this.nextStates.add(ns);
            }
            for (int k = 0 ; k < moves1.size() ; k++){
                ns1 = deep_copy();
                ns1.get(1-clr).set(j, new Point(moves1.get(k).x, moves1.get(k).y));
                this.enemyNextStates.add(ns1); //enemy next moves
            }
            moves = Controller.dummyState.get(clr).get(j).get_enemy_piles();
            moves1 = Controller.dummyState.get(1-clr).get(j).get_enemy_piles();
            for (int k = 0 ; k < moves.size() ; k++){
                ns = deep_copy();
                ns.get(clr).set(j, new Point(moves.get(k).x, moves.get(k).y));
                for (int l = 0 ; l < ns.get(1-clr).size() ; l++){
                    if (Controller.dummyState.get(1-clr).get(l).getPlace().equals(moves.get(k))){
                        ns.get(1-clr).set(l, new Point(-1, -1));
                        break;
                    }
                }
                this.enemyCollision.add(ns);
            }
             for (int k = 0 ; k < moves1.size() ; k++){
                ns1 = deep_copy();
                ns1.get(1-clr).set(j, new Point(moves1.get(k).x, moves1.get(k).y));
                for (int l = 0 ; l < ns1.get(clr).size() ; l++){
                    if (Controller.dummyState.get(clr).get(l).getPlace().equals(moves1.get(k))){
                        ns1.get(clr).set(l, new Point(-1, -1));
                        break;
                    }
                }
                this.enemyLose.add(ns1);
            }
        }
    }
    
    private void set_enemy_moves(){
        
        ArrayList<ArrayList<Point>>  ns;
        ArrayList<ArrayList<Point>>  ns1;
        ArrayList<Point> moves;
        ArrayList<Point> moves1;
        set_dummy();
        for (int j = 0 ; j < Controller.dummyState.get(clr).size() ; j++){
            moves = Controller.dummyState.get(clr).get(j).get_enemy_piles();
            moves1 = Controller.dummyState.get(1-clr).get(j).get_possible_moves();
            for (int k = 0 ; k < moves.size() ; k++){
                ns = deep_copy();
                ns.get(clr).set(j, new Point(moves.get(k).x, moves.get(k).y));
                enemyCollision.add(ns);
            }
            for (int k = 0 ; k < moves1.size() ; k++){
                ns1 = deep_copy();
                ns1.get(1-clr).set(j, new Point(moves1.get(k).x, moves1.get(k).y));
                this.enemyLose.add(ns1); //enemy next moves
            }
            moves = Controller.dummyState.get(clr).get(j).get_enemy_piles();
            moves1 = Controller.dummyState.get(1-clr).get(j).get_enemy_piles();
            for (int k = 0 ; k < moves.size() ; k++){
                ns = deep_copy();
                ns.get(clr).set(j, new Point(moves.get(k).x, moves.get(k).y));
                for (int l = 0 ; l < ns.get(1-clr).size() ; l++){
                    if (Controller.dummyState.get(1-clr).get(l).getPlace().equals(moves.get(k))){
                        ns.get(1-clr).set(l, new Point(-1, -1));
                        break;
                    }
                }
                enemyCollision.add(ns);
            }
             for (int k = 0 ; k < moves1.size() ; k++){
                ns1 = deep_copy();
                ns1.get(1-clr).set(j, new Point(moves1.get(k).x, moves1.get(k).y));
                for (int l = 0 ; l < ns1.get(clr).size() ; l++){
                    if (Controller.dummyState.get(clr).get(l).getPlace().equals(moves1.get(k))){
                        ns1.get(clr).set(l, new Point(-1, -1));
                        break;
                    }
                }
                this.enemyLose.add(ns1);
            }
        }
    }
    
    
    public ArrayList<ArrayList<ArrayList<Point>>> get_next_states(){
        return this.nextStates;
    } 
    
    public ArrayList<ArrayList<ArrayList<Point>>> get_enemy_moves(){
        return this.enemyCollision;
    }
    
    public ArrayList<ArrayList<ArrayList<Point>>> get_enemyLose(){
        return this.enemyLose;
    }
    
    public ArrayList<ArrayList<ArrayList<Point>>> get_enemyNextStates(){
        return this.enemyNextStates;
    }
    
}
