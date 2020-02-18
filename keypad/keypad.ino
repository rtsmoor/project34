#include <Keypad.h>

const byte ROWS = 4; //four rows
const byte COLS = 4; //four columns
char keys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};
String pass="";
String code="";
byte colPins[COLS] = {5, 4, 11, 2}; //connect to the row pinouts of the keypad
byte rowPins[ROWS] = {9, 8, 7, 6}; //connect to the column pinouts of the keypad

Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );
int led= 12;
int shock = 13;
int buzzer = 3;
int song = 0;
void setup(){
   Serial.begin(9600);
   pinMode(led, OUTPUT);
   pinMode(shock, OUTPUT);
   pinMode(buzzer, OUTPUT);
}
void loop()
{
 char key = keypad.getKey();
 if (key){;
    code += key;
     Serial.println(code);
     if (key == # && code == pass){
              Serial.println("correct");
              code = "";
    }
      else{
      Serial.println("incorrect");
    }
  }
}
