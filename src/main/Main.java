package main;

import Files.*;
import Process.*;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        long startTimeMillis = System.currentTimeMillis();

        //Reading Binary Sequence of File
        // String directory = "C:\\Users\\adham\\OneDrive\\Desktop\\Files For Project";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Enter File Name:");
        String fileName = scanner.nextLine();
        System.out.println("Please Enter File Directory:");
        String directory = scanner.nextLine();
        System.out.println("Please Enter n:");
        int n = scanner.nextInt();

//        FileClass file = new FileClass(fileName,directory,n);
//        Compress compressedFile = new Compress(file);
//        compressedFile.save();
        OptimizedVersion.compress(fileName,directory,n);

        fileName = "6287."+n+"."+fileName+".hc";

        ExtractClass extract = new ExtractClass(fileName,directory);
        Decompress decompressedFile = new Decompress(extract);
        decompressedFile.save();


        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("Elapsed Time in milli seconds: "+ (currentTimeMillis-startTimeMillis));
    }
}