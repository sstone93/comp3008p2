import csv
from datetime import datetime

'''
dataProcessor.py
Run from the command line with no arguments
This processes the log data for the three provided schemes
into a single csv file with one line per user.
'''

#Function to import the original csv files
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
    
#Writes the intermediate file that contains one line per login
def writeLoginsToCSV(data):
    with open("logins.csv", "wb") as csvfile:
        fieldnames = ["user", "site", "scheme", "event", "timeDelta"]
        writer = csv.DictWriter(csvfile, fieldnames)
        
        writer.writeheader()
        for row in data:
            writer.writerow(row)
            
#reads the intermediate file
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
    
#Writes the final data to the csv file
def writeUserDataToCSV(data):
    with open("userData.csv", "wb") as csvfile:
        fieldnames = ["user", "scheme", "totalLogins", "successes", "failures", "meanSucTime", "meanFailTime"]
        writer = csv.DictWriter(csvfile, fieldnames)
        
        writer.writeheader()
        for row in data:
            writer.writerow(row)
    
#Function to identify login attempts in the original data
def findLogins(rawData):
    startEvents = {}
    data = []
    
    for row in rawData:
        if row["mode"] == "enter" and row["event"] == "start":
            startEvents[row['user']] = row
            
        if row["mode" ] == "login":
            if not row['user'] in startEvents.keys():
                #login events are not content if a 
                #corresponding start event was not found
                continue
                
            startEvent = startEvents[row['user']]
            
            startTime = datetime.strptime(startEvent["time"],"%Y-%m-%d %H:%M:%S")
            endTime = datetime.strptime(row["time"],"%Y-%m-%d %H:%M:%S")
            delta =(endTime - startTime).total_seconds()
            
            logEntry = {"user": row["user"], "site": row["site"], "scheme": row["scheme"], "event": row["event"], "timeDelta": delta}
            
            data.append(logEntry)
            
            del startEvents[row['user']]
            
    return data
    
#Takes the intermediate data and organizes it into a single entry per user
def organizeByUser(loginsData):
    usersDict = {}
    
    #a dictionary with users as keys and lists of login events as values is created
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
    
#the main function that is executed when the script is run
#calls the other functions in the required order
def main():
    rawData = readRawDataCSV()
    
    loginData = findLogins(rawData)
    
    writeLoginsToCSV(loginData)
    
    loginData = readLoginsCSV()
    
    dataByUser = organizeByUser(loginData)
    
    writeUserDataToCSV(dataByUser)
    
if __name__ == "__main__":
    main()