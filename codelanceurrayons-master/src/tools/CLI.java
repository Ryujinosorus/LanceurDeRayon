package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import colorfinders.RecursiveColorFinder;
import raytracer.ColorFinder;
import raytracer.Parser;
import raytracer.RayTracer;
import scene.Scene;

public class CLI {

	/**
	 * Setup a raytracer according to the command line parameters.
	 * 
	 * @param args the command line parameters.
	 * @throws IOException               if the scene file cannot be found.
	 * @throws InstantiationException    if the illuminating model cannot be found.
	 * @throws IllegalAccessException    if the illuminating model cannot be found.
	 * @throws ClassNotFoundException    if the illuminating model cannot be found.
	 * @throws NoSuchMethodException     if the illuminating model cannot be found.
	 * @throws InvocationTargetException if the illuminating model cannot be found.
	 */
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
		String sceneFile;
		ColorFinder colorFinder;
		switch (args.length) {
		case 1:
			sceneFile = args[0];
			colorFinder = new RecursiveColorFinder();
			break;
		case 2:
			sceneFile = args[1];
			colorFinder = (ColorFinder) Class.forName("lanceurrayondlb.colormodel." + args[0] + "ColorFinder")
					.getDeclaredConstructor().newInstance();
			break;
		default:
			System.out.println(
					"Usage: java [-Dtimeout=2400] [-Dnoshow] [-Dsampling=middle|grid/4|random/10] [-Ddivision=value] [-Dgrid] [-Dnoshadow] -jar raytracer.jar [ColorFinder] scenefile");
			return;
		}
		Scene scene = new Parser(new BufferedReader(new FileReader(sceneFile))).parse();
		colorFinder.setShadowEnabled(scene.isShadowEnabled());
		String sampler = System.getProperty("sampling");

		if (sampler != null) {
			scene.setSampler(sampler);
		}

		RayTracer tracer = new RayTracer(System.getProperty("grid") != null, colorFinder);
		BufferedImage out = new BufferedImage(scene.getWidth(), scene.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < scene.getWidth(); x++) {
			for (int y = 0; y < scene.getHeight(); y++) {
				out.setRGB(x, y, Color.WHITE.getRGB());
			}
		}
		JLabel label;
		if (System.getProperty("noshow") == null) {
			JFrame frame = new JFrame("Image générée");
			label = new JLabel(new ImageIcon(out));
			frame.getContentPane().add(new JScrollPane(label));
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		} else {
			// null object design pattern
			label = new JLabel();
		}
		long start = System.currentTimeMillis();
		tracer.trace(scene, out, label);
		long stop = System.currentTimeMillis();
		if (System.getProperty("noshow") == null) {
			System.out.printf("Rendering time: %d ms%n", stop - start);
		}
		ImageIO.write(out, "png", new File(scene.getOutputFileName()));
	}
}
