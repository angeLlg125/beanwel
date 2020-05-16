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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.Constants;
import utils.Bean;

/**
 *
 * @author angel
 */
public class MainBoard implements Runnable{
    public Bean[][] images;
    int [] underAnimation = new int[Constants.ARRAY_SIZE_Y];

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
        int count = 0;
        String leftColorId = "";

        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[i].length; j++) {
            	images[i][j] = this.getBeanWithNoDuplicades(leftColorId, i, j, count);
            	if(images[i][j].getColorId() != leftColorId) {
            		leftColorId = images[i][j].getColorId(); 
            		count = 1;
            	}else {
            		count +=1;
            	}
            	// Initialize rows under animation in -1
            	if(i == 0) {
            		this.underAnimation[j] = -1;
            	}
            }
            leftColorId = "";
            count = 0;
        }

        this.canvasReference.repaint();
    }
    
    private Bean getBeanWithNoDuplicades(String leftItem, int i, int j, int count) {
    	// Calculate bean position
        int y = Constants.BOARD_X + (i * Constants.BEAN_WIDTH) + Constants.MARGING * (i+1);
        int x = Constants.BOARD_Y + (j * Constants.BEAN_HEIGHT) + Constants.MARGING * (j+1);
    	String valuesToSkipp = "";
    	
    	if(i - 2 >= 0) {
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
        
        // Draw rectangles
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[0].length; j++) {
                y = Constants.BOARD_X + (i * Constants.BEAN_WIDTH) + Constants.MARGING * (i+1);
                x = Constants.BOARD_Y + (j * Constants.BEAN_HEIGHT) + Constants.MARGING * (j+1);
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
        // Draw images
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[0].length; j++) {
            	if(images[i][j] != null) {
            		images[i][j].drawImage(g, canvas, j);
            	}
            }
        }
    }
    
    // When user release the click button, then will be able to drag another bean
    public void releaseClickMouseLogic(int x, int y){
    	this.validationActive = false;
    }
    
    // When user is dragging and release the click button, then reset the selected i, j of the bean
    public void releaseMouseLogic(int x, int y){
    	this.validationActive = false;
    	this.bean_i_dragg = -1;
    	this.bean_j_dragg = -1;
    }
    
    public void draggMouseLogic(int x, int y){
        if (this.validationActive) {
            return;
        }
        
        // Calculate the i, j of the selected bean 
		int temp_i_dragg =  (y - Constants.BOARD_X - Constants.MARGING )/(Constants.BEAN_WIDTH+Constants.MARGING);
		int temp_j_dragg = (x - Constants.BOARD_Y- Constants.MARGING )/(Constants.BEAN_HEIGHT+Constants.MARGING);

        if(bean_i_dragg == -1 && bean_j_dragg == -1) {
        	// When there is no a selected bean, select current bean 
            this.bean_i_dragg = temp_i_dragg;
            this.bean_j_dragg = temp_j_dragg;
        }else {
        	// Validate if the positions i, j are different from the new ones, also validate if you are dragging outside the matrix
        	if(temp_i_dragg == this.bean_i_dragg && temp_j_dragg == this.bean_j_dragg 
        			|| temp_i_dragg >= this.images.length || temp_i_dragg < 0
        			|| temp_j_dragg >= this.images[0].length || temp_j_dragg < 0) {
        				// DO Nothing in this scenarios
        		
        	}else {
        		this.validateSwapDirection(temp_i_dragg, temp_j_dragg, this.bean_i_dragg, this.bean_j_dragg);
        	}
        }
    }
    
    private void validateSwapDirection(int new_i, int new_j, int image_i, int image_j){
    	// Validate if the animation of selected beans is active
    	// Validate the min distance between two beans, is 1, more means they are not one next to the other
    	if(this.images[new_i][new_j].isThreadActive() 
    		|| this.images[image_i][image_j].isThreadActive()
    		|| Math.abs(new_i - image_i) > 1 || Math.abs(new_j - image_j) > 1){
    		return;
    		
    	// Verify the direction where the bean will be moved
    	}else if(new_j < image_j) {
        	// Left
            this.validationActive = true;
        	this.performSwap("left", "right",  image_i, image_j, image_i, new_j);

        }else if(new_j > image_j){
        	// Right
            this.validationActive = true;
        	this.performSwap("right", "left",image_i, image_j, image_i, new_j);

        }else if(new_i < image_i){
        	// Top
            this.validationActive = true;
        	this.performSwap("top", "down",image_i, image_j, new_i, image_j);
        	
        }else if(new_i > image_i){
        	// Down
            this.validationActive = true;
        	this.performSwap("down", "top", image_i, image_j, new_i, image_j);
        	
        }
    }
    
    private void performSwap(
    		String firstAnimation, 
    		String seccondAnimation,
    		int first_i, int first_j, int seccond_i, int seccond_j) {

    	// If the selected bean and the new one has different color verify swap
    	if(!images[first_i][first_j].getColorId().equals(images[seccond_i][seccond_j].getColorId())) {

    			// Swap selected bean and new bean
    		    this.swap(first_i, first_j, seccond_i, seccond_j);
    		    
    		    // Search if there is a coincidence on the beans, if true then those beans will be exploded
                if(searchAlgorithm(this.images, first_i, first_j) | searchAlgorithm(this.images, seccond_i, seccond_j)) {
                    images[first_i][first_j].startAnimation(seccondAnimation, this.canvasReference);
                    images[seccond_i][seccond_j].startAnimation(firstAnimation, this.canvasReference);
                
                // if there is not a coincidence, only do a swap return animation
                }else {
                	this.swap(first_i, first_j, seccond_i, seccond_j);
                    images[first_i][first_j].startAnimation(firstAnimation+"return", this.canvasReference);
                    images[seccond_i][seccond_j].startAnimation(seccondAnimation+"return", this.canvasReference);
                    //imagesUnderAnimation.add(images[first_i][first_j]);
                    //imagesUnderAnimation.add(images[seccond_i][seccond_j]);
                }
        // If beans has same color, just do swap return animation
        }else {
            images[first_i][first_j].startAnimation(firstAnimation+"return", this.canvasReference);
            images[seccond_i][seccond_j].startAnimation(seccondAnimation+"return", this.canvasReference);
            //imagesUnderAnimation.add(images[first_i][first_j]);
            //imagesUnderAnimation.add(images[seccond_i][seccond_j]);
        }
    }

    // Swap beans
    public final void swap(int i1, int j1, int i2, int j2){
        Bean temp = images[i1][j1];
        Bean temp2 = images[i2][j2];
        images[i1][j1] = images[i2][j2];
        images[i2][j2] = temp;
    }

    
    private boolean searchAlgorithm(Bean[][] images, int i, int j) {
    	LinkedList<Bean> tempResultImagesToExplode = new LinkedList<>();
    	boolean explode = false;
 
    	tempResultImagesToExplode.add(images[i][j]);
    	
    	int lastIPosition = i;
    	int count = 1;
    	
    	// Top: count coincidences
    	for (int i2 = i - 1; i2 > -1; i2--) {
			if(images[i2][j].getColorId() == images[i][j].getColorId()
					&& !images[i2][j].isThreadActive()
            		&& !images[i2][j].isReadyToRemove()
            		&& !images[i2][j].isReadyToExplode()){

				count++;
			}else {
				break;
			}
		}
    	// Down: count coincidences
    	for (int i2 = i + 1; i2 < images.length; i2++) {
			if(images[i2][j].getColorId() == images[i][j].getColorId() 
					&& !images[i2][j].isThreadActive()
            		&& !images[i2][j].isReadyToRemove()
            		&& !images[i2][j].isReadyToExplode()) {

				lastIPosition = i2;
				count++;
			}else {
				break;
			}
		}

    	// If more than three coincidences where found then go to method and explode it
    	if(count >= Constants.BEANS_TO_MATCH) {
    		this.setReadyToExplodeVertical(lastIPosition, j, count);
    		explode = true;
    	}

    	count = 1;
    	int lastJPosition = j;
    	// Right: count coincidences
    	for (int j2 = j + 1; j2 < images[i].length; j2++) {
			if(images[i][j2].getColorId() == images[i][j].getColorId() 
					&& !images[i][j2].isThreadActive()
            		&& !images[i][j2].isReadyToRemove()
            		&& !images[i][j2].isReadyToExplode()) {
				tempResultImagesToExplode.addFirst(images[i][j2]);

				count++;
			}else {
				break;
			}
		}
    	
    	// Left: count coincidences
    	for (int j2 = j -1; j2 > -1; j2--) {
			if(images[i][j2].getColorId() == images[i][j].getColorId() 
					&& !images[i][j2].isThreadActive()
            		&& !images[i][j2].isReadyToRemove()
            		&& !images[i][j2].isReadyToExplode()) {

				lastJPosition = j2;
				count++;
			}else {
				break;
			}
		}

    	if(count >= Constants.BEANS_TO_MATCH) {
    		this.setReadyToExplodeHorizontal(i, lastJPosition, count);
    		explode = true;
    	}

    	return explode;
    }
    
    public void setReadyToExplodeVertical(int i, int j, int total) {
		// Update row position under animation
		if (i > this.underAnimation[j]) {
			this.underAnimation[j] = i;
		}

    	while(total > 0) {
    		this.images[i][j].setReadyToExplode(true);
    		total--;
    		i--;
		}
    	// This has to be executed on a different class
    	new Thread(this).start();
    }
    
    // This has to be executed on a different class
    public void setReadyToExplodeHorizontal(int i, int j, int total) {
    	while(total > 0) {
    		// update row position under animation for every column
    		if (i > this.underAnimation[j]) {
    			this.underAnimation[j] = i;
    		}

    		this.images[i][j].setReadyToExplode(true);
    		total--;
    		j++;
		}
    	
    	// This has to be done inside the while, is required a thread for every column
    	new Thread(this).start();
    }
    
    private synchronized void explode() {
		for (int i = 0; i < images.length; i++) {
			for (int j = 0; j < images.length; j++) {
				if(images[i][j] != null && images[i][j].isReadyToExplode()) {
					images[i][j].startAnimation("explosion", this.canvasReference);

					images[i][j].remove();
				}
			}
		}
    }
    
    private int searchFirstImageToMove(int i, int j) {
    	for (; i > -1; i--) {
    		if(!images[i][j].isReadyToExplode()) {
    			return i;
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
    	boolean isColumnReviewed = false;
    	boolean columnWillExplode = false;
    	
		for (int j = 0; j < images[0].length; j++) {
			for (int i = images.length - 1; i > -1; i--) {

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
						images[i][j] = images[positionToInjectNewBean][j];
						
						images[i][j].setTimesToMoveDown(i-positionToInjectNewBean);
						images[i][j].setIsFalling(true);
					}else {
				        int y = Constants.BOARD_X - (position * Constants.BEAN_WIDTH) + Constants.MARGING * (position+1);
				        int x = Constants.BOARD_Y + (j * Constants.BEAN_HEIGHT) + Constants.MARGING * (j+1);
						images[i][j] = this.getRandomBean(
								x,
								y);
						
						images[i][j].setTimesToMoveDown(i-positionToInjectNewBean);
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
		// While will be removed
		while(state != -1) {
			switch(state) {
			// Explode Images
				case 0:
					// Explode Beans
					this.explode();
					state = 1;
				break;
				
				case 1:
					// Calculate how many positions a bean will be moved down
						this.animateColumnForExplodedBeans();
						state = 2;
				break;
				// Move all images down
				case 2:
					
					// Start animation to move bean down
					for (int i = 0; i < images.length; i++) {
						for (int j = images[0].length - 1; j > -1; j--) {
							if (images[i][j].getIsFalling()) {
								images[i][j].setIsFalling(false);
								//imagesUnderAnimation.add(images[i][j]);
								images[i][j].startAnimation("downWhenExplosion", this.canvasReference);
							}
						}
					}
					state = 3;

					break;

				case 3:
					// Search if the beans that where moved down found a match and need to be exploded
			    	boolean incremetController = false;
			    	int current_i = -1;
			    	int current_j = 0;
			    	while(current_i < images.length-1 || current_j < images[0].length-1) {
			    		
			    		incremetController = !incremetController;
			    		if(incremetController) {
			    			current_i++;
			    		}else {
			    			current_j++;
			    		}
			    		
			    		for (int i = current_i, temp_j = current_j; i >= 0 && temp_j <=  images[0].length -1; i--, temp_j++) {
			    			this.searchAlgorithm(images, i, temp_j);
			    			
			    			if(temp_j != i)
			    				this.searchAlgorithm(images, temp_j, i);
						}
			    	}
			    	
					state = -1;
					break;
				case 4:
					// No longer necesary
			    	incremetController = false;
			    	current_i = -1;
			    	current_j = 0;
			    	while(current_i < images.length-1 || current_j < images[0].length-1) {
			    		
			    		incremetController = !incremetController;
			    		if(incremetController) {
			    			current_i++;
			    		}else {
			    			current_j++;
			    		}
			    		
			    		for (int i = current_i, temp_j = current_j; i >= 0 && temp_j <=  images[0].length -1; i--, temp_j++) {
			    			images[i][temp_j].startAnimation("explosion", this.canvasReference);
			    			
			    			if(temp_j != i)
			    			images[temp_j][i].startAnimation("explosion", this.canvasReference);
						}
			            try {
			                TimeUnit.MILLISECONDS.sleep(200);
			            } catch (InterruptedException ex) {
			                Logger.getLogger(Bean.class.getName()).log(Level.SEVERE, null, ex);
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
