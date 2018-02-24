
package intelligentchessgame;

import java.awt.Point;
import java.util.ArrayList;


public class IntelligentChessGame {

    public static ArrayList<ArrayList<ChessPiece>> deep_copy(ArrayList<ArrayList<ChessPiece>> state){
        ArrayList<ArrayList<ChessPiece>> s = new ArrayList<>();
        for (int i = 0 ; i < state.size(); i++){
            s.add(new ArrayList<>());
            for (int j = 0 ; j < state.get(i).size() ; j++){
                ChessPiece cp = null;
                if (state.get(i).get(j) instanceof Pawn){
                    cp = (Pawn) new Pawn(new Point(state.get(i).get(j).getPlace().x, state.get(i).get(j).getPlace().y) ,
                            new Point(state.get(i).get(j).listIndex.x, state.get(i).get(j).listIndex.y), 
                            state.get(i).get(j).getDark(), state.get(i).get(j).getWhite());
                }
                else if (state.get(i).get(j) instanceof King){
                    cp = (King) new King(new Point(state.get(i).get(j).getPlace().x, state.get(i).get(j).getPlace().y) ,
                            new Point(state.get(i).get(j).listIndex.x, state.get(i).get(j).listIndex.y), 
                            state.get(i).get(j).getDark(), state.get(i).get(j).getWhite());
                }
                else if (state.get(i).get(j) instanceof Knight){
                    cp = (Knight) new Knight(new Point(state.get(i).get(j).getPlace().x, state.get(i).get(j).getPlace().y) ,
                            new Point(state.get(i).get(j).listIndex.x, state.get(i).get(j).listIndex.y), 
                            state.get(i).get(j).getDark(), state.get(i).get(j).getWhite());
                }
                else if (state.get(i).get(j) instanceof Bishop){
                    cp = (Bishop) new Bishop(new Point(state.get(i).get(j).getPlace().x, state.get(i).get(j).getPlace().y) ,
                            new Point(state.get(i).get(j).listIndex.x, state.get(i).get(j).listIndex.y), 
                            state.get(i).get(j).getDark(), state.get(i).get(j).getWhite());
                }
                else if (state.get(i).get(j) instanceof Queen){
                    cp = (Queen) new Queen(new Point(state.get(i).get(j).getPlace().x, state.get(i).get(j).getPlace().y) ,
                            new Point(state.get(i).get(j).listIndex.x, state.get(i).get(j).listIndex.y), 
                            state.get(i).get(j).getDark(), state.get(i).get(j).getWhite());
                }
                else if (state.get(i).get(j) instanceof Rook){
                    cp = (Rook) new Rook(new Point(state.get(i).get(j).getPlace().x, state.get(i).get(j).getPlace().y) ,
                            new Point(state.get(i).get(j).listIndex.x, state.get(i).get(j).listIndex.y), 
                            state.get(i).get(j).getDark(), state.get(i).get(j).getWhite());
                }
                cp.set_possible_moves(Controller.allPieces);
                cp.set_enemy_attacks(Controller.allPieces);
                s.get(i).add(cp);
            }
        }
        return s;
    }
    public static void main(String[] args) {
        Controller c = new Controller();
        
        
    }
    
}
