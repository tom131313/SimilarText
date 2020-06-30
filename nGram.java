package app;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class nGram
{
   static {System.out.println("Starting class: " + MethodHandles.lookup().lookupClass().getCanonicalName());}
List<String> fileList = new ArrayList<String>(); // build command as a list of strings

   static final String skipList = 
         "\\ba\\b"
      + "|\\ban\\b"
      + "|\\band\\b"
      + "|\\bare\\b"
      + "|\\bas\\b"
      + "|\\bat\\b"
      + "|\\bbe\\b"
      + "|\\bbut\\b"
      + "|\\bby\\b"
      + "|\\bfor\\b"
      + "|\\bif\\b"
      + "|\\bin\\b"
      + "|\\binto\\b"
      + "|\\bis\\b"
      + "|\\bit\\b"
      + "|\\bno\\b"
      + "|\\bnot\\b"
      + "|\\bof\\b"
      + "|\\bon\\b"
      + "|\\bor\\b"
      + "|\\bsuch\\b"
      + "|\\bthat\\b"
      + "|\\bthe\\b"
      + "|\\btheir\\b"
      + "|\\bthen\\b"
      + "|\\bthere\\b"
      + "|\\bthese\\b"
      + "|\\bthey\\b"
      + "|\\bthis\\b"
      + "|\\bto\\b"
      + "|\\bwas\\b"
      + "|\\bwill\\b"
      + "|\\bwith\\b"
      ;
   static BufferedReader input;
   public PrintWriter output;
   static String inFileName;
   static String outFileName;
   static String sortFileName;
   static int countOnlyStr1;
   static int countOnlyStr2;
   static int countBothStr1Str2;
   static nGram b;

   public static void main(final String[] args) throws Exception, IOException, FileNotFoundException {
       b = new nGram();
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\RANSAC-philipperemy\\src\\RANSAC.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\FindLightStrings\\src\\RANSAC.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\RANSAC-minD3D\\src\\RANSAC.java");
        b.fileList.add("C:\\Users\\RKT\\frc\\FRC2018\\RANSAC-minD3D\\averageRANSAC-master\\src\\RANSAC.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\Intermediate Vision Java\\src\\org\\usfirst\\frc\\team4237\\robot\\RANSAC.java");
        b.fileList.add("C:\\Users\\RKT\\frc\\FRC2019\\Code\\FindLightString\\src\\org\\usfirst\\frc\\team4237\\robot\\RANSAC.java");
        b.fileList.add("C:\\Users\\RKT\\frc\\FRC2018\\RANSAC-minD3D\\averageRANSAC-master\\src\\RansacTest.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\RANSAC-minD3D\\src\\RansacTest.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\RANSAC-philipperemy\\src\\RansacTest.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\Intermediate Vision Java\\src\\org\\usfirst\\frc\\team4237\\robot\\RANSAC_Iterator.java");
        b.fileList.add("C:\\Users\\RKT\\frc\\FRC2019\\Code\\FindLightString\\src\\org\\usfirst\\frc\\team4237\\robot\\RANSAC_Iterator.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\Simple Vision\\src\\org\\usfirst\\frc\\team4237\\robot\\camera_process.java");
        b.fileList.add("C:\\Users\\RKT\\frc\\FRC2019\\Code\\FindLightString\\src\\org\\usfirst\\frc\\team4237\\robot\\camera_process.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\Intermediate Vision Java\\src\\org\\usfirst\\frc\\team4237\\robot\\camera_process.java");
        b.fileList.add("C:\\Users\\RKT\\eclipse-workspace\\FindLightStrings\\src\\Main.java");

       b.generateGram();
       b.compareFiles();
   }

   public void generateGram() throws Exception, IOException, FileNotFoundException {

         int countFiles = 0;
         String str;
         loop:
         while(true) {
            countFiles++;
            //inFileName = "in" + countFiles + ".txt";
            if ( countFiles > b.fileList.size()) break loop;
            inFileName = b.fileList.get(countFiles-1);
            System.out.println(inFileName);
            outFileName = "out" + countFiles + ".txt";
            sortFileName = "sort" + countFiles + ".txt";
            {
            try {
            input = new BufferedReader(new FileReader(inFileName));
            output = new PrintWriter(outFileName);
            } catch (final FileNotFoundException e) 
            {
               //System.err.println(e);
               break loop;
            }
            while ((str = input.readLine()) != null) {
               //System.out.println(">" + str + "<");
               // output.println(gram);// -----you had this originally, we don't need this
               str = str.toLowerCase();
               str = str.replaceAll(skipList, ""); // skip a few common words; short list could be expanded
               str = str.replaceAll("\\W", ""); // characters to ignore - all non-words (this is, keep a-z 0-9 _)
               // str = str.replaceAll("[\\[\\]\\:\\/\\?\\<\\>\\;\\(\\)\\{\\}\\,\\.\\'\\s]",
               // ""); // add stuff to ignore
               //System.out.println(">" + str + "<");
               final int line = str.length();
               final int numGram = 4;
               for (int x = 1; x < line - numGram + 2; x++) {
                  String biGram = str.substring(x - 1, x + numGram - 1);
                  output.println(biGram);
               }
            }
            input.close();
            output.close();
            sortFile();
            }
         }
        }

private static void sortFile()
{
    try
    {
        // execute sort command in a cmd (operating system) window
        List<String> command = new ArrayList<String>(); // build command as a list of strings
        command.add("cmd");
        command.add("/c");
        command.add("sort");
        command.add(outFileName);
        command.add("/O");
        command.add(sortFileName);

        //System.out.println(" Run sort command");
        ProcessBuilder pb1 = new ProcessBuilder(command);
        Process process1 = pb1.start();
        int errCode1 = process1.waitFor();
        command.clear();
        System.out.println("sort command executed, any errors? " + (errCode1 == 0 ? "No" : "Yes"));
        
        String mountOutput = output(process1.getInputStream());
        System.out.println("sort output:" + mountOutput);
        System.out.println("sort errors:" + output(process1.getErrorStream()));

    }
    catch (Exception ex2)
    {
        System.out.println("Error in sort process " + ex2);
    }
}

private static String output(InputStream inputStream) throws IOException
{
    StringBuilder sb = new StringBuilder();
    BufferedReader br = null;

    try
    {
        br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = br.readLine()) != null)
        {
            sb.append(line + System.getProperty("line.separator"));
        }
    }
    finally
    {
        br.close();
    }

    return sb.toString();
}

