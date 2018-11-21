package com.tracfone.twist.services;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CreateJar {

        public static void main(String[] args) {

                Process process;
                try {
                        process = new ProcessBuilder("ant","-f" ,"C:\\X_Drive\\projects\\Twist\\twist-services\\build.xml").start();
                        process.waitFor();
                        InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    while ((line = br.readLine()) != null) {
                      System.out.println(line);
                    }

                } catch (Exception e) {
                        e.printStackTrace();
                }



        }

}