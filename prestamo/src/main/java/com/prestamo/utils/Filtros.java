package com.prestamo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Filtros {
    public static ArrayList<ArrayList<String>> filtarArchivo(String rutaCompleta, String criterios,String columnas, String[] campos){
        int i,aux;
        String linea;
        Boolean todoDato = false,todoColumna = false;
        String[] camposAuxiliar, auxiliar, filtroColumnas=null;
        BufferedReader archivoNormal = null;
        ArrayList<ArrayList<String>> datosFiltrados = new ArrayList<>();
        ArrayList<String[]> filtroDatos = new ArrayList<>();
        ArrayList<String> listaCampos = new ArrayList<>();
        Collections.addAll(listaCampos, campos);
        if(!(criterios.equals("*"))){
            camposAuxiliar = criterios.split (",");
            for(i=0;i<camposAuxiliar.length;i++){
                auxiliar = camposAuxiliar[i].split(":");
                filtroDatos.add(auxiliar);
            }
            for(i=0;i<filtroDatos.size();i++){
                aux = listaCampos.indexOf(filtroDatos.get(i)[0]);
                if(aux == -1){
                    System.out.println("No se encontro el campo: "+filtroDatos.get(i)[0]);
                }else{
                    filtroDatos.get(i)[0] = Integer.toString(aux);
                }
            }
        }else{
            if((criterios.equals("*"))){
                todoDato = true;
            }
        }
        if(!(columnas.equals("*"))){
            filtroColumnas = columnas.split (",");
        }else{
            if((criterios.equals("*"))){
                todoColumna = true;
            }
        }
        try{
            File archivoMenor = new File(rutaCompleta);
            archivoNormal = new BufferedReader (new FileReader(archivoMenor));
            while((linea = archivoNormal.readLine()) != null){
                listaCampos = new ArrayList<>();
                camposAuxiliar = linea.split(",");
                if(todoDato){
                    if(todoColumna){
                        Collections.addAll(listaCampos, camposAuxiliar);
                        datosFiltrados.add(listaCampos);
                    }else{
                        datosFiltrados.add(filtrarColumnas(camposAuxiliar,filtroColumnas));
                    }
                }else{
                    if(filtrarDatos(camposAuxiliar,filtroDatos)!= null){
                        if(todoColumna){
                            Collections.addAll(listaCampos, camposAuxiliar);
                            datosFiltrados.add(listaCampos);
                        }else{
                            datosFiltrados.add(filtrarColumnas(camposAuxiliar,filtroColumnas));
                        }
                    }
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Error: Archivo no encontrado");
            System.out.println(e.getMessage());
        }catch(IOException e) {
            System.out.println("Error de lectura del archivo");
            System.out.println(e.getMessage());
        
        }catch(NumberFormatException e){
            System.out.println("Por favor ingrese la busqueda en formato: campo1:valor1,campo2:valor2 para criterio de filtrado o 1,2,3 para columnas de interes");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Los indices que ingreso en \"columnas de interes\" estan fuera del rango de busqueda");
        }finally {
            try {
                if(archivoNormal != null)
                    archivoNormal.close();
            }
            catch (IOException e) {
                System.out.println("Error al cerrar el archivo");
                System.out.println(e.getMessage());
            }
        }
    return datosFiltrados;
    }
    
    public static String[] filtrarDatos(String[] renglon, ArrayList<String[]> filtroDatos){
        int i = 0;
        boolean flag = true;
        while(flag && (i<filtroDatos.size())){
            if(!(renglon[Integer.parseInt(filtroDatos.get(i)[0])].equals(filtroDatos.get(i)[1]))){
                flag = false;
            }
            i++;
        }
        if(!flag){
            renglon = null;
        }
    return renglon;
    }
    
    public static ArrayList<String> filtrarColumnas(String[] renglon, String[] filtroColumnas){
        int i;
        ArrayList<String> renglonFiltrado = new ArrayList<>();
        for(i = 0;i<filtroColumnas.length;i++){
            renglonFiltrado.add(renglon[Integer.parseInt(filtroColumnas[i])-1]);
        }
        
        return renglonFiltrado;
    }
}
