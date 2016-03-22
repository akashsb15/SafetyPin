import sys
import csv
import math

# TO DO: Account for daylight savings times
# TO DO: Download all data for relevant cities and years

def isItDark(city, month, day, year, hour, minute):
    fname = '../' + city + '/sun_' + year + '.csv'
    with open(fname,'rb') as sundata:
        sun = list(sundata.read().splitlines())

    d = int(day)
    m = int(month)

    row = sun[d-1].split(',')

    sunrise = int(row[(m*2)-1])

    sunset = int(row[(m*2)])

    t = int(hour + minute)

    if t < sunrise: # Time is between midnight and sunrise
        print 'Between midnight and sunrise'
        return True # It is dark
    else:
        if t < sunset: # Time is between sunrise and sunset
            print 'Between sunrise and sunset'
            return False # It is not dark
        else: # Time is between sunset and midnight
            print 'Between sunset and midnight'
            return True # It is not dark

if __name__ == '__main__':
    city = sys.argv[1]
    month = sys.argv[2]
    day = sys.argv[3]
    year = sys.argv[4]
    hour = sys.argv[5]
    minute = sys.argv[6]

    if (len(month) != 2) or (len(day) != 2) or (len(year) != 4) or (len(hour) != 2) or (len(minute) != 2):
        print 'Usage: Please use 2-digit month, day, hour, and minute and 4-digit year.'
    if (year != '2012') and (year != '2013') and (year != '2014'):
        print 'Usage: Do not have sun data for this city for that year!'
    if (city != 'Boston') or (city != 'Chicago') or (city != 'Denver') or (city != 'NewYork') or (city != 'Philly'):
        print 'Usage: Please enter Boston, Chicago, Denver, NewYork, or Philly as city name.'

    isItDark(city,month,day,year,hour,minute)
