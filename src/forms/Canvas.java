/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import Boards.MainBoard;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author angel
 */
public class Canvas extends JPanel implements MouseListener, MouseMotionListener{
    MainBoard board = new MainBoard(this);
    
    public Canvas(){
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    @Override
    public void paint(Graphics g) {
        board.drawBoard(g, this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        board.releaseClickMouseLogic(me.getX(), me.getY());
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       this.board.releaseMouseLogic(me.getX(), me.getY());
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        this.board.draggMouseLogic(me.getX(), me.getY());
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent me) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
