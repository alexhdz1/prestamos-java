package com.prestamo.utils;


import com.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import  java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;


public class WriteFileTask {
    
    public void CrearDirectorio(){
        File directorio = new File("./src/main/java/com/prestamo/db/resultado/");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
                }
            }
    }

    public String CrearNombre(){
        String path = "./src/main/java/com/prestamo/db/resultado/";
        LocalDateTime hoy = LocalDateTime.now();
        int ano = hoy.getYear();
        int mes = hoy.getMonthValue();
        int dia = hoy.getDayOfMonth();
        int hora = hoy.getHour();
        int minuto = hoy.getMinute();
        String nombre_archivo = path
                                +String.valueOf(ano)
                                +String.valueOf(mes)
                                +String.valueOf(dia)
                                +"_"
                                +String.valueOf(hora)
                                +String.valueOf(minuto)
                                +".csv";
        return nombre_archivo;
    }
    public void CrearArchivo(ArrayList<ArrayList<String>> estructuraFiltrada){
        String nombre_archivo = CrearNombre();          
        try{ 
            //String [] record = "2,Virat,Kohli,India,30".split(",");                 
            FileWriter mFileWriter = new FileWriter(nombre_archivo, false);
            CSVWriter writer = new CSVWriter(mFileWriter);

            for (ArrayList<String> consulta : estructuraFiltrada){
                String[] valores_filtrados = new String[consulta.size()];
                valores_filtrados = consulta.toArray(valores_filtrados);
                writer.writeNext((valores_filtrados)); 
            } 
            writer.close();
    
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
