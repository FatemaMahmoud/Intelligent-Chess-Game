
package intelligentchessgame;

import java.awt.Point;
import java.util.ArrayList;




public class ComputerPlayer{
    private int clr; //zero for black, 1 for white
    private int depth;
    private ArrayList<ArrayList<StateNode>> tree;
    private ArrayList<ArrayList<Point>> ns;
    public volatile static boolean my_turn;
    private ArrayList<Integer> explredSoFar;
    public static volatile boolean playnow;
    
    
    public ComputerPlayer(int clr, int depth){
        ns = null;
        playnow = false;
        this.clr = clr;
        this.depth = depth;
        tree = new ArrayList<>();
        for (int i = 0 ; i < depth ; i++){
            tree.add(new ArrayList<>());
        }
        explredSoFar = new ArrayList<>();
        for (int i = 0 ; i < depth ; i++){
            explredSoFar.add(0);
        }
    }
    
    private ArrayList<ArrayList<Point>> getPlaces(){
        ArrayList<ArrayList<Point>> state = new ArrayList<>();
        for (int i = 0 ; i < 2 ; i++){
            state.add(new ArrayList());
            for (int j = 0 ; j < Controller.allPieces.get(i).size() ; j++){
                state.get(i).add(new Point(Controller.allPieces.get(i).get(j).getPlace().x, Controller.allPieces.get(i).get(j).getPlace().y));
            }
        }
        return state;
    }
    
    public void set_depth(int depth){
        this.depth = depth;
    }
    
    public int getDepth() {
        return depth;
    }
    
    private void depth_first(){
       
        int c;
        for (int i = 1 ; i < depth ; i++){
            if (this.ns != null && playnow)
                return;
            if (!tree.get(i-1).get(explredSoFar.get(i-1)).get_explored()){
                tree.get(i-1).get(explredSoFar.get(i-1)).set_succ_index(this.tree.get(i).size());
                if (i%2 == 0)
                    c = clr;
                else 
                    c = 1 - clr;
                
                this.tree.get(i).addAll(this.next_states(tree.get(i-1).get(explredSoFar.get(i-1)).get_enemy_moves(), c));
                this.tree.get(i).addAll(this.next_states(tree.get(i-1).get(explredSoFar.get(i-1)).get_next_states(), c));
                
                tree.get(i-1).get(explredSoFar.get(i-1)).set_explored();
                explredSoFar.set(i-1, explredSoFar.get(i-1)+1) ;

            }

            if (explredSoFar.get(i) != 0 || (i == depth - 1)){
                int ex = explore(i-1);
                if (ex == -1)
                    return;
                i = ex;
            } 
        } 
    }
    
    private void addElements(ArrayList<StateNode> t, ArrayList<StateNode> add){
        for (int i = 0 ; i < add.size() ; i++){
            t.add(add.get(i));
        }
    }
    
    
    private int explore(int depth){
        for (int i = depth ; i >= 0 ; i--){
            if(explredSoFar.get(i) != tree.get(i).size())
                return i;
        }
        return -1;
    }
    
    private ArrayList<StateNode> next_states(ArrayList<ArrayList<ArrayList<Point>>> ns, int c){
        ArrayList<StateNode> nnodes = new ArrayList<>();
        for (int i = 0 ; i < ns.size(); i++){
            nnodes.add(new StateNode(ns.get(i), c));
        }
        return nnodes;
    }
    
    private void compute_utility(){
        for (int i = 0 ; i < this.tree.get(depth-1).size() ; i++){
            if (this.ns != null && playnow)
                return;
            StateNode leaf =  this.tree.get(depth-1).get(i);
            leaf.set_dummy();
            leaf.set_utility(evaluate(leaf));
        }
    }
    
