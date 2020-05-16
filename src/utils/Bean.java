/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import forms.Canvas;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angel
 */
public class Bean implements Runnable{

    String colorId;
    String state;
    String imageName;

    private int x = 0;
    private int y = 0;
    public int timesToMoveDown = 0;
    
    private String animationName;
    private Canvas canvasReference;
    private boolean isThreadActive;
    private boolean isImageSelected;
    private boolean isReadyToExplode;
    private boolean isFalling;
    private boolean remove;
    private boolean isVisible;
    
    public Bean(int x, int y, String colorId){
        this.x = x;
        this.y = y;
        
        this.colorId = colorId;
        this.state = "bean";
        this.imageName = state + "_" + colorId;
        this.isThreadActive = false;
        this.isImageSelected = false;
        this.isReadyToExplode = false;
        this.isVisible = true;
        this.remove = false;
        this.isFalling = false;
    }
    
    public void drawImage(Graphics g, Canvas canvas, int color){
    	if(this.isVisible && this.y >= Constants.BOARD_Y - 10) {
        /**if(isThreadActive()) {
           // g.drawImage(Constants.getImage("shadow"), x, y + 3, Constants.BEAN_WIDTH + 6, Constants.BEAN_HEIGHT + 6, canvas);
            g.drawImage(Constants.getImage(this.imageName), x, y, Constants.BEAN_WIDTH , Constants.BEAN_HEIGHT, canvas);
    	}else if (isImageSelected) {
           // g.drawImage(Constants.getImage("shadow"), x, y + 3, Constants.BEAN_WIDTH + 3, Constants.BEAN_HEIGHT + 3, canvas);
            g.drawImage(Constants.getImage(this.imageName), x, y, Constants.BEAN_WIDTH, Constants.BEAN_HEIGHT, canvas);
        }else {*/
            //g.drawImage(Constants.getImage("shadow"), x, y + 3, Constants.BEAN_WIDTH, Constants.BEAN_HEIGHT, canvas);
            g.drawImage(Constants.getImage(this.imageName), x, y, Constants.BEAN_WIDTH, Constants.BEAN_HEIGHT, canvas);
        //}
    	}
    }
    
    public synchronized void startAnimation(String animationName, Canvas canvas){
        this.canvasReference = canvas;
        this.animationName = animationName;

        new Thread(this).start();	
    }
    

    @Override
    public void run() {

    	this.setIsThreadActive(true);

        switch(this.animationName){
            case "explosion":
            	ResourceManager.playSound("bubble.wav");
                for (int i = 1; i <= 5; i++) {
                    this.imageName = this.animationName + this.colorId + i;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(120);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
        		}
                this.setIsVisible(false);
                break;
            case "shine":
            		String temp = this.imageName;
                    this.imageName = "shadow";
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.imageName = temp;
                    this.canvasReference.repaint();
        		this.timesToMoveDown--;
                break;
            case "downWhenExplosion":
            	while(this.timesToMoveDown > 0) {
	                for(int i = 0; i < Constants.BEAN_HEIGHT + Constants.MARGING; i++){
	                    this.y += 1;
	                    this.canvasReference.repaint();
	                    try {
	                        TimeUnit.NANOSECONDS.sleep(1);
	                    } catch (InterruptedException ex) {
	                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
	                    }
	                }
	        		this.timesToMoveDown--;
	    		}
                
                break;
            case "top":
                for(int i = 0; i< Constants.BEAN_HEIGHT + Constants.MARGING; i++){
                    this.y -= 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "down":
                for(int i = 0; i < Constants.BEAN_HEIGHT + Constants.MARGING; i++){
                    this.y += 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "right":
                for(int i = 0; i < Constants.BEAN_WIDTH + Constants.MARGING; i++){
                    this.x += 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "left":
                for(int i = 0; i < Constants.BEAN_WIDTH + Constants.MARGING; i++){
                    this.x -= 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "topreturn":
                for(int i = 0; i< Constants.BEAN_HEIGHT + Constants.MARGING; i++){
                    this.y -= 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for(int i = 0; i < Constants.BEAN_HEIGHT + Constants.MARGING; i++){
                    this.y += 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                break;
            case "downreturn":
                for(int i = 0; i < Constants.BEAN_HEIGHT + Constants.MARGING; i++){
                    this.y += 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for(int i = 0; i< Constants.BEAN_HEIGHT + Constants.MARGING; i++){
                    this.y -= 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "rightreturn":
                for(int i = 0; i < Constants.BEAN_WIDTH + Constants.MARGING; i++){
                    this.x += 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for(int i = 0; i < Constants.BEAN_WIDTH + Constants.MARGING; i++){
                    this.x -= 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "leftreturn":
                for(int i = 0; i < Constants.BEAN_WIDTH + Constants.MARGING; i++){
                    this.x -= 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for(int i = 0; i < Constants.BEAN_WIDTH + Constants.MARGING; i++){
                    this.x += 1;
                    this.canvasReference.repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
        this.imageName = state + "_" + colorId;
        this.setIsThreadActive(false);
        canvasReference.repaint();
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
        this.imageName = this.state + "_" + colorId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isThreadActive() {
        return isThreadActive;
    }

    public void setIsThreadActive(boolean isThreadActive) {
        this.isThreadActive = isThreadActive;
    }

    public boolean isImageSelected() {
        return isImageSelected;
    }

    public void setIsImageSelected(boolean isImageSelected) {
        this.isImageSelected = isImageSelected;
    }
    
    public boolean isReadyToExplode() {
        return isReadyToExplode;
    }

    public void setReadyToExplode(boolean isReadyToExplode) {
        this.isReadyToExplode = isReadyToExplode;
    }

    public boolean isVisible() {
    	return this.isVisible;
    }
    
    public void setIsVisible(boolean isVisible) {
    	this.isVisible = isVisible;
    }
    
    public void setTimesToMoveDown(int times) {
    	this.timesToMoveDown += times;
    }
    
    public int getTimesToMoveDown() {
    	return timesToMoveDown;
    }
    
    public void remove() {
    	this.remove = true;
    }
    
    public boolean isReadyToRemove() {
    	return this.remove;
    }
    
    public void setIsFalling(boolean isFalling) {
    	this.isFalling = isFalling;
    }
    
    public boolean getIsFalling() {
    	return this.isFalling;
    }
}
