import csv
from datetime import datetime

def readRawDataCSV():
    data = []

    try:
        with open("Text28_log.csv",'rb') as csvfile:
            reader = csv.DictReader(csvfile)            
            for row in reader:
                data.append(row)
    except:
        print "Couldn't read csv file supplied"
        return
    
    try:
        with open("ourData.csv") as csvfile:
            reader = csv.DictReader(csvfile)
            for row in reader:
                data.append(row)
                data[len(data) - 1]["scheme"] = "ThreeChunk"
    except Exception as e:
        print e
        print "Couldn't read csv file supplied"
        return
    return data
    
def writeLoginsToCSV(data):
    with open("logins.csv", "wb") as csvfile:
        fieldnames = ["user", "scheme", "event", "timeDelta"]
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
    
def writeUserDataToCSV(data):
    with open("userData.csv", "wb") as csvfile:
        fieldnames = ["user", "scheme", "totalLogins", "successes", "failures", "meanSucTime", "meanFailTime"]
        writer = csv.DictWriter(csvfile, fieldnames)
        
        writer.writeheader()
        for row in data:
            writer.writerow(row)
    
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
            
            logEntry = {"user": row["user"], "scheme": row["scheme"], "event": row["event"], "timeDelta": delta}
            
            data.append(logEntry)
            
            logins = logins + 1
            del startEvents[row['user']]
            
    print "Starts %d" %starts
    print "Complete logins %d" %logins
    print "Logins without a start %d" %falseLogins
    return data
    
def organizeByUser(loginsData):
    usersDict = {}
    for row in loginsData:
        if row['user'] in usersDict.keys():
            usersDict[row['user']].append(row)
        else:
            usersDict[row['user']] = [row]
    
    data = []
    for user in usersDict.keys():
        successes = 0
        failures = 0
        sucSum = 0
        failSum = 0
        
        for login in usersDict[user]:
            if login['event'] == "success":
                successes = successes + 1
                sucSum = sucSum + float(login['timeDelta'])
            else:
                failures = failures + 1
                failSum = failSum + float(login['timeDelta'])
              
        if successes == 0:
            meanSuccess = 0
        else:
            meanSuccess = (sucSum/successes)
            
        if failures == 0:
            meanFailure = 0
        else:
            meanFailure = (failSum/failures)
            
        logEntry = {"user": user, "scheme": usersDict[user][0]["scheme"], "totalLogins":  successes + failures, "successes": successes, "failures": failures, "meanSucTime": meanSuccess, "meanFailTime": meanFailure}
        
        data.append(logEntry)
        
    return data
    
            
def main():
    rawData = readRawDataCSV()
    
    loginData = findLogins(rawData)
    
    writeLoginsToCSV(loginData)
    
    loginData = readLoginsCSV()
    
    dataByUser = organizeByUser(loginData)
    
    writeUserDataToCSV(dataByUser)
    
if __name__ == "__main__":
    main()