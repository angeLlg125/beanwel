/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author angel
 */
public class ResourceManager {

    public static void playSound(String soundName) {
        String gongFile = "sound/"+soundName;
        InputStream in;

        try {
            in = ResourceManager.class.getClassLoader().getResourceAsStream(gongFile);
            AudioStream audioStream = new AudioStream(in);

            AudioPlayer.player.start(audioStream);
        } catch (Exception ex) {

        }
    }

    public static BufferedImage readImage(String path){
        try {
           return convertToARGB(ImageIO
                   .read(ResourceManager
                           .class
                           .getClassLoader()
                           .getResourceAsStream("image/"+path)));
        } catch (IOException e) {
        }
	return null;
    }
    
    public static BufferedImage convertToARGB(BufferedImage image) {
        BufferedImage newImage;
        Graphics2D g;
        
        newImage = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
        g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return newImage;
    }
}
