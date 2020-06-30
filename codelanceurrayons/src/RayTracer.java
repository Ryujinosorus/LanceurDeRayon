import java.io.IOException;

public class RayTracer {

    public static void main(String[] args) throws IOException, IllegalArgumentException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong argument, please give a scene file to the program");
        }

        ImageGenerator imgGenerator = new ImageGenerator(args[0]);
        imgGenerator.generate();
    }

}
