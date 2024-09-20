package CIOS_Class;

import java.io.*;
import java.util.*;

public class DepartmentCategory {
    private String departmentId, departmentName, departmentInformation;

    public DepartmentCategory() {
        
        
    }

    public DepartmentCategory(String departmentId, String departmentName, String departmentInformation) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentInformation = departmentInformation;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentInformation() {
        return departmentInformation;
    }

    public void setDepartmentInformation(String departmentInformation) {
        this.departmentInformation = departmentInformation;
    }
    
    // Generate Auto category ID based on previous ID
    
    public String autoID() {
        String IDS = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Department.txt"));
            String line;
            String CID = null;

            while ((line = reader.readLine()) != null) {
                String[] categoryInfo = line.split(",");
                CID = categoryInfo[0];
            }

            if (CID != null) {
                String x = CID.substring(1);
                int ID = Integer.parseInt(x);

                if (ID > 0 && ID < 9) {
                    ID = ID + 1;
                    IDS = "C00" + ID;
                } else if (ID >= 9 && ID < 99) {
                    ID = ID + 1;
                    IDS = "C0" + ID;
                } else if (ID >= 99) {
                    ID = ID + 1;
                    IDS = "C" + ID;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error Auto ID: " + e.getMessage());
        }
        return IDS;
    }
    
    public int insertDepartmentCategory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Department.txt", true))) {
            String line = getDepartmentId() + "," + getDepartmentName() + "," + getDepartmentInformation();
            writer.write(line);
            writer.newLine();
            return 1;
        } catch (IOException e) {
            System.out.println("Error inserting data: " + e.getMessage());
            return 0;
        }
    }
    
    public String[] getProductCategoryName() {
    List<String> categoryNameList = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("Department.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length > 0) {
                String categoryName = parts[1];
                categoryNameList.add(categoryName);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading Department.txt: " + e.getMessage());
    }

    String[] departmentName = categoryNameList.toArray(new String[0]);
    return departmentName;
}
    
}
