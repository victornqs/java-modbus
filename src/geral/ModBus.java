package geral;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.msg.WriteCoilResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

@SuppressWarnings("unused")
public class ModBus {
	 public static void main(String[] args) {
		    try {           

		         /**************************************/

		        //Leitura de dados ModBus TCP
		        int port = Modbus.DEFAULT_PORT;
		        String refe = "1";//HEX Address
		        int ref=Integer.parseInt(refe,16);//Hex to int          
		        int count = 20; //Numero de endere�os para leitura, ir� ler em sequencia por um contador.
		        int SlaveAddr=1;
		        String astr = "192.168.0.15";
		        InetAddress addr = InetAddress.getByName(astr);
		        TCPMasterConnection con = new TCPMasterConnection(addr); //A conex�o
		        ModbusTCPTransaction trans = null; //A transa��o(do pacote?)

		        //1.Preparando a requisi��o
		        /************************************/
		        ReadMultipleRegistersRequest Rreq = new ReadMultipleRegistersRequest(ref,count);
		        ReadMultipleRegistersResponse Rres = new ReadMultipleRegistersResponse();

		        Rreq.setUnitID(SlaveAddr); //Define o endere�o slave  
		        Rres.setUnitID(SlaveAddr); //Define o endere�o slave

		        //2. Abre a conex�o
		        con.setPort(port);
		        con.connect();
		        con.setTimeout(2500);

		        //3. Inicia a transa��o
		        trans = new ModbusTCPTransaction(con);
		        trans.setRetries(5);

		        trans.setReconnecting(true);
		        trans.setRequest(Rreq);
		        trans.execute();

		        /*Imprime a resposta*/
		        Rres = (ReadMultipleRegistersResponse) trans.getResponse();

		        System.out.println("Conectado a =  "+ astr + con.isConnected() + " / Iniciando registro " + Integer.toHexString(ref));
		        count=20;
		    for (int k=0;k<count;k++){
		    	int n = k+2; //Para sincronizar os indices
		       System.out.println("Valor lido no"+" �ndice: " + Rres.getRegisterValue(k));
		    }       

		    /****************Fechar Conex�o**************/
		        con.close();
		        System.out.println("\nConectado = " + con.isConnected());
		        System.exit(0);//edit Java bug error


		    } catch (Exception ex) {
		      ex.printStackTrace();
		    } 




		  }//main
}