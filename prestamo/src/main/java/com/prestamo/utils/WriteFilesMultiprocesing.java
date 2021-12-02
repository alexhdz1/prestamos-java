package com.prestamo.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WriteFilesMultiprocesing implements Runnable{
    Path path = Paths.get("./src/main/java/com/prestamo/db/211129COVID19MEXICO.csv");
    
    private WriteFileTask writer;
    private ArrayList<ArrayList<String>> estructuraFiltrada;


    public WriteFilesMultiprocesing(WriteFileTask writer,ArrayList<ArrayList<String>> estructuraFiltrada) {
         
        this.writer = writer;
        this.estructuraFiltrada = estructuraFiltrada;
    }
    @Override
    public void run() {
        try{ 
            
            writer.CrearArchivo(estructuraFiltrada);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
