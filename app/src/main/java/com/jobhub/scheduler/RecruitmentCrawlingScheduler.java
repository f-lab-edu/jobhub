package com.jobhub.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentCrawlingScheduler {

    @Scheduled(cron = "0 0 12 * * ?")
    public void crawlingRecruitments() {

    }
}
