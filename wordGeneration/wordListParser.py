import string

def main():
    words = []
    with open("EnglishWords.txt",'rb') as file:
        for word in file:
            word = string.strip(word)
            if len(word) == 6 and str.isalpha(word) and word == str.lower(word):
                #print word
                words.append(word)
                
    print len(words)
    
    with open("6LetterWord.txt",'wb') as file:
        for word in words:
            file.write(word + "\n")
    
if __name__ == "__main__":
    main()