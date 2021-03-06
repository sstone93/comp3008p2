COMP 3008B Project 2 T01 readme

This file contains a listing and description of all the files in the A2-T01-sourceFiles folder submitted for this projects.

Folder: DataProcessing
Purpose: everything related to the log processing for part 1
Files:
Blankpt28_log.csv - the log data for the blank pass tiles system (downloaded from culearn)
dataProcessor.py - the python script that was used to pre-process the log files for statistical analysis.
Imagept28_log.csv - the log data for the image pass tiles system (downloaded from culearn)
logins.csv - an intermediate file from the log processing, it contains one row for each unique login (generated by running dataProcessor.py)
Text28_log.csv - the log data for the randomly assigned text system (downloaded from culearn)
userDat.csv - the final log data that is used for statistical analysis (generated by running dataProcessor.py)

Folder: DataProcessingP2
Purpose: everything related to the log processing for part 2
Files:
dataProcessor.py - the python script that was used to pre-process the log files for statistical analysis.
logins.csv - an intermediate file from the log processing, it contains one row for each unique login (generated by running dataProcessor.py)
ourData.csv - the log data from the testing of our password scheme
Text28_log.csv - the log data for the randomly assigned text system (downloaded from culearn)
userDat.csv - the final log data that is used for statistical analysis (generated by running dataProcessor.py)

File: LimeSurvey Questionnaire.pdf - The questionnaire used to measure user perceptions

Folder: LogFiles
Purpose: The log files obtained from our user testing
Files:
logs (folder) - this folder containers one text file with the xml formatted logs for each individual user
ourData.csv - the aggregated log data from the testing of our password scheme

Folder: PasswordTestingApplication
Purpose: The eclipse project files and source code for our password scheme and testing application
If the jar contained in this file is run, log files will be created in this folder.
Files:
bin {folder) - This folder contains the compiled .class files
PasswordTestingApp.jar - a runnable jar file of our application used for participant testing.
src (folder) - This folder contains the source code for our application
    Controller (folder)
        Controller.java - this file contains the controller class. This is where the main function to execute the program is located.
    model (folder)
        MainModel.java - contains the class that represents the testing system model
        Password.java - contains the class that represents the password scheme we created
    resources (folder)
        e1.png to e16.png - emoji images used for the third section of our password
        l1.jpg to l16.jpg - the landscape images used for the first portion of our password
        user.txt - a file used to keep track of the next userID
        words.txt - a file containing the 1024 different words for our password scheme
    view (folder)
        PasswordPanel.java - the password entry portion of the GUI
        View.java - the main wrapper for the GUI

Folder: RFiles
Purpose: The R files for running analysis on the data
Files:
a2_Part1_Q3.R - this script performs the statistical analysis required for Part 1 Question 3.
	The working directory must be set to the current directoty before this file can be run.
	It performs statisical analysis on userData.csv located in ~A2-T01-sourceFiles/DataProcessing
a2_Part2_Q6_Part1.R - this script performs the statistical analysis for comparing the results of our password scheme with Text28
	The working directory must be set to the current directoty before this file can be run.
	It performs statisical analysis on userData.csv located in ~A2-T01-sourceFiles/DataProcessing2
a2_Part2_Q6_Part2.R - this script performs the statistical analysis for our LimeSurvey results
	The working directory must be set to the current directoty before this file can be run.
	It performs statisical analysis on userData.csv located in ~A2-T01-sourceFiles/DataProcessing2