#include <WiFi.h>
#include <HCSR04.h>
#include <FirebaseESP32.h>


#include <LiquidCrystal_I2C.h>
#include <Stepper.h>

#define FIREBASE_HOST "https://fufusdproject-default-rtdb.asia-southeast1.firebasedatabase.app/"
#define FIREBASE_AUTH "941oeN7zcT4342EBUnsr3gxAHGzm2D3WMJuqVLhV"
#define WIFI_SSID "azmiMdGhazali@unifi"
#define WIFI_PASSWORD "az630813"

FirebaseData firebaseData;

String path = "/ESP32";

int OldDistance;
int NewDistance;

int stateMotion = LOW;
int valMotion = 0;

LiquidCrystal_I2C lcd(0x27,16,2);  // set the LCD address to 0x27 for a 16 chars and 2 line display
const int stepsPerRevolution = 2048;
const int buttonPin = 27; // pin button

int buttonState = 0;
int buttonDeviceState = 0;

Stepper myStepper = Stepper(stepsPerRevolution, 26, 33 , 25, 32);//pin esp

//ultrasonic sensor
//#define echoPin 18
//#define trigPin 19
UltraSonicDistanceSensor distanceSensor(19,18);//initialisation class HCSR04 (trig pin , echo pin)

long duration;
int distance;
void setup() {
  // put your setup code here, to run once:

  lcd.init();                      // initialize the lcd 
  // Print a message to the LCD.
  lcd.backlight();
  lcd.setCursor(0,0);
  lcd.print("Hello, Slaves");
  lcd.setCursor(0,1);
  lcd.print("FuFu Pet Feeder");

  //stepper motor
  myStepper.setSpeed(15);
  Serial.begin(9600);

  //button
  pinMode(buttonPin,INPUT);

  //ultrasonic

  //pinMode(trigPin, OUTPUT); // Sets the trigPin as an OUTPUT
  //pinMode(echoPin, INPUT); // Sets the echoPin as an INPUT
  Serial.println("Ultrasonic Sensor HC-SR04 Test"); // print some text in Serial Monitor
  Serial.println("with Arduino UNO R3");

  initWifi();
  OldDistance = distanceSensor.measureDistanceCm();

}

void loop() {
  // put your main code here, to run repeatedly:
  // Step one revolution in one direction:
  //pinSerial.println("clockwise");
  //myStepper.step(-stepsPerRevolution);
  buttonState = digitalRead(buttonPin);
  
  if(Firebase.getInt(firebaseData, path + "/motor")){
    buttonDeviceState = firebaseData.intData();
   
  }
  //Serial.print(Firebase.getInt(firebaseData, path + "/motor"));
  Serial.println("test");
  
  //StepRevolution for time
  //motor
  if(buttonState == HIGH || buttonDeviceState == HIGH){
    
    myStepper.step(-stepsPerRevolution);
  }
  
  //UltraSonicSensor
  NewDistance = distanceSensor.measureDistanceCm();
  //Serial.println(NewDistance);
  if(NewDistance != OldDistance){
    Firebase.setInt(firebaseData, path + "/distance", NewDistance);
    OldDistance = NewDistance; 
    
  }
 delay(1000);
  
  // Step one revolution in the other direction:
}

void initWifi(){
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
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

void errorQueueCallback (QueueInfo queueinfo){

  if (queueinfo.isQueueFull())
  {
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
