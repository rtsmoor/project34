#include <Keypad.h>
#include <Wire.h>
#include <SPI.h>
#include <MFRC522.h>
#include <SHA512.h>


#define HASH_SIZE 32
#define BLOCK_SIZE 128

#define SS_PIN 10
#define RST_PIN 9

long enteredCode = 0;
char enteredCodeArray[4] = {'0', '0', '0', '0'};

//serial communication variables
String stringIn;
bool received = false;
bool withdraw = false;
int moneyArray[4];


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
byte rowPins[ROWS] = {8,7,6,5}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {4,3,2,1}; //connect to the column pinouts of the keypad

//initialize an instance of class NewKeypad
Keypad customKeypad = Keypad( makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); 

void setup(){ 
  Wire.begin(8);  //0x08 =8
  Wire.onReceive(receiveEvent);
  Wire.onRequest(sendEvent);
  Serial.begin(115200);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  SPI.begin();
  mfrc522.PCD_Init();
  Serial.println("ready");

  
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
    Serial.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
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

      //Start check
      Serial.println("authorizing");
      sendEvent();
      finished = true;
      delay(3000);
    }

    
    if(customKey != '*' || customKey != 'D'){
    enteredCodeArray[keyCounter] = customKey;
    if(keyCounter >= 0){
      Serial.println('*');
      }
    }
    
    if(customKey == 'D'){   //char D is deleting the whole line
      if(keyCounter > 0){
        keyCounter = -1;
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


//Send status
void sendEvent(){
  byte response [11];
  if(passUID != 0 || passUID != ""){
    for(byte i = 0; i < 11; i++){
      response[i] = (byte)passUID.charAt(i);
    }
    Wire.write(response, sizeof(response)); 
  }
}


//Receive hashed pincode from database via ESP8266
void receiveEvent(int howMany){
  String receivedCode = "";
  while(Wire.available()){
    char b = Wire.read();
    receivedCode += b;
    
  }
  if(receivedCode.equals(cardBlockedCode)){
    Serial.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPass blocked, contact costumer support.");
    cardBlocked = true;
    resetting();
  }
  if(enteredCode != 0 && finished){
    passwordControl(receivedCode);
  }
}


//Compare hashed database pincode and hashed entered code
void passwordControl(String pincode){
    String checkCode;
    for(int i = 0; i < 32; i++){
      checkCode += (char)hashedCode.charAt(i);
    }
    if(checkCode == pincode){
      //Serial.print(checkCode); Serial.print(" : "); Serial.println(pincode);
      Serial.println("success"); // hier serialprint van dat ie goed is 
      for(int i = 0; i < 200; i++){
        delay(50);
      }
    }
    else{
      Serial.print(checkCode); Serial.print(" : "); Serial.println(pincode);
      Serial.println("denied"); // hier serialprint van dat er nog x aantal pogingen zijn en dat die fout is
    }
    resetting();

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
    sendEvent();
    setup();
}

//serial communication code
void serialEvent() { // To check if there is any data on the serial line
  if (Serial.available()) {
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
    for(int i = 0; i < 4; i++) moneyArray[i] = 0;
        //code voor het 'resetten' van ale gegevens
        
  } 

  if(withdraw){
    //wait until more input comes
      if(stringIn == "fifty"){
        moneyArray[0]++;
        Serial.println("received_fifty");
      }
      if(stringIn == "twenty"){
        moneyArray[1]++;
        Serial.println("received_twenty");
      }
      if(stringIn == "ten"){
        moneyArray[2]++;
        Serial.println("received_ten");
      }
      if(stringIn == "five"){
        moneyArray[3]++;
        Serial.println("received_five");
      }
      
    //else {
      
     // int amount = Serial.parseInt();
     // Serial.println(amount);
     // Serial.println("sendMore");
      
//      printMoneys(amount);
      //TODO print moneys
  //}
    
  if(stringIn == "complete"){
    withdraw = false;
    Serial.println("received");
  }
  }
  
  if(stringIn == "withdraw"){
    Serial.println("sendTransaction");
    withdraw = true;     
  }

//outputString(stringIn);
  
  received = false;
  stringIn = "";
}
