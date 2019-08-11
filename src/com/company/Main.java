package com.company;

import java.io.File;
import java.util.logging.Logger;

public class Main {
    //private static Logger log = Logger.getLogger(Main.class.getName());
    private static final String INPUTFOLDERPATH; //Путь к исходным файлам
    private static final String OUTPUTFOLDERPATH; //Путь к преобразованным файлам
    private static final String URL; //Адрес ресурса, на который отправляется POST-запрос

    static {
        INPUTFOLDERPATH = "srcFolder";
        OUTPUTFOLDERPATH = "dstFolder";
        URL = "http://google.com";
    }

    public static void main(String[] args) throws MyException{
        File[] folderEntries = new File(INPUTFOLDERPATH).listFiles();

        for (File entry : folderEntries) {
            if (!entry.isDirectory()) {
                MyFile xmlFile = new MyFile();
                xmlFile.open(entry.getAbsolutePath());

                MyRequest request = new MyRequest(URL);
                xmlFile = xmlFile.toDst();
                if(xmlFile != null) {
                    xmlFile.save(OUTPUTFOLDERPATH + "/converted_" + entry.getName());
                    request.send(xmlFile.toString());
                }
                else {
                    throw new MyException("метод toDst() вернул null");
                }
            }
        }
    }
}
