from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
import time
import hashlib
import json
import requests

chrome_options = Options()
chrome_options.add_argument("--headless")

url = [
    # JobKorea
    'https://www.jobkorea.co.kr/Top100'
    '/?Main_Career_Type=1&Search_Type=1&BizJobtype_Bctgr_Code=10031&BizJobtype_Bctgr_Name=개발·데이터&BizJobtype_Code=0'
    '&BizJobtype_Name=개발·데이터+전체&Major_Big_Code=0'
    '&Major_Big_Name=전체&Major_Code=0&Major_Name=전체'
    '&Edu_Level_Code=9&Edu_Level_Name=전체&Edu_Level_Name=전체&MidScroll=&duty-depth1=on',
    # Kakao
    'https://careers.kakao.com/jobs',
    # Line
    'https://careers.linecorp.com/jobs?ca=All',
    # Naver
    'https://recruit.navercorp.com/rcrt/list.do'
]


class Recruitment:
    def __init__(self, id, url, provider, title, department, career
                 , company_name, company_address, start_date, end_date, signed_hash):
        self.id = id
        self.url = url
        self.provider = provider
        self.title = title
        self.department = department
        self.career = career
        self.companyName = company_name
        self.companyAddress = company_address
        self.startDate = start_date
        self.endDate = end_date
        self.signedHash = signed_hash

    def to_dict(self):
        return {
            'id': self.id,
            'url': self.url,
            'provider': self.provider,
            'title': self.title,
            'department': self.department,
            'career': self.career,
            'companyName': self.companyName,
            'companyAddress': self.companyAddress,
            'startDate': self.startDate,
            'endDate': self.endDate,
            'signedHash': self.signedHash
        }


def load_page_and_get_hash(specific_url, title, chrome_options):
    try:
        # 상세 페이지용 드라이버
        with webdriver.Chrome(options=chrome_options) as inner_driver:
            inner_driver.get(specific_url)
            time.sleep(0)
            # 페이지 전체 HTML을 가져옴
            page_content = inner_driver.page_source
            # 페이지 전체 HTML과 title을  MD5 해시로 변환
            page_hash = hashlib.md5((title + page_content).encode('utf-8')).hexdigest()
        return page_hash
    except Exception as e:
        print('secpific page url : ', specific_url)
        print('title : ', title)
        print(e)
        return None


def crawl_jobkorea_website(url):
    driver = webdriver.Chrome(options=chrome_options)
    driver.get(url[0])
    time.sleep(5)

    top_listings_section = driver.find_element(By.XPATH, "//ol[@class='rankList']")
    recruitments = []
    job_postings = top_listings_section.find_elements(By.XPATH, ".//li")

    for job in job_postings:
        id = None
        specific_page_url = job.find_element(By.XPATH, './/div[@class="info"]/div[@class="tit"]/a').get_attribute(
            'href')
        title = job.find_element(By.XPATH, './/div[@class="info"]/div[@class="tit"]/a/span').text
        career = job.find_element(By.XPATH, './/div[@class="sDsc"]/span[1]').text
        company_name = job.find_element(By.XPATH, './/div[@class="coTit"]/a[@class="coLink"]/b').text
        company_address = job.find_element(By.XPATH, './/div[@class="sDsc"]/span[3]').text
        signed_hash = load_page_and_get_hash(specific_page_url, title, chrome_options)
        role = [elem.text for elem in job.find_elements(By.XPATH, './/div[@class="sTit"]/span')]

        recruitment = Recruitment(
            id=id,
            url=specific_page_url,
            provider='JobKorea',
            title=title,
            department='개발/데이터',
            career=career,
            company_name=company_name,
            company_address=company_address,
            start_date='N/A',
            end_date='N/A',
            signed_hash=signed_hash

        )
        recruitments.append(recruitment.to_dict())

    driver.quit()

    headers = {'Content-Type': 'application/json'}
    response = requests.post('http://localhost:8080/api/v1/processData'
                             , data=json.dumps(recruitments), headers=headers)
    return response.status_code


