/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boards;

import forms.Canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;
import utils.Constants;
import utils.Bean;

/**
 *
 * @author angel
 */
public class MainBoard implements Runnable{
    public Bean[][] images;
    private LinkedList<Bean> imagesUnderAnimation = new LinkedList<>();

    private Bean selectedImage;
    private boolean validationActive = false;
    private int bean_i_dragg = -1;
    private int bean_j_dragg = -1;
    
    private Canvas canvasReference;
    
    public MainBoard(Canvas canvas){
        images = new Bean[Constants.ARRAY_SIZE_X][Constants.ARRAY_SIZE_Y];
        this.canvasReference = canvas;

        this.initImages();
    }
    
    public void initImages(){
       /*for (int i = 0; i < images.length; i++) {
           for (int j = 0; j < images[0].length; j++) {
               x = Constants.BOARD_X + (i * Constants.BEAN_WIDTH) + Constants.MARGING * (i+1);
               y = Constants.BOARD_Y + (j * Constants.BEAN_HEIGHT) + Constants.MARGING * (j+1);
               images[i][j] = this.getRandomBean(x, y);
           }
       }*/
        int count = 0;
        String leftColorId = "";

        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[0].length; j++) {
            	images[i][j] = this.getBeanWithNoDuplicades(leftColorId, i, j, count);
            	
            	if(images[i][j].getColorId() != leftColorId) {
            		leftColorId = images[i][j].getColorId(); 
            		count = 1;
            	}else {
            		count +=1;
            	}
            }
            leftColorId = "";
            count = 0;
        }        
    }
    
    private Bean getBeanWithNoDuplicades(String leftItem, int i, int j, int count) {
        int x = Constants.BOARD_X + (i * Constants.BEAN_WIDTH) + Constants.MARGING * (i+1);
        int y = Constants.BOARD_Y + (j * Constants.BEAN_HEIGHT) + Constants.MARGING * (j+1);
    	String valuesToSkipp = "";
    	
    	if(i - 2 > 0) {
    		if(images[i-1][j].getColorId() == images[i - 2][j].getColorId()) {
    			valuesToSkipp = images[i - 1][j].getColorId();
    		}
    	}
    	
    	if(count > 1) {
    		valuesToSkipp += ","+leftItem;
    	}
    	
    	return new Bean(x, y, getRandomImage(valuesToSkipp));
    }
    
    private String getRandomImage(String ignore) {
    	Random rand = new Random();
    	String result = null;
    	do{
	        switch(rand.nextInt((7 - 1) + 1) + 1){
		        case 1:
		            result = "red";
		            break;
		        case 2:
		            result = "pink";
		            break;
		        case 3:
		            result = "blue";
		            break;
		        case 4:
		            result = "white";
		            break;
		        case 5:
		            result = "yellow";
		            break;
		        case 6:
		            result = "purple";
		            break;
		        case 7:
		            result = "green";
		            break;
	        }
    	}while(ignore.contains(result));
        
        return result;
    }
    
    private Bean getRandomBean(int x, int y) {
    	Random rand = new Random();
        switch(rand.nextInt((7 - 1) + 1) + 1){
	        case 1:
	            return new Bean(x, y, "red");
	        case 2:
	            return new Bean(x, y, "pink");
	        case 3:
	            return new Bean(x, y, "blue");
	        case 4:
	            return new Bean(x, y, "white");
	        case 5:
	            return new Bean(x, y, "yellow");
	        case 6:
	            return new Bean(x, y, "purple");
	        default:
	            return new Bean(x, y, "green");
        }
    }
    
    public void drawBoard(Graphics g, Canvas canvas){
    	int x, y;
    	boolean black = true;
    	int count = 0;
    	
    	// Background
        g.drawImage(Constants.getImage("bkg1"), 0, 0, 800, 800, canvas);
        
        // Draw rectangules
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[0].length; j++) {
                x = Constants.BOARD_X + (i * Constants.BEAN_WIDTH) + Constants.MARGING * (i+1);
                y = Constants.BOARD_Y + (j * Constants.BEAN_HEIGHT) + Constants.MARGING * (j+1);
            	if(images[i][j] != null) {
            		if(count%2==0) {
                        g.setColor(new Color(168, 173, 181, 80));
                        g.fillRect(x, y, Constants.BEAN_WIDTH, Constants.BEAN_HEIGHT);
                        black = false;
            		}else {
                        g.setColor(new Color(80, 82, 84, 80));
                        g.fillRect(x, y, Constants.BEAN_WIDTH, Constants.BEAN_HEIGHT);
                        black = true;
            		}
            		count++;
            	}
            	if(images[i][j].isImageSelected()) {
                    g.setColor(new Color(168, 173, 181, 180));
                    g.fillRect(x, y, Constants.BEAN_WIDTH, Constants.BEAN_HEIGHT);
            	}
            }
            count++;
        }
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[0].length; j++) {
            	if(images[i][j] != null) {
            		images[i][j].drawImage(g, canvas, j);
            	}
            }
        }
    }
    
    public void releaseClickMouseLogic(int x, int y){
        /*if (this.isAnimationInProgress()) {
            return;
        }
        this.validationActive = false;

        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[0].length; j++) {
            	images[i][j].setIsImageSelected(false);
                if(!images[i][j].isThreadActive() && checkIfMouseInsideRankCoordinates(x, y, images[i][j].getX(), images[i][j].getY())){
                    if(images[i][j].isImageSelected() != true 
                    		&& !images[i][j].isThreadActive()
                    		&& !images[i][j].isReadyToRemove()
                    		&& !images[i][j].isReadyToExplode()) {// && this.selectedImage == null){
                        //images[i][j].startAnimation("explosion", canvasReference, 1);
                        //ResourceManager.playSound("bubble.wav");
                        //images[i][j].setIsImageSelected(true);
                        ////this.selectedImage = images[i][j];
                        canvasReference.repaint();
                    }/*else{
                        if (this.selectedImage.equals(images[i][j])) {
                        	 images[i][j].setIsImageSelected(false);
                            this.selectedImage = null;
                            this.canvasReference.repaint();
                        }else {
                        	images[i][j].setIsImageSelected(false);
                        	this.validateSwapDirection(this.selectedImage.getX()+1, this.selectedImage.getY()+1, i, j);
                            this.selectedImage = null;
                            this.canvasReference.repaint();
                        }
                    }/*
                   return;
                }
            }
        }*/
    }
    
    public void releaseMouseLogic(int x, int y){
    	this.validationActive = false;
    }
    
    public void draggMouseLogic(int x, int y){
        if (this.validationActive || isAnimationInProgress()) {
            return;
        }
        
        if (this.selectedImage != null) {
            this.selectedImage.setIsImageSelected(false);
        }

        if(bean_i_dragg == -1 && bean_j_dragg == -1) {
	        for (int i = 0; i < images.length; i++) {
	            for (int j = 0; j < images[0].length; j++) {
	            	if(!images[i][j].isThreadActive()
	                        && checkIfMouseInsideRankCoordinates(x, y, images[i][j].getX(), images[i][j].getY())){

                            this.bean_i_dragg = i;
                            this.bean_j_dragg = j;
                        break;
	                }
	            }
	        }
        }else {
            this.validateSwapDirection(x, y, this.bean_i_dragg, this.bean_j_dragg);
        }
    }
    
    private void validateSwapDirection(int x, int y, int image_i, int image_j){
        if(x < images[image_i][image_j].getX()) {

            if(image_i - 1 >= 0) {
            	if(!images[image_i - 1][image_j].getColorId().equals(images[image_i][image_j].getColorId())) {
            
                    //System.out.println("Left");
            		this.validationActive = true;
                    // TODO: VALIDATIONS
            		this.swap(image_i -1, image_j, image_i, image_j);
                    
                    if(searchAlgorithm(this.images, image_i, image_j) | searchAlgorithm(this.images, image_i-1, image_j)) {
                    	
                        images[image_i - 1][image_j].startAnimation("left", this.canvasReference, 1);
                        images[image_i][image_j].startAnimation("right", this.canvasReference, 1);

                        imagesUnderAnimation.add(images[image_i - 1][image_j]);
                        imagesUnderAnimation.add(images[image_i][image_j]);
                        new Thread(this).start();
                    }else {
                    	this.swap(image_i -1, image_j, image_i, image_j);
                        images[image_i][image_j].startAnimation("leftreturn", this.canvasReference, 1);
                        images[image_i-1][image_j].startAnimation("rightreturn", this.canvasReference, 1);
                        imagesUnderAnimation.add(images[image_i - 1][image_j]);
                        imagesUnderAnimation.add(images[image_i][image_j]);
                    }

                    this.bean_i_dragg = -1;
                    this.bean_j_dragg = -1;
            	}else {
                    images[image_i][image_j].startAnimation("leftreturn", this.canvasReference, 1);
                    images[image_i-1][image_j].startAnimation("rightreturn", this.canvasReference, 1);
                    imagesUnderAnimation.add(images[image_i - 1][image_j]);
                    imagesUnderAnimation.add(images[image_i][image_j]);
                }
            }
        	this.bean_i_dragg = -1;
        	this.bean_j_dragg = -1;
        }else if(x > images[image_i][image_j].getX() + Constants.BEAN_WIDTH){
            if(image_i + 1 < images.length) {
            	if(!images[image_i][image_j].getColorId().equals(images[image_i + 1][image_j].getColorId())) {
	                    //System.out.println("Right");
	            		this.validationActive = true;
	                    // TODO: VALIDATIONS
	            		this.swap(image_i + 1, image_j, image_i, image_j);
	                    
	                    if(searchAlgorithm(this.images, image_i, image_j) | searchAlgorithm(this.images, image_i + 1, image_j)) {
		                    images[image_i + 1][image_j].startAnimation("right", this.canvasReference, 1);
		                    images[image_i][image_j].startAnimation("left", this.canvasReference, 1);
		                    imagesUnderAnimation.add(images[image_i + 1][image_j]);
		                    imagesUnderAnimation.add(images[image_i][image_j]);
		                    new Thread(this).start();
	                    }else {
	                    	this.swap(image_i + 1, image_j, image_i, image_j);
	    	                images[image_i+1][image_j].startAnimation("leftreturn", this.canvasReference, 1);
	    	                images[image_i][image_j].startAnimation("rightreturn", this.canvasReference, 1);
	    	                imagesUnderAnimation.add(images[image_i + 1][image_j]);
	    	                imagesUnderAnimation.add(images[image_i][image_j]);
	                    }
	
	                    this.bean_i_dragg = -1;
	                    this.bean_j_dragg = -1;
	            }else {
	                images[image_i+1][image_j].startAnimation("leftreturn", this.canvasReference, 1);
	                images[image_i][image_j].startAnimation("rightreturn", this.canvasReference, 1);
	                imagesUnderAnimation.add(images[image_i + 1][image_j]);
	                imagesUnderAnimation.add(images[image_i][image_j]);
	            }
            }
        	this.bean_i_dragg = -1;
        	this.bean_j_dragg = -1;
        }else if(y < images[image_i][image_j].getY()){
            if(image_j - 1 >= 0) {
            	if(!images[image_i][image_j].getColorId().equals(images[image_i][image_j - 1].getColorId())) {
                    //System.err.println("Top");
	            		this.validationActive = true;

	            		this.swap(image_i, image_j, image_i, image_j - 1);
	                    
	                    if(searchAlgorithm(this.images, image_i, image_j) | searchAlgorithm(this.images, image_i, image_j - 1)) {
		                    images[image_i][image_j].startAnimation("down", this.canvasReference, 1);
		                    images[image_i][image_j - 1].startAnimation("top", this.canvasReference, 1);
		                    imagesUnderAnimation.add(images[image_i][image_j]);
		                    imagesUnderAnimation.add(images[image_i][image_j - 1]);
		                    new Thread(this).start();
		                    // TODO: VALIDATIONS
	                    }else {
	    	                images[image_i][image_j].startAnimation("downreturn", this.canvasReference, 1);
	    	                images[image_i][image_j-1].startAnimation("topreturn", this.canvasReference, 1);
	    	                imagesUnderAnimation.add(images[image_i][image_j]);
	    	                imagesUnderAnimation.add(images[image_i][image_j - 1]);
	    	                this.swap(image_i, image_j, image_i, image_j - 1);
	                    }
	
	                    this.bean_i_dragg = -1;
	                    this.bean_j_dragg = -1;
	            }else {
	                images[image_i][image_j].startAnimation("topreturn", this.canvasReference, 1);
	                images[image_i][image_j-1].startAnimation("downreturn", this.canvasReference, 1);
	                imagesUnderAnimation.add(images[image_i][image_j]);
	                imagesUnderAnimation.add(images[image_i][image_j - 1]);
	            }
            }	
        	this.bean_i_dragg = -1;
        	this.bean_j_dragg = -1;
        }else if(y > images[image_i][image_j].getY() + Constants.BEAN_HEIGHT){
            if(image_j + 1 < images[0].length) {
            	if(!images[image_i][image_j].getColorId().equals(images[image_i][image_j + 1].getColorId())) {
                    //System.out.println("Down");

            		this.validationActive = true;
                    
            		this.swap(image_i, image_j, image_i, image_j + 1);
                    
                    if(searchAlgorithm(this.images, image_i, image_j) | searchAlgorithm(this.images, image_i, image_j + 1)) {
                        images[image_i][image_j].startAnimation("top", this.canvasReference, 1);
                        images[image_i][image_j + 1].startAnimation("down", this.canvasReference, 1);
                        imagesUnderAnimation.add(images[image_i][image_j]);
                        imagesUnderAnimation.add(images[image_i][image_j + 1]);
                        new Thread(this).start();

                    }else {
                    	this.swap(image_i, image_j, image_i, image_j + 1);
    	                images[image_i][image_j].startAnimation("downreturn", this.canvasReference, 1);
    	                images[image_i][image_j+1].startAnimation("topreturn", this.canvasReference, 1);
    	                imagesUnderAnimation.add(images[image_i][image_j]);
    	                imagesUnderAnimation.add(images[image_i][image_j+1]);
                    }
                    this.bean_i_dragg = -1;
                    this.bean_j_dragg = -1;
	            }else {
	                images[image_i][image_j].startAnimation("downreturn", this.canvasReference, 1);
	                images[image_i][image_j+1].startAnimation("topreturn", this.canvasReference, 1);
	                imagesUnderAnimation.add(images[image_i][image_j]);
	                imagesUnderAnimation.add(images[image_i][image_j+1]);
	            }
            }
        	this.bean_i_dragg = -1;
        	this.bean_j_dragg = -1;
        }
    }

    // TODO: Validate that beans don´t swap when beansare falling down in a specific column
    public final void swap(int i1, int j1, int i2, int j2){
        Bean temp = images[i1][j1];
        images[i1][j1] = images[i2][j2];
        images[i2][j2] = temp;
    }

    private boolean searchAlgorithm(Bean[][] images, int i, int j) {
    	LinkedList<Bean> tempResultImagesToExplode = new LinkedList<>();
    	boolean explode = false;
    	// Down
    	tempResultImagesToExplode.add(images[i][j]);
    	for (int j2 = j + 1; j2 < images.length; j2++) {
			if(images[i][j2].getColorId() == images[i][j].getColorId() 
					&& !images[i][j2].isThreadActive()
            		&& !images[i][j2].isReadyToRemove()
            		&& !images[i][j2].isReadyToExplode()) {
					//&& !images[i][j2].isThreadActive()) {
				tempResultImagesToExplode.addFirst(images[i][j2]);
			}else {
				break;
			}
		}
    	
    	//Top
    	for (int j2 = j-1; j2 > -1; j2--) {
			if(images[i][j2].getColorId() == images[i][j].getColorId() 
					&& !images[i][j2].isThreadActive()
            		&& !images[i][j2].isReadyToRemove()
            		&& !images[i][j2].isReadyToExplode()){
					//&& !images[i][j2].isThreadActive()) {
				tempResultImagesToExplode.addFirst(images[i][j2]);
			}else {
				break;
			}
		}
    	if(tempResultImagesToExplode.size() >= Constants.BEANS_TO_MATCH) {
    		this.setReadyToExplode(tempResultImagesToExplode);
    		explode = true;
    	}

    	tempResultImagesToExplode.clear();
    	tempResultImagesToExplode.add(images[i][j]);

    	//Right
    	for (int i2 = i + 1; i2 < images.length; i2++) {
			if(images[i2][j].getColorId() == images[i][j].getColorId() 
					&& !images[i2][j].isThreadActive()
            		&& !images[i2][j].isReadyToRemove()
            		&& !images[i2][j].isReadyToExplode()) {
				tempResultImagesToExplode.addFirst(images[i2][j]);
			}else {
				break;
			}
		}
    	
    	// Left
    	for (int i2 = i -1; i2 > -1; i2--) {
			if(images[i2][j].getColorId() == images[i][j].getColorId() 
					&& !images[i2][j].isThreadActive()
            		&& !images[i2][j].isReadyToRemove()
            		&& !images[i2][j].isReadyToExplode()) {
				tempResultImagesToExplode.addFirst(images[i2][j]);
			}else {
				break;
			}
		}

    	if(tempResultImagesToExplode.size() >= Constants.BEANS_TO_MATCH) {
    		this.setReadyToExplode(tempResultImagesToExplode);
    		explode = true;
    	}

    	return explode;
    }
    
    public void setReadyToExplode(LinkedList<Bean> tempResultImagesToExplode) {
    	for (Bean image : tempResultImagesToExplode) {
			image.setReadyToExplode(true);
		}
    }
    
    private synchronized void explode() {
		for (int i = 0; i < images.length; i++) {
			for (int j = 0; j < images.length; j++) {
				if(images[i][j] != null && images[i][j].isReadyToExplode()) {
					images[i][j].setIsThreadActive(true);
					images[i][j].startAnimation("explosion", this.canvasReference, 1);
					this.imagesUnderAnimation.add(images[i][j]);
					images[i][j].remove();
				}
			}
		}
    }
    
    private int searchFirstImageToMove(int i, int j) {
    	for (; j > -1; j--) {
    		if(!images[i][j].isReadyToExplode()) {
    			return j;
    		}
		}
    	// If this return is reached error while searching not null bean
    	return -1;
    }
    
    // This method search if an image is ready to be removed (readyToExplode)
    // then search from j to j-- the first image that is not readyToExplode
    // and use the position of that image in order to move the images down
    private synchronized void animateColumnForExplodedBeans() {
    	int position = 1;
    	int positionToInjectNewBean = 0;
    	Bean tempImage;
    	boolean isColumnReviewed = false;
    	boolean columnWillExplode = false;
    	
		for (int i = 0; i < images.length; i++) {
			for (int j = images[0].length - 1; j > -1; j--) {

				if(!columnWillExplode) {
					columnWillExplode = images[i][j].isReadyToRemove();
				}
				if (columnWillExplode){
					if(!isColumnReviewed) {
						positionToInjectNewBean = this.searchFirstImageToMove(i, j);
						isColumnReviewed = true;
					}
					
					if(positionToInjectNewBean > -1) {
						// Don´t swap when zero, because it will return tempImage to the same position
						images[i][j] = images[i][positionToInjectNewBean];
						
						images[i][j].setTimesToAnimate(j-positionToInjectNewBean);
						images[i][j].setIsFalling(true);
					}else {
						images[i][j] = this.getRandomBean(
								Constants.BOARD_X + (i * Constants.BEAN_WIDTH) + Constants.MARGING * (i+1),
								(Constants.BOARD_Y - (position * Constants.BEAN_HEIGHT) + Constants.MARGING * (position+1)));
						
						images[i][j].setTimesToAnimate(j-positionToInjectNewBean);
						images[i][j].setIsFalling(true);
						position ++;
					}
					positionToInjectNewBean --;
				}
			}
			position = 1;
			isColumnReviewed = false;
			columnWillExplode = false;
		}
    }
    
    public synchronized boolean isAnimationInProgress(){
        boolean result = false;

        for (int i = 0; i < imagesUnderAnimation.size(); i++) {
            Bean current = imagesUnderAnimation.peek();
            if(current != null && !current.isThreadActive()){
               imagesUnderAnimation.pop();
            }else {
            	result = true;
            }
        }

        if(imagesUnderAnimation.isEmpty()) {
        	return false;
        }
        return result;
    }
    
    protected final boolean checkIfMouseInsideRankCoordinates(int x_m, int y_m, int x_j, int y_j){
         if(x_m > x_j && x_m < x_j + Constants.BEAN_WIDTH
             && y_m > y_j && y_m < y_j + Constants.BEAN_HEIGHT){
             return true;
        }
         return false;
    }

	@Override
	public void run() {
		int state = 0;
		while(state != -1) {
			//System.out.println("Inside while");
			switch(state) {
			// Explode Images
				case 0:
					if (isAnimationInProgress()) {
						//System.out.println("In progress");
					}else {
						//System.out.println("End Progress");
						this.explode();
						state = 1;
					}
				break;
				// Remove exploded images
				case 1:
					if (isAnimationInProgress()) {
						//System.out.println("Wating to remove");
					}else {
						this.animateColumnForExplodedBeans();
						state = 2;
					}
				break;
				// Move all images down
				case 2:
					if (isAnimationInProgress()) {
						//System.out.println("Wating to delete");
					}else {
						for (int i = 0; i < images.length; i++) {
							for (int j = images[0].length - 1; j > -1; j--) {
								if (images[i][j].getIsFalling()) {
									images[i][j].setIsFalling(false);
									//imagesUnderAnimation.add(images[i][j]);
									images[i][j].startAnimation("downWhenExplosion", this.canvasReference, images[i][j].getTimesToAnimate());
								}
							}
						}
						state = -1;
					}
					break;
				// search algoritm
				case 3:
					if (isAnimationInProgress()) {
						//System.out.println("explote");
					}else {
						for (int i = 0; i < images.length; i++) {
							for (int j = images[0].length - 1; j > -1; j--) {
								if (!images[i][j].getIsFalling()) {
									if(this.searchAlgorithm(images, i, j)) {
										new Thread(this).start();
									}
								}
							}
						}
					}
					state = -1;
					break;
				default:
					state = -1;
			}
		}
	}
}
