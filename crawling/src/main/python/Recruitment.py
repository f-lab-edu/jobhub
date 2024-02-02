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
