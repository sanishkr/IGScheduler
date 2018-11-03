package com.sns.igscheduler.Tools;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class FileHelper {
    public  static boolean  deleteFileIfexist(String Path){
        try
        {
            Files.deleteIfExists(Paths.get(Path));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
            return false;
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
            return false;
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
            return false;
        }
        return true;
    }
}
