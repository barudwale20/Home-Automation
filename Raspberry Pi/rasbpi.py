import RPi.GPIO as GPIO
import urllib.request
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(5,GPIO.OUT)
true = 1
while(true):
    #try:
    response = urllib.request.urlopen('https://barudwale20.000webhostapp.com/ha/buttonStatus.php')
    status = response.read()
    status=status.decode();
                        
                #except (urllib.HTTPError, e):
                #                        print ()

                #except (urllib.URLError, e):
                #                        print ()

                #print (status,status)        
    if (status=="0,0"):
        GPIO.setup(21, GPIO.OUT) 
        GPIO.output(21, GPIO.LOW)
        GPIO.setup(20, GPIO.OUT) 
        GPIO.output(20, GPIO.LOW)
    elif (status=='1,1'):
                    #print("passing")
        GPIO.setup(21, GPIO.OUT) 
        GPIO.output(21, GPIO.HIGH)
        GPIO.setup(20, GPIO.OUT) 
        GPIO.output(20, GPIO.HIGH)
    elif (status=='0,1'):
        print("passing")
        GPIO.setup(21, GPIO.OUT) 
        GPIO.output(21, GPIO.LOW)
        GPIO.setup(20, GPIO.OUT) 
        GPIO.output(20, GPIO.HIGH)
    elif (status=='1,0'):
        print("passing")
        GPIO.setup(21, GPIO.OUT) 
        GPIO.output(21, GPIO.HIGH)
        GPIO.setup(20, GPIO.OUT) 
        GPIO.output(20, GPIO.LOW)
