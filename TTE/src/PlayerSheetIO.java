import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


public class PlayerSheetIO//save load routine (It probably works...)
{
    public static void savePlayerSheetToFile(Sheet sheet, String filename)
    {
        /*
        Open object output stream, send sheet obj + file name to destination (TTE folder)
        if file was unable to save, throw stderr back to main
         */

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(sheet);
            System.out.println("Player sheet saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving player sheet: " + e.getMessage());
        }
    }

    public static Sheet loadSheetFromDirectory()
    {
        /*
        Create a blank sheet object, create JFileChooser. Filter is set for TTE .dat files and those are the only ones that will be loaded.
        User must select a valid .dat TTE file. If a valid one is not chosen the file will not load.
        Successful files will be passed into the loadedSheet object and returned back to Main.
         */
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

    /*

       ### This loading Routine is for DEBUG USE ONLY. Only use if the traditional GUI loading has broken down ###
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

     */


}
