package CIOS_Class;

// Import necessary classes for file handling operations in the code
import java.io.*;


public class Database {
    private static final String FILE_PATH = "C:\\Colombo-intitute-of-studies";
    private File file;
    private String fileName;

    public Database(String fileName) {
        this.fileName = fileName;
        createANewFile();
    }

    public boolean createANewFile() {
        try {
            file = new File(FILE_PATH + fileName);
            if (file.createNewFile()) {
                System.out.println("File Created: " + file.getName());
                return true;
            } else {
                System.out.println("File Already Exists!");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with creating the file: " + e);
            return false;
        }
    }

    public boolean writeDataToFile(String record) {
        try {
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(record);
            bufferedWriter.newLine();
            bufferedWriter.close();
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Something went wrong with writing to the file: " + e);
            return false;
        }
    }

    public BufferedReader readFile() {
        if (!createANewFile()) {
            try {
                FileReader reader = new FileReader(file);
                return new BufferedReader(reader);
            } catch (IOException e) {
                System.out.println("Something went wrong with reading the file: " + e);
            }
        }
        return null;
    }
}