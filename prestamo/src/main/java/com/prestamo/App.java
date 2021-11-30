package com.prestamo;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import com.prestamo.utils.Architecture;
import com.prestamo.utils.ReadFileMultiTask;
import com.prestamo.utils.WriteFileTask;
import com.prestamo.utils.CrearArchivo;
import com.prestamo.utils.Filtros;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
  
  public static void main(String[] args) throws IOException, InterruptedException, ExecutionException{
    int i,j;
    BufferedReader archivoNormal = null;
    ArrayList<ArrayList<String>> estructuraFiltrada = new ArrayList<>();
    String columnas = "";
    String filtrado = "";
    String[] campos = null;
    String linea;
    
    try {
      File directorio = new File("./src/main/java/com/prestamo/db/input_dividido");
      if (!directorio.exists()) {
          if (directorio.mkdirs()) {
              System.out.println("Directorio creado");
              Architecture arquitectura = new Architecture();
              int num_cores = arquitectura.getCores();
              CrearArchivo archivos = new CrearArchivo(num_cores);
              archivos.crearArchivo();
          } else {
              System.out.println("Error al crear directorio");
              }
      }

      ReadFileMultiTask reader = new ReadFileMultiTask();
      reader.execute();
      WriteFileTask prueba = new WriteFileTask();
      prueba.CrearDirectorio();

      try{
        File archivoMenor = new File("./src/main/java/com/prestamo/db/211129COVID19MEXICO.csv");
        archivoNormal = new BufferedReader (new FileReader(archivoMenor));
        linea = archivoNormal.readLine();
        campos = linea.split(",");
      }catch (FileNotFoundException e){
        System.out.println("Error: Archivo no encontrado");
        System.out.println(e.getMessage());
    }catch(Exception e) {
        System.out.println("Error de lectura del archivo");
        System.out.println(e.getMessage());
    }finally {
        try {
            if(archivoNormal != null)
                archivoNormal.close();
        }
        catch (Exception e) {
            System.out.println("Error al cerrar el archivo");
            System.out.println(e.getMessage());
        }
    }
    for(i=0;i<campos.length;i++){
        System.out.println("\t"+Integer.toString(i+1)+" --- "+campos[i]);
    }
    
    System.out.println ("Ingresa las columnas de interes: ");
    Scanner columnaEscaner = new Scanner (System.in);
    columnas = columnaEscaner.nextLine ();
    System.out.println ("Ingresa el criterio de filtrado: ");
    Scanner filtroEscaner = new Scanner (System.in);
    filtrado = filtroEscaner.nextLine ();
    
    estructuraFiltrada = Filtros.filtarArchivo("./src/main/java/com/prestamo/db/211129COVID19MEXICO.csv", filtrado,columnas, campos);

    prueba.CrearArchivo(estructuraFiltrada); 
    columnaEscaner.close();
    filtroEscaner.close();

    } catch (Exception e) {
        System.out.println(e);
    }
  }
}