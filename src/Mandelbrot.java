import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Mandelbrot {

    static int width = 2000, height = 2000, max = 1000, iter;

    public static double map(double value, double low1, double high1, double low2, double high2) {
        return low2 + (value - low1) * (high2 - low2) / (high1 - low2);
    }


    public static void main(String[] args) throws Exception {


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0;
        int[] colour = new int[max];
        for (int i = 0; i < 256; i++) {
            colour[i] = Color.HSBtoRGB(i / 256f, 1, i / (i + 8f));
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double c_re = map(x, 0, width, -2, 2);
                double c_im = map(y, 0, height, -2, 2);

                double o_re = c_re, o_im = c_im;

                double julia_re = -0.79, julia_im = 0.15;

                for (iter = 0; iter < max; iter++) {
                    double new_re = c_re * c_re - c_im * c_im;
                    double new_im = 2 * c_re * c_im;

                    c_re = new_re + o_re;
                    c_im = new_im + o_im;

                    if (c_re * c_re + c_im * c_im > 4) {

                        break;
                    }
                }

                System.out.println("X = " + x);
                System.out.println("Y = " + y);
                System.out.println();

                if (iter < max) image.setRGB(x, y, colour[iter]);
                else image.setRGB(x, y, black);

            }
        }
        int num = 11;
        ImageIO.write(image, "png", new File("mymandelbrot" + num + ".png"));

//        for(int j = 0; j <256; j++) {
//            System.out.println(Math.abs(colour[j]));
//        }
    }
}


