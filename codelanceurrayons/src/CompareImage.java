import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CompareImage {

    CompareImage() {
    }

    public void compare(String image1Path, String image2Path, String outputPath) throws IOException {
        BufferedImage image1 = ImageIO.read(new File(image1Path));
        BufferedImage image2 = ImageIO.read(new File(image2Path));
        BufferedImage output = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_RGB);

        int col1 = 0; // color from image 1
        int col2 = 0; // color from image 2
        int diff = 0; // count all the pixel difference

        if (image1.getHeight() == image2.getHeight() && image1.getWidth() == image2.getWidth()) {
            for (int i = 0; i < image1.getWidth(); i++) {
                for (int j = 0; j < image1.getHeight(); j++) {

                    col1 = image1.getRGB(i, j);
                    // shift bits to get R G B colors
                    int red1 = (col1 >> 16) & 255;
                    int green1 = (col1 >> 8) & 255;
                    int blue1 = (col1) & 255;

                    col2 = image2.getRGB(i, j);
                    int red2 = (col2 >> 16) & 255;
                    int green2 = (col2 >> 8) & 255;
                    int blue2 = (col2) & 255;

                    if (col1 != col2) {
                        // calculate the color result
                        int redR = Math.abs(red1 - red2);
                        int greenR = Math.abs(green1 - green2);
                        int blueR = Math.abs(blue1 - blue2);
                        // shift them back
                        output.setRGB(i, j, (255 << 24) | (redR << 16) | (greenR << 8) | blueR);
                        diff++;
                    } else {
                        output.setRGB(i, j, 0);
                    }
                }
            }
            System.out.println(diff <= 1000 ? "OK" : "KO");
            System.out.println(diff);
            ImageIO.write(output, "PNG", new File(outputPath));
            return;
        }
        throw new IOException("Les images ne sont pas de la même dimension.");
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Args = Image1Path Image2Path");
            System.exit(1);
        }
        CompareImage comp = new CompareImage();
        try {
            comp.compare(args[0], args[1], "diff.png");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
