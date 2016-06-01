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
		        int count = 20; //Numero de endereços para leitura, irá ler em sequencia por um contador.
		        int SlaveAddr=1;
		        String astr = "192.168.0.15";
		        InetAddress addr = InetAddress.getByName(astr);
		        TCPMasterConnection con = new TCPMasterConnection(addr); //A conexão
		        ModbusTCPTransaction trans = null; //A transação(do pacote?)

		        //1.Preparando a requisição
		        /************************************/
		        ReadMultipleRegistersRequest Rreq = new ReadMultipleRegistersRequest(ref,count);
		        ReadMultipleRegistersResponse Rres = new ReadMultipleRegistersResponse();

		        Rreq.setUnitID(SlaveAddr); //Define o endereço slave  
		        Rres.setUnitID(SlaveAddr); //Define o endereço slave

		        //2. Abre a conexão
		        con.setPort(port);
		        con.connect();
		        con.setTimeout(2500);

		        //3. Inicia a transação
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
		       System.out.println("Valor lido no"+" índice: " + Rres.getRegisterValue(k));
		    }       

		    /****************Fechar Conexão**************/
		        con.close();
		        System.out.println("\nConectado = " + con.isConnected());
		        System.exit(0);//edit Java bug error


		    } catch (Exception ex) {
		      ex.printStackTrace();
		    } 




		  }//main
}