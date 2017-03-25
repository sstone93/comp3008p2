loginData <- read.csv("C:/Tineke/Comp Sci/3008/Assignment 2/data.csv")
setwd("C:/Tineke/Comp Sci/3008/Assignment 2") #will save anything here
#ctrl-R to run a single line of script (runs line cursor is on)
View(loginData) #brings up data
ls.str(loginData) #lists columns as tells you what they are (char, num etc.)
#loginData$user use this if only want this col, just looking at this col
  #can be used instead of loginData

#Descriptive Stats
#mean, std, median for
  #total, succ, fail - logins
  #succ, fail - login time
mean(loginData.subAZ$totalLogins)
median(loginData.subAZ$totalLogins)
sd(loginData.subAZ$totalLogins)

mean(loginData.subBlank$totalLogins)
median(loginData.subBlank$totalLogins)
sd(loginData.subBlank$totalLogins)

mean(loginData.subImage$totalLogins)
median(loginData.subImage$totalLogins)
sd(loginData.subImage$totalLogins)

mean(loginData.subAZ$successes)
median(loginData.subAZ$successes)
sd(loginData.subAZ$successes)

mean(loginData.subBlank$successes)
median(loginData.subBlank$successes)
sd(loginData.subBlank$successes)

mean(loginData.subImage$successes)
median(loginData.subImage$successes)
sd(loginData.subImage$successes)

mean(loginData.subAZ$failures)
median(loginData.subAZ$failures)
sd(loginData.subAZ$failures)

mean(loginData.subBlank$failures)
median(loginData.subBlank$failures)
sd(loginData.subBlank$failures)

mean(loginData.subImage$failures)
median(loginData.subImage$failures)
sd(loginData.subImage$failures)

mean(loginData.subAZ$meanSucTime)
median(loginData.subAZ$meanSucTime)
sd(loginData.subAZ$meanSucTime)

mean(loginData.subBlank$meanSucTime)
median(loginData.subBlank$meanSucTime)
sd(loginData.subBlank$meanSucTime)

mean(loginData.subImage$meanSucTime)
median(loginData.subImage$meanSucTime)
sd(loginData.subImage$meanSucTime)

mean(loginData.subAZ$meanFailTime)
median(loginData.subAZ$meanFailTime)
sd(loginData.subAZ$meanFailTime)

mean(loginData.subBlank$meanFailTime)
median(loginData.subBlank$meanFailTime)
sd(loginData.subBlank$meanFailTime)

mean(loginData.subImage$meanFailTime)
median(loginData.subImage$meanFailTime)
sd(loginData.subImage$meanFailTime)

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
















