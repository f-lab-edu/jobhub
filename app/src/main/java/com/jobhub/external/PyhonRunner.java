package com.jobhub.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

@Slf4j
@Component
public class PyhonRunner {

    public void runPythonScript() {
        checkAndInstallRequiredPackages();
        try {
            Process pythonProcess = runPythonCrawling();
            handlePythonProcess(pythonProcess);
        } catch (IOException | InterruptedException e) {
            log.error("Error Occur When Python Crawling", e);
        }
    }

    private Process runPythonCrawling() throws IOException, InterruptedException {
        ProcessBuilder pythonBuilder = new ProcessBuilder(
                "python3","../crawling/src/main/python/crawl_job_website.py"
        );
        Process pythonProcess = pythonBuilder.start();
        log.debug("Python Crawling Process Started");
        pythonProcess.waitFor();
        log.debug("Python Crawling Process Finished");
        return pythonProcess;
    }

    private void handlePythonProcess(Process pythonProcess) throws IOException {
        StringWriter output = readPythonProcessOutput(pythonProcess);
        if (output.toString().contains("Finished")) {
            log.info("Python Crawling Process Success");
        } else {
            logPythonProcessErrors(pythonProcess);
        }
    }

    private StringWriter readPythonProcessOutput(Process pythonProcess) throws IOException {
        StringWriter output = new StringWriter();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()))) {
            log.debug("Result Buffer created");
            reader.transferTo(output);
        }
        return output;
    }

    private void logPythonProcessErrors(Process pythonProcess) throws IOException {
        StringWriter errorOutput = new StringWriter();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(pythonProcess.getErrorStream()))) {
            log.debug("Result Buffer created");
            reader.transferTo(errorOutput);
        }
        log.error("Python Crawling Process Error: " + errorOutput);
    }

    private void checkAndInstallRequiredPackages() {
        checkAndInstallPythonPackage("selenium");
        checkAndInstallPythonPackage("requests");
    }

    private void checkAndInstallPythonPackage(String packageName) {
        try {
            // Check if selenium is installed
            ProcessBuilder checkBuilder = new ProcessBuilder();
            checkBuilder.command("pip", "show", packageName);
            Process checkProcess = checkBuilder.start();
            int exitCode = checkProcess.waitFor();

            // If selenium is not installed, install it
            if (exitCode != 0) {
                ProcessBuilder installBuilder = new ProcessBuilder();
                installBuilder.command("pip", "install", packageName);
                Process installProcess = installBuilder.start();
                installProcess.waitFor();
            }
        } catch (Exception e) {
            log.error("Error occurred when checking or installing package {}", packageName, e);
        }
    }

}
