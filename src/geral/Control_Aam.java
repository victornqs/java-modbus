package geral;

import java.io.*;
import java.lang.*;
import java.net.InetAddress;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

public class Control_Aam {
   
    public static void main(String args[]){
                try {                        
                        /* The important instances of the class*/
                        TCPMasterConnection con = null;  //the connection
                        ModbusTCPTransaction trans = null;  //the transaction
                        ReadInputRegistersRequest rreq = null;  //the read request
                        ReadInputRegistersResponse rres = null;  //the read response
                        WriteCoilRequest req = null;  //the write request

                        /* Variables for storing the parameters */
                        InetAddress addr = null;  // the slave's address
                        int port = 502;  // the default port
                        int coil = 167;  // one of the coils (D0 1 for this address) to switch ON/OFF
                                                                                                        
                        //1. Setup the parameters
                        addr = InetAddress.getByName("192.168.0.15");  // ** The address assigned to the module **

                        //2. Open the connection
                        con = new TCPMasterConnection(addr);
                        con.setPort(port);
                        con.connect();
                        
                        
//~~~~~~~~~~~~~~~~~~~~ The faulty Read Request ~~~~~~~~~~~~~~~~~~~~
                        //3r. Prepare the READ request
                        int k = 167;  // The address of the  "Vin 0" channel (correct??)
                        rreq = new ReadInputRegistersRequest(k, 1);  // Reading 8 bytes (of what??)
        
                        //4r. Prepare the READ transaction
                        trans = new ModbusTCPTransaction(con);
                        trans.setRequest(rreq);
        
                        //5r. Execute the READ transaction
                        trans.execute();
                        rres = (ReadInputRegistersResponse) trans.getResponse();
                        System.out.println("Hex Value of register " + "= "+rres.getHexMessage());
                        
                        
//~~~~~~~~~~~~~~~~~~~~ The functional Write Request ~~~~~~~~~~~~~~~~~~~~
                        //3w. Prepare the request
                        req = new WriteCoilRequest(coil, true);  // Switching ON the "DO 1" (= address 17)

                        //4w. Prepare the transaction
                        trans = new ModbusTCPTransaction(con);
                        trans.setRequest(req);

                        //5w. Execute the transaction repeat times
                        trans.execute();        
                        
                        //6. Close the connection
                        con.close();

                }
                catch (Exception ex) {
                        System.out.println("Error");
                        ex.printStackTrace();
                }           
    }
}