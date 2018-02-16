import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author cgray
 */
public class GLEventListener implements com.jogamp.opengl.GLEventListener {
    public static String pixelCount;
    public static Boolean start;
    public static Boolean reStart;

    static int xSize;
    static int ySize;
    static int pixels;
    static int pixelSize;
    static int chance;
    static int j;
    int randomPointer;
    Random randNum;
    static float percent;
    static float percentHolder;
    static Boolean drawCircle;
    static Boolean drawRed;
    static Boolean drawGreen;
    static Boolean drawBlue;
    static Boolean connectType;

    static int[][] stickyPixels;

    static DLAProcess dlaGen;

    protected void setup(GL2 gl2, int width, int height) {
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        //coordinate system origin at lower left with width and height same as the window
        GLU glu = new GLU();
        glu.gluOrtho2D(-width / 2, width / 2, -height / 2, height / 2);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
        gl2.glViewport(0, 0, width, height);
        xSize = width / 2;
        ySize = height / 2;
        randNum = new Random();
        pixelCount = "0";
        start = false;
    }

    public static void buttons(String command, int toDraw, int bias, Boolean drawKillCircle, Boolean r, Boolean g, Boolean b, Boolean connections) {
        switch (command) {
            case "Start":
                start = true;
                pixels = toDraw;
                pixelSize = 1;
                chance = bias;
                dlaGen = new DLAProcess(xSize * 2, ySize * 2, 0, 0, pixels, pixelSize, chance);
                stickyPixels = new int[pixels][2];
                j = 0;
                stickyPixels[j][0] = 0;
                stickyPixels[j][1] = 0;
                percent = 1f / pixels;
                percentHolder = percent;
                drawCircle = drawKillCircle;
                drawRed = r;
                drawGreen = g;
                drawBlue = b;
                connectType = connections;
                break;

            case "Pause":
                start = false;
                break;

            case "Unpause":
                start = true;
                break;

            case "Draw Circle":
                drawCircle = drawKillCircle;
                break;

            case "Red":
                drawRed = r;
                break;

            case "Green":
                drawGreen = g;
                break;

            case "Blue":
                drawBlue = b;
                break;

            case "Connections":
                connectType = connections;
        }
    }

    public static void setColours(String colour, Boolean drawThis) {
        switch (colour) {

        }
    }

    protected void render(GL2 gl2, int width, int height) {
        if (start) {
            gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

            int kCircleRadius;

            if (xSize != ySize) {
                if (xSize > ySize)
                    kCircleRadius = ySize;

                else
                    kCircleRadius = xSize;
            } else
                kCircleRadius = xSize;

            int lCircleRadius = kCircleRadius;
            lCircleRadius *= 0.8;
            kCircleRadius *= 0.9;

            gl2.glLoadIdentity();

            if (drawCircle)
                gl2.glColor3f(1, 1, 1);

            else
                gl2.glColor3f(0, 0, 0);

            drawCircle(gl2, 0, 0, kCircleRadius, 1024, 0);
            gl2.glColor3f(0, 0, 0);
            drawCircle(gl2, 0, 0, lCircleRadius, 64, 1);

            if (j < pixels) {
                percent += percentHolder;
                randomPointer = randNum.nextInt(dlaGen.pixelsInLaunchCircle);

                int[] coordinates = new int[2];
                coordinates[0] = dlaGen.lCircleBounds[randomPointer][0];
                coordinates[1] = dlaGen.lCircleBounds[randomPointer][1];

                coordinates = movePixel(coordinates, gl2);

                stickyPixels[j][0] = coordinates[0];
                stickyPixels[j][1] = coordinates[1];
                j++;
                pixelCount = "Pixels placed: " + j + " of a total: " + pixels;
                JOGLFrame.refreshCounter(pixelCount);
            }

            int[] coords = new int[2];
            float red = 0;
            float green = 0;
            float blue = 0;
            float rPercent = 0;
            float gPercent = 0;
            float bPercent = 0;

            if (drawRed) {
                red = 1;
                rPercent = red / j * 0.9f;
            }

            if (drawGreen) {
                green = 1;
                gPercent = green / j * 0.9f;
            }

            if (drawBlue) {
                blue = 1;
                bPercent = blue / j * 0.9f;
            }

            gl2.glBegin(GL2.GL_POINTS);
            for (int i = 0; i < j; i++) {
                gl2.glColor3f(red, green, blue);
                coords[0] = stickyPixels[i][0];
                coords[1] = stickyPixels[i][1];
                gl2.glVertex2f(coords[0], coords[1]);
                red -= rPercent;
                green -= gPercent;
                blue -= bPercent;
            }
            gl2.glEnd();
        }
    }

