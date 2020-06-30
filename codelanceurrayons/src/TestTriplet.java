import math.Point3D;
import math.Vector3D;
import math.Color;

import java.util.Arrays;

public class TestTriplet {

    public static Object buildObject(String str) throws Exception {
        String[] triplet = str.split(" ");
        if (triplet.length == 1)
            return Double.parseDouble(triplet[0]);

        double x = Double.parseDouble(triplet[1]);
        double y = Double.parseDouble(triplet[2]);
        double z = Double.parseDouble(triplet[3]);

        switch (triplet[0]) {
            case "P":
                return new Point3D(x, y, z);
            case "V":
                return new Vector3D(x, y, z);
            case "C":
                return new Color(x, y, z);
            default:
                throw new Exception("Type Invalide /!\\ " + Arrays.toString(triplet));
        }
    }

    public static void main(String[] args) throws Exception {
        String[] data = args[0].split(",");
        String operation = data[1];
        Object o1 = buildObject(data[0]);
        Object o2 = buildObject(data[2]);
        try {
            Class<?> clazz2 = o2.getClass() == Double.class ? double.class : o2.getClass();
            Object o3 = o1.getClass().getMethod(operation, clazz2).invoke(o1, o2);
            System.out.println(o3);
        } catch (Exception e) {
            System.out.println("Interdit");
        }
    }

}
