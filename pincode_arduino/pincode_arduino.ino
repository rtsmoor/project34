#include <Keypad.h>
#include <Wire.h>

const byte ROWS = 4; //four rows
const byte COLS = 4; //four columns
//define the cymbols on the buttons of the keypads
char hexaKeys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};
bool pinCheck = false;
int pinCount = 0;
int i = 0;
int checkDatabase = 0;
long enteredCode = 0;
char enteredCodeArray[4] = {'0', '0', '0', '0'};
char pinCode[4] = {'2','4','5','1'};
byte rowPins[ROWS] = {9,8,7,6}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {5,4,3,2}; //connect to the column pinouts of the keypad

//initialize an instance of class NewKeypad
Keypad customKeypad = Keypad( makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); 

void setup(){
  Wire.begin(8);  //0x08 =8
  Wire.onReceive(receiveEvent);
  Wire.onRequest(sendEvent);
  Serial.begin(9600);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  Serial.print("Please enter pincode: ");
}
  
void loop(){
  char customKey = customKeypad.getKey();
  digitalWrite(10, LOW);
  digitalWrite(11, LOW);
  if (customKey){
    
    if(customKey == '#'){
      Serial.println();
      for(int x = 0; x < i; x++){
        int currentNumber = enteredCodeArray[(i-x)-1] - '0';
        enteredCode += currentNumber * round(pow(10, x));
      }
      checkDatabase = 1;
      Serial.println("Authorizing...");
      sendEvent();
      
      delay(3000);
    }

    
    if(customKey != '*' || customKey != 'D'){
    enteredCodeArray[i] = customKey;
    if(i >= 0){
      Serial.print("*");
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

void sendEvent(){
  Wire.write(checkDatabase);
}

void receiveEvent(int howMany){
    byte highbyte = Wire.read();
    byte lowbyte = Wire.read();
    int receivedCode = (highbyte<<8)+lowbyte;
    passwordControl(receivedCode);
}

void passwordControl(int pincode){
  if(enteredCode != 0){
    if(enteredCode == pincode){
      Serial.println("Succes");
      checkDatabase = 0;
    }
    else{
      Serial.println("Access denied");
      Serial.println();
      Serial.print("Please enter pincode: ");
    }
    if(i != 0){
        i = -1;
      }
  }
}
