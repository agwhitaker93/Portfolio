package agwhitaker93;

import java.util.Random;

public class DLAProcess {
    int[][] kCircleBounds;
    int[][] lCircleBounds;
    int xMax;
    int yMax;
    int maxPixels;
    int k;
    int l;
    int j;
    int pixelsInKillCircle;
    int pixelsInLaunchCircle;
    Random randNum;
    String adjacentTo;
    int pixelSize;
    int chance;

    public DLAProcess(int width, int height, int x, int y, int pixels, int size, int percent) {
        xMax = width;
        yMax = height;
        randNum = new Random();
        maxPixels = pixels;
        k = 0;
        l = 0;
        j = 0;
        pixelSize = size;
        chance = percent;
    }

    public void setCirclePoints(int amount, int circleType) {
        k = 0;
        l = 0;

        switch (circleType) {
            case 0:
                pixelsInKillCircle = amount;
                kCircleBounds = new int[pixelsInKillCircle][2];
                break;

            case 1:
                pixelsInLaunchCircle = amount;
                lCircleBounds = new int[pixelsInLaunchCircle][2];
                break;
        }
    }

    public void nextCircleCoord(int x, int y, int circleType) {
        switch (circleType) {
            case 0:
                kCircleBounds[k][0] = x;
                kCircleBounds[k][1] = y;
                k++;
                break;

            case 1:
                lCircleBounds[l][0] = x;
                lCircleBounds[l][1] = y;
                l++;
                break;
        }
    }

//	public void generatePixels(int x, int y)
//	{
//		stickyPixels[j][0] = x;
//		stickyPixels[j][1] = y;
//		float percent = 1f / maxPixels;
//		float percentHolder = percent;
//		
//		for (j = 1; j < maxPixels; j++)
//		{
//			
//			percent += percentHolder;
//			int randomPointer = randNum.nextInt(pixelsInLaunchCircle);
//			
//			int[] coordinates = new int[2];
//			coordinates[0] = lCircleBounds[randomPointer][0];
//			coordinates[1] = lCircleBounds[randomPointer][1];
//	    	
//			coordinates = movePixel(coordinates);
//	    	
//	    	System.out.println("Points plotted: " + j);
//			
//			stickyPixels[j][0] = coordinates[0];
//			stickyPixels[j][1] = coordinates[1];
//		}
//	}

    public int coordPlusPlus(int value) {
        if (value > 0) {
            switch (randNum.nextInt(chance)) {
                case 0:
                    value--;
                    break;

                default:
                    value++;
                    break;
            }
        } else
            value++;

        return value;
    }

    public int coordMinusMinus(int value) {
        if (value < 0) {
            switch (randNum.nextInt(chance)) {
                case 0:
                    value++;
                    break;

                default:
                    value--;
                    break;
            }
        } else
            value--;

        return value;
    }

    public boolean checkAdjacency(int[] position, int[][] stickyPixels, Boolean eightConnection) {
        if (eightConnection) {
            for (int i = 0; i < maxPixels; i++) {
                if (position[0] == stickyPixels[i][0] || position[0] == stickyPixels[i][0] + pixelSize ||
                        position[0] == stickyPixels[i][0] - pixelSize) {

                    if (position[1] == stickyPixels[i][1] || position[1] == stickyPixels[i][1] + pixelSize ||
                            position[1] == stickyPixels[i][1] - pixelSize) {
                        adjacentTo = "Sticky";
                        return true;
                    }
                }
            }

            for (int i = 0; i < pixelsInKillCircle; i++) {
                if (position[0] == kCircleBounds[i][0] || position[0] == kCircleBounds[i][0] + pixelSize ||
                        position[0] == kCircleBounds[i][0] - pixelSize) {

                    if (position[1] == kCircleBounds[i][1] || position[1] == kCircleBounds[i][1] + pixelSize ||
                            position[1] == kCircleBounds[i][1] - pixelSize) {
                        adjacentTo = "Kill";
                        return true;
                    }
                }

            }
        } else {
            for (int i = 0; i < maxPixels; i++) {
                if (position[0] == stickyPixels[i][0]) {

                    if (position[1] == stickyPixels[i][1] + pixelSize ||
                            position[1] == stickyPixels[i][1] - pixelSize) {
                        adjacentTo = "Sticky";
                        return true;
                    }
                } else if (position[0] == stickyPixels[i][0] + pixelSize ||
                        position[0] == stickyPixels[i][0] - pixelSize) {
                    if (position[1] == stickyPixels[i][1]) {
                        adjacentTo = "Sticky";
                        return true;
                    }
                }
            }

            for (int i = 0; i < pixelsInKillCircle; i++) {
                if (position[0] == kCircleBounds[i][0]) {

                    if (position[1] == kCircleBounds[i][1] + pixelSize ||
                            position[1] == kCircleBounds[i][1] - pixelSize) {
                        adjacentTo = "Kill";
                        return true;
                    }
                } else if (position[0] == kCircleBounds[i][0] + pixelSize ||
                        position[0] == kCircleBounds[i][0] - pixelSize) {
                    if (position[1] == kCircleBounds[i][1]) {
                        adjacentTo = "Kill";
                        return true;
                    }
                }

            }
        }

        return false;
    }


}
