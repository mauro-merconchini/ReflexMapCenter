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

    //These doubles will hold the coordinates of the map's geometric midpoint
    private double midX, midY, midZ;

    //These will hold the record values for the largest and smallest values for a vertex
    private double xMax, xMin, yMax, yMin, zMax, zMin;

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

    //This will make sure that the first time vertices are pulled, they will be the new records in min and max to compare to
    private boolean neverComparedBefore = true;

    /**
     * This is the constructor for the map processor
     * @param mapFile The map file to be processed
     */
    public MapProcessor (String mapFile) throws FileNotFoundException
    {
        validateReflexMapFile(mapFile);

        if (reflexValidated)
        {
            //Set the map file parameter
            this.mapFile = mapFile;

            //Initialize the map scanner
            mapScanner = new Scanner(new File(mapFile));

            //Tell the user everything is honky dory for the scanner
            System.out.println("\nLoaded your file: " + mapFile);

            lines = new String[lineCount()];
            System.out.println("Created array of lines with size " + lines.length + "\n");
        }
    }

    /**
     * This method will take care of calling helpers to center the map
     */
    public void centerMap()
    {
        createLinesArray();

        calculateCenter();
    }

    /**
     * This method will find the geometric center of the inputted map
     */
    private void calculateCenter()
    {
        //Iterate through all the lines
        for (int i = 0; i < lines.length; i++)
        {
            //Once you arrive at the beginning of a vertex group
            if (lines[i].contains("vertices"))
            {
                //iterate ahead
                for (int j = i + 1; j < lines.length; j++)
                {
                    if (lines[j].contains("faces"))
                    {
                        //Call the helper method with the start and end
                        compareVertices(i + 1, j);

                        //Break this loop
                        break;
                    }
                }
            }
        }

        //Calculate the three coordinates of the geometric midpoint
        midX = (xMin + xMax) / 2;
        midY = (yMin + yMax) / 2;
        midZ = (zMin + zMax) / 2;

        System.out.printf("Map's midpoint: %.6f %.6f %.6f\n\n", midX, midY, midZ);
    }

    private void compareVertices(int start, int end)
    {
        //This scanner will hold a current line and pass vertex coordinates
        Scanner vertScanner;

        //These doubles will hold the coordinates of a vertex
        double vertX, vertY, vertZ;

        for (int i = start; i < end; i++)
        {
            //Feed the line into the scanner
            vertScanner = new Scanner(lines[i]);

            //Extract 3 doubles and throw them into the container doubles
            vertX = Double.parseDouble(String.valueOf(vertScanner.nextDouble()));
            vertY = Double.parseDouble(String.valueOf(vertScanner.nextDouble()));
            vertZ = Double.parseDouble(String.valueOf(vertScanner.nextDouble()));

            //If this is the first set of coordinates pulled, set them as the records to establish a comparison point
            if (neverComparedBefore)
            {
                xMax = vertX;
                xMin = vertX;

                yMax = vertY;
                yMin = vertY;

                zMax = vertZ;
                zMin = vertZ;

                neverComparedBefore = false;
            }

            else
            {
                //Compare the X coordinate and see if it beats either record
                if (vertX > xMax)
                {
                    xMax = vertX;
                }

                if (vertX < xMin)
                {
                    xMin = vertX;
                }

                //Compare the Y coordinate and see if it beats either record
                if (vertY > yMax)
                {
                    yMax = vertY;
                }

                if (vertY < yMin)
                {
                    yMin = vertY;
                }

                //Compare the Z coordinate and see if it beats either record
                if (vertZ > zMax)
                {
                    zMax = vertZ;
                }

                if (vertZ < zMin)
                {
                    zMin = vertZ;
                }
            }
        }
    }

    private int lineCount() throws FileNotFoundException
    {
        //This integer will hold the amount of lines
        int lines = 0;

        //Create a temporary scanner to iterate through the map file
        Scanner lineCounter = new Scanner(new File(mapFile));

        //Iterate through the map file and increment the counter per each line
        while(lineCounter.hasNextLine())
        {
            lineCounter.nextLine();
            lines++;
            //System.out.println("I am at line" + lines);
        }

        //Spit out the amount of lines
        return lines;
    }

    /**
     * This method will populate the lines array with each line of the map file
     */
    private void createLinesArray()
    {
        //This will iterate through the lines array
        for (int i = 0; i < lines.length; i++)
        {
            //Stop at end-of-File
            if (mapScanner.hasNextLine())
            {
                //Add the current line to the lines array
                lines[i] = mapScanner.nextLine();
            }
        }
    }

    /**
     * This method will perform a validation check to make sure the map file is from Reflex Arena
     * @param mapToCheck The name of the map file to validate
     * @throws FileNotFoundException
     */
    private void validateReflexMapFile(String mapToCheck) throws FileNotFoundException
    {
        //Initialize the map scanner
        reflexValidator = new Scanner(new File(mapToCheck));

        if (reflexValidator.next().contains("reflex"))
        {
            reflexValidated = true;
            System.out.println("\nThis is a valid Reflex Arena map file");
        }

        else
        {
            System.out.println("\nThis is not a Reflex Arena map file");
        }
    }
}
