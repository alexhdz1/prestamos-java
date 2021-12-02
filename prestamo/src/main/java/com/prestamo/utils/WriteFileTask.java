package com.prestamo.utils;


import com.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import  java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
            FileWriter mFileWriter = new FileWriter(nombre_archivo, true);
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
    

    public void CrearArchivoConcurrente(int num_cores,ArrayList<ArrayList<ArrayList<String>>> estructuraFiltrada){
        
        ExecutorService pool = Executors.newFixedThreadPool(num_cores);
        for(int i = 0;i<num_cores;i++){ 
            pool.submit(new WriteFilesMultiprocesing(new WriteFileTask(),estructuraFiltrada.get(i)));
        }
        pool.shutdown();
    }
}
