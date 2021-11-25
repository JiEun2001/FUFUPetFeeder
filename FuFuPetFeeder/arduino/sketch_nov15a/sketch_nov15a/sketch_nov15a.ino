#include <WiFi.h>

#include <HCSR04.h>

#include <FirebaseESP32.h>

#include "time.h"

#include <ArduinoJson.h>

#include "SoundData.h"

#include <XT_DAC_Audio.h>

#include <LiquidCrystal_I2C.h>

#include <Stepper.h>

#define FIREBASE_HOST "https://fufusdproject-default-rtdb.asia-southeast1.firebasedatabase.app/"
#define FIREBASE_AUTH "941oeN7zcT4342EBUnsr3gxAHGzm2D3WMJuqVLhV"
#define WIFI_SSID "aznan99"
#define WIFI_PASSWORD "81437658"

XT_Wav_Class Sound(sample);

XT_DAC_Audio_Class DacAudio(25, 0);

uint32_t DemoCounter = 0;

FirebaseData firebaseData;

String path = "/ESP32";

int OldDistance;
int NewDistance;
int tempbtnstate = 0;

//button for auto btn data
String time1;
String time2;
String time3;
String time4;
String time5;

//initialized unsigned integer for millis
uint32_t currentmillis;
uint32_t previousmillis = 0;

int playSoundTime = 0;
int oldState = 0;
int playSound = 0;

int stateMotion = LOW;
int valMotion = 0;

LiquidCrystal_I2C lcd(0x27, 16, 2); // set the LCD address to 0x27 for a 16 chars and 2 line display
const int stepsPerRevolution = 2048;
const int buttonPin = 27; // pin button

int buttonState = 0;
int buttonDeviceState = 0;

Stepper myStepper = Stepper(stepsPerRevolution, 26, 33, 12, 32); //pin esp

//ultrasonic sensor
//#define echoPin 3
//#define trigPin 19
UltraSonicDistanceSensor distanceSensor(19,3); //initialisation class HCSR04 (trig pin , echo pin)

long duration;
int distance;

//time
const char * ntpServer = "pool.ntp.org";
const long gmtOffset_sec = 28800;
const int daylightOffset_sec = 0;

int second;
int minute;
void printLocalTime() {
  struct tm timeinfo;
  if (!getLocalTime( & timeinfo)) {
    Serial.println("Failed to obtain time");
    return;
  }
  Serial.println( & timeinfo, "%A, %B %d %Y %H:%M:%S");
}
void errorQueueCallback(QueueInfo queueinfo) {

  if (queueinfo.isQueueFull()) {
    Serial.println("Queue is full");
  }

  Serial.print("Remaining queues: ");
  Serial.println(queueinfo.totalQueues());

  Serial.print("Being processed queue ID: ");
  Serial.println(queueinfo.currentQueueID());

  Serial.print("Data type:");
  Serial.println(queueinfo.dataType());

  Serial.print("Method: ");
  Serial.println(queueinfo.firebaseMethod());

  Serial.print("Path: ");
  Serial.println(queueinfo.dataPath());

  Serial.println();
}
void initWifi() {
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi please wait");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  Firebase.setMaxErrorQueue(firebaseData, 10);
  Firebase.beginAutoRunErrorQueue(firebaseData, errorQueueCallback);

  //Set database read timeout to 1 minute (max 15 minutes)
  Firebase.setReadTimeout(firebaseData, 1000 * 60);
  //tiny, small, medium, large and unlimited.
  //Size and its write timeout e.g. tiny (1s), small (10s), medium (30s) and large (60s).
  Firebase.setwriteSizeLimit(firebaseData, "tiny");
}
void playAudio(void * parameter){
  while(true){
  DacAudio.FillBuffer();
  if (Sound.Playing == false)
    vTaskDelete(NULL);
}
}
void setup() {
  // put your setup code here, to run once:

  lcd.init(); // initialize the lcd 
  // Print a message to the LCD.
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print("Hello,Welcome to FUFU");
  lcd.setCursor(0, 1);
  lcd.print("FuFu Pet Feeder");

  //stepper motor
  myStepper.setSpeed(15);
  Serial.begin(9600);

  //button
  pinMode(buttonPin, INPUT);

  //ultrasonic

  //pinMode(trigPin, OUTPUT); // Sets the trigPin as an OUTPUT
  //pinMode(echoPin, INPUT); // Sets the echoPin as an INPUT
  Serial.println("Ultrasonic Sensor HC-SR04 Test"); // print some text in Serial Monitor
  Serial.println("with Arduino UNO R3");

  //initwifi here 
  initWifi();
  OldDistance = distanceSensor.measureDistanceCm();
  //

  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer);
  printLocalTime();

}

