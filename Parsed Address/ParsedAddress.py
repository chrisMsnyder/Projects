import re

class Error(Exception):
    pass

class AddressSyntaxException(Error):
    pass
    

class Address:
    def __init__(self, street_num, street_type, direction, apt_num, city, state, street_name, zip, line):
        self.street_num = street_num
        self.street_type = street_type
        self.direction = direction
        self.apt_num = apt_num
        self.city = city
        self.state = state
        self.street_name = street_name
        self.zip = zip
        self.line = line
        self.isValid = True




############################################################
#def printAddress(self, index)
#
# Parameters:
#       self      -  necessary for OO
#       index     -  index of address dictionary inside the list
#
# Purpose:
#       Prints out the address info for this address object
#       
#
# Returns:
#       nothing
############################################################# 
           
    def printAddress(self, index):
        print('{0:1} {1:30}'.format(index, self.line))
        print('{0:1} {1:30}'.format('', self.city + ', ' + self.state + ' ' + self.zip))

        try:
            if self.line == '':
                raise AddressSyntaxException('MISSING LINES')
            if self.city == '':
                raise AddressSyntaxException('MISSING CITY')
            if self.state == '':
                raise AddressSyntaxException('MISSING STATE')
            if self.zip == '':
                raise AddressSyntaxException('MISSING ZIP')
            if not self.street_num[0].isdigit():
                raise AddressSyntaxException('INVALID STREET NUM')
            if re.match(r'\d{5}(-\d{4})?', self.zip) == None:
                raise AddressSyntaxException('INVALID ZIP')
            print('{0:30}{1:15}{2:15}{3:15}{4:15}{5:15}'.format('', self.street_num, self.direction, self.apt_num, self.street_type, self.street_name))

        except AddressSyntaxException as e:
            print('{0:30}{1:30}'.format('', "***" + str(e.args[0]) +"****"))
            self.isValid = False

        print('')
