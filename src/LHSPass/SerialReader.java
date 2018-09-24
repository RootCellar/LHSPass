import com.fazecast.jSerialComm.*;

public class SerialReader implements SerialPortPacketListener
{

    boolean working = false;

    int PORT_USE = 2;

    UserHandler handler;

    SerialPort[] ports;

    public SerialReader( UserHandler u ) {

        handler = u;
        
        out("Starting up serial reader...");

        ports = SerialPort.getCommPorts();
        
        out("Trying " + ports.length + " ports");

        for(int i=0; i<ports.length; i++) {
            
            out("Found " + ports[i].getDescriptivePortName() );
            
            if(!ports[i].isOpen()) ports[i].openPort();

            if(ports[i].isOpen()) {
                out( "Opened " + ports[i].getDescriptivePortName() );
                ports[i].addDataListener(this);
            }
            
        }
        
        /*
        if(ports.length >= PORT_USE + 1) {
        sensor = ports[PORT_USE];

        if(!sensor.isOpen()) sensor.openPort();

        if(sensor.isOpen()) {

        sensor.addDataListener( this );

        working = true;
        }

        }
        */

    }
    
    public void out(String s) {
        handler.out("[SERIAL READER] " + s);
    }
    
    public int getPacketSize() {
        return 6;
    }

    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    public void serialEvent(SerialPortEvent event) {
        out("Received Data");
        
        byte[] newData = event.getReceivedData();

        read(newData);
    }
    
    public void read(byte[] data) {
        String info = "";
        
        for(int i=0; i<data.length; i++) {
            info += (char) data[i];
        }
        
        out("Read: " + info);
        
        handler.tagSwipe(info);
    }
}