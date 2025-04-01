package demoanalyzer.com.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HeaderParser {

    public void parse() {
        {
            try {
                // Pełna ścieżka do Pythona w wirtualnym środowisku .venv
                String pythonPath = "C:\\Users\\kacpe\\IdeaProjects\\DemoAnalyzer\\src\\main\\resources\\AwpyDemoParser\\.venv\\Scripts\\python.exe";
                // Ścieżka do skryptu Pythona
                String pythonScriptPath = "C:\\Users\\kacpe\\IdeaProjects\\DemoAnalyzer\\src\\main\\resources\\AwpyDemoParser\\Info.py";

                // Tworzymy proces do uruchomienia Pythona
                ProcessBuilder pb = new ProcessBuilder(pythonPath, pythonScriptPath);
                pb.redirectErrorStream(true);  // Łączenie strumieni wyjścia i błędów
                Process process = pb.start();

                // Odczytujemy wynik z wyjścia procesu (stdout)
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // Czekamy na zakończenie procesu
                int exitCode = process.waitFor();
                System.out.println("Python script exited with code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
