package com.fylbyte.app;

import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.File;


public class App {
    private static String size;
    private static String type;
    private static String fileType;
    private static int fileCount;

    public static void main(String[] args) {
        List<String> unitList = Arrays.asList("kb", "mb", "gb");
        List<String> typeList = Arrays.asList("r", "n");
        List<String> fileTypeList = Arrays.asList("s", "m");
        
        try{
            try(Scanner sc = new Scanner(System.in);){
                while (true) {
                    System.out.println("Enter the size of the file to create (e.g., 10KB, 5MB, 1GB): ");
                    size = sc.nextLine().trim();
                
                    if (size.length() > 2) {
                        String unit = size.substring(size.length() - 2).toLowerCase();
                        String numberPart = size.substring(0, size.length() - 2);
                
                        if (!unitList.contains(unit)) {
                            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid unit\u001B[0m: Supported units are KB, MB, and GB. Example: 100MB\n");
                            continue;
                        }
                
                        if (!isInteger(numberPart)) {
                            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid number format\u001B[0m: Please enter a valid numeric size before the unit. Example: 100MB\n");
                            continue;
                        }

                        break;
                
                    } else {
                        System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInput too short\u001B[0m: Please enter a file size with a number and unit. Example: 100MB\n");
                    }
                }

                while (true) {
                    System.out.println("Select the byte type - null bytes (n) or random bytes (r): ");
                    type = sc.nextLine().trim();
                
                    if (type.length() == 1) {
                        if (!typeList.contains(type.toLowerCase())) {
                            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid byte type\u001B[0m: Please enter 'n' for null bytes or 'r' for random bytes.\n");
                            continue;
                        }
                
                        break;
                    } else {
                        System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid input length\u001B[0m: Enter only one character - 'n' for null bytes or 'r' for random bytes.\n");
                    }
                }

                while (true) {
                    System.out.println("Select the file count type - single file (s) or multiple files (m): ");
                    fileType = sc.nextLine().trim();
                
                    if (fileType.length() == 1) {
                        fileType = fileType.toLowerCase();
                
                        if (!fileTypeList.contains(fileType)) {
                            System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid option\u001B[0m: Please enter 's' for a single file or 'm' for multiple files.\n");
                            continue;
                        }
                
                        if (fileType.equals("m")) {
                            while (true) {
                                System.out.println("Enter the number of files to create: ");
                                if (sc.hasNextInt()) {
                                    fileCount = sc.nextInt();
                                    if(fileCount < 2){
                                        System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid number\u001B[0m: Please enter a positive integer for the number of files.\n");
                                        sc.nextLine();
                                        continue;
                                    }
                                    sc.nextLine();
                                    break;
                                } else {
                                    System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid number\u001B[0m: Please enter a valid integer for the number of files.\n");
                                    sc.nextLine(); 
                                }
                            }
                        } else {
                            fileCount = 1;
                        }
                
                        break;
                    } else {
                        System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mInvalid input length\u001B[0m: Please enter only one character - 's' for single file or 'm' for multiple files.\n");
                    }
                }

                generateFiles();
            }
        }catch(NoSuchElementException e){
            System.out.println("\nProgram terminated. Goodbye!");
            System.exit(0);
        }
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void generateFiles(){
        long byteCount = extractBytes();
    
        long fileSize = byteCount/fileCount;
        String currentPath = System.getProperty("user.dir");
    
        for(int i = 0; i < fileCount; i++){
            try{
                if(type.equalsIgnoreCase("r")){ 
                    System.out.println("\u001B[33mFile is creating...\u001B[0m");
                    generateRandomByteFile(currentPath + File.separator + String.format("%04d", i) + ".dummy", fileSize);
                }else{
                    System.out.println("\u001B[33mFile is creating...\u001B[0m");
                    generateNullByteFile(currentPath + File.separator + String.format("%04d", i) + ".dummy", fileSize);
                }
            }catch(IOException e){
                System.err.println("[\u001B[31mERROR\u001B[0m] \u001B[33mFailed to create dummy file/s\u001B[0m: " + e.getMessage());
                return;
            }
        }
    }

    private static long extractBytes(){
        int unitSize = Integer.parseInt(size.substring(0, size.length()-2));
        String unit = size.substring(size.length()-2).toLowerCase();
        long byteSize = 0;  // Changed to long
        switch (unit) {
            case "kb":
                byteSize = (long)unitSize * 1024;
                break;
            case "mb":
                byteSize = (long)unitSize * 1024 * 1024;
                break;
            case "gb":
                byteSize = (long)unitSize * 1024 * 1024 * 1024;
                break;
            default:
                throw new IllegalArgumentException("Invalid unit: " + unit);
        }
        return byteSize; 
    }

    public static void generateRandomByteFile(String fileName, long byteCount) throws IOException {
        SecureRandom random = new SecureRandom();
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[4096];
            long remaining = byteCount;

            while (remaining > 0) {
                int toWrite = (int) Math.min(remaining, buffer.length);
                random.nextBytes(buffer);
                fos.write(buffer, 0, toWrite);
                remaining -= toWrite;
            }
        }
        System.out.println("\n[\u001B[32mSUCCESS\u001B[0m] File/s created and saved successfully to: " + fileName);
    }

    public static void generateNullByteFile(String fileName, long byteCount) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[4096];
            long remaining = byteCount;

            while (remaining > 0) {
                int toWrite = (int) Math.min(remaining, buffer.length);
                fos.write(buffer, 0, toWrite);
                remaining -= toWrite;
            }
        }
        System.out.println("\n[\u001B[32mSUCCESS\u001B[0m] File/s created and saved successfully to: " + fileName);
    }
}
