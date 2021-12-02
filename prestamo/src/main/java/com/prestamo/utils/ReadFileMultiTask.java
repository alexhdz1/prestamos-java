package com.prestamo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Stream;


public class ReadFileMultiTask {
    Path path = Paths.get("./src/main/java/com/prestamo/db/211129COVID19MEXICO.csv");
    Path path_folder = Paths.get("./src/main/java/com/prestamo/db/input_dividido/");
        
    public void execute() throws IOException, InterruptedException, ExecutionException {
        useForkJoinPool(path,path_folder);
        useParallelStream(path, path_folder);
    }
 
    static void useForkJoinPool(Path path,Path path_folder) throws InterruptedException, ExecutionException {
        long begin = System.currentTimeMillis();
        
        if (Files.exists(path) && Files.isDirectory(path_folder)) {
            File[] list = path.toFile().listFiles();
            ReadFileTask task = new ReadFileTask(list);
            ForkJoinPool fjp = new ForkJoinPool();
            ForkJoinTask<Integer> future = fjp.submit(task);
            System.out.println(future);
        }
 
        System.out.println("tiempo usado:" + (System.currentTimeMillis() - begin) + "mills");
    }

    static void useParallelStream(Path path, Path path_folder) throws IOException {
        
        long begin = System.currentTimeMillis();
        if (Files.exists(path) && Files.isDirectory(path_folder)) {
            int[] count = { 0, 0 };
            @SuppressWarnings("resource")
            Stream<Path> files = Files.list(path_folder);
            files.parallel().forEach(p -> {
                count[0]++;
                try (BufferedReader reader = Files.newBufferedReader(p)) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        count[1]++;
                    }
                } catch (IOException e) {
                }
            });
            System.out.println("archivos en folder:" + count[0]);
            System.out.println("lineas contadas:" + count[1]);
        }
        System.out.println("tiempo usado:" + (System.currentTimeMillis() - begin) + "mills");
    }  
}