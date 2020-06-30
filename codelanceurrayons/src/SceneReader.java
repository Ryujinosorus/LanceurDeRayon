import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import environment.Scene;

public class SceneReader {
    private final Scene scene;
    private Scanner scanner;

    SceneReader(String filename) {
        this.scene = new Scene();
        try {
            this.scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    public Scene read() {
        String line;
        String[] data;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            data = line.split("\\s+", 2);
            try {
                if (!(line.startsWith("#") || line.trim().length() == 0))
                    this.scene.getClass().getDeclaredMethod(data[0], String.class).invoke(scene, data[1]);
            } catch (Exception e) {
                System.out.println("Erreur :" + line);
            }
        }

        return scene;
    }

    public static void main(String[] args) {
        SceneReader sr = new SceneReader(args[0]);
        Scene scene = sr.read();
        System.out.println(scene.getOutput());
        System.out.println(scene.getSize()[0] * sr.scene.getSize()[1]);
        System.out.println(scene.getProps().size());
        System.out.println(scene.getLights().size());
    }

}
