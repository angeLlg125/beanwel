/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

/**
 *
 * @author angel
 */
public class MainContainer extends JFrame implements ComponentListener{
    
    Canvas canvas = new Canvas();
    public MainContainer(){
        this.addComponentListener(this);
        this.pack();
        canvas.setSize(800, 800);
        this.setVisible(true);
        this.setSize(800 + this.getInsets().right, 800+ this.getInsets().top);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(canvas);
        
        this.updateSize(0, 0);
    }
    
    private void updateSize(int x, int y){

    }

    @Override
    public void componentResized(ComponentEvent ce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.s
    }

    @Override
    public void componentShown(ComponentEvent ce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
