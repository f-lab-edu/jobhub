from selenium import webdriver
from selenium.webdriver.common.by import By

chrome_options = webdriver.ChromeOptions()
chrome_options.add_argument("--headless")

driver = webdriver.Chrome(options=chrome_options)

page_number = 1
recruitments = []

while True:
    driver.get(f"https://www.rocketpunch.com/jobs?page={page_number}")
    job_listings = driver.find_elements(By.XPATH, '//div[@class="company item"]')

    if len(job_listings) == 0 or page_number > 10:
        break

    for job in job_listings:
        company_name = job.find_element(By.XPATH, './/a[@class="company-name nowrap header name"]').text
        job_titles_elements = job.find_elements(By.XPATH, './/a[@class="nowrap job-title"]')
        for title_element in job_titles_elements:
            job_title = title_element.text
            if job_title:
                job_description = job.find_element(By.XPATH, './/div[@class="description"]').text
                job_status_info = job.find_element(By.XPATH, './/div[@class="job-stat-info"]').text
                job_dates = job.find_element(By.XPATH, './/div[@class="job-dates"]').text

                job_listing = {
                    'Job Status Info': job_status_info,
                    'Job Dates': job_dates,
                    'Company Name': company_name,
                    'Job Title': job_title,
                    'Job Description': job_description
                }

                recruitments.append(job_listing)

    page_number += 1

driver.quit()

# Print the list of job listings
for job in recruitments:
    print(job)
