package com.example;

import java.io.File;

public class FileTools {
    public static int folderSize(File directory) {
    int length = 0;

    if (directory.isFile())
         length += directory.length();
    else{
        for (File file : directory.listFiles()) {
             if (file.isFile())
                 length += file.length();
             else
                 length += folderSize(file);
        }
    }

    return length;
}
}
