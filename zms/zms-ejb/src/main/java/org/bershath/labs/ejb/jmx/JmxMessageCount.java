package org.bershath.labs.ejb.jmx;


import jakarta.ejb.LocalBean;
import jakarta.ejb.Singleton;
import org.apache.activemq.artemis.api.core.management.QueueControl;
import org.jboss.logging.Logger;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@LocalBean
@Singleton
public class JmxMessageCount {

    private static final Logger log = Logger.getLogger(JmxMessageCount.class);

    public long getMessageCount(String queueAddressName) throws Exception {
        return getQueueControl(getMbeanServer(),getMBeanObjectName(queueAddressName)).getMessageCount();
    }

    private ObjectName getMBeanObjectName(String queueAddressName) throws MalformedObjectNameException {
        return ObjectName.getInstance(getObjectNameString(queueAddressName));
    }

    private String getObjectNameString(String queueAddressName){
        return  "org.apache.activemq.artemis:broker=\"default\",component=addresses,address=\"jms.queue.Z\",subcomponent=queues,routing-type=\"anycast\",queue=\"jms.queue.Z\"";
    }

    private MBeanServer getMbeanServer(){
        return ManagementFactory.getPlatformMBeanServer();
    }

    private QueueControl getQueueControl(MBeanServer mBeanServer, ObjectName objectName){
        return MBeanServerInvocationHandler.newProxyInstance(mBeanServer,objectName,QueueControl.class,false);

    }

}
