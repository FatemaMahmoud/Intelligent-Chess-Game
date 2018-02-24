
package intelligentchessgame;

import static intelligentchessgame.Controller.allPieces;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class ChessBoard extends javax.swing.JPanel {

    private JLabel place0;
    private JLabel place1;
    private Component [] labels;
    private ArrayList<Point> validPlaces;
    private ArrayList<Point> enemyPiles;
    private ArrayList<Point> specialMove;
    public ComputerPlayer cp;
    private String[] Pieces;
    
    public ChessBoard() {
        initComponents();
        scndInitComps();
    }
    
    private void scndInitComps(){
        Pieces = new String[4];
        Pieces[0] = "Knight";
        Pieces[1] = "Rook";
        Pieces[2] = "Queen";
        Pieces[3] = "Bishop";
        place0 = null;
        place1 = null;
        labels = this.getComponents();
        
        validPlaces = new ArrayList<>();
        enemyPiles = new ArrayList<>();
        specialMove = new ArrayList<>();
    }
    
    public void init_players(int clr, int diffLvl){
        cp = new ComputerPlayer(clr, diffLvl);
    }
    
    public void comp_turn(){
        ArrayList<ArrayList<Point>> npcs = null;
        if (cp.getDepth() == 9){
            for (int i = 2 ; i <= 9 ; i++){
                cp.setDepth(i);
                npcs = cp.alpha_beta_search();
                if (ComputerPlayer.playnow){
                    ComputerPlayer.playnow = false;
                    break;
                }
            }
            cp.setDepth(9);
        }
        else
            npcs = cp.alpha_beta_search();
        
        get_move(npcs);
        ComputerPlayer.my_turn = false;     
    }
    private void get_move(ArrayList<ArrayList<Point>> npcs){
        Point p0;
        Point p1;
        if (place1 != null){
            place1.setBorder(BorderFactory.createLineBorder(new Color(255,255,204), 1));
            place1 = null;
            place0.setBorder(BorderFactory.createLineBorder(new Color(255,255,204), 1));
        }
        this.validPlaces.clear();
        this.enemyPiles.clear();
        this.specialMove.clear();
        this.place0 = null;
        for (int j = 0 ; j < npcs.get(cp.get_clr()).size() ; j++){
            if (!(npcs.get(cp.get_clr()).get(j).equals(allPieces.get(cp.get_clr()).get(j).getPlace()))){
                p0 = allPieces.get(cp.get_clr()).get(j).getPlace();
                p1 = npcs.get(cp.get_clr()).get(j);
                this.place0 = get_label(p0);
                update_bg(get_label(p1), "computer");
                break;
            }
        }
        
    }
    public void set_place0(JLabel l){
        place0 = l;
    }
    public JLabel get_label(Point index){
        if (index.x == -1)
            return null;
        return (JLabel)labels[index.x + index.y*8];   
    }
    private Point get_index(JLabel l){
        for (int i = 0 ; i < labels.length ; i++){
            if (l.equals(labels[i]))
                return new Point(i%8, i/8);      
        }
        return new Point(-1, -1); 
    }
    
    private void valid_moves(Point p){
        
        for(int i = 0 ; i < 2 ; i++){
            for(int j = 0 ; j < Controller.allPieces.get(i).size() ; j++){
                if(Controller.allPieces.get(i).get(j).getPlace().equals(p)){
                    validPlaces.clear();
                    validPlaces.addAll(Controller.allPieces.get(i).get(j).possibleMoves);
                    enemyPiles.clear();
                    enemyPiles.addAll(Controller.allPieces.get(i).get(j).enemyPiles);
                    specialMove.clear();
                    specialMove.addAll(Controller.allPieces.get(i).get(j).specialMoves);
                    break;
                }
                
            }
        }
        for (int i = 0 ; i < validPlaces.size() ; i++){
            JLabel l = (JLabel)labels[validPlaces.get(i).x + validPlaces.get(i).y*8];
            if(!l.getBackground().equals(Color.WHITE))
                l.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("white-g.png")));
            else
                l.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("d-.png")));
        }
        for (int i = 0 ; i < enemyPiles.size() ; i++){
            JLabel l = (JLabel)labels[enemyPiles.get(i).x + enemyPiles.get(i).y*8];
            l.setBorder(BorderFactory.createLineBorder(Color.red, 5));
        }
          for (int i = 0 ; i < specialMove.size() ; i++){
            JLabel l = (JLabel)labels[this.specialMove.get(i).x + this.specialMove.get(i).y*8];
            l.setBorder(BorderFactory.createLineBorder(Color.red, 5));
        }
    }
    
    private void default_piles(){
        for (int i = 0 ; i < validPlaces.size() ; i++){
            JLabel l = (JLabel)labels[validPlaces.get(i).x + validPlaces.get(i).y*8]; 
            if(!l.getBackground().equals(Color.WHITE))
                l.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("tri-ply-448821-64_1000.jpg")));
            else
                l.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("cherry-wood.jpg")));
        }
        for (int i = 0 ; i < enemyPiles.size() ; i++){
            JLabel l = (JLabel)labels[enemyPiles.get(i).x + enemyPiles.get(i).y*8];
            l.setBorder(BorderFactory.createLineBorder(new Color(255,255,204), 1));
        }
        for (int i = 0 ; i < specialMove.size() ; i++){
            JLabel l = (JLabel)labels[specialMove.get(i).x + specialMove.get(i).y*8];
            l.setBorder(BorderFactory.createLineBorder(new Color(255,255,204), 1));
        }
    }
    
    private void move_piece(){
        for(int i = 0 ; i < 2 ; i++){
            for(int j = 0 ; j < Controller.allPieces.get(i).size() ; j++){
                if(Controller.allPieces.get(i).get(j).getPlace().equals(this.get_index(place1))){
                    Controller.allPieces.get(i).get(j).setPlace(new Point(-1, -1));
                }
                if (i == 0 &&
                    Controller.allPieces.get(i).get(j).getPlace().equals(new Point(get_index(place1).x, get_index(place1).y+1)) && this.specialMove.contains(get_index(place1))
                    ||  i == 1 &&
                    Controller.allPieces.get(i).get(j).getPlace().equals(new Point(get_index(place1).x, get_index(place1).y-1)) && this.specialMove.contains(get_index(place1))){
                    JLabel l = (JLabel)labels[Controller.allPieces.get(i).get(j).getPlace().x + Controller.allPieces.get(i).get(j).getPlace().y*8];
                    Controller.allPieces.get(i).get(j).setPlace(new Point(-1, -1));
                    if (!l.getBackground().equals(Color.WHITE))
                        l.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("tri-ply-448821-64_1000.jpg")));
                    else
                        l.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("cherry-wood.jpg"))); 
                    }
                
                if(Controller.allPieces.get(i).get(j).getPlace().equals(this.get_index(place0))){
                    Controller.allPieces.get(i).get(j).setPlace(this.get_index(place1));
                    if (!place1.getBackground().equals(Color.WHITE))
                        place1.setIcon(Controller.allPieces.get(i).get(j).getWhite());
                    else
                        place1.setIcon(Controller.allPieces.get(i).get(j).getDark());
                    if (!place0.getBackground().equals(Color.WHITE))
                        place0.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("tri-ply-448821-64_1000.jpg")));
                    else
                        place0.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("cherry-wood.jpg")));
                    
                }
            }
        }
        this.update_possible_moves();
        PromotePawn();  
        this.update_possible_moves();
        checkKing();
        
    }
    private void checkKing(){
        King k = (King) allPieces.get(0).get(7);
        if (k.getPlace().x == -1) {
            if (cp.get_clr() == 0) {
                JOptionPane.showMessageDialog(this, "Woohoow you won!!");
                System.exit(1);
            } else {
                JOptionPane.showMessageDialog(this, "LOSER!!");
                System.exit(1);
            }
        }
        k = (King)allPieces.get(1).get(7);
        if (k.getPlace().x == -1) {
            if (cp.get_clr() == 0) {
                JOptionPane.showMessageDialog(this, "LOSER!");
                System.exit(1);
            } else {
                JOptionPane.showMessageDialog(this, "Woohoow you won!!");
                System.exit(1);
            }
        }

    }
    private void PromotePawn(){
        Point p = new Point();
        int i;
        for(i=8; i<16; i++){
            if(Controller.allPieces.get(0).get(i) instanceof Pawn && Controller.allPieces.get(0).get(i).getPlace().y==7){
                p = new Point(0,i);
                break;
            }
            if(Controller.allPieces.get(1).get(i) instanceof Pawn && Controller.allPieces.get(1).get(i).getPlace().y==0){
                p = new Point(1,i);
                break;
            }
        }
        if(i!=16){
            ChessPiece pi = Controller.allPieces.get(p.x).get(p.y);
            String promoteTo=null;
            while(promoteTo==null){
                promoteTo = (String) JOptionPane.showInputDialog(this, 
                "Whick piece do you want to promote your pawn to?",
                "Chess pieces",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                Pieces, 
                Pieces[0]);
            }
            switch(promoteTo){
                case "Knight":
                    if(p.x == 0)
                        Controller.allPieces.get(p.x).set(p.y, new Knight(pi.getPlace(), p, 
                                new ImageIcon(this.getClass().getClassLoader().getResource("knight-d-d.png")),
                                new ImageIcon(this.getClass().getClassLoader().getResource("knight-d-w.png"))));
                    else
                        Controller.allPieces.get(p.x).set(p.y, new Knight(pi.getPlace(), p, 
                                new ImageIcon(this.getClass().getClassLoader().getResource("knight-w-d.png")),
                                new ImageIcon(this.getClass().getClassLoader().getResource("knight-w-w.png"))));
                    break;
                case "Rook":
                    if(p.x == 0)
                        Controller.allPieces.get(p.x).set(p.y, new Rook(pi.getPlace(), p, 
                                new ImageIcon(this.getClass().getClassLoader().getResource("rook-d-d.png")),
                                new ImageIcon(this.getClass().getClassLoader().getResource("rook-d-w.png"))));
                    else
                        Controller.allPieces.get(p.x).set(p.y, new Rook(pi.getPlace(), p, 
                                new ImageIcon(this.getClass().getClassLoader().getResource("rook-w-d.png")),
                                new ImageIcon(this.getClass().getClassLoader().getResource("rook-w-w.png"))));
                    break;
                case "Queen":
                    if(p.x == 0)
                        Controller.allPieces.get(p.x).set(p.y, new Queen(pi.getPlace(), p, 
                                new ImageIcon(this.getClass().getClassLoader().getResource("queen-d-d.png")),
                                new ImageIcon(this.getClass().getClassLoader().getResource("queen-d-w.png"))));
                    else
                        Controller.allPieces.get(p.x).set(p.y, new Queen(pi.getPlace(), p, 
                                new ImageIcon(this.getClass().getClassLoader().getResource("queen-w-d.png")),
                                new ImageIcon(this.getClass().getClassLoader().getResource("queen-w-w.png"))));
                    break;
                case "Bishop":
                    if(p.x == 0)
                        Controller.allPieces.get(p.x).set(p.y, new Bishop(pi.getPlace(), p, 
                                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-d-d.png")),
                                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-d-w.png"))));
                    else
                        Controller.allPieces.get(p.x).set(p.y, new Bishop(pi.getPlace(), p, 
                                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-w-d.png")),
                                new ImageIcon(this.getClass().getClassLoader().getResource("bishop-w-w.png"))));
                    break;
            }
            JLabel l = get_label(pi.getPlace());
            if (l!=null && !l.getBackground().equals(Color.WHITE))
                        l.setIcon(Controller.allPieces.get(p.x).get(p.y).getWhite());
                    else
                        l.setIcon(Controller.allPieces.get(p.x).get(p.y).getDark());
        }
    }
    
    private void update_possible_moves(){
        for (int i = 0 ; i < 2 ; i++){
            for (int j = 0 ; j < Controller.allPieces.get(i).size(); j++){
                Controller.allPieces.get(i).get(j).set_possible_moves(Controller.allPieces);
                Controller.allPieces.get(i).get(j).set_enemy_attacks(Controller.allPieces);
            }
        }
    }
    
    
    private boolean valid_click(JLabel l, String player){    
        if (!player.equals("computer")){
            if (ComputerPlayer.my_turn)
                return false;
            for (int i = 0 ; i < Controller.allPieces.get(1-cp.get_clr()).size(); i++){
                if (Controller.allPieces.get(1-cp.get_clr()).get(i).getPlace().equals(get_index(l))){
                    return true;
                }
            }
            
        }
        return false;
    }
    
    public void update_bg(JLabel l, String player){
//        if (!this.valid_click(l, player) && !player.equals("computer")){
//            System.out.println("entered");
//            return;
//        }
                
        if (place0 != null && place1 != null){ //player only enters this
       
            place1.setBorder(BorderFactory.createLineBorder(new Color(255,255,204), 1));
            place1 = null;
            place0.setBorder(BorderFactory.createLineBorder(new Color(255,255,204), 1));
            l.setBorder(BorderFactory.createLineBorder(new Color(102,204,0), 5));
            this.validPlaces.clear();
            this.enemyPiles.clear();
            this.specialMove.clear();
            place0 = l;
            this.valid_moves(this.get_index(l));
        }
        if (place0 == null && place1 == null){
          
            l.setBorder(BorderFactory.createLineBorder(new Color(102,204,0), 5));
            this.validPlaces.clear();
            this.enemyPiles.clear();
            this.specialMove.clear();
            this.valid_moves(this.get_index(l));
            place0 = l;
        }
        else if (place0 == l){
            
            l.setBorder(BorderFactory.createLineBorder(new Color(255,255,204), 1));
            default_piles();
            this.validPlaces.clear();
            this.enemyPiles.clear();
            this.specialMove.clear();
            place1 = null;
            place0 = null;
        }
        else{
            if (this.validPlaces.contains(this.get_index(l)) || this.enemyPiles.contains(this.get_index(l)) || this.specialMove.contains(this.get_index(l)) || 
                    player.equals("computer")){
                place1 = l;
                if (!player.equals("computer"))
                    default_piles();
                this.move_piece();
                place0.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
                l.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
//                place0.revalidate();
//                l.revalidate();
//                this.repaint();
                
                if (!player.equals("computer")){
                    ComputerPlayer.my_turn = true;
                }
                    
                
            }
      
            else{
                JOptionPane.showMessageDialog(this, "Invalid Move!"); 
            }
        }   
    }
    
    public void load(){
        JLabel l = new JLabel();
        //Controller.loadGame();
        try{
        String f1="pieces.bin";
        String f2="player.bin";
        ObjectInputStream is=new ObjectInputStream(new FileInputStream(f1));
        allPieces=(ArrayList<ArrayList<ChessPiece>>)is.readObject();
        is=new ObjectInputStream(new FileInputStream(f2));
        int clr = is.readInt();
        int depth = is.readInt();
        cp = new ComputerPlayer(clr, depth);
        ComputerPlayer.my_turn = is.readBoolean();
        ChessFrame.name = is.readLine();
        is.close();
         }
        catch(FileNotFoundException e){
              JOptionPane.showMessageDialog(null, "There is no saved game");

            }
        catch (IOException e ){
               JOptionPane.showMessageDialog(null, "Input Output Problem occurs");
            }

        catch(ClassNotFoundException e){
               JOptionPane.showMessageDialog(null, "Something wrong occurs !");

        }
        update_possible_moves();
        for (int i = 0 ; i < labels.length ; i++){
            l = (JLabel)labels[i];
            if(!l.getBackground().equals(Color.WHITE))
                l.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("tri-ply-448821-64_1000.jpg")));
            else
                l.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("cherry-wood.jpg")));
        }
        for(int i=0; i<2; i++)
            for(int j=0; j<16; j++){
                l = get_label(Controller.allPieces.get(i).get(j).getPlace());
                if(l == null)
                    continue;
                if (!l.getBackground().equals(Color.WHITE))
                        l.setIcon(Controller.allPieces.get(i).get(j).getWhite());
                    else
                        l.setIcon(Controller.allPieces.get(i).get(j).getDark());
            }
        Controller.start = true;
    }
    public void saveGame(){
           try{
           String f1="pieces.bin";
           String f2="player.bin";
           ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(f1));
                os.writeObject(allPieces);
                os=new ObjectOutputStream(new FileOutputStream(f2));
                os.writeInt(cp.get_clr());
                os.writeInt(cp.getDepth());
                os.writeBoolean(ComputerPlayer.my_turn);
                os.writeBytes(GameStatePanel.name);
                os.close();
            }
            catch(FileNotFoundException e){


            }
            catch (IOException e ){
               JOptionPane.showMessageDialog(null, "Input Output Problem occurs");
            }

       }

   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(99, 54, 14), 10));
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jLabel3.setBackground(new java.awt.Color(153, 153, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rook-d-w.png"))); // NOI18N
        jLabel3.setToolTipText("");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel3.setName("00"); // NOI18N
        jLabel3.setOpaque(true);
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
        });
        add(jLabel3);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/knight-d-d.png"))); // NOI18N
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        add(jLabel4);

        jLabel2.setBackground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bighop-d-w.png"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        add(jLabel2);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/queen-d-d.png"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel5.setOpaque(true);
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        add(jLabel5);

        jLabel6.setBackground(new java.awt.Color(153, 153, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/king-d-w.png"))); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        add(jLabel6);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bishop-d.png"))); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        add(jLabel7);

        jLabel8.setBackground(new java.awt.Color(153, 153, 153));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/knight-d-w.png"))); // NOI18N
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel8.setOpaque(true);
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        add(jLabel8);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rook-d-d.png"))); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel9.setOpaque(true);
        jLabel9.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        add(jLabel9);

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-d-d.png"))); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel11.setOpaque(true);
        jLabel11.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        add(jLabel11);

        jLabel10.setBackground(new java.awt.Color(153, 153, 153));
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-d-w.png"))); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel10.setOpaque(true);
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        add(jLabel10);

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-d-d.png"))); // NOI18N
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel12.setOpaque(true);
        jLabel12.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        add(jLabel12);

        jLabel13.setBackground(new java.awt.Color(153, 153, 153));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-d-w.png"))); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel13.setOpaque(true);
        jLabel13.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        add(jLabel13);

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-d-d.png"))); // NOI18N
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel14.setOpaque(true);
        jLabel14.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        add(jLabel14);

        jLabel15.setBackground(new java.awt.Color(153, 153, 153));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-d-w.png"))); // NOI18N
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel15.setOpaque(true);
        jLabel15.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        add(jLabel15);

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-d-d.png"))); // NOI18N
        jLabel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel16.setOpaque(true);
        jLabel16.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        add(jLabel16);

        jLabel17.setBackground(new java.awt.Color(153, 153, 153));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-d-w.png"))); // NOI18N
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel17.setOpaque(true);
        jLabel17.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        add(jLabel17);

        jLabel18.setBackground(new java.awt.Color(153, 153, 153));
        jLabel18.setForeground(new java.awt.Color(153, 153, 153));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel18.setToolTipText("");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel18.setOpaque(true);
        jLabel18.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        add(jLabel18);

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel19.setOpaque(true);
        jLabel19.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        add(jLabel19);

        jLabel20.setBackground(new java.awt.Color(153, 153, 153));
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel20.setOpaque(true);
        jLabel20.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        add(jLabel20);

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel21.setOpaque(true);
        jLabel21.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        add(jLabel21);

        jLabel22.setBackground(new java.awt.Color(153, 153, 153));
        jLabel22.setForeground(new java.awt.Color(153, 153, 153));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel22.setOpaque(true);
        jLabel22.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        add(jLabel22);

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel23.setOpaque(true);
        jLabel23.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });
        add(jLabel23);

        jLabel24.setBackground(new java.awt.Color(153, 153, 153));
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel24.setOpaque(true);
        jLabel24.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel24MouseEntered(evt);
            }
        });
        add(jLabel24);

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel25.setOpaque(true);
        jLabel25.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });
        add(jLabel25);

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel26.setOpaque(true);
        jLabel26.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });
        add(jLabel26);

        jLabel27.setBackground(new java.awt.Color(153, 153, 153));
        jLabel27.setForeground(new java.awt.Color(153, 153, 153));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel27.setOpaque(true);
        jLabel27.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });
        add(jLabel27);

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel28.setOpaque(true);
        jLabel28.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });
        add(jLabel28);

        jLabel29.setBackground(new java.awt.Color(153, 153, 153));
        jLabel29.setForeground(new java.awt.Color(153, 153, 153));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel29.setOpaque(true);
        jLabel29.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });
        add(jLabel29);

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel30.setOpaque(true);
        jLabel30.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });
        add(jLabel30);

        jLabel31.setBackground(new java.awt.Color(153, 153, 153));
        jLabel31.setForeground(new java.awt.Color(153, 153, 153));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel31.setOpaque(true);
        jLabel31.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });
        add(jLabel31);

        jLabel32.setBackground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel32.setOpaque(true);
        jLabel32.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });
        add(jLabel32);

        jLabel33.setBackground(new java.awt.Color(153, 153, 153));
        jLabel33.setForeground(new java.awt.Color(153, 153, 153));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel33.setOpaque(true);
        jLabel33.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        add(jLabel33);

        jLabel34.setBackground(new java.awt.Color(153, 153, 153));
        jLabel34.setForeground(new java.awt.Color(153, 153, 153));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel34.setToolTipText("");
        jLabel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel34.setOpaque(true);
        jLabel34.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
        });
        add(jLabel34);

        jLabel35.setBackground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel35.setOpaque(true);
        jLabel35.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
        });
        add(jLabel35);

        jLabel36.setBackground(new java.awt.Color(153, 153, 153));
        jLabel36.setForeground(new java.awt.Color(153, 153, 153));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel36.setOpaque(true);
        jLabel36.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });
        add(jLabel36);

        jLabel37.setBackground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel37.setOpaque(true);
        jLabel37.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });
        add(jLabel37);

        jLabel38.setBackground(new java.awt.Color(153, 153, 153));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel38.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel38.setOpaque(true);
        jLabel38.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });
        add(jLabel38);

        jLabel39.setBackground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel39.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel39.setOpaque(true);
        jLabel39.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
        });
        add(jLabel39);

        jLabel40.setBackground(new java.awt.Color(153, 153, 153));
        jLabel40.setForeground(new java.awt.Color(153, 153, 153));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel40.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel40.setOpaque(true);
        jLabel40.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
        });
        add(jLabel40);

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel41.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel41.setOpaque(true);
        jLabel41.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });
        add(jLabel41);

        jLabel42.setBackground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel42.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel42.setOpaque(true);
        jLabel42.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
        });
        add(jLabel42);

        jLabel43.setBackground(new java.awt.Color(153, 153, 153));
        jLabel43.setForeground(new java.awt.Color(153, 153, 153));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel43.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel43.setOpaque(true);
        jLabel43.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });
        add(jLabel43);

        jLabel44.setBackground(new java.awt.Color(255, 255, 255));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel44.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel44.setOpaque(true);
        jLabel44.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
        });
        add(jLabel44);

        jLabel45.setBackground(new java.awt.Color(153, 153, 153));
        jLabel45.setForeground(new java.awt.Color(153, 153, 153));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel45.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel45.setOpaque(true);
        jLabel45.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
        });
        add(jLabel45);

        jLabel46.setBackground(new java.awt.Color(255, 255, 255));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel46.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel46.setOpaque(true);
        jLabel46.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
        });
        add(jLabel46);

        jLabel47.setBackground(new java.awt.Color(153, 153, 153));
        jLabel47.setForeground(new java.awt.Color(153, 153, 153));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel47.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel47.setOpaque(true);
        jLabel47.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
        });
        add(jLabel47);

        jLabel48.setBackground(new java.awt.Color(255, 255, 255));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cherry-wood.jpg"))); // NOI18N
        jLabel48.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel48.setOpaque(true);
        jLabel48.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
        });
        add(jLabel48);

        jLabel49.setBackground(new java.awt.Color(153, 153, 153));
        jLabel49.setForeground(new java.awt.Color(153, 153, 153));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tri-ply-448821-64_1000.jpg"))); // NOI18N
        jLabel49.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel49.setOpaque(true);
        jLabel49.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
        });
        add(jLabel49);

        jLabel50.setBackground(new java.awt.Color(153, 153, 153));
        jLabel50.setForeground(new java.awt.Color(153, 153, 153));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-w-w.png"))); // NOI18N
        jLabel50.setToolTipText("");
        jLabel50.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel50.setOpaque(true);
        jLabel50.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
        });
        add(jLabel50);

        jLabel51.setBackground(new java.awt.Color(255, 255, 255));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-w-d.png"))); // NOI18N
        jLabel51.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel51.setOpaque(true);
        jLabel51.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });
        add(jLabel51);

        jLabel52.setBackground(new java.awt.Color(153, 153, 153));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-w-w.png"))); // NOI18N
        jLabel52.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel52.setOpaque(true);
        jLabel52.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
        });
        add(jLabel52);

        jLabel53.setBackground(new java.awt.Color(255, 255, 255));
        jLabel53.setForeground(new java.awt.Color(153, 153, 153));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-w-d.png"))); // NOI18N
        jLabel53.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel53.setOpaque(true);
        jLabel53.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
        });
        add(jLabel53);

        jLabel54.setBackground(new java.awt.Color(153, 153, 153));
        jLabel54.setForeground(new java.awt.Color(153, 153, 153));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-w-w.png"))); // NOI18N
        jLabel54.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel54.setOpaque(true);
        jLabel54.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel54MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel54MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel54MouseExited(evt);
            }
        });
        add(jLabel54);

        jLabel55.setBackground(new java.awt.Color(255, 255, 255));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-w-d.png"))); // NOI18N
        jLabel55.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel55.setOpaque(true);
        jLabel55.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel55MouseClicked(evt);
            }
        });
        add(jLabel55);

        jLabel56.setBackground(new java.awt.Color(153, 153, 153));
        jLabel56.setForeground(new java.awt.Color(153, 153, 153));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-w-w.png"))); // NOI18N
        jLabel56.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel56.setOpaque(true);
        jLabel56.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel56MouseClicked(evt);
            }
        });
        add(jLabel56);

        jLabel57.setBackground(new java.awt.Color(255, 255, 255));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pawn-w-d.png"))); // NOI18N
        jLabel57.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel57.setOpaque(true);
        jLabel57.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel57MouseClicked(evt);
            }
        });
        add(jLabel57);

        jLabel58.setBackground(new java.awt.Color(255, 255, 255));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rook-w-d.png"))); // NOI18N
        jLabel58.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel58.setOpaque(true);
        jLabel58.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel58MouseClicked(evt);
            }
        });
        add(jLabel58);

        jLabel59.setBackground(new java.awt.Color(153, 153, 153));
        jLabel59.setForeground(new java.awt.Color(153, 153, 153));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/knight-w-w.png"))); // NOI18N
        jLabel59.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel59.setOpaque(true);
        jLabel59.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel59MouseClicked(evt);
            }
        });
        add(jLabel59);

        jLabel60.setBackground(new java.awt.Color(255, 255, 255));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bishop-w-d.png"))); // NOI18N
        jLabel60.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel60.setOpaque(true);
        jLabel60.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel60MouseClicked(evt);
            }
        });
        add(jLabel60);

        jLabel61.setBackground(new java.awt.Color(153, 153, 153));
        jLabel61.setForeground(new java.awt.Color(153, 153, 153));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/queen-w-w.png"))); // NOI18N
        jLabel61.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel61.setOpaque(true);
        jLabel61.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel61MouseClicked(evt);
            }
        });
        add(jLabel61);

        jLabel62.setBackground(new java.awt.Color(255, 255, 255));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/king-w-d.png"))); // NOI18N
        jLabel62.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel62.setOpaque(true);
        jLabel62.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel62MouseClicked(evt);
            }
        });
        add(jLabel62);

        jLabel63.setBackground(new java.awt.Color(153, 153, 153));
        jLabel63.setForeground(new java.awt.Color(153, 153, 153));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bishop-w-w.png"))); // NOI18N
        jLabel63.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel63.setOpaque(true);
        jLabel63.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel63MouseClicked(evt);
            }
        });
        add(jLabel63);

        jLabel64.setBackground(new java.awt.Color(255, 255, 255));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/knight-w-d.png"))); // NOI18N
        jLabel64.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel64.setOpaque(true);
        jLabel64.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel64MouseClicked(evt);
            }
        });
        add(jLabel64);

        jLabel65.setBackground(new java.awt.Color(153, 153, 153));
        jLabel65.setForeground(new java.awt.Color(153, 153, 153));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rook-w-w.png"))); // NOI18N
        jLabel65.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 204)));
        jLabel65.setOpaque(true);
        jLabel65.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel65MouseClicked(evt);
            }
        });
        add(jLabel65);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel54MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseEntered
  
    }//GEN-LAST:event_jLabel54MouseEntered

    private void jLabel54MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseExited
     
    }//GEN-LAST:event_jLabel54MouseExited

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel54, "clicked");
    }//GEN-LAST:event_jLabel54MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel3, "clicked");
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel4, "clicked");
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel2, "clicked");
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel5, "clicked");
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        update_bg(jLabel6, "clicked");
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        update_bg(jLabel7, "clicked");
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        update_bg(jLabel8, "clicked");
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        update_bg(jLabel9, "clicked");
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        update_bg(jLabel11, "clicked");
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
       update_bg(jLabel19, "clicked");
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        update_bg(jLabel20, "clicked");
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        update_bg(jLabel21, "clicked");
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        update_bg(jLabel10, "clicked");
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        update_bg(jLabel12, "clicked");
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
       update_bg(jLabel13, "clicked");
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        update_bg(jLabel14, "clicked");
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        update_bg(jLabel15, "clicked");
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        update_bg(jLabel16, "clicked");
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        update_bg(jLabel17, "clicked");
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel18, "clicked");
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel22, "clicked");
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel23, "clicked");
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel24MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel24MouseEntered

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel24, "clicked");
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel25, "clicked");
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel26, "clicked");
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel27, "clicked");
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel28, "clicked");
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel29, "clicked");
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel30, "clicked");
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel31, "clicked");
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel32, "clicked");
    }//GEN-LAST:event_jLabel32MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel33, "clicked");
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel34, "clicked");
    }//GEN-LAST:event_jLabel34MouseClicked

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel35, "clicked");
    }//GEN-LAST:event_jLabel35MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel36, "clicked");
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel37, "clicked");
    }//GEN-LAST:event_jLabel37MouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel38, "clicked");
    }//GEN-LAST:event_jLabel38MouseClicked

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel39, "clicked");
    }//GEN-LAST:event_jLabel39MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel40, "clicked");
    }//GEN-LAST:event_jLabel40MouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel41, "clicked");
    }//GEN-LAST:event_jLabel41MouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        // TODO add your handling code here
        update_bg(jLabel42, "clicked");
    }//GEN-LAST:event_jLabel42MouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel43, "clicked");
    }//GEN-LAST:event_jLabel43MouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel44, "clicked");
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel45, "clicked");
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel46, "clicked");
        
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel47, "clicked");
    }//GEN-LAST:event_jLabel47MouseClicked

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel48, "clicked");
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel49, "clicked");
    }//GEN-LAST:event_jLabel49MouseClicked

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel50, "clicked");
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel51, "clicked");
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel52, "clicked");
    }//GEN-LAST:event_jLabel52MouseClicked

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel53, "clicked");
    }//GEN-LAST:event_jLabel53MouseClicked

    private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel55, "clicked");
    }//GEN-LAST:event_jLabel55MouseClicked

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel56, "clicked");
    }//GEN-LAST:event_jLabel56MouseClicked

    private void jLabel57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel57MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel57, "clicked");
    }//GEN-LAST:event_jLabel57MouseClicked

    private void jLabel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel58, "clicked");
    }//GEN-LAST:event_jLabel58MouseClicked

    private void jLabel59MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel59, "clicked");
    }//GEN-LAST:event_jLabel59MouseClicked

    private void jLabel60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseClicked
        // TODO add your handling code here
        update_bg(jLabel60, "clicked");
    }//GEN-LAST:event_jLabel60MouseClicked

    private void jLabel61MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel61MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel61, "clicked");
    }//GEN-LAST:event_jLabel61MouseClicked

    private void jLabel62MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel62MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel62, "clicked");
    }//GEN-LAST:event_jLabel62MouseClicked

    private void jLabel63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel63MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel63, "clicked");
    }//GEN-LAST:event_jLabel63MouseClicked

    private void jLabel64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel64, "clicked");
    }//GEN-LAST:event_jLabel64MouseClicked

    private void jLabel65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseClicked
        // TODO add your handling code here:
        update_bg(jLabel65, "clicked");
    }//GEN-LAST:event_jLabel65MouseClicked
       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
