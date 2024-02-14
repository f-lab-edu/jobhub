package com.jobhub.scheduler;

import com.jobhub.external.PyhonRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RecruitmentCrawlingScheduler {

    private final PyhonRunner pyhonRunner;

    @Scheduled(cron = "0 0 12 * * ?")
    public void crawlingRecruitments() {
        pyhonRunner.runPythonScript();
    }
}
