/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author angel
 */
public class Constants {
    
    public static int BEAN_WIDTH = 80;
    public static int BEAN_HEIGHT = 80;

    // Minimum size: x = 2, y = 2
    public static int ARRAY_SIZE_X = 8;
    public static int ARRAY_SIZE_Y = 8;
    public static int BEANS_TO_MATCH = 3;
    
    public static int BOARD_X = 30;
    public static int BOARD_Y = 30;

    public static int MARGING = 0;
    
    public static int SPEED = 3;
    
    private static HashMap<String, BufferedImage> images = new HashMap<>();
    
    static {
        images.put("bean_blue", ResourceManager.readImage("bean_blue.png"));
        images.put("explosionblue1", ResourceManager.readImage("explosionblue1.png"));
        images.put("explosionblue2", ResourceManager.readImage("explosionblue2.png"));
        images.put("explosionblue3", ResourceManager.readImage("explosionblue3.png"));
        images.put("explosionblue4", ResourceManager.readImage("explosionblue4.png"));
        images.put("explosionblue5", ResourceManager.readImage("explosionblue5.png"));
        
        images.put("bean_green", ResourceManager.readImage("bean_green.png"));
        images.put("explosiongreen1", ResourceManager.readImage("explosiongreen1.png"));
        images.put("explosiongreen2", ResourceManager.readImage("explosiongreen2.png"));
        images.put("explosiongreen3", ResourceManager.readImage("explosiongreen3.png"));
        images.put("explosiongreen4", ResourceManager.readImage("explosiongreen4.png"));
        images.put("explosiongreen5", ResourceManager.readImage("explosiongreen5.png"));
        
        images.put("bean_red", ResourceManager.readImage("bean_red.png"));
        images.put("explosionred1", ResourceManager.readImage("explosionred1.png"));
        images.put("explosionred2", ResourceManager.readImage("explosionred2.png"));
        images.put("explosionred3", ResourceManager.readImage("explosionred3.png"));
        images.put("explosionred4", ResourceManager.readImage("explosionred4.png"));
        images.put("explosionred5", ResourceManager.readImage("explosionpink5.png"));
        
        images.put("bean_pink", ResourceManager.readImage("bean_pink.png"));
        images.put("explosionpink1", ResourceManager.readImage("explosionpink1.png"));
        images.put("explosionpink2", ResourceManager.readImage("explosionpink2.png"));
        images.put("explosionpink3", ResourceManager.readImage("explosionpink3.png"));
        images.put("explosionpink4", ResourceManager.readImage("explosionpink4.png"));
        images.put("explosionpink5", ResourceManager.readImage("explosionpink5.png"));
        
        images.put("bean_yellow", ResourceManager.readImage("bean_yellow.png"));
        images.put("explosionyellow1", ResourceManager.readImage("explosionyellow1.png"));
        images.put("explosionyellow2", ResourceManager.readImage("explosionyellow2.png"));
        images.put("explosionyellow3", ResourceManager.readImage("explosionyellow3.png"));
        images.put("explosionyellow4", ResourceManager.readImage("explosionyellow4.png"));
        images.put("explosionyellow5", ResourceManager.readImage("explosionyellow5.png"));
        
        images.put("bean_white", ResourceManager.readImage("bean_white.png"));
        images.put("explosionwhite1", ResourceManager.readImage("explosionwhite1.png"));
        images.put("explosionwhite2", ResourceManager.readImage("explosionwhite2.png"));
        images.put("explosionwhite3", ResourceManager.readImage("explosionwhite3.png"));
        images.put("explosionwhite4", ResourceManager.readImage("explosionwhite4.png"));
        images.put("explosionwhite5", ResourceManager.readImage("explosionwhite5.png"));

        images.put("bean_purple", ResourceManager.readImage("bean_purple.png"));
        images.put("explosionpurple1", ResourceManager.readImage("explosionpurple1.png"));
        images.put("explosionpurple2", ResourceManager.readImage("explosionpurple2.png"));
        images.put("explosionpurple3", ResourceManager.readImage("explosionpurple3.png"));
        images.put("explosionpurple4", ResourceManager.readImage("explosionpurple4.png"));
        images.put("explosionpurple5", ResourceManager.readImage("explosionpurple5.png"));
        
        images.put("shadow", ResourceManager.readImage("shadow.png"));
        images.put("bkg1", ResourceManager.readImage("background2.jpg"));
        
    }

    public static BufferedImage getImage(String name){
        return images.get(name);
    }
}
