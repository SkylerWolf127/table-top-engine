import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    public static Sheet loadSheetFromDirectory(){
        Sheet loadedSheet = new Sheet();

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TTE Sheet Files", "dat");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()))){
                loadedSheet=(Sheet)ois.readObject();

            }catch (IOException | ClassNotFoundException e){
                System.err.println("Error loading player sheet: " + e.getMessage());
            }
        }
        return loadedSheet;
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
