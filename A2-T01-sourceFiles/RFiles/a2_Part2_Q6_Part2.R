#Functions:
  #setwd(...):    sets the working directory of the script. THIS MUST BE SET BEFORE THE SCRIPT CAN RUN
  #median(...):   calculates the median of given values
  #multhist(...): creates a multicategory histogram for the inputed list
  #Mode(...):     calculates the mode of given values. This code was found at 
                    #http://stackoverflow.com/questions/2547402/is-there-a-built-in-function-for-finding-the-mode

#working directory must be set before running
setwd("...")
userData <- read.csv("~/A2-T01-sourceFiles/RFiles/surveyResults.csv")


theMem <- list(userData$M00SQ004, userData$M00SQ005)
theTime <- list(userData$LT0SQ004, userData$LT0SQ005)
theSecurity <- list(userData$SC0SQ004, userData$SC0SQ005)
theOpinion <- list(userData$U00SQ001, userData$U00SQ002, userData$U00SQ003)

#Descriptive Stats
#find the mode of dataset
Mode <- function(x) {
  ux <- unique(x)
  ux[which.max(tabulate(match(x, ux)))]
}

median(userData$M00SQ004)
v <- userData$M00SQ004
theMode <- Mode(v)
print(theMode)

median(userData$M00SQ005)
v <- userData$M00SQ005
theMode <- Mode(v)
print(theMode)

median(userData$LT0SQ004)
v <- userData$LT0SQ004
theMode <- Mode(v)
print(theMode)

median(userData$LT0SQ005)
v <- userData$LT0SQ005
theMode <- Mode(v)
print(theMode)

median(userData$SC0SQ004)
v <- userData$SC0SQ004
theMode <- Mode(v)
print(theMode)

median(userData$SC0SQ005)
v <- userData$SC0SQ005
theMode <- Mode(v)
print(theMode)

median(userData$U00SQ001)
v <- userData$U00SQ001
theMode <- Mode(v)
print(theMode)

median(userData$U00SQ002)
v <- userData$U00SQ002
theMode <- Mode(v)
print(theMode)

median(userData$U00SQ003)
v <- userData$U00SQ003
theMode <- Mode(v)
print(theMode)


#Histograms
colours = c("Blue", "Purple")

colours2 = c("Blue", "Purple", "Green")

library("plotrix")
multhist(theMem, xlab = "Memorability Rating", ylab = "Frequency", main = "User Memorability Ratings", col = colours, breaks = c(0.5, 1.5, 2.5, 3.5 ,4.5 , 5.5))

multhist(theTime, xlab = "Entry Time Rating", ylab = "Frequency", main = "User Entry Time Ratings", col = colours, breaks = c(1.5, 2.5, 3.5 ,4.5 , 5.5))

multhist(theSecurity, xlab = "Security Rating", ylab = "Frequency", main = "User Security Ratings", col = colours, breaks = c(1.5, 2.5, 3.5 ,4.5 , 5.5))

multhist(theOpinion, xlab = "Consideration of Use Rating", ylab = "Frequency", main = "User Consideration of Use Raitings", col = colours2, breaks = c(0.5, 1.5, 2.5, 3.5 ,4.5 , 5.5))


legend("topright", c("ThreeChunk", "User-Chosen Text"), col=c("blue", "purple"), lwd=10)

legend("topright", c("For Bank", "For Facebook", "For School"), col=c("blue", "purple", "green"), lwd=10)


#Inferential Statistics
userData.subNeverBank <- subset(userData, userData$U00SQ001 <= 2 )
userData.subDefBank <- subset(userData, userData$U00SQ001 >= 4)

userData.subNeverFb <- subset(userData, userData$U00SQ002 <= 2)
userData.subDefFb <- subset(userData, userData$U00SQ002 >= 5)

userData.subNeverSchool <- subset(userData, userData$U00SQ003 <= 2)
userData.subDefSchool <- subset(userData, userData$U00SQ003 >= 5)


#memorability rating
wilcox.test(userData$M00SQ004, userData$M00SQ005, exact = FALSE, paired = FALSE)

#entry time rating
wilcox.test(userData$LT0SQ004, userData$LT0SQ005, exact = FALSE, paired = FALSE)


#security rating
wilcox.test(userData$SC0SQ004, userData$SC0SQ005, exact = FALSE, paired = FALSE)

#usage consideration raiting
wilcox.test(userData.subDefBank$U00SQ001, userData.subNeverBank$U00SQ001, exact = FALSE, paired = FALSE)

wilcox.test(userData.subDefFb$U00SQ001, userData.subNeverFb$U00SQ001, exact = FALSE, paired = FALSE)

wilcox.test(userData.subDefSchool$U00SQ003, userData.subNeverSchool$U00SQ003, exact = FALSE, paired = FALSE)


