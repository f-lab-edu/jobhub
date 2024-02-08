package com.jobhub.external;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;

@Component
public class PyhonRunner {
    public void runPythonScript() {

        String pythonScriptPath = "../crawling/src/main/python/crawl_job_website.py";
        String pythonVirtualEnvPath = "../crawling/venv/bin/python3";
        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        pythonVirtualEnvPath,
                        pythonScriptPath
                );
        try {
            Process process = processBuilder.start();
            System.out.println("Started");
            process.getOutputStream().close();
            System.out.println("Closed");
            StringWriter output = new StringWriter();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                System.out.println("Buffer created");
                reader.transferTo(output);
            }
            System.out.println(output);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
