loginData <- read.csv("C:/Tineke/Comp Sci/3008/Assignment 2/data.csv")
setwd("C:/Tineke/Comp Sci/3008/Assignment 2") #will save anything here
#ctrl-R to run a single line of script (runs line cursor is on)
View(loginData) #brings up data
ls.str(loginData) #lists columns as tells you what they are (char, num etc.)
#loginData$user use this if only want this col, just looking at this col
  #can be used instead of loginData

#mean, std, median for
  #total, succ, fail - logins
  #succ, fail - login time
mean(loginData$totalLogins)
median(loginData$totalLogins)
sd(loginData$totalLogins)

mean(loginData$successes)
median(loginData$successes)
sd(loginData$successes)

mean(loginData$failures)
median(loginData$failures)
sd(loginData$failures)

mean(loginData$meanSucTime)
median(loginData$meanSucTime)
sd(loginData$meanSucTime)

mean(loginData$meanFailTime)
median(loginData$meanFailTime)
sd(loginData$meanFailTime)

#Histogram 1
#multicategory -- scheme
#x axis = num logins
#y axis = num users who had x logins
#3 histograms (one for total, one for successes, and one for failures
hist(loginData$totalLogins, xlab = "Total Logins", xlim = c(10, 30), main = "Histogram for Logins")

loginData.subAZ <- subset(loginData, loginData$scheme == 'unknown;az-6')
loginData.subBlank <- subset(loginData, loginData$scheme == 'unknown;BlankPassTil')
loginData.subImage <- subset(loginData, loginData$scheme == 'unknown;ImagePasstil')

loginBySchemeTotal <- list(loginData.subAZ$totalLogins, loginData.subBlank$totalLogins, loginData.subImage$totalLogins)

loginBySchemeSucc <- list(loginData.subAZ$totalLogins, loginData.subBlank$totalLogins, loginData.subImage$totalLogins)

loginBySchemeFail <- list(loginData.subAZ$totalLogins, loginData.subBlank$totalLogins, loginData.subImage$totalLogins)


library("plotrix")
multhist(loginBySchemeTotal, xlab = "Total Logins", main = "Histogram for Total Logins")

multhist(loginBySchemeSucc, xlab = "Total Logins", main = "Histogram for Successful Logins")

multhist(loginBySchemeFail, xlab = "Total Logins", main = "Histogram for Failed Logins")

#Boxplot
#x axis = login time
#Two boxplots, successful and unsuccessful
#one box per scheme
boxplot(loginData$meanSucTime ~ loginData$scheme, ylab = "Login Time", xlab = "Passowrd Scheme", 
        main = "Succesful Login Times")

boxplot(loginData$meanFailTime ~ loginData$scheme, ylab = "Login Time", xlab = "Passowrd Scheme", 
        main = "Failure Login Times", outline = FALSE)
#outline = FALSE doesn't draw outliers
















