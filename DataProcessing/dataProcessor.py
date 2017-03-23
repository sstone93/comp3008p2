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
    
def writeLoginsToCSV(data):
    with open("logins.csv", "wb") as csvfile:
        fieldnames = ["user", "site", "scheme", "event", "timeDelta"]
        writer = csv.DictWriter(csvfile, fieldnames)
        
        writer.writeheader()
        for row in data:
            writer.writerow(row)
            
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
    startEvents = {}
    data = []
    
    starts = 0
    logins = 0
    falseLogins = 0
    
    for row in rawData:
        if row["mode"] == "enter" and row["event"] == "start":
            startEvents[row['user']] = row
            starts = starts + 1
            
        if row["mode" ] == "login":
            if not row['user'] in startEvents.keys():
                falseLogins = falseLogins + 1
                continue
                
            startEvent = startEvents[row['user']]
            
            
            startTime = datetime.strptime(startEvent["time"],"%Y-%m-%d %H:%M:%S")
            endTime = datetime.strptime(row["time"],"%Y-%m-%d %H:%M:%S")
            delta =(endTime - startTime).total_seconds()
            
            logEntry = {"user": row["user"], "site": row["site"], "scheme": row["scheme"], "event": row["event"], "timeDelta": delta}
            
            data.append(logEntry)
            
            logins = logins + 1
            del startEvents[row['user']]
            
    print "Starts %d" %starts
    print "Complete logins %d" %logins
    print "Logins without a start %d" %falseLogins
    return data
    
def organizeByUser(loginsData):
    users = {}
    for row in loginsData:
        print row['user']
        if row['user'] in users.keys():
            users[row['user']].append(row)
        else:
            users[row['user']] = [row]
    
    for user in users.keys():
        print user
        print len(users[user])
        
    print len(users)
    
            
def main():
    rawData = readRawDataCSV()
    
    loginData = findLogins(rawData)
    
    writeLoginsToCSV(loginData)
    
    loginData = readLoginsCSV()
    
    organizeByUser(loginData)
    
if __name__ == "__main__":
    main()