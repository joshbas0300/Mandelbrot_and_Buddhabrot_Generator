
import refrence.Complex;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Buddhabrot {

    static int width = 500, height = 500, iter;
    static int[][] pixels = new int[width][height];

//    public static double map(double value, double low1, double high1, double low2, double high2) {
//        return low2 + (value - low1) * (high2 - low2) / (high1 - low2);
//    }


    final static double EPSILON = 1e-12;

    public static double testmap(double valueCoord1, double startCoord1, double endCoord1, double startCoord2, double endCoord2) {

        if (Math.abs(endCoord1 - startCoord1) < EPSILON) {
            throw new ArithmeticException("/ 0");
        }

        double offset = startCoord2;
        double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
        return ratio * (valueCoord1 - startCoord1) + offset;
    }


    public static void main(String[] args) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = 0;
            }
        }


        Random random = new Random();
        for (int samples = 0; samples < 1000000; samples++) {


            int randx = random.nextInt(width);
            int randy = random.nextInt(height);

            double complex_real = testmap(randx, 0, width, -2, 2);
            double complex_imaginary = testmap(randy, 0, height, -2, 2);

            Complex complex = new Complex(complex_real, complex_imaginary);


            List<Complex> pointList = formula(complex, 2000);

            for (Complex point : pointList) {

//                System.out.println(point);

                if (point.im() <= 2 && point.im() >= -2 && point.re() <= 2 && point.re() >= -2) {

                    int i = (int) testmap(point.re(), -2, 2, 0, 500);
                    int j = (int) testmap(point.im(), -2, 2, 0, 500);

                    if (i > 500 || i < 0 && j > 500 || j < 0) {
                        System.err.println("thefuckthiskeepshappening");
                    }

                    pixels[i][j]++;

                }

            }
        }

        int maxint = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int intint = pixels[i][j];

                if (intint > maxint) {
                    maxint = intint;
                }
            }
        }

        System.out.println(maxint);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int rgb = (int) testmap(pixels[i][j],0,maxint,0,255);

                image.setRGB(i,j,rgb);


            }
        }

        try {
            ImageIO.write(image, "png", new File("plswork2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                if(pixels[i][j] > 2)
//                System.out.println(pixels[i][j]);
//            }
//        }


    }


    public static ArrayList formula(Complex comp, int max) {

        Complex Z = new Complex(0, 0);

        ArrayList list = new ArrayList();
        ArrayList empty = new ArrayList();

        int n = 0;

        while (n < max && Z.sqmagnitude() <= 4) {

            Z = Z.times(Z).plus(comp);
            ++n;

            list.add(Z);

        }

        if (n == max) {
            return empty;
        } else {
            return list;
        }
    }
}






