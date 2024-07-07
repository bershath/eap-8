package org.bershath.labs.jms.intercept;

import org.apache.activemq.artemis.api.core.*;
import org.apache.activemq.artemis.core.protocol.core.Channel;
import org.apache.activemq.artemis.core.protocol.core.Packet;
import org.apache.activemq.artemis.core.protocol.core.impl.RemotingConnectionImpl;
import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.ActiveMQExceptionMessage;
import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.MessagePacket;
import org.apache.activemq.artemis.spi.core.protocol.RemotingConnection;
import org.jboss.logging.Logger;

/**
 *
 * @author  :   Tyronne Wickramarathne
 * @since   :   03-05-2024
 * @version :   2.0
 *<br/>
 * This interceptor intercepts incoming packets to the broker and performs a few operations:
 *      - Obtains the remote client's IP address
 *      - Obtains the client id
 *      - Logs the payload of String messages
 *
 *<br/>
 *  Configuration:
 *  The easiest is to define a module in the EAP subsystem :
 *  The module.xml needs to be placed along with the packaged jar in the 'main' folder of the module.
 *  The module.xml should look identical to the following:
 *<br/>
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <module xmlns="urn:jboss:module:1.1" name="org.bershath.labs.jms.intercept">
 *     <resources>
 *         <resource-root path="jms-interceptor-1.0-SNAPSHOT.jar"/>
 *     </resources>
 *     <dependencies>
 *         <module name="org.apache.activemq.artemis"/>
 *         <module name="org.jboss.logging"/>
 *     </dependencies>
 * </module>
 *}
 * </pre>
 *<br/>
 * Deployment :
 * Start the eap server in admin mode:
 * <pre>
 * % ./standalone.sh -c int.xml --start-mode=admin-only
 * </pre>
 *<br/>
 * Log on to JBoss CLI:
 * Add the interceptor:
 * <pre>
 *     ./subsystem=messaging-activemq/server=default:list-add(name=incoming-interceptors,value={name=>"org.bershath.labs.jms.intercept.JmsInterceptor",module=>"org.bershath.labs.jms.intercept"})
 *</pre>
 *<br/>
 *
 * The build process will autonomously create a module to host the interceptor
 * The extras folder contains the module.xml which gets copied to the EAP 8 module.
 *
 * Optional:
 * This is an optional step to divert the interceptor logging to a separate file. The following logging configuration needs to
 * be added to the exiting logging configuration in the JBoss EAP configuration file.
 * <br/>
 * <pre>
 * {@code
 * <!--  Artemis interceptor specific logging begins -->
 * <periodic-rotating-file-handler name="INTERCEPTOR_FILE" autoflush="true">
 *      <level name="DEBUG"/>
 *     	<formatter>
 *     		<named-formatter name="PATTERN"/>
 *   	</formatter>
 *    	<file relative-to="jboss.server.log.dir" path="artemis_interceptor.log"/>
 *    	<suffix value=".yyyy-MM-dd"/>
 *     	<append value="true"/>
 * 	</periodic-rotating-file-handler>
 * 	<logger category="org.bershath.labs.jms.intercept" use-parent-handlers="false">
 *    	<level name="DEBUG"/>
 *    	<handlers>
 *         	<handler name="INTERCEPTOR_FILE"/>
 *   	</handlers>
 * 	</logger>
 * <!--  Artemis interceptor specific logging ends -->
 *}
 *</pre>
 *
 */

public class JmsInterceptor implements Interceptor {

    private static final Logger log = Logger.getLogger(JmsInterceptor.class);
    private static final String bannedWord = "MNOP";
    /**
     * @param packet
     * @param remotingConnection
     * @return
     * @throws ActiveMQException
     */
    @Override
    public boolean intercept(Packet packet, RemotingConnection remotingConnection) throws ActiveMQException {
        log.trace(Interceptor.class.getName() + " called");
        log.trace("Processing packet: " + packet.getClass().getName() + " that came from " + remotingConnection.getRemoteAddress() +".");
        log.debug("RemotingConnection: " + remotingConnection.getRemoteAddress() + " with client ID = " + remotingConnection.getID());

        if(packet instanceof MessagePacket){
            MessagePacket messagePacket = (MessagePacket) packet;
            ICoreMessage iCoreMessage = messagePacket.getMessage();
            // Make sure the message type to be a TextMessage
            if(iCoreMessage.getType() == Message.TEXT_TYPE){
                ActiveMQBuffer activeMQBuffer = iCoreMessage.getBodyBuffer();
                SimpleString payload = activeMQBuffer.readNullableSimpleString();
                log.debug("Payload " + payload);

                //Here we can do some fancy stuff
                if (payload.toString().toLowerCase().contains(bannedWord.toLowerCase())) {
                    RemotingConnectionImpl remotingConnectionImpl = (RemotingConnectionImpl) remotingConnection;
                    Channel channel = remotingConnectionImpl.getChannel(packet.getChannelID(), -1);
                    log.info("A client from "+ remotingConnection.getRemoteAddress() + " tried to send a message containing the word " + payload +  ". Denied permission "  );
                    ActiveMQException exception = new ActiveMQException("Message containing " + bannedWord + " word was not permitted. Message must not contain banned words");
                    Packet exceptionPacket = new ActiveMQExceptionMessage(exception);
                    channel.sendAndFlush(exceptionPacket);
                    remotingConnection.destroy();
                    return false; // Not proceeding to the next interceptor
                }
            }
        }
        return true;  // Must return true to proceed to the next interceptor
    }
}