void loop() {

  struct tm timeinfo;
  if (!getLocalTime( & timeinfo)) {
    Serial.println("Failed to obtain time");
    return;
  }
  if (second != timeinfo.tm_sec) {
    second = timeinfo.tm_sec;
    printLocalTime();
    time_t now;
    time( & now);
    Serial.println(now);

    buttonState = digitalRead(buttonPin);

 if (Firebase.getInt(firebaseData, path + "/motor")) {
      buttonDeviceState = firebaseData.intData();
      //check state
      if(oldState != buttonDeviceState){
        oldState = buttonDeviceState;
        if(oldState == 1){
          playSound = 0;
        }else{
          playSound = 1;
        }
      }
    }
    //play sound after state changed.
    if(playSound == 1){
      DacAudio.FillBuffer();
      if (Sound.Playing == false)
        DacAudio.Play( & Sound);
      playSound = 0;
      xTaskCreate(
        playAudio,    // Function that should be called
        "Play Audio",  // Name of the task (for debugging)
        1000,            // Stack size (bytes)
        NULL,            // Parameter to pass
        1,               // Task priority
        NULL             // Task handle
      );
    }
  }
  //Serial.print(Firebase.getInt(firebaseData, path + "/motor"));
  Serial.println("test");

  //StepRevolution for time
  //motor
  if (buttonState == HIGH || buttonDeviceState == HIGH) {

    myStepper.step(-stepsPerRevolution);
  }

  //UltraSonicSensor
  NewDistance = distanceSensor.measureDistanceCm();
  //Serial.println(NewDistance);
  if (NewDistance != OldDistance) {
    Firebase.setIntAsync(firebaseData, path + "/distance", NewDistance);
    OldDistance = NewDistance;

  }

  if (minute != timeinfo.tm_min) {
    time_t now;
    time( & now);
    Serial.println(now);
    minute = timeinfo.tm_min;
    Firebase.setStringAsync(firebaseData, path + "/History" + "/" + now + "/historydistance", String(NewDistance));
  }

  // set auto 

  /////
  String time24hour;
  int time12hour;
  String timeampm;

  if (timeinfo.tm_hour > 12) {
    time12hour = timeinfo.tm_hour - 12;
    timeampm = "PM";
  } else if (timeinfo.tm_hour == 0) {
    time12hour = timeinfo.tm_hour + 12;
    timeampm = "AM";
  } else {
    time12hour = timeinfo.tm_hour;
    timeampm = "AM";
  }
  // hh:mm
 char currentTimeinFormat[80];
sprintf(currentTimeinFormat, "%02d:%02d", time12hour, timeinfo.tm_min);
String timein12hour = String(currentTimeinFormat) + " " + timeampm;

if(Firebase.getString(firebaseData,"/Masa/time1")){
      time1 = firebaseData.stringData();
      }
    if(Firebase.getString(firebaseData,"/Masa/time2")){
      time2 = firebaseData.stringData();
      }
    if(Firebase.getString(firebaseData,"/Masa/time3")){
      time3 = firebaseData.stringData();
      }
    if(Firebase.getString(firebaseData,"/Masa/time4")){
      time4 = firebaseData.stringData();
      }
    if(Firebase.getString(firebaseData,"/Masa/time5")){
      time5 = firebaseData.stringData();
      }

    if(time1 == timein12hour || time2 == timein12hour ||time3 == timein12hour ||time4 == timein12hour ||time5 == timein12hour){
    buttonDeviceState  = HIGH;
    oldState = HIGH;
    }else{
      buttonDeviceState = LOW;
      if(oldState != buttonDeviceState){
        oldState = buttonDeviceState;
        if(oldState == 1){
          playSound = 0;
        }else{
          playSound = 1;
        }
      }
    }
}

// put your main code here, to run repeatedly:
// Step one revolution in one direction:
//pinSerial.println("clockwise");
//myStepper.step(-stepsPerRevolution);

// Step one revolution in the other direction:
