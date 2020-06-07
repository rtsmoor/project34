#include <Keypad.h>
#include <SPI.h>
#include <MFRC522.h>
#include <SHA512.h>
#include <Stepper.h>
#include "Adafruit_Thermal.h"
#include "SoftwareSerial.h"

#define HASH_SIZE 32
#define BLOCK_SIZE 128

#define SS_PIN 9
#define RST_PIN 8
#define TX_PIN 6
#define RX_PIN 5

SoftwareSerial mySerial(RX_PIN, TX_PIN); 
Adafruit_Thermal printer(&mySerial);

long enteredCode = 0;
char enteredCodeArray[4] = {'0', '0', '0', '0'};
int amount;
int cashCounter[4];
const int stepsPerRevolution = 200;

Stepper myStepper1(stepsPerRevolution, 49, 47, 48, 46);
Stepper myStepper2(stepsPerRevolution, 42, 43, 44, 45);
Stepper myStepper3(stepsPerRevolution, 41, 39, 40, 38);
Stepper myStepper4(stepsPerRevolution, 34, 36, 35, 37);

//serial communication variables
String stringIn;
bool received = false;
bool withdraw = false;

//Hashing

struct hashVector
{
    const char *name;
    const char *data;
    uint8_t hash[HASH_SIZE];
};



hashVector const pinHash = {"SHA-512 #1", enteredCodeArray};

SHA512 sha512;

byte buffer[BLOCK_SIZE + 2];
String hashedCode = "";

void hashMaker(Hash *hash, const struct hashVector *updateHash, size_t inc)
{
    size_t size = strlen(updateHash->data);
    size_t posn, len;
    uint8_t value[HASH_SIZE];

    hash->reset();
    for (posn = 0; posn < size; posn += inc) {
        len = size - posn;
        if (len > inc)
            len = inc;
        hash->update(updateHash->data + posn, len);
    }
    hash->finalize(value, sizeof(value));
    for(int i = 0; i < 32; i++){
      byte b = value[i];
      hashedCode += String(b, HEX);
    }
    //Serial.println(hashedCode);
}

//End of hashing






MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.

const byte ROWS = 4; //four rows
const byte COLS = 4; //four columns
//define the cymbols on the buttons of the keypads
char hexaKeys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};
bool cardPresented = false;
bool finished = false;
bool cardBlocked = false;
String cardBlockedCode = "11111111111111111111111111111111";
int pinCount = 0;
int keyCounter = 0;
int checkDatabase = 0;
char *md5str;
String passUID= "";
byte colPins[COLS] = {29, 28, 24, 26}; //connect to the row pinouts of the keypad
byte rowPins[ROWS] = {33, 31, 32, 30}; //connect to the column pinouts of the keypad


//initialize an instance of class NewKeypad
Keypad customKeypad = Keypad( makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); 

void setup(){ 
  mySerial.begin(9600);
  Serial.begin(115200);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  SPI.begin();
  mfrc522.PCD_Init();
  Serial.println("ready");
  pinMode(2, OUTPUT);

  
 // Serial.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlease present your card: ");
}
  
void loop(){
   if(received){
    inputHandler();
   }
    // Look for new cards
   if(cardPresented == false){
    if ( ! mfrc522.PICC_IsNewCardPresent()) 
    {
      return;
    }
    // Select one of the cards
    if ( ! mfrc522.PICC_ReadCardSerial()) 
    {
      return;
    }
    //Show UID on serial monitor
    String content= "";
    byte letter;
    for (byte i = 0; i < mfrc522.uid.size; i++) 
    {
       /*Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
       Serial.print(mfrc522.uid.uidByte[i], HEX);*/
       content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
       content.concat(String(mfrc522.uid.uidByte[i], HEX));
    }
  
    content.toUpperCase();
    passUID = content.substring(1);
    Serial.print("ArdPassNr_");
    Serial.println(passUID);
    cardPresented = true;

    if(cardBlocked){
      setup();
      exit;
      return;
    }
    if(cardPresented){
    delay(2000);
    Serial.println("enterPin");
    }
  } 
  
  //Button is pressed
  char customKey = customKeypad.getKey();
  if (customKey){


    //Register code
    if(customKey == '#'){
      for(int x = 0; x < keyCounter; x++){
        int currentNumber = enteredCodeArray[(keyCounter-x)-1] - '0';
        enteredCode += currentNumber * round(pow(10, x));
      }
      
      hashMaker(&sha512, &pinHash, 4);

      
      for(int i = 0; i < 4; i++){
              Serial.println(enteredCodeArray[i]);
              enteredCodeArray[i] = '0';
      }

      String checkCode;
      for(int i = 0; i < 32; i++){
      checkCode += (char)hashedCode.charAt(i);
      }
        Serial.println(enteredCode);
        Serial.println(hashedCode);
        Serial.print("ArdPinHashed_");
        Serial.println(checkCode);
        Serial.println("ArdSend_#");


      //Start check
      hashedCode = "";
      int delaying = 0;
      if(keyCounter != 0){
        keyCounter = -1;
      }
      checkDatabase = 0;
      enteredCode = 0;
      Serial.println("authorizing");
      finished = true;
      delay(3000);
    }

    
    if(customKey != '*' && customKey != 'D' && customKey != '#'){
    enteredCodeArray[keyCounter] = customKey;
    if(keyCounter >= 0){
      Serial.println("ArdSend_*");
      }
    }
    
    if(customKey == 'D'){   //char D is deleting the whole line
      if(keyCounter > 0){
        keyCounter = -1;
        Serial.println("ArdSend_D");
      }
      pinCount = 0;
      for(int x = 0; x < keyCounter; x++){
        enteredCodeArray[x] = 0;
      }
      enteredCode = 0;
    }
    keyCounter++;
    enteredCode = 0;
  }
}

