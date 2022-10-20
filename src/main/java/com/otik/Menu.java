package com.otik;

import com.otik.coder.*;
import com.otik.decoder.Decoder;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

    public static void startMenu() throws IOException {
        Scanner scan = new Scanner(System.in);
        boolean success;
        boolean exit = false;
        System.out.println("\nАРХИВАТОР");
        while(!exit) {
            success=false;
            System.out.print("""
                    Заархивировать файл (1)
                    Разархивировать файл (2)
                    Выход (3)

                    Выберите действие:\s""");
            switch (scan.nextInt()) {
                case 1 -> {
                    success = startCoder();
                    if (success)
                        System.out.println("\nАрхивация прошла успешно\n");
                }
                case 2 -> {
                    success = startDecoder();
                    if (success)
                        System.out.println("\nРаспаковка прошла успешно\n");
                    else
                        System.out.println("\nРаспаковка не удалась\n");
                }
                default -> exit = true;
            }
        }
        System.out.println("\nЗаврешение работы программы...");
    }

    private static boolean startCoder() throws IOException {
        Scanner scan = new Scanner(System.in);
        Coder coder = new Coder();
        System.out.print("Введите имя и расположение архивируемого файла: ");
        coder.setSource(scan.nextLine());
        System.out.print("Введите имя и расположение архива: ");
        coder.setTarget(scan.nextLine());
        return coder.encode();
    }

    private static boolean startDecoder() throws IOException {
        Scanner scan = new Scanner(System.in);
        Decoder decoder = new Decoder();
        System.out.print("Введите имя и расположение архива: ");
        decoder.setSource(scan.nextLine());
        System.out.print("Укажите директорию для распаковки: ");
        decoder.setTargetDirectory(scan.nextLine());
        System.out.print("Введите имя распакованного файла (или оставьте пустым): ");
        decoder.setTargetFileName(scan.nextLine());
        return decoder.decode();
    }
}
