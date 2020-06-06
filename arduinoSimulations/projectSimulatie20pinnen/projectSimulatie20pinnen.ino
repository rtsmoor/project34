bool avoidLoop = false;
//serial communication variables
String stringIn;
bool received = false;
bool withdraw = false;
int moneyArray[4];

bool cardPresented = false;

void setup() {
   Serial.begin(115200);
   Serial.println("ready");
}

void loop() {
  delay(3000); //wait for gui to finish loading
 if(!avoidLoop)simulateLogin();
 if(received){
    inputHandler();
   }
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
   if(stringIn == "success"){

     
 
   }

  if(withdraw){
    //wait until more input comes
      if(stringIn == "fifty"){
        moneyArray[0]++;
Serial.println("ready");
      }
      if(stringIn == "twenty"){
        moneyArray[1]++;
  
      }
      if(stringIn == "ten"){
        moneyArray[2]++;
       
      }
      if(stringIn == "five"){
        moneyArray[3]++;
       
      }
    
  if(stringIn == "complete"){
    withdraw = false;
    
  }
  }
  
  if(stringIn == "withdraw"){
    
    withdraw = true;     
  }

//outputString(stringIn);
  
  received = false;
  stringIn = "";
}

void simulateLogin(){
  avoidLoop = true;
  cardPresented = true;
  String passNumber = "50 41 93 1D";
  delay(50);
  Serial.print("ArdPassNr_");
  Serial.println(passNumber);
  delay(1000); //assume the user will wait a little bit before the next input
  Serial.println("ArdSend_*");
  delay(1000);
  Serial.println("ready");
  delay(1000);
  Serial.println("ArdSend_*");
  delay(1000);
  Serial.println("ArdSend_*");
  delay(1000);
  Serial.println("ArdSend_*");
  delay(1000);
  //als de gebruiker op # drukt, verstuur dan eerst het gehaste wachtwoord, en daarna het # (of alleen het wachtwoord, nog even kijken wat handig is)
  Serial.println("ArdPinHashed_454fd8661718a7ed022925eec8227f2b");
  delay(1000);
  Serial.println("ArdSend_#");
  delay(3000);

  Serial.println("ArdSend_3"); //geld pin menu
  delay(3000);
  Serial.println("ArdSend_2"); //50 pinnen
  delay(3000);
  Serial.println("ArdSend_2"); // optie 1
  
}
