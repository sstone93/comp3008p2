#Functions:
  #setwd(...):    sets the working directory of the script. THIS MUST BE SET BEFORE THE SCRIPT CAN RUN
  #mean(..):      calculates the mean of the given values
  #median(...):   calculates the median of given values
  #sd(...):       calculates the standard devation of values
  #multhist(...): creates a multicategory histogram for the inputed list
  #box plot:      create a boxplot of the given values


#working directory must be set before running
setwd("...")
loginData <- read.csv("~/A2-T01-sourceFiles/DataProcessingP2/userData.csv")

loginData.subAZ <- subset(loginData, loginData$scheme == 'unknown;az-6')
loginData.sub3C <- subset(loginData, loginData$scheme == 'ThreeChunk')

#Descriptive Stats
#mean, std, median for
#total, succ, fail - logins
#succ, fail - login time
mean(loginData.sub3C$totalLogins)
median(loginData.sub3C$totalLogins)
sd(loginData.sub3C$totalLogins)

mean(loginData.sub3C$successes)
median(loginData.sub3C$successes)
sd(loginData.sub3C$successes)

mean(loginData.sub3C$failures)
median(loginData.sub3C$failures)
sd(loginData.sub3C$failures)

mean(loginData.sub3C$meanSucTime)
median(loginData.sub3C$meanSucTime)
sd(loginData.sub3C$meanSucTime)

mean(loginData.sub3C$meanFailTime)
median(loginData.sub3C$meanFailTime)
sd(loginData.sub3C$meanFailTime)

#Histogram 1
#multicategory -- scheme
#x axis = num logins
#y axis = num users who had x logins
#3 histograms (one for total, one for successes, and one for failures

loginBySchemeTotal <- list(loginData.subAZ$totalLogins, loginData.sub3C$totalLogins)

loginBySchemeSucc <- list(loginData.subAZ$successes, loginData.sub3C$successes)

loginBySchemeFail <- list(loginData.subAZ$failures, loginData.sub3C$failures)

colours = c("Blue", "Purple")

library("plotrix")
multhist(loginBySchemeTotal, xlab = "Total Logins", ylab = "Frequency", main = "Histogram for Total Logins", col = colours)

multhist(loginBySchemeSucc, xlab = "Total Successful Logins", ylab = "Frequency", main = "Histogram for Successful Logins", col = colours)

multhist(loginBySchemeFail, xlab = "Total Unsuccessful Logins", ylab = "Frequency", main = "Histogram for Failed Logins", col = colours)

legend("topright", c("Text28", "ThreeChunk"), col=c("blue", "purple"), lwd=10)

#Histogram 2 Login Time/User
loginBySchemeSuccTime <- list(loginData.subAZ$meanSucTime, loginData.sub3C$meanSucTime)

loginBySchemeFailTime <- list(loginData.subAZ$meanFailTime, loginData.sub3C$meanFailTime)

multhist(loginBySchemeSuccTime, xlab = "Login Time", ylab = "Frequency", main = "Histogram for Successful Login Times", col = colours)

multhist(loginBySchemeFailTime, xlab = "Login Time", ylab = "Frequency", main = "Histogram for Failed Login Times", col = colours)

#Boxplot
#x axis = login time
#Two boxplots, successful and unsuccessful
#one box per scheme
theNames = c("Text28", "ThreeChunk")

loginData$meanFailTime[loginData$meanFailTime == 0] <- NA
loginData$meanSuccTime[loginData$meanFailTime == 0] <- NA
#removing 0 values when no faliure or success occured

boxplot(loginData$meanSucTime ~ loginData$scheme, ylab = "Login Time", xlab = "Password Scheme", 
        main = "Boxplot for Successful Login Times", col = colours, names = theNames)

boxplot(loginData$meanFailTime ~ loginData$scheme, ylab = "Login Time", xlab = "Password Scheme", 
        main = "Boxplot for Failure Login Times", outline = FALSE, col = colours, names = theNames)
#outline = FALSE doesn't draw outliers

#Inferential Statistics
#data is not normal, need to do a wilcox test instead of t

#changing number of successsful and unsuccessful logins to proportions of the total number of logins 
#in order to properly compare the data
#as logins for Text28 were done more times than ThreeChunk
prop.success <- loginData[ , 4] / loginData[, 3]
#prop.success

prop.fail <- loginData[ , 5] / loginData[, 3]
#prop.fail

loginData2 <- cbind(loginData, prop.success, prop.fail)
View(loginData2)

loginData2.subAZ <- subset(loginData2, loginData2$scheme == 'unknown;az-6')
loginData2.sub3C <- subset(loginData2, loginData2$scheme == 'ThreeChunk')

  #successes
wilcox.test(loginData2.sub3C$prop.success, loginData2.subAZ$prop.success, exact = FALSE, paired = FALSE)

  #failures
wilcox.test(loginData2.sub3C$prop.fail, loginData2.subAZ$prop.fail, exact = FALSE, paired = FALSE)

  #meanSucTime
wilcox.test(loginData.sub3C$meanSucTime, loginData.subAZ$meanSucTime, exact = FALSE, paired = FALSE)

  #meanFailTime
wilcox.test(loginData.sub3C$meanFailTime, loginData.subAZ$meanFailTime, exact = FALSE, paired = FALSE)

