package geral;

import java.net.*;
import java.io.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleInputRegister;
import net.wimpi.modbus.procimg.SimpleRegister;
import net.wimpi.modbus.util.*;
import net.wimpi.modbus.facade.*;
import net.wimpi.modbus.msg.ModbusRequest;

public class Escrita {

	@SuppressWarnings("null")
	public static void main (String args[]){
	
		int port = Modbus.DEFAULT_PORT;
		int ref = 1;
		
		SimpleRegister sr =null;
		sr = new SimpleRegister(1);
		
		try {
			String astr = "192.168.0.15";
			InetAddress addr = InetAddress.getByName(astr);
			TCPMasterConnection con = new TCPMasterConnection(addr); //the connection
			ModbusTCPTransaction trans = null; //the transaction
			
			WriteSingleRegisterRequest Wreq = new WriteSingleRegisterRequest(ref,sr);
			WriteSingleRegisterResponse Wres = new WriteSingleRegisterResponse();
			
			
			//2. Open the connection
			con.setPort(port);
			con.connect();
			System.out.println("Connected... " + port + " " + ref);
			
			trans = new ModbusTCPTransaction(con);
			trans.setRequest(Wreq);
			
			trans.execute();
			Wres = (WriteSingleRegisterResponse) trans.getResponse();
			System.out.println("The value is: " + Wres.getHexMessage());
			
			
			con.close();
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Finished");
	}
}