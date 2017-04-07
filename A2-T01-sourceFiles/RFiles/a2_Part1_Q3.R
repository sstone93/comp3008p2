#Functions:
  #setwd(...):    sets the working directory of the script. THIS MUST BE SET BEFORE THE SCRIPT CAN RUN
  #mean(..):      calculates the mean of the given values
  #median(...):   calculates the median of given values
  #sd(...):       calculates the standard devation of values
  #multhist(...): creates a multicategory histogram for the inputed list
  #box plot:      create a boxplot of the given values

#Note: working directory must be set before running
setwd("...")
loginData <- read.csv("~/A2-T01-sourceFiles/DataProcessing/userData.csv")


loginData.subAZ <- subset(loginData, loginData$scheme == 'unknown;az-6')
loginData.subBlank <- subset(loginData, loginData$scheme == 'unknown;BlankPassTil')
loginData.subImage <- subset(loginData, loginData$scheme == 'unknown;ImagePasstil')

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

loginBySchemeTotal <- list(loginData.subAZ$totalLogins, loginData.subBlank$totalLogins, loginData.subImage$totalLogins)

loginBySchemeSucc <- list(loginData.subAZ$successes, loginData.subBlank$successes, loginData.subImage$successes)

loginBySchemeFail <- list(loginData.subAZ$failures, loginData.subBlank$failures, loginData.subImage$failures)

colours = c("Red", "Purple", "Blue")

library("plotrix")
multhist(loginBySchemeTotal, xlab = "Total Logins", ylab = "Frequency", main = "Histogram for Total Logins", col = colours)

multhist(loginBySchemeSucc, xlab = "Total Successful Logins", ylab = "Frequency", main = "Histogram for Successful Logins", col = colours)

multhist(loginBySchemeFail, xlab = "Total Unsuccessful Logins", ylab = "Frequency", main = "Histogram for Failed Logins", col = colours)

legend("topright", c("Text28", "Blankpt28", "Imagept28"), col=c("red", "purple", "blue"), lwd=10)

#Histogram 2 Login Time/User
loginBySchemeSuccTime <- list(loginData.subAZ$meanSucTime, loginData.subBlank$meanSucTime, loginData.subImage$meanSucTime)

loginBySchemeFailTime <- list(loginData.subAZ$meanFailTime, loginData.subBlank$meanFailTime, loginData.subImage$meanFailTime)

multhist(loginBySchemeSuccTime, xlab = "Login Time", ylab = "Frequency", main = "Histogram for Successful Login Times", col = colours)

multhist(loginBySchemeFailTime, xlab = "Login Time", ylab = "Frequency", main = "Histogram for Failed Login Times", col = colours)


#Boxplot
#x axis = login time
#Two boxplots, successful and unsuccessful
#one box per scheme
theNames = c("Text28", "Blankpt28", "Imagept28")

boxplot(loginData$meanSucTime ~ loginData$scheme, ylab = "Login Time", xlab = "Password Scheme", 
        main = "Boxplot for Successful Login Times", col = colours, names = theNames)

boxplot(loginData$meanFailTime ~ loginData$scheme, ylab = "Login Time", xlab = "Password Scheme", 
        main = "Boxplot for Failure Login Times", outline = FALSE, col = colours, names = theNames)
#outline = FALSE doesn't draw outliers