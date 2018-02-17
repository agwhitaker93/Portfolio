import datetime
import sys
import time
from pydub import AudioSegment
from pydub.playback import play

jingle = AudioSegment.from_wav("jingle.wav")

def get_year():
    while True:
        try:
            year = input("Enter target year (or enter -1 to exit): ")

            if year == -1:
                break

            if year >= datetime.datetime.now().year:
                return year

            print "Cannot go back in time\n"

        except:
            print "Invalid input\n"

def get_month():
    while True:
        try:
            month = input("Enter target numerical month (or enter -1 to exit): ")

            if month == -1:
                break

            if 1 <= month <= 12:
                return month

            print "Invalid value for month\n"

        except:
            print "Invalid input\n"

def get_day():
    while True:
        try:
            day = input("Enter target day (or enter -1 to exit): ")

            if day == -1:
                break

            if 1 <= day <= 31:
                return day

            print "Invalid value for day\n"

        except:
            print "Invalid input\n"

def get_hour():
    while True:
        try:
            hour = input("Enter target hour (or enter -1 to exit): ")

            if hour == -1:
                break

            if 0 <= hour <= 24:
                return hour

            print "Invalid value for hour\n"

        except:
            print "Invalid input\n"

play(jingle)
target_year = get_year()
target_month = get_month()
target_day = get_day()
target_hour = get_hour()

current_datetime = datetime.datetime.now()
current_datetime = current_datetime - datetime.timedelta(microseconds=current_datetime.microsecond)

target_datetime = datetime.datetime(target_year, target_month, target_day, target_hour, 0, 0, 0)
print "\nCurrent datetime is:\t", current_datetime
print "Your target is:\t\t", target_datetime

remaining_datetime = target_datetime - current_datetime

while True:
    print remaining_datetime
    remaining_datetime = remaining_datetime - datetime.timedelta(seconds=1)

    if remaining_datetime < (current_datetime - current_datetime):
        break

    time.sleep(1)

play(jingle)

input