void menuInput(){
  bool exitProgram = true;
  while(exitProgram){
  if(Serial.available()) {
    Serial.println("serial event");
  if (Serial.available()) {
    Serial.println("serial event 2");
//    while (true) {
      stringIn = Serial.readString();
//      stringIn += inChar;

//      if (inChar == '\n') break;
//    }
   if(stringIn != NULL) received = true;
  }
  }
  char customKey = customKeypad.getKey();
  switch(customKey){
    case 'B':
      exitProgram = false;
      Serial.println("ArdSend_B");
    break;
    case 'A':
      Serial.println("ArdSend_A");
    break;
    case 'C':
      Serial.println("ArdSend_C");
    break;
      case '1':
      Serial.println("ArdSend_1");
    break;
      case '2':
      Serial.println("ArdSend_2");
    break;
      case '3':
      Serial.println("ArdSend_3");
    break;
      case '4':
      Serial.println("ArdSend_4");
    break;
      case '5':
      Serial.println("ArdSend_5");
    break;
    }
    if(received) {Serial.println("receiving");inputHandler();}
  }
}

void resetting(){
  int delaying = 0;
  if(keyCounter != 0){
        keyCounter = -1;
      }
    checkDatabase = 0;
    enteredCode = 0;
    cardPresented = false;
    passUID = "00000000000";
    cardBlocked = false;
    hashedCode = "";
}

//serial communication code
void serialEvent() { // To check if there is any data on the serial line
  Serial.println("serial event");
  if (Serial.available()) {
    Serial.println("serial event 2");
//    while (true) {
      stringIn = Serial.readString();
//      stringIn += inChar;

//      if (inChar == '\n') break;
//    }
   if(stringIn != NULL) received = true;
  }
}

//handles serial input
void inputHandler() {
//  stringOut = "ERROR: No (correct) input";
  if(stringIn == "abort"){
    Serial.println("reset complete");
    resetting();
    for(int i = 0; i < 4; i++) cashCounter[i] = 0;
  }

  if(stringIn == "printBon"){
    printReceipt();
    Serial.println("Print bon");
  }


  if(withdraw){
    //wait until more input comes
      if(stringIn == "fifty"){
        cashCounter[0]++;
        
      }
      if(stringIn == "twenty"){
        cashCounter[1]++;
      }
      if(stringIn == "ten"){
        cashCounter[2]++;
         
      }
      if(stringIn == "five"){
        cashCounter[3]++;
        
      }
    
  if(stringIn == "complete"){
    dispense();
    withdraw = false;
    Serial.println("received");
  }
  }
  
  if(stringIn == "withdraw"){
    Serial.println("sendTransaction");
    withdraw = true;     
  }

  if(stringIn == "success"){
    Serial.println("keypad input");
    stringIn = "";
    received = false;
    menuInput();
  }

  

//outputString(stringIn);
  
  received = false;
  stringIn = "";
}

void dispense(){
  Stepper steppers[4] = {myStepper4, myStepper3, myStepper2, myStepper1};
  for(int i = 0; i < 4; i++){
    steppers[i].setSpeed(60);
    for(int x = 0; x < cashCounter[i]; x++){
      int stepperSpeed;
      if(i == 2){
        stepperSpeed = -1750;
      }
      else{
        stepperSpeed = 1750;
      }
      steppers[i].step(stepperSpeed);
    }
  }
  Serial.println("ArdSend_finish");
  delay(100);
  Serial.println("ArdSend_finish");
}


void printReceipt() {
  printer.begin();
  printer.justify('C');
  printer.setSize('M');
  printer.feed(2);
  printer.println(F("---------------------------- \n"));
  printer.println(F("Welcome to ItsFreeStonksEstate\n"));
  printCashCounter();
  printer.justify('C');
  printer.println(F("Thank you for your withdrawal"));
  printer.println(F("---------------------------- \n"));
  printer.feed(6);
  printer.setDefault(); // Restore printer to defaults
}

void resetCashCounter(){
  for(int i = 0;i<4;i++){
    cashCounter[i] = 0;
  }
}
void amountCounter(){
  amount = ((cashCounter[0]*50) + (cashCounter[1]*20) + (cashCounter[2]*10) + (cashCounter[3]*5));
}

void printCashCounter(){
    amountCounter();
    printer.setSize('S');
    printer.justify('L');
    printer.print(cashCounter[0]);
    printer.println(F(" x 50Stanks\n"));
    printer.print(cashCounter[1]);
    printer.println(F(" x 20Stanks\n"));
    printer.print(cashCounter[2]);
    printer.println(F(" x 10Stanks\n"));
    printer.print(cashCounter[3]);
    printer.println(F(" x 5Stanks\n"));
    printer.print(F("Total :"));
    printer.println(amount);
    printer.setSize('M');
    amount = 0;
    resetCashCounter();
}
