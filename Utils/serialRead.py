import serial
import time
#ser = serial.Serial('COM6', 250000, timeout=0)
#ser = serial.Serial('COM6', 230400, timeout=0)
#ser = serial.Serial('COM6', 115200, timeout=0)
#ser = serial.Serial('COM6', 76800, timeout=0)
#ser = serial.Serial('COM6', 38400, timeout=0)
#ser = serial.Serial('COM6', 19200, timeout=0)
ser = serial.Serial(
    port='COM6',\
    baudrate=9600,\
    parity=serial.PARITY_NONE,\
    stopbits=serial.STOPBITS_ONE,\
    bytesize=serial.EIGHTBITS,\
    timeout=None)

print("connected to: " + ser.portstr)
time.sleep(3)

	
print("Starting to read...")
count=1
#this will store the line
seq = []
count = 1


#while True:
#    for c in ser.read():
#        seq.append(chr(c)) #convert from ANSII
#        joined_seq = ''.join(str(v) for v in seq) #Make a string from array

#        if chr(c) == '\n':
#            print("Line " + str(count) + ': ' + joined_seq)
#            seq = []
#            count += 1
#            break

startTime=0
endTime = 0

#while True:
line = ser.read(1)
startTime = time.clock()
ser.read(2048)
endTime = time.clock()

ser.close()

print(endTime-startTime)

