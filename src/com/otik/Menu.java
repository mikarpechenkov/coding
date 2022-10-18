package com.otik;

import com.otik.analysis.AnalysisInfo;
import com.otik.readers.*;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    public static void startMenu() throws IOException {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        System.out.println("\nFILE READER");
        while(!exit) {
            System.out.print("""
                    Анализировать файл побайтово (1)
                    Анализировать файл посимвольно (utf-8) (2)
                    Анализировать файл по октетам (3)
                    Выход (0)

                    Выберите действие:\s""");
            switch (scan.nextInt()) {
                case 1 -> startReaderFileByBytes();
                case 2 -> startReaderFileByCharacter();
                case 3 -> startReaderFileByOctet();
                default -> exit = true;
            }
        }
        System.out.println("\nЗаврешение работы программы...");
    }

    private static void startReaderFileByBytes() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите имя и расположение файла: ");
        String path=scan.nextLine();
        ReaderFileByBytes reader=new ReaderFileByBytes(path);
        reader.readData();
        AnalysisInfo<Integer> info= new AnalysisInfo<>(reader.getDataOfFile());
        System.out.println(info);
    }

    private static void startReaderFileByCharacter() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите имя и расположение файла: ");
        String path=scan.nextLine();
        ReaderFileByCharacter reader=new ReaderFileByCharacter(path);
        reader.readData();
        AnalysisInfo<Character> info= new AnalysisInfo<>(reader.getDataOfFile());
        System.out.println(info);
    }

    private static void startReaderFileByOctet() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите имя и расположение файла: ");
        String path=scan.nextLine();
        ReaderFileByOсtet reader=new ReaderFileByOсtet(path);
        reader.readData();
        AnalysisInfo<String> info= new AnalysisInfo<>(reader.getDataOfFile());
        System.out.println(info);
    }
}
