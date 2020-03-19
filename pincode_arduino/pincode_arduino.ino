#include <Keypad.h>
#include <Wire.h>
#include <MD5.h>
#include <SPI.h>
#include <MFRC522.h>
#include <Crypto.h>
#include <SHA512.h>
#include <string.h>

#define HASH_SIZE 64
#define BLOCK_SIZE 128

#define SS_PIN 10
#define RST_PIN 9

long enteredCode = 0;
char enteredCodeArray[4] = {'0', '0', '0', '0'};


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
    for(int i = 0; i < 64; i++){
      byte b = value[i];
      hashedCode += String(b, HEX);
    }
    Serial.println();
    Serial.println(hashedCode);
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
int pinCount = 0;
int i = 0;
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
  Serial.begin(9600);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  SPI.begin();
  mfrc522.PCD_Init();

  
  Serial.print("Please present your card: ");
}
  
void loop(){

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
    Serial.println(passUID);
    cardPresented = true;

    Serial.print("Please enter pincode: ");
  } 
  
  //Button is pressed
  char customKey = customKeypad.getKey();
  if (customKey){


    //Register code
    if(customKey == '#'){
      Serial.println();
      for(int x = 0; x < i; x++){
        int currentNumber = enteredCodeArray[(i-x)-1] - '0';
        enteredCode += currentNumber * round(pow(10, x));
      }


      hashMaker(&sha512, &pinHash, 4);

      //Start check
      Serial.println("Authorizing...");
      sendEvent();
      finished = true;
      delay(3000);
    }

    
    if(customKey != '*' || customKey != 'D'){
    enteredCodeArray[i] = customKey;
    if(i >= 0){
      Serial.print('*');
    }
    }
    if(customKey == 'D'){   //char D is deleting the whole line
      if(i > 0){
        i = -1;
      }
      pinCount = 0;
      for(int x = 0; x < i; x++){
        enteredCodeArray[x] = 0;
      }
      enteredCode = 0;
      Serial.println();
    }
    i++;
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
  if(enteredCode != 0 && finished){
    passwordControl(receivedCode);
  }
}


//Compare hashed database pincode and hashed entered code
void passwordControl(String pincode){
    String checkCode(enteredCode);
    if(checkCode == pincode){
      //Serial.print(checkCode); Serial.print(" : "); Serial.println(pincode);
      Serial.println("Succes");
    }
    else{
      //Serial.print(checkCode); Serial.print(" : "); Serial.println(pincode);
      Serial.println("Access denied");
    }
    if(i != 0){
        i = -1;
      }
    checkDatabase = 0;
    enteredCode = 0;
    cardPresented = false;
    passUID = "00000000000";

    Serial.println();
    Serial.print("Please enter pincode: ");
}
