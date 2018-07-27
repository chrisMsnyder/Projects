###########################################################
# Author: Chris Snyder
# lok139
# address.py
# Project 5
###########################################################

from ParsedAddress import Address
###########################################################
# def get Address(file):
#
# Parameters:   
#       file -  Input file to read from
#
# Purpose:
#       Extracts the address info, lines, city, state, 
#        zip from the input file
#
# Returns:
#       line, city, state, city strings to populate address
########################################################### 

def getAddress(file):
    line = ""
    city = ""
    state = ""
    zip = ""
    while True:
        command, params = extractCommand(file)
        if command == "ADDREND":
            break
        if command == "LINE":
            line = "".join(filter(None, list(line + " " + params))).lstrip()
        elif command == "CITY":
            city = params
        elif command == "STATE":
            state = params
        else:
            zip = params
            
    return line, city, state, zip




############################################################
#def printAddress(addressD, index)
#
# Parameters:
#       addressD  -  Address dictionary
#       index     -  index of address dictionary inside the list
#
# Purpose:
#       Prints out the address info for the given address 
#        dictionary loated at a given index
#
# Returns:
#       nothing
############################################################# 
           
def printAddress(addressO, index):
    print('{0:1} {1:30}'.format(index, addressO.line))
    print('{0:1} {1:30}'.format('', addressO.city + ', ' + addressO.state + ' ' + addressO.zip))
    print('{0:30}{1:15}{2:15}{3:15}{4:15}{5:15}'.format('', addressO.street_num, addressO.direction, addressO.apt_num, addressO.street_type, addressO.street_name))
    print('')





#############################################################
# def extractCommand(file)
#
# Parameters:
#       file  -  the input file to extract from
#       
# Purpose:
#       extracts the command and parameter arguments from a 
#       read in line of the input file
#  
# Returns:
#       Command string, and the associated parameters for 
#       that command
#############################################################
def extractCommand(file):
    line = file.readline()
    if line == "":
        return None, None
    line = line.rstrip('\n')
    command = str(line.split()[0])
    params = " ".join(line.split()[1:])
    return (command, params)