    private double evaluate (StateNode state){
        
        int wMobility=0, bMobility=0, temp=0;
        int doubled=0, isolated=0, blocked=0;
        double evaluate = 0;
        
        bMobility = bMobility + state.get_next_states().size() + state.get_enemy_moves().size();
        for (int j = 8; j < 16 ; j++){
            if (state.get_gameState().get(clr).get(j).x != -1){
                temp++;
                Pawn p = (Pawn) Controller.dummyState.get(clr).get(j);
                if(p.isIsolated(Controller.dummyState))
                    isolated++;
                if(p.isDoubled(Controller.dummyState))
                    doubled++;
                if(p.isBlocked(Controller.dummyState))
                    blocked++;
            }
        }    
            
        wMobility = wMobility + state.get_enemyLose().size() + state.get_enemyNextStates().size(); //make fun for enemy
        for (int j = 8; j < 16 ; j++){
            if (state.get_gameState().get(1-clr).get(j).x != -1){
                temp--;
                Pawn p = (Pawn) Controller.dummyState.get(1-clr).get(j);
                if(p.isIsolated(Controller.dummyState))
                    isolated--;
                if(p.isDoubled(Controller.dummyState))
                    doubled--;
                if(p.isBlocked(Controller.dummyState))
                    blocked--;
            }
        }    
           
        
        //Adding mobility to utility function giving it weight 0.1
        evaluate += 0.1*(bMobility-wMobility);
        
        
        //#no of black pawns - #no of white pawns
        evaluate += temp;
        
        //#no of black isolated, doubled, and blocked pawns - #no of white isolated, doubled, and blocked pawns with the weight 0.5
        evaluate += 0.5*(isolated+doubled+blocked);
        
        //#no of black rooks - #no of white rooks giving the rook weight 5
        temp = 0;
        if(state.get_gameState().get(clr).get(0).x != -1)
            temp++;
        if(state.get_gameState().get(clr).get(1).x != -1)
            temp++;
        if(state.get_gameState().get(1-clr).get(0).x != -1)
            temp--;
        if(state.get_gameState().get(1-clr).get(1).x != -1)
            temp--;
        evaluate += (5*temp);
        
        //#no of black bishops and knights - #no of white bishops and knights giving them weight 3
        temp = 0;
        if(state.get_gameState().get(clr).get(2).x != -1)
            temp++;
        if(state.get_gameState().get(clr).get(3).x != -1)
            temp++;
        if(state.get_gameState().get(clr).get(4).x != -1)
            temp++;
        if(state.get_gameState().get(clr).get(5).x != -1)
            temp++;
        if(state.get_gameState().get(1-clr).get(2).x != -1)
            temp--;
        if(state.get_gameState().get(1-clr).get(3).x != -1)
            temp--;
        if(state.get_gameState().get(1-clr).get(4).x != -1)
            temp--;
        if(state.get_gameState().get(1-clr).get(5).x != -1)
            temp--;
        evaluate += (3*temp);
        
        //#no of black kings - #no of white kings giving them weight 200
        temp = 0;
        if(state.get_gameState().get(clr).get(6).x != -1)
            temp++;
        if(state.get_gameState().get(1-clr).get(6).x != -1)
            temp--;
        evaluate += (200*temp);
        
        //#no of black queens - #no of white queens giving them weight 9
        temp = 0;
        if(state.get_gameState().get(clr).get(7).x != -1)
            temp++;
        if(state.get_gameState().get(1-clr).get(7).x != -1)
            temp--;
        evaluate += (9*temp);
        return evaluate;

        
    }
    
    public ArrayList<ArrayList<Point>> alpha_beta_search(){
        tree.clear();
        for (int i = 0 ; i < depth ; i++){
            tree.add(new ArrayList<>());
            this.explredSoFar.set(i, 0);
        }
        if(this.depth == 5){
            System.out.println();
        }
        tree.get(0).add(new StateNode(getPlaces(), clr));
        this.depth_first();
        if (this.ns != null && playnow)
            return this.ns;
        this.compute_utility();
        if (this.ns != null && playnow)
            return this.ns;
        max_value(new Point(0,0), -Double.MAX_VALUE, Double.MAX_VALUE);
        return this.ns;
    }
    
    public void setDepth(int d){
        this.depth = d;
    }
    
    private double max_value(Point stateIndex, double alpha, double beta){
        
        if (this.tree.get(stateIndex.x).get(stateIndex.y).get_leaf())
            return this.tree.get(stateIndex.x).get(stateIndex.y).get_utility();
        else{
            double v =  -Double.MAX_VALUE;
            for(int i = 0 ; i < this.tree.get(stateIndex.x).get(stateIndex.y).get_next_states().size()+
                    this.tree.get(stateIndex.x).get(stateIndex.y).get_enemy_moves().size(); i++){
                
                double min = min_value(new Point(stateIndex.x+1, this.tree.get(stateIndex.x).get(stateIndex.y).get_succ_index()+i), alpha, beta); 
                if (min > v){
                    v = min;
                    if (stateIndex.x == 0){
                        ns = this.tree.get(stateIndex.x+1).get(this.tree.get(stateIndex.x).get(stateIndex.y).get_succ_index()+i).get_gameState();
                    }
                }
                if (v >= beta)
                    return v;
                if (v > alpha){
                    alpha = v;
                }
            }
            return v;
        }
    }
    
    private double min_value(Point stateIndex, double alpha, double beta){
        double v = Double.MAX_VALUE;
        if (this.tree.get(stateIndex.x).get(stateIndex.y).get_leaf())
            return this.tree.get(stateIndex.x).get(stateIndex.y).get_utility();
        else{
            for(int i = 0 ; i < this.tree.get(stateIndex.x).get(stateIndex.y).get_next_states().size()+
                    this.tree.get(stateIndex.x).get(stateIndex.y).get_enemy_moves().size(); i++){
                if ( this.tree.get(stateIndex.x).get(stateIndex.y).get_next_states().size()+
                    this.tree.get(stateIndex.x).get(stateIndex.y).get_enemy_moves().size() == 18 && this.tree.size() == 5){
                    System.out.println("here" + this.tree.size());
                }
                double max = max_value(new Point(stateIndex.x+1, this.tree.get(stateIndex.x).get(stateIndex.y).get_succ_index()+i), alpha, beta); 
                if (max < v)
                    v = max;
                if (v <= alpha){
                    return v;
                }
                if (v < beta){
                    beta = v;
                }
            }
            return v;
        }
    }
    
    public int get_clr(){
        return this.clr;
    }
    
}