def crawl_kakao_website(url):
    driver = webdriver.Chrome(options=chrome_options)
    driver.get(url[1])
    time.sleep(5)

    container = driver.find_element(By.XPATH, '//*[@id="mArticle"]/div/ul[2]')
    job_postings = container.find_elements(By.TAG_NAME, 'a')
    recruitments = []
    for job in job_postings:
        # temp_department = job.find_element(By.XPATH,
        #                                    ".//div[contains(@class, 'list_tag')]"
        #                                    "//span[contains(text(), 'System')]").text
        specific_page_url = job.get_attribute('href')
        # department = temp_department.strip("#")
        title = job.find_element(By.XPATH, './/h4').text
        career = title.split('(')[-1].split(')')[0] if '(' in title and ')' in title else 'N/A'
        end_date = job.find_element(By.XPATH, '//dd[contains(text(), "영입종료시")]').text
        signed_hash = load_page_and_get_hash(specific_page_url, title, chrome_options)

        recruitment = Recruitment(
            id=None,
            url=specific_page_url,
            provider='Kakao',
            title=title,
            department='N/A',
            career=career,
            company_name='Kakao',
            company_address='판교',
            start_date='N/A',
            end_date=end_date,
            signed_hash=signed_hash
        )
        print(recruitment)
        recruitments.append(recruitment.to_dict())

    driver.quit()
    headers = {'Content-Type': 'application/json'}
    response = requests.post('http://localhost:8080/api/v1/processData',
                             data=json.dumps(recruitments), headers=headers)
    return response.status_code


def crawl_line_website(url):
    driver = webdriver.Chrome(options=chrome_options)
    driver.get(url[2])
    time.sleep(5)

    job_postings = driver.find_elements(By.XPATH, '//ul[@class="job_list"]/li')
    recruitments = []

    for job in job_postings:
        id = None
        specific_page_url = job.find_element(By.XPATH, './/a').get_attribute('href')
        title = job.find_element(By.XPATH, './/a/h3[@class="title"]').text
        spans = job.find_elements(By.XPATH, './/div[@class="text_filter"]/span')
        department = 'N/A'
        # 각 span 태그를 순회하며 특정 키워드가 포함된 태그를 찾음 (department)
        for span in spans:
            text = span.text.strip()
            if any(keyword in text for keyword in
                   ["Business", "Marketing", "Planning", "Engineering", "Corporate", "Design"]):
                department = text
                break
        company_address = job.find_element(By.XPATH, './/a/div[@class="text_filter"]/span[1]').text
        date_range = job.find_element(By.XPATH, './/a/span[@class="date"]').text
        start_date, end_date = date_range.split(' ~ ')
        end_date = end_date.replace('Until filled', 'closed')
        signed_hash = load_page_and_get_hash(specific_page_url, title, chrome_options)

        recruitment = Recruitment(
            id=id,
            url=specific_page_url,
            title=title,
            provider='Line',
            department=department,
            career='N/A',
            company_name='Line',
            company_address=company_address,
            start_date=start_date,
            end_date=end_date,
            signed_hash=signed_hash
        )
        recruitments.append(recruitment.to_dict())

    driver.quit()

    headers = {'Content-Type': 'application/json'}
    response = requests.post('http://localhost:8080/api/v1/processData',
                             data=json.dumps(recruitments), headers=headers)

    return response.status_code


