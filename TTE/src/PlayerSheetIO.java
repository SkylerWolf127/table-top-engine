import java.io.*;
public class PlayerSheetIO//save load routine (It probably works...)
{
    public static void savePlayerSheetToFile(Sheet sheet, String filename)
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(sheet);
            System.out.println("Player sheet saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving player sheet: " + e.getMessage());
        }
    }

    public static Sheet loadPlayerSheetFromFile(String filename)
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Sheet sheet = (Sheet) ois.readObject();
            System.out.println("Player sheet loaded from " + filename);
            return sheet;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading player sheet: " + e.getMessage());
            return null;
        }
    }


}
