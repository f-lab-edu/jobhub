package com.jobhub.external;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PyhonRunnerTest {

    @Autowired
    private PyhonRunner pyhonRunner;

    @Test
    void runPythonScript() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", resolvePythonScriptPath("hello.py"));
        processBuilder.start();
    }

    @Test
    void runMyPythonRunner() throws IOException {
        pyhonRunner.runPythonScript();
    }

    private String resolvePythonScriptPath(String filename) {
        File file = new File("src/test/resources/" + filename);
        return file.getAbsolutePath();
    }
}
