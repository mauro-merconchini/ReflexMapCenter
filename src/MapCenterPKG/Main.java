package MapCenterPKG;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        //argument 0: Map file name

        //********CODE BELOW CHECKS FOR INPUT VALIDITY********

        //This will hold whether the user arguments are valid, if any checks below trip this to false, no execution code will run
        boolean argumentsValid = true;

        //First of all, check that they have the right amount of inputs, which should be exactly 1
        //This value can be modified later if more arguments (such as option switches) are implemented
        if (args.length > 1 || args.length < 1)
        {
            System.out.println("You have an incorrect number of arguments. The input format is as follows:\n" +
                    "ARGUMENT 0 (String): The name of a .map file (present in this directory)\n");

            argumentsValid = false;
        }

        //Once we confirm that the user has the right amount of inputs, check them individually for validity
        else
        {
            //Create a scanner to check the individual inputs
            Scanner inputScanner;

            //This for loop will check each input
            for (int i = 0; i < args.length; i++)
            {
                //This string will hold the current argument
                String currentArg = args[i];
                //Set the scanner to scan the current argument
                inputScanner = new Scanner(currentArg);

                //If this is the first argument (argument 0), and it has any value
                if (i == 0 && inputScanner.hasNext())
                {
                    //Throw an error if the input does not have the .map file type
                    if (!inputScanner.next().contains(".map"))
                    {
                        System.out.println("ARGUMENT 0 Error: This is not a .map file");

                        argumentsValid = false;
                    }

                    //Else, it must be good
                    else
                    {
                        System.out.println("ARGUMENT 0 is good");
                    }
                }
            }
        }
        //**********CODE BELOW WILL PROCESS MAP FILE**********

        if (argumentsValid)
        {
        	Locale.setDefault(Locale.ENGLISH);
        	
            //The following declaration will hold the argument
            String mapFile = args[0];

            //Create the map processor object and pass it the file
            MapProcessor cpm5Processor = new MapProcessor(mapFile);

            //Execute further instructions only if the file was validated
            if (cpm5Processor.reflexValidated)
            {
                cpm5Processor.centerMap();
            }
        }
    }
}
