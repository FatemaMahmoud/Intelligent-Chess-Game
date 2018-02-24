package intelligentchessgame;

import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Controller {
    
    public volatile static ArrayList<ArrayList<ChessPiece>> allPieces;
    private ChessFrame cf; 
    public volatile static  ArrayList<ArrayList<ChessPiece>>dummyState;
    public static volatile boolean  start;
    private void init(){
        allPieces = new ArrayList<>();
        allPieces.add(new ArrayList<>()); //black for first iteration & white for second iteration
        allPieces.get(0).add(new Rook(new Point(0, 0), new Point(0, 0), 
                new ImageIcon(this.getClass().getClassLoader().getResource("rook-d-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("rook-d-w.png"))));
        allPieces.get(0).add(new Rook(new Point(7, 0), new Point(0, 1),
                new ImageIcon(this.getClass().getClassLoader().getResource("rook-d-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("rook-d-w.png"))));
        
        allPieces.get(0).add(new Knight(new Point(1, 0), new Point(0, 2),
                new ImageIcon(this.getClass().getClassLoader().getResource("knight-d-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("knight-d-w.png"))));
        allPieces.get(0).add(new Knight(new Point(6, 0), new Point(0, 3),
                new ImageIcon(this.getClass().getClassLoader().getResource("knight-d-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("knight-d-w.png"))));
        
        allPieces.get(0).add(new Bishop(new Point(2, 0), new Point(0, 4), 
                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("bighop-d-w.png"))));
        allPieces.get(0).add(new Bishop(new Point(5, 0), new Point(0, 5),
                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("bighop-d-w.png"))));
        allPieces.get(0).add(new Queen(new Point(3, 0), new Point(0, 6),
                new ImageIcon(this.getClass().getClassLoader().getResource("queen-d-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("queen-d-w.png"))));
        allPieces.get(0).add(new King(new Point(4, 0), new Point(0, 7),
                new ImageIcon(this.getClass().getClassLoader().getResource("king-d-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("king-d-w.png"))));
       
        for (int j = 0 ; j < 8 ; j++){
            allPieces.get(0).add(new Pawn(new Point(j, 1), new Point(0, 8+j),
                new ImageIcon(this.getClass().getClassLoader().getResource("pawn-d-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("pawn-d-w.png"))));    
        }
        //white pieces
        allPieces.add(new ArrayList<>()); 
        allPieces.get(1).add(new Rook(new Point(0, 7), new Point(1, 0), 
                new ImageIcon(this.getClass().getClassLoader().getResource("rook-w-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("rook-w-w.png"))));
        allPieces.get(1).add(new Rook(new Point(7, 7), new Point(1, 1),
                new ImageIcon(this.getClass().getClassLoader().getResource("rook-w-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("rook-w-w.png"))));
        allPieces.get(1).add(new Knight(new Point(1, 7), new Point(1, 2),
                new ImageIcon(this.getClass().getClassLoader().getResource("knight-w-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("knight-w-w.png"))));
        allPieces.get(1).add(new Knight(new Point(6, 7), new Point(1, 3),
                new ImageIcon(this.getClass().getClassLoader().getResource("knight-w-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("knight-w-w.png"))));
        allPieces.get(1).add(new Bishop(new Point(2, 7), new Point(1, 4), 
                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-w.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-w-w.png"))));
        allPieces.get(1).add(new Bishop(new Point(5, 7), new Point(1, 5),
                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-w.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-w-w.png"))));
   
        allPieces.get(1).add(new Queen(new Point(3, 7), new Point(1, 6),
                new ImageIcon(this.getClass().getClassLoader().getResource("queen-w-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("queen-w-w.png"))));
        
        allPieces.get(1).add(new King(new Point(4, 7), new Point(1, 7),
                new ImageIcon(this.getClass().getClassLoader().getResource("king-w-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("king-w-w.png"))));
        
        for (int j = 0 ; j < 8 ; j++){
            allPieces.get(1).add(new Pawn(new Point(j, 6), new Point(1, 8+j),
                new ImageIcon(this.getClass().getClassLoader().getResource("pawn-w-d.png")),
                new ImageIcon(this.getClass().getClassLoader().getResource("pawn-w-w.png"))));    
        }   
        
        for (int i = 0 ; i < 2 ; i++){
            for (int j = 0 ; j < allPieces.get(i).size() ; j++){
                allPieces.get(i).get(j).set_possible_moves(Controller.allPieces);
            }
        }
        dummyState = IntelligentChessGame.deep_copy(allPieces);
        
    }
    
    public Controller(){
        
        init();
        cf = new ChessFrame();
        cf.setVisible(true);
        while(!start){
            
        }
        if (cf.cb.cp.get_clr() == 1){
            ComputerPlayer.my_turn = true;
            cf.gsp.setTurnText();
            cf.cb.comp_turn();
        }
        else
            ComputerPlayer.my_turn = false;
        while(true){
            if (ComputerPlayer.my_turn){
                cf.gsp.setTurnText();
                cf.cb.comp_turn();
                ComputerPlayer.my_turn = false;
                cf.gsp.setTurnText();
            }
            
        }
        
    }
    
    
    public static boolean friend_collision(Point listIndex, Point dest, ArrayList<ArrayList<ChessPiece>> arr){
        for (int i = 0 ; i < arr.get(listIndex.x).size() ; i++){
            if (i != listIndex.y && arr.get(listIndex.x).get(i).getPlace().equals(dest))
                return true;
        }
        return false;
    }
    
    public static boolean enemy_collision(Point listIndex, Point dest, ArrayList<ArrayList<ChessPiece>> arr){
        int x = 1 - listIndex.x;
        for (int i = 0 ; i < arr.get(x).size() ; i++){
            if (arr.get(x).get(i).getPlace().equals(dest))
                return true;
        }
        return false;
    }
    
    public static boolean adjacent_pawn(Point listIndex, Point adjPile, ArrayList<ArrayList<ChessPiece>> arr){
        int enemyIndex = 1 - listIndex.x;
        for (int i = 8 ; i < 16 ; i++){
            if (arr.get(enemyIndex).get(i) instanceof Pawn && arr.get(enemyIndex).get(i).getPlace().equals(adjPile)){
                Pawn p = (Pawn) arr.get(enemyIndex).get(i);
                if(enemyIndex == 1 && p.enPassant_white)
                    return true;
                else if(enemyIndex == 0 && p.enPassant_black){
                    p.enPassant_black = false;
                    return true;
                }
            }
        }
        return false;
    }    
}
