#include <SPI.h>
#include <MFRC522.h>
 
#define SS_PIN 10
#define RST_PIN 9

int piezo = 5

MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.
 
void setup() {
  Serial.begin(9600);   // Initiate a serial communication
  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Approximate your card to the reader...");
  Serial.println();

pinMode(piezo, OUTPUT);
}

void loop() 
{
  // Look for new cards
  delay(30);
  if (!mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Select one of the cards
  if (!mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
  //Show UID on serial monitor
  Serial.print("UID tag :");
  String content= "";
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
     Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
     Serial.print(mfrc522.uid.uidByte[i], HEX);
     content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
     content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  Serial.println();
  Serial.print("Message : "); 
  content.toUpperCase();
  if (content.substring(1) == "") //change here the UID of the card/cards that you want to give access ()
  {
    Serial.println("Authorized access");
    Serial.println();

    delay(3000);

  }
 
 else   {
    Serial.println(" Access denied");
    delay(3000);
    
  }
} 



/*void play() {
  int notelength;
  for (int playMusic = 0; playMusic < 29; playMusic++){
  if (a == 4) { //chorus
    // chorus
    notelength = beatlength * song1_chorus_rhythmn[b];
    if (song1_chorus_melody[b] > 0) {
      Serial.print(lyrics_chorus[c]);
      
      tone(piezo, song1_chorus_melody[b], notelength);
      c++;
    }
    b++;
    if (b >= sizeof(song1_chorus_melody) / sizeof(int)) {
      Serial.println("");
      a++;
      b = 0;
      c = 0;
    }
  }
  delay(notelength); // necessary because piezo is on independent timer
  noTone(piezo);
  delay(notelength * beatseparationconstant); // create separation between notes
  }
} */
