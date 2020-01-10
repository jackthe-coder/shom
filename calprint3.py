import re
import datetime


class Calprint:


    def __init__(self, filename):
        self.filename = filename
        self.li = []
        self.entries = []
        self.fpointer = None
        self.frequency = None
        self.until_date = None

    

    def get_events_for_day(self,dt):
        
        self.entries = []
        
        self.fpointer = open(self.filename,'r')

        for line in self.fpointer:
            if re.match("^DTSTART:.*", line):
                word = re.split("[:]{1}",line)
                start = word[1]
                dt_s = extract(start)
                event_date = extract_date(start)
                self.li.append(event_date)
                self.li.append(dt_s)
            
            elif re.match("^DTEND:.*",line):
                word = re.split("[:]{1}",line)
                end = word[1]
                dt_e = extract(end)
                self.li.append(dt_e)
            
            elif re.match("^RRULE:.*",line):
                word = re.split("[;:=]+",line)
                self.frequency = word[2]
                self.until_date = extract(word[6])
            
            elif re.match("^LOCATION:.*",line):
                word = re.split("[:]{1}",line)
                location = word[1]
                self.li.append(location)
            
            elif re.match("^SUMMARY:.*",line):
                self.word = re.split("[:]{1}",line)
                self.summary = self.word[1]
                self.li.append(self.summary)
            
            elif re.match("^END:VEVENT$", line):
                self.entries.append(self.li)
                if self.frequency:
                    repeat_entries(self)
                self.li = []
                self.frequency = None
                self.until_date = None


        self.entries.sort(key=lambda x:x[1])
        
        event_date = ""
        for item in self.entries:
            if dt == item[0]:
                cal_day = print_date(item[0])
                event_date = cal_day
                event_date += "\n"
                for i in cal_day:
                    event_date += "-"
                event_date += "\n"
                event_date += print_time(item[0],self.entries)
                return event_date

        return None;


            
def extract_date(dt):
    dt= re.split("[T]",dt)
    date = dt[0]
    yy = int(date[:4])
    mm = int(date[4:6])
    dd = int(date[6:8])
    hr = 0
    min = 0
    sec = 0
    dt_0 = datetime.datetime(yy,mm,dd,hr,min,sec)
    return (dt_0)
                    
def extract(dt1):
    """extarcting datetime info from string input"""
    yy =  int(dt1[:4])
    mo =  int(dt1[4:6])
    dd =  int(dt1[6:8])
    hr =  int(dt1[9:11])
    mi =  int(dt1[11:13])
    ss =  int(dt1[13:])
    dt_2 = datetime.datetime(yy,mo,dd,hr,mi,ss)
    return (dt_2)
        
def repeat_entries(self):
    """Appending repeat"""
    curr_date = self.li[0]
    curr_date_s =self.li[1]
    curr_date_e =self.li[2]
    li_repeat = []
    curr_date = increment_date(curr_date,self.frequency)
    curr_date_s= increment_date(curr_date_s,self.frequency)
    curr_date_e= increment_date(curr_date_e,self.frequency)
    while (self.until_date >= curr_date):
        li_repeat.append(curr_date)
        li_repeat.append(curr_date_s)
        li_repeat.append(curr_date_e)
        li_repeat.append(self.li[3])
        li_repeat.append(self.li[4])
        self.entries.append(li_repeat)
        li_repeat = []
        curr_date = increment_date(curr_date,self.frequency)
        curr_date_s= increment_date(curr_date_s,self.frequency)
        curr_date_e= increment_date(curr_date_e,self.frequency)

        
def increment_date(date,frequency):
    """Incrementing date for repeat"""
    temp = date
    if (frequency == "WEEKLY"):
        inc = datetime.timedelta(days=7)
        return temp + inc
    else:
        inc = datetime.timedelta(days=1)
        return temp + inc


def combine (hour,min,degit):
    if hour < 10:
        result = (" {}:{} {}").format(hour,min,degit)
        return result
    else:
        result = ("{}:{} {}").format(hour,min,degit)
        return result
        
def print_date(date):
    """Formatting date output """
    month = date.strftime("%B")
    day = date.strftime("%d")
    year = date.strftime("%Y")
    weekday = date.strftime("%a")
    result = "%s %s, %s (%s)" %(month,day,year,weekday)
    return result
        
def print_time(date,list):
    alltheresult = ""
    for i in list:
        if date == i[0]:
            date_start = i[1]
            date_end = i[2]
            sum = i[3]
            loc = i[4]
            summary = sum.rstrip('\n')
            location = loc.rstrip('\n')
            start_hour = int(date_start.strftime("%I"))
            start_min = date_start.strftime("%M")
            start_degit = date_start.strftime("%p").lower()
            start = combine(start_hour,start_min,start_degit)
            end_hour = int(date_end.strftime("%I"))
            end_min = date_end.strftime("%M")
            end_degit = date_end.strftime("%p").lower()
            end = combine(end_hour,end_min,end_degit)
            result = ("{} to {}: {} [{}]").format(start,end,location,summary)
            alltheresult += result
            alltheresult += "\n"
    alltheresult = alltheresult.rstrip('\n')
    return alltheresult
        
        
        
        
        


    





                            
