#include <ESP8266WiFi.h>
#include <Wire.h>
#include <MySQL_Connection.h>
#include <MySQL_Cursor.h>

int checkDatabase = 0;

IPAddress server_addr(37,59,55,185);  // IP of the MySQL *server* here
char user[] = "gooHxZWiSx";              // MySQL user login username
char password[] = "r2Gf2810Tu";        // MySQL user login password

// Sample query
char query[] = "SELECT pincode FROM gooHxZWiSx.login WHERE pass_number = '77456882'";

// WiFi card example
char ssid[] = "VRV9517DA56DB";         // your SSID
char pass[] = "Sarpsborg08";     // your SSID Password


WiFiClient client;                 // Use this for WiFi instead of EthernetClient
MySQL_Connection conn(&client);
MySQL_Cursor cur = MySQL_Cursor(&conn);

void setup() {
  Wire.begin(4, 5);
  Serial.begin(115200);
  while (!Serial); // wait for serial port to connect
  WiFi.begin(ssid, pass);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  // print out info about the connection:
  Serial.println("\nConnected to network");
  Serial.print("My IP address is: ");
  Serial.println(WiFi.localIP());
  if (conn.connect(server_addr, 3306, user, password)) {
    delay(1000);
  }
  else{
    Serial.println("Connection failed.");
  }
}



void loop() {
  Wire.requestFrom(8,1);
  checkDatabase = Wire.read();
  Serial.println(checkDatabase);
  if(checkDatabase == 1){
    masterRequest();
  }
}

void masterRequest(){
  row_values *row = NULL;
  byte response[32];
  String pincode;

  delay(1000);

  // Initiate the query class instance
  MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
  // Execute the query
  cur_mem->execute(query);
  // Fetch the columns (required) but we don't use them.
  column_names *columns = cur_mem->get_columns();

  // Read the row (we are only expecting the one)
  do {
    row = cur_mem->get_next_row();
    if (row != NULL) {
      pincode = row->values[0];
    }
    Wire.beginTransmission(8);
    for (byte i=0;i<32;i++) 
      {
        response[i] = (byte)pincode.charAt(i);
      }
    Wire.write(response, sizeof(response));
    Wire.endTransmission();
  } while (row != NULL);
  // Deleting the cursor also frees up memory used
  delete cur_mem;

  // Show the result
  delay(500);
  checkDatabase = 0;
}