private void compareFiles() throws IOException {

    for (int i = 0; i < b.fileList.size() - 1; i++) {
        // get the first filename
        String inFileName1 = "sort" + (i+1) + ".txt";
        for (int j = i+1; j < b.fileList.size(); j++) {
            // compare with all the rest
            String inFileName2 = "sort" + (j+1) + ".txt";
            System.out.println(inFileName1 + " " + inFileName2);
            System.out.println (b.fileList.get(i) + " " + b.fileList.get(j));
            mergeFiles(inFileName1, inFileName2);
        }
    }
}

private void mergeFiles(String inFileName1, String inFileName2) {
    SortReader inputSorted1;
    SortReader inputSorted2;
    String str1="";
    String str2="";
    countOnlyStr1 = 0;
    countOnlyStr2 = 0;
    countBothStr1Str2 = 0;
    inputSorted1 = b.new SortReader(inFileName1);
    inputSorted2 = b.new SortReader(inFileName2);
    if (!inputSorted1.endFile()) str1 = inputSorted1.read();
    if (!inputSorted2.endFile()) str2 = inputSorted2.read();

    while (!inputSorted1.endFile() && !inputSorted2.endFile()) {
        //System.out.println(str1 + " " + str2);

        // if str1 < str2 str1 not matched and read another str1
        // if str1 == str2 they match read str1 and read str2
        // if str1 > str2 str2 not matched and read another str2
        // if str1 end file run out str2
        // if str2 end file run out str1
// The result is a negative integer if this String object lexicographically precedes the argument string.
// The result is a positive integer if this String object lexicographically follows the argument string.
// The result is zero if the strings are equal; compareTo returns 0 exactly when the equals(Object) method would return true.
        if ( str1.compareTo(str2) < 0 ) {
            countOnlyStr1++;
            str1 = inputSorted1.read();
        }
        else if ( str1.compareTo(str2) > 0 ) {
            countOnlyStr2++;
            str2 = inputSorted2.read();
        }
        else { // equal
            countBothStr1Str2++;
            str1 = inputSorted1.read();
            str2 = inputSorted2.read();
        }
    }
    // run out the file that needs it and count the n-grams
    if (inputSorted1.endFile()) {        
        while ( !inputSorted2.endFile() ) {
            countOnlyStr2++;
            inputSorted2.read();
        }
    }
    else {
        while ( !inputSorted1.endFile() ) {
            countOnlyStr1++;
            inputSorted1.read();
        }
    }
    System.out.println(countOnlyStr1 + " " + countOnlyStr2 + " " + countBothStr1Str2 + " " +
        ( (float)(2*countBothStr1Str2) / (float)(countOnlyStr1 + countOnlyStr2 + 2*countBothStr1Str2) ) );
}

class SortReader {
    private BufferedReader file;
    private boolean endFile = false;
    private String str = null;

    public SortReader(String fileName) {
        //System.out.println("opening merge file " + fileName);
        try {
            file = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            endFile = true;
            //e.printStackTrace();
        }
    }
        String read () {
        if (!endFile)
                try {
                    str = file.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        if (str == null) endFile = true;
            return str;
    }

    boolean endFile() {
        return endFile;
    }

}

}
