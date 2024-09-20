package CIOS_Class;

import java.util.ArrayList;
import java.io.*;


public class Employee {
    
    Database database = new Database("Employee.txt");
    
    private String employeeId;
    private String employeeName;
    private String employeeDepartment;
    private String designations;
    private int epfNumber;

    public Employee() {
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public String getDesignations() {
        return designations;
    }

    public void setDesignations(String designations) {
        this.designations = designations;
    }

    public int getEpfNumber() {
        return epfNumber;
    }

    public void setEpfNumber(int epfNumber) {
        this.epfNumber = epfNumber;
    }
  
    
    // Generate Auto category ID based on previous ID
    
    public String autoID() {
        String IDS = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Employee.txt"));
            String line;
            String EID = null;

            while ((line = reader.readLine()) != null) {
                String[] employeeInfo = line.split(",");
                EID = employeeInfo[0];
            }

            if (EID != null) {
                String x = EID.substring(1);
                int ID = Integer.parseInt(x);

                if (ID > 0 && ID < 9) {
                    ID = ID + 1;
                    IDS = "E00" + ID;
                } else if (ID >= 9 && ID < 99) {
                    ID = ID + 1;
                    IDS = "E0" + ID;
                } else if (ID >= 99) {
                    ID = ID + 1;
                    IDS = "E" + ID;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error Auto ID: " + e.getMessage());
        }
        return IDS;
    }
    
    // Add New Employee
    
    public int addNewEmployee() {
        int noRows = 0;
        String employeeData = getEmployeeId()+ "," + getEmployeeName()+ "," + getEmployeeDepartment()+ "," + getDesignations()+ ","
                + getEpfNumber()+ "\n";

        try (FileWriter fileWriter = new FileWriter("Employee.txt", true)) {
            fileWriter.write(employeeData);
            noRows = 1;
        } catch (IOException ex) {
            System.out.println("Error inserting employee data " + ex);
        }
        return noRows;
    }
    
    
     public String viewAllEmployee() 
    {
       
        String employees = "";
        String employeeDetails = "";
        String[] words = null;
    
        try ( BufferedReader emp = new BufferedReader(new FileReader("Employee.txt"))) {
            while ((employees = emp.readLine()) != null) {
                words = employees.split(",");
                employeeDetails += words[0]+"\t"+words[1]+"\t"+ words[2]+"\t"+words[3]+"\t"+words[4]+"\n";
            }
        } 
        catch (IOException ex) {
            System.out.println("Error viewing employee data " + ex);
        }
        
        return employeeDetails;
    }
     
    public ArrayList<Employee> searchEmployeeDetails(String keyword) {
    ArrayList<Employee> searchResults = new ArrayList<>();
    try {
        String[] words;
        BufferedReader emp = new BufferedReader(new FileReader("Employee.txt"));
        String employee;
        
        while ((employee = emp.readLine()) != null) {
            words = employee.split(",");
            String id = words[0];
            String name = words[1];
            String department =  words[2];
            String designations = words [3];
            int epfNumber = Integer.parseInt(words[4]);
            
            // Check if any of the fields match the search keyword
            if (id.equalsIgnoreCase(keyword) ||
                name.toLowerCase().contains(keyword.toLowerCase()) ||
                department.toLowerCase().contains(keyword.toLowerCase()) ||
                designations.toLowerCase().contains(keyword.toLowerCase())||
                String.valueOf(epfNumber).equals(keyword)) {
                
                Employee searchResult = new Employee();
                searchResult.setEmployeeId(id);
                searchResult.setEmployeeName(name);
                searchResult.setEmployeeDepartment(department);
                searchResult.setDesignations(designations);
                searchResult.setEpfNumber(epfNumber);
                searchResults.add(searchResult);
            }
        }
        
        emp.close();
    } catch (Exception e) {
        // Handle any exceptions that occur during file processing
        e.printStackTrace();
    }
    return searchResults;
}
     

    public String[] searchEmployee() {
        String[] employeeData = null;
        try {
            String employeeId = getEmployeeId();
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("Employee.txt"));
            while ((line = reader.readLine()) != null) {
                employeeData = line.split(",");
                if (employeeData[0].equals(employeeId)) {
                    // Found matching book
                    break;
                }
            }
            reader.close();
        } catch (Exception ex) {
            System.out.print("Error searching employee: " + ex);
        }
        return employeeData;
    }

     public boolean updateEmployeeToFile() {
    try {
        String employeeId = getEmployeeId();
        String line;
        File inputFile = new File("Employee.txt");
        File tempFile = new File("temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        while ((line = reader.readLine()) != null) {
            String[] employeeData = line.split(",");
            if (employeeData[0].equals(employeeId)) {
                // Update book details in the line
                String updatedLine = employeeId + "," + getEmployeeName()+ "," + getEmployeeDepartment()+ "," +
                        getDesignations() + "," + getEpfNumber();
                writer.write(updatedLine);
            } else {
                writer.write(line);
            }
            writer.newLine();
        }
            writer.close();
            reader.close();

        boolean successful = tempFile.renameTo(inputFile);
        return successful;
        
        } catch (Exception ex) {
            System.out.print("Error updating employee: " + ex);
            return false;
        }
    }
     
     public int deleteEmployee() {
        int noRows = 0;
        String employeeId = getEmployeeId();
        try {
            File inputFile = new File("Employee.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] employeeData = line.split(",");
                if (employeeData[0].equals(employeeId)) {
                    // Skip deleting the line with matching bookId
                    continue;
                }
                writer.write(line);
                writer.newLine();
                noRows++;
            }
            writer.close();
            reader.close();

            boolean successful = tempFile.renameTo(inputFile);
            if (!successful) {
                // Unable to rename temporary file, reset noRows to 0
                noRows = 0;
            }
        } catch (Exception ex) {
            System.out.print("Error deleting employee: " + ex);
            noRows = 0;
        }
        return noRows;
    }
}
