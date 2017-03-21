import csv
from datetime import datetime

def readRawDataCSV():
    data = []
    try:
        with open("Blankpt28_log.csv",'rb') as csvfile:
            reader = csv.DictReader(csvfile)            
            for row in reader:
                data.append(row)
    except:
        print "Couldn't read csv file supplied"
        return
        
    try:
        with open("Imagept28_log.csv",'rb') as csvfile:
            reader = csv.DictReader(csvfile)            
            for row in reader:
                data.append(row)
    except:
        print "Couldn't read csv file supplied"
        return
        
    try:
        with open("Text28_log.csv",'rb') as csvfile:
            reader = csv.DictReader(csvfile)            
            for row in reader:
                data.append(row)
    except:
        print "Couldn't read csv file supplied"
        return
    return data
    
def readLoginsCSV():
    data = []
    try:
        with open("logins.csv",'rb') as csvfile:
            reader = csv.DictReader(csvfile)
            for row in reader:
                data.append(row)
    except:
        print "Couldn't read csv file supplied"
        return
    return data
    
def findLogins(rawData):
    startEvent = None
    data = []
    starts = 0
    logins = 0
    '''and row["event"] == "success"'''
    for row in rawData:
        if row["mode"] == "enter" and row["event"] == "start":
            startEvent = row
            starts = starts + 1
        if row["mode" ] == "login":
            if startEvent == None:
                continue
            startTime = datetime.strptime(startEvent["time"],"%Y-%m-%d %H:%M:%S")
            endTime = datetime.strptime(row["time"],"%Y-%m-%d %H:%M:%S")
            delta =(endTime - startTime).total_seconds()
            
            logEntry = {"user": row["user"], "scheme": row["scheme"], "event": row["event"], "timeDelta": delta}
            
            data.append(logEntry)
            
            logins = logins + 1
            startEvent = None
            
    return data
    
def writeLoginsToCSV(data):
    with open("logins.csv", "wb") as csvfile:
        fieldnames = ["user", "scheme", "event", "timeDelta"]
        writer = csv.DictWriter(csvfile, fieldnames)
        
        writer.writeheader()
        for row in data:
            writer.writerow(row)
            
def main():
    rawData = readRawDataCSV()
    
    loginData = findLogins(rawData)
    
    writeLoginsToCSV(loginData)
    
    print readLoginsCSV()
    
    
if __name__ == "__main__":
    main()