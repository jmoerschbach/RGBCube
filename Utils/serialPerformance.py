import serial
import time
#ser = serial.Serial('COM6', 250000, timeout=0)
#ser = serial.Serial('COM6', 230400, timeout=0)
ser = serial.Serial('COM6', 115200, timeout=0)
#ser = serial.Serial('COM6', 76800, timeout=0)
#ser = serial.Serial('COM6', 38400, timeout=0)
#ser = serial.Serial('COM6', 19200, timeout=0)
time.sleep(3)

state = 7
byteStream = bytearray(25)

def sendToCube():
    ser.write('a'.encode())
    ser.write(byteStream)
    ser.write('e'.encode())

def manipulateStream(state):
    if state==0:
        byteStream[0]=0x01
        byteStream[1]=0xff
        byteStream[2]=0x00
        byteStream[3]=0x00
        byteStream[4]=0xff
        byteStream[5]=0x00
        byteStream[6]=0x00
    elif state==1:
        byteStream[0]=0x02
        byteStream[1]=0x00
        byteStream[2]=0xff
        byteStream[3]=0x00
        byteStream[4]=0x00
        byteStream[5]=0xff
        byteStream[6]=0x00
    elif state==2:
        byteStream[0]=0x04
        byteStream[1]=0x00
        byteStream[2]=0x00
        byteStream[3]=0xff
        byteStream[4]=0x00
        byteStream[5]=0x00
        byteStream[6]=0xff
    elif state==3:
        byteStream[0]=0x08
        byteStream[1]=0xff
        byteStream[2]=0x00
        byteStream[3]=0x00
        byteStream[4]=0xff
        byteStream[5]=0x00
        byteStream[6]=0x00
    elif state==4:
        byteStream[0]=0x10
        byteStream[1]=0x00
        byteStream[2]=0xff
        byteStream[3]=0x00
        byteStream[4]=0x00
        byteStream[5]=0xff
        byteStream[6]=0x00
    elif state==5:
        byteStream[0]=0x20
        byteStream[1]=0x00
        byteStream[2]=0x00
        byteStream[3]=0xff
        byteStream[4]=0x00
        byteStream[5]=0x00
        byteStream[6]=0xff
    elif state==6:
        byteStream[0]=0x40
        byteStream[1]=0xff
        byteStream[2]=0x00
        byteStream[3]=0x00
        byteStream[4]=0xff
        byteStream[5]=0x00
        byteStream[6]=0x00
    elif state==7:
        byteStream[0]=0x80
        byteStream[1]=0x00
        byteStream[2]=0xff
        byteStream[3]=0x00
        byteStream[4]=0x00
        byteStream[5]=0xff
        byteStream[6]=0x00
while True:
    manipulateStream(state)
    sendToCube()
    state = state + 1
    if state > 7:
        state = 0

ser.close

