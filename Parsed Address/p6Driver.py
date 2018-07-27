######################################################################
# Author: Chris Snyder
# lok139
# p6Driver.py
# Project 6
######################################################################

import sys
import address as a
import checkAddress as ca
import difflib as df

######################################################################
# def main():
#
# Parameters:
#       None
#
# Purpose:
#       Main function, where the program begins. It reads an input file
#       line by line, constructing a dictionary, of lists of dictionaries
#       of addresses
# Returns:
#       nothing
#######################################################################
def main():
    if (len(sys.argv) != 2):
        return print("Number of arguments invalid.")

    custdict = dict()
    #open input file
    with open(sys.argv[1], "r") as file:
        #read each line one at a time until end of file
        while True:
            command, params = a.extractCommand(file)
            #break if end of file
            if command == None:
                break
            name = params.lstrip()
            addrlist = []
            
            #iterate over each of the addresses in a customer
            while True:
                #line, city, state, zip = ""
                command,params = a.extractCommand(file)
                if command == "CUSTOMEREND":
                    break

                #addrdict = dict()
                #addrdict["LINE"], addrdict["CITY"], addrdict["STATE"], addrdict["ZIP"] = a.getAddress(file)
                line, city, state, zip = a.getAddress(file)
                addr = ca.parseAddress(line, city, state, zip)
                addrlist.append(addr)
                #print('{0:20}{1:15}{2:15}{3:15}{4:15}{5:15}'.format(' ', addr.street_num, addr.direction, addr.apt_num, addr.street_type, addr.street_name))
            custdict[name] = addrlist
    printAll(custdict)




#########################################################################
# def printAll(custdict)
#
# Parameters:
#       custdict  -  dictionary of customers
#
# Purpose:
#       prints all of the customers addresses
#
# Returns:
#       nothing
########################################################################
def printAll(custdict):
    #iterate through each customer in the dict
    for customer in custdict:
        print('{0:30}{1:15}{2:15}{3:15}{4:15}{5:15}'.format(customer, 'StNum', 'Direction', 'AptNum', 'StType', 'StName'))
        #iterates through each address fro wach customer
        for index, addressO in enumerate(custdict[customer]):
            addressO.printAddress(index + 1)
        printScores(custdict[customer])




def printScores(addrlist):
    print('{0:10}{1:15}{2:15}{3:15}'.format('', 'Address', 'Address', 'Score'))
    for i, addr in enumerate(addrlist):
        for j in range(i, len(addrlist)):
            if i != j and addrlist[i].isValid != False and addrlist[j].isValid != False:
                print('{0:10}{1:15}{2:15}'.format(i+1, j+1, addrScores(addrlist[i], addrlist[j])))   

    print('\n')   


def addrScores(addr1, addr2):
    score = 0
    #check street name scores
    if addr1.street_name == '' and addr2.street_name == '':
        score -= 20
    elif addr1.street_name == '' or addr2.street_name == '':
        score -= 20
    elif addr1.street_name == addr2.street_name:
        score += 20
    else :
        score -= 5

    #check street type scores
    if addr1.street_type == '' and addr2.street_type == '':
        score += 10
    elif addr1.street_type == '' or addr2.street_type == '':
        score += 5
    elif addr1.street_type == addr2.street_type:
        score += 10
    else:
        score -= 10

    #check direction score
    if addr1.direction == '' and addr2.direction == '':
        score += 5
    elif addr1.direction == '' or addr2.direction == '':
        score -= 5
    elif addr1.direction == addr2.direction:
        score += 5
    else:
        score -= 10

    #check apt_num
    s = df.SequenceMatcher(None, addr1.apt_num, addr2.apt_num)
    if addr1.apt_num == '' and addr2.apt_num == '':
        score += 10
    elif addr1.apt_num == '' or addr2.apt_num == '':
        score -= 10
    elif addr1.apt_num == addr2.apt_num:
        score += 20
    elif s.ratio() > 0.6:
        score += int(5 * s.ratio())
    else:
        score -= 20

    #check city score
    s = df.SequenceMatcher(None, addr1.city, addr2.city)
    if addr1.city == '' and addr2.city == '':
        score += 10
    elif addr1.city == '' or addr2.city == '':
        score -= 10
    elif addr1.city == addr2.city:
        score += 20
    elif s.ratio() > 0.6:
        score += int(15 * s.ratio())
    else:
        score -= 20

    #check state score
    if addr1.state == '' or addr2.state == '':
        score = score
    elif addr1.state == addr2.state:
        score += 10
    else:
        score -= 20

    #check street num
    if addr1.street_num == '' and addr2.street_num == '':
        score = score
    elif addr1.street_num == '' or addr2.street_num == '':
        score -= 20
    elif addr1.street_num == addr2.street_num:
        score += 20

    #check zip code score
    if len(addr1.zip) == 10 and len(addr2.zip) == 10 and addr1.zip == addr2.zip:
        score += 80
    elif len(addr1.zip) == 5 and len(addr2.zip) == 5 and addr1.zip == addr2.zip:
        score += 5
    elif addr1.zip[:5] == addr2.zip[:5] and len(addr1.zip) == len(addr2.zip):
        score += 5
    


    #make sure the score is between 0 and 100
    if score < 0:
        score = 0;
    if score > 100:
        score = 100
    return score




if __name__ == "__main__":
        main()