def crawl_naver_website(url):
    driver = webdriver.Chrome(options=chrome_options)
    driver.get(url[3])
    time.sleep(5)
    recruitments = []
    job_postings = driver.find_elements(By.XPATH, '//ul[@class="card_list"]/li[@class="card_item "]')

    for job in job_postings:
        temp_driver = webdriver.Chrome(options=chrome_options)
        job_link = job.find_element(By.XPATH, './/a[@class="card_link"]')
        job_link_url = job_link.get_attribute('href')
        temp_driver.get(job_link_url)
        job_link = temp_driver.find_element(By.XPATH, './/a[@class="card_link"]')  # Find the link again in the new page
        temp_driver.execute_script("arguments[0].click();", job_link)  # Click the link
        specific_page_url = temp_driver.current_url  # Get the current URL

        id = None
        title = temp_driver.find_element(By.XPATH, './/h4[@class="card_title"]').text
        department = temp_driver.find_element(By.XPATH, './/dl[@class="card_info"]/dd[1]').text
        field = temp_driver.find_element(By.XPATH, './/dl[@class="card_info"]/dd[2]').text
        career = temp_driver.find_element(By.XPATH, './/dl[@class="card_info"]/dd[3]').text
        conditions = temp_driver.find_element(By.XPATH, './/dl[@class="card_info"]/dd[4]').text
        date_range = temp_driver.find_element(By.XPATH, './/dl[@class="card_info"]/dd[6]').text
        start_date, end_date = date_range.split(' ~ ')
        position = f"{career}/{conditions}"
        signed_hash = load_page_and_get_hash(specific_page_url, title, chrome_options)

        recruitment = Recruitment(
            id=id,
            url=specific_page_url,
            title=title,
            provider='Naver',
            department=department,
            career=position,
            company_name='Naver',
            company_address='판교',
            start_date=start_date,
            end_date=end_date,
            signed_hash=signed_hash
        )
        recruitments.append(recruitment.to_dict())
        temp_driver.quit()  # Close the temporary driver after getting the details of each job

    driver.quit()

    headers = {'Content-Type': 'application/json'}
    response = requests.post('http://localhost:8080/api/v1/processData',
                             data=json.dumps(recruitments), headers=headers)

    return response.status_code


def crawl_rocketpunch_website(url):
    driver = webdriver.Chrome(options=chrome_options)
    page_number = 1
    recruitments = []

    while True:
        driver.get(f"https://www.rocketpunch.com/jobs?page={page_number}")
        time.sleep(5)
        job_postings = driver.find_elements(By.XPATH, '//div[@class="company item"]')

        if len(job_postings) == 0 or page_number > 10:
            break

        for job in job_postings:
            id = None
            company_name = job.find_element(By.XPATH, './/a[@class="company-name nowrap header name"]').text
            job_titles_elements = job.find_elements(By.XPATH, './/a[@class="nowrap job-title"]')
            for title_element in job_titles_elements:
                job_title = title_element.text
                if job_title:
                    specific_page_url = job.find_element(By.XPATH, './/div[@class="job-name"]/a').get_attribute('href')
                    signed_hash = load_page_and_get_hash(specific_page_url, job_title, chrome_options)
                    job_status_info = job.find_element(By.XPATH, './/div[@class="job-stat-info"]').text
                    end_date = job.find_element(By.XPATH, './/div[@class="job-dates"]').text
                    department = '개발/데이터'  # Update this as per your requirement

                    recruitment = Recruitment(
                        id=id,  # Update this as per your requirement
                        url=specific_page_url,
                        title=job_title,
                        provider='RocketPunch',
                        department=department,
                        career=job_status_info,
                        company_name=company_name,
                        company_address='N/A',  # Update this as per your requirement
                        start_date="N/A",
                        end_date=end_date,
                        signed_hash=signed_hash
                    )
                    recruitments.append(recruitment.to_dict())
        page_number += 1

    driver.quit()
    headers = {'Content-Type': 'application/json'}
    response = requests.post('http://localhost:8080/api/v1/processData',
                             data=json.dumps(recruitments), headers=headers)

    return response.status_code


def main():
    try:
        #  jobkorea_response = crawl_jobkorea_website(url)
        kakao_resposne = crawl_kakao_website(url)
        print(kakao_resposne)
        # line_response = crawl_line_website(url)
        # print(line_response)
        # naver_response = crawl_naver_website(url)
        # rocketpunch_response = crawl_rocketpunch_website(url)
    finally:
        # print(jobkorea_response)
        #  print(line_response)
        # print(rocketpunch_response)
        print('Finished')


if __name__ == "__main__":
    main()
