from ParsedAddress import Address
import re

directions = {'':'', 'N':'NORTH', 'S':'SOUTH', 'E':'EAST', 'W':'WEST', 'NORTH':'NORTH', 'SOUTH':'SOUTH', 'EAST':'EAST', 'WEST': 'WEST', 'NW':'NORTH WEST', 'NE':'NORTH EAST', 'SW': 'SOUTH WEST', 'SE': 'SOUTH EAST', "NORTHEAST": "NORTH EAST", "NORTHWEST": "NORTH WEST", "SOUTHEAST": "SOUTH EAST", "SOUTHWEST": "SOUTH WEST", 'NS': 'NORTH SOUTH' }
roadtypes = {'':'', 'ST':'STREET', 'STREET':'STREET', 'ROAD':'ROAD', 'RD':'ROAD', 'AVE':'AVENUE', 'AVENUE':'AVENUE', 'LANE':'LANE', 'LN':'LANE', 'SQUARE':'SQUARE', 'SQ':'SQUARE', 'BLVD': 'BOULEVARD', 'BOULEVARD': 'BOULEVARD'}

def parseAddress(line, city, state, zip):
    street_num, street_name, rType, apt, dir = parseLine(line)
    return Address(street_num, rType, dir, apt, city, state, street_name, zip, line)




def parseLine(line):
    line = line.replace('.', '')
    line = line.replace('-', '')
    linelist = line.split()
    street_num = linelist[0]
    rType = ""
    dir = ""

    apt_list = []
    street_name_list = []
    for index, part in enumerate(linelist):
        if part == 'APT' or part == 'NR':
            apt_list = linelist[index + 1:]
            break
        elif index > 0 and part not in roadtypes:
            street_name_list.append(part)
        elif part in roadtypes:
            rType = part
            #street_name_list.append(part)
            if len(linelist) > index + 1 and linelist[index + 1] in directions:
                dir = linelist[index + 1]         
                apt_list = linelist[index+3:]
                break       
        

    dirlist = []
    if dir == "" and len(street_name_list) > 1:
        for index, part in enumerate(street_name_list):
            if part in directions:
                dirlist.append(directions[part])
                street_name_list.remove(street_name_list[index])
            else:
                break
        if len(dirlist) > 0:
            dir = ' '.join(dirlist)
        

    street_name = ' '.join(street_name_list)
    rType = roadtypes[rType]
    apt = ' '.join(apt_list)
    
    return street_num, street_name, rType, apt, dir