    public int[] movePixel(int[] position, GL2 gl2) {
        int[] coords = new int[2];

        coords[0] = position[0];
        coords[1] = position[1];
        boolean reset = true;

        gl2.glBegin(GL2.GL_POINTS);

        while (reset) {
            while (!dlaGen.checkAdjacency(coords, stickyPixels, connectType)) {
                switch (randNum.nextInt(8)) {
                    case 0:                        //X++
                        coords[0] = dlaGen.coordPlusPlus(coords[0]);
                        break;

                    case 1:                        //X--
                        coords[0] = dlaGen.coordMinusMinus(coords[0]);
                        break;

                    case 2:                        //Y++
                        coords[1] = dlaGen.coordPlusPlus(coords[1]);
                        break;

                    case 3:                        //Y--
                        coords[1] = dlaGen.coordMinusMinus(coords[1]);
                        break;

                    case 4:                        //X++ Y++
                        coords[0] = dlaGen.coordPlusPlus(coords[0]);
                        coords[1] = dlaGen.coordPlusPlus(coords[1]);
                        break;

                    case 5:                        //X-- Y--
                        coords[0] = dlaGen.coordMinusMinus(coords[0]);
                        coords[1] = dlaGen.coordMinusMinus(coords[1]);
                        break;

                    case 6:                        //X++ Y--
                        coords[0] = dlaGen.coordPlusPlus(coords[0]);
                        coords[1] = dlaGen.coordMinusMinus(coords[1]);
                        break;

                    case 7:                        //X-- Y++
                        coords[0] = dlaGen.coordMinusMinus(coords[0]);
                        coords[1] = dlaGen.coordPlusPlus(coords[1]);
                        break;
                }
            }

            if (dlaGen.adjacentTo == "Kill") {
                coords[0] = position[0];
                coords[1] = position[1];
                reset = true;
            } else
                reset = false;
        }

        if (dlaGen.adjacentTo == "Kill") {
            coords[0] = 0;
            coords[1] = 0;
        }

        gl2.glEnd();

        return coords;
    }

    @Override
    public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
        setup(glautodrawable.getGL().getGL2(), width, height);
    }

    @Override
    public void init(GLAutoDrawable glautodrawable) {
    }

    @Override
    public void dispose(GLAutoDrawable glautodrawable) {
    }

    @Override
    public void display(GLAutoDrawable glautodrawable) {
        render(glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight());
    }

    private void drawPixel(GL2 gl2, int x, int y, int size) {
        drawSquare(gl2, x, y, size);
    }

    private void drawSquare(GL2 gl2, int x, int y, float offset) {
        gl2.glBegin(GL2.GL_QUADS);

        gl2.glVertex2f(x, y);                    //bottom left corner
        gl2.glVertex2f(x + offset, y);            //bottom right corner
        gl2.glVertex2f(x + offset, y + offset);    //top right corner
        gl2.glVertex2f(x, y + offset);            //top left corner

        gl2.glEnd();
    }

    //code courtesy of SiegeLord at http://slabode.exofire.net/circle_draw.shtml
    private void drawCircle(GL2 gl2, float cx, float cy, float r, int numSegments, int circleType) {
        dlaGen.setCirclePoints(numSegments, circleType);
        double theta = 2 * 3.1415926 / numSegments;
        double tangentialFactor = Math.tan(theta);

        double radialFactor = Math.cos(theta);

        float x = r;
        float y = 0;
//    	System.out.println(numSegments);

        gl2.glBegin(GL2.GL_POINTS);
        for (int i = 0; i < numSegments; i++) {
            int drawX = (int) (x + cx);
            int drawY = (int) (y + cy);
//    		drawPixel(gl2, drawX, drawY, pixelSize);
            gl2.glVertex2f(drawX, drawY);
            dlaGen.nextCircleCoord(drawX, drawY, circleType);

            float tx = -y;
            float ty = x;

            x += tx * tangentialFactor;
            y += ty * tangentialFactor;

            x *= radialFactor;
            y *= radialFactor;
        }
        gl2.glEnd();
    }
}