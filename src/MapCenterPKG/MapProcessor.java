package MapCenterPKG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class MapProcessor
{
    //This string will hold the name of the map file (or the path)
    private String mapFile;

    //These integers will hold the shift values on the respective axis
    private int xShift, yShift, zShift;

    //This scanner will perform scans on the map file
    private Scanner mapScanner;

    //This String array will hold the lines of a map file
    private String[] lines;

    //This scanner will check that the map file is from Reflex, the boolean will hold whether or not it is
    private Scanner reflexValidator;
    public boolean reflexValidated = false;

    //This will take care of writing the new file
    private FileWriter mapWriter;

    //This will hold the location of the main executable
    private String exeDirectory = System.getProperty("user.dir");

    //This will hold the location of the working directory (where the files will be processed before output)
    private String workingDirectory = exeDirectory + "\\working";

    //These hold the locations of the tools used
    private String brushShifterLocation = exeDirectory + "\\tools\\ReflexBrushShifter";
    private String entityShifterLocation = exeDirectory + "\\tools\\ReflexEntityShifter";

    /**
     * This is the constructor for the map processor
     * @param mapFile The map file to be processed
     */
    public MapProcessor (String mapFile) throws FileNotFoundException
    {
        //Set the map file parameter
        this.mapFile = mapFile;

        //Initialize the map scanner
        mapScanner = new Scanner(new File(mapFile));

        //Tell the user everything is honky dory for the scanner
        System.out.println("\nLoaded your file: " + mapFile);
    }

    public void centerMap()
    {

    }
}
