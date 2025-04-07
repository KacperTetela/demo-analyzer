package demoanalyzer.com.domain.parserhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParserHandler {


    public boolean parse() {
        try {
            // Full path to Python in the .venv virtual environment
            String pythonPath = "C:\\Users\\kacpe\\IdeaProjects\\DemoAnalyzer\\src\\main\\resources\\awpy\\.venv\\Scripts\\python.exe";
            // Ścieżka do skryptu Pythona
            String pythonScriptPath = "C:\\Users\\kacpe\\IdeaProjects\\DemoAnalyzer\\src\\main\\resources\\awpy\\Info.py";

            // We create a process to run Python
            ProcessBuilder pb = new ProcessBuilder(pythonPath, pythonScriptPath);
            pb.redirectErrorStream(true);  // Łączenie strumieni wyjścia i błędów
            Process process = pb.start();

            // We read the result from the process output (stdout)
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // We are waiting for the process to finish
            int exitCode = process.waitFor();
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


}
