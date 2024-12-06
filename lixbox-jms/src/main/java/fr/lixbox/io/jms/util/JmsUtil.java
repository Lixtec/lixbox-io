/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 *    This file is part of lixbox-io.
 *
 *    lixbox-supervision is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    lixbox-supervision is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with lixbox-io.  If not, see <https://www.gnu.org/licenses/>
 *   
 *   @AUTHOR Lixbox-team
 *
 ******************************************************************************/
package fr.lixbox.io.jms.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.resource.LixboxResources;
import fr.lixbox.common.thread.GenericThread;
import fr.lixbox.common.util.DateUtil;
import fr.lixbox.common.util.StringUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.QueueReceiver;
import jakarta.jms.QueueSender;
import jakarta.jms.QueueSession;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

/**
 * Cette classe est un utilitaire qui utilise JMS
 * 
 * @author ludovic.terral
 */
public class JmsUtil
{
    // ----------- Attribut -----------
    protected static final Log LOG = LogFactory.getLog(JmsUtil.class);
    
    public final static String JNDI_FACTORY = "org.jnp.interfaces.NamingContextFactory";
    public final static String REMOTE_JNDI_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    public static final String QUEUE_CONNECTION_FACTORY = "ConnectionFactory";
    public static final String REMOTE_QUEUE_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    public static final int AUTO_ACKNOWLEDGE = Session.AUTO_ACKNOWLEDGE;
    public static final int CLIENT_ACKNOWLEDGE = Session.CLIENT_ACKNOWLEDGE;
    public static final int DUPS_OK_ACKNOWLEDGE = Session.DUPS_OK_ACKNOWLEDGE;
    public static final int SESSION_TRANSACTED = Session.SESSION_TRANSACTED;
    
    protected QueueConnectionFactory qconFactory;
    protected QueueConnection qcon;
    protected QueueSession qsession;
    protected QueueSender qsender;
    protected QueueReceiver qreceiver;
    protected Queue queue;
    protected Queue replyQueue;
    protected TextMessage textMsg;
    protected ObjectMessage objectMsg;
    protected InitialContext ctx;
    protected String qconFactoryName;
    protected String qconName;
    protected String queueName;
    protected String replyQueueName;
    protected int modeAcquittement;



    // ----------- Methode -----------
    /**
     * Constructeur
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * 
     * @throws ProcessusException
     */
    public JmsUtil(String jmsFactoryJndiName, String queueName) throws ProcessusException
    {
        try
        {
            final InitialContext context = new InitialContext();
            init(jmsFactoryJndiName, queueName, null, context, AUTO_ACKNOWLEDGE);
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Constructeur
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * @param replyQueueName
     * 
     * @throws ProcessusException
     */
    public JmsUtil(String jmsFactoryJndiName, String queueName, String replyQueueName) throws ProcessusException
    {
        try
        {
            final InitialContext context = new InitialContext();
            init(jmsFactoryJndiName, queueName, replyQueueName, context, AUTO_ACKNOWLEDGE);
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Constructeur
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * @param contextSystem
     * 
     * @throws ProcessusException
     */
    public JmsUtil(String jmsFactoryJndiName, String queueName, InitialContext contextSystem) throws ProcessusException
    {
        try
        {
            init(jmsFactoryJndiName, queueName, null, contextSystem, AUTO_ACKNOWLEDGE);
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Constructeur
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * @param replyQueueName
     * @param contextSystem
     * 
     * @throws ProcessusException
     */
    public JmsUtil(String jmsFactoryJndiName, String queueName, String replyQueueName, InitialContext contextSystem)
            throws ProcessusException
    {
        try
        {
            init(jmsFactoryJndiName, queueName, replyQueueName, contextSystem, AUTO_ACKNOWLEDGE);
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Constructeur
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * @param modeAcquittement
     * 
     * @throws ProcessusException
     */
    public JmsUtil(String jmsFactoryJndiName, String queueName, int modeAcquittement) throws ProcessusException
    {
        try
        {
            final InitialContext context = new InitialContext();
            init(jmsFactoryJndiName, queueName, null, context, modeAcquittement);
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Constructeur
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * @param replyQueueName
     * @param modeAcquittement
     * 
     * @throws ProcessusException
     */
    public JmsUtil(String jmsFactoryJndiName, String queueName, String replyQueueName, int modeAcquittement)
            throws ProcessusException
    {
        try
        {
            final InitialContext context = new InitialContext();
            init(jmsFactoryJndiName, queueName, replyQueueName, context, modeAcquittement);
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Constructeur
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * @param contextSystem
     * @param modeAcquittement
     * 
     * @throws ProcessusException
     */
    public JmsUtil(String jmsFactoryJndiName, String queueName, InitialContext contextSystem, int modeAcquittement)
            throws ProcessusException
    {
        try
        {
            init(jmsFactoryJndiName, queueName, null, contextSystem, modeAcquittement);
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Constructeur
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * @param replyQueueName
     * @param contextSystem
     * @param modeAcquittement
     * 
     * @throws ProcessusException
     */
    public JmsUtil(String jmsFactoryJndiName, String queueName, String replyQueueName, InitialContext contextSystem,
            int modeAcquittement) throws ProcessusException
    {
        try
        {
            init(jmsFactoryJndiName, queueName, replyQueueName, contextSystem, modeAcquittement);
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode initialise l'utilitaire Jms
     * 
     * @param jmsFactoryJndiName
     * @param queueName
     * @param replyQueueName
     * @param context
     * @param modeAcquittement
     * 
     * @throws BusinessException
     */
    protected void init(String jmsFactoryJndiName, String queueName, String replyQueueName, InitialContext context,
            int modeAcquittement)
    {
        ctx = context;
        qconFactoryName = jmsFactoryJndiName;
        this.queueName = queueName;
        this.modeAcquittement = modeAcquittement;
        this.queueName = queueName;
        this.replyQueueName = replyQueueName;
    }



    protected void init()
    {
        try
        {
            if (ctx == null)
            {
                throw new JMSException(LixboxResources.getString("ERROR.JMS.JNDI.INACCESSIBLE"));
            }
            qconFactory = (QueueConnectionFactory) ctx.lookup(qconFactoryName);
            if (qconFactory == null)
            {
                throw new JMSException(LixboxResources.getString("ERROR.JMS.JNDI.QUEUEFACTO.INDISPONIBLE"));
            }
            if (StringUtil.isEmpty(queueName))
            {
                throw new JMSException(LixboxResources.getString("ERROR.PARAM.INCORRECT.02", "queueName"));
            }
            
            try
            {
                String user = (String) ctx.getEnvironment().get(Context.SECURITY_PRINCIPAL);
                String password = (String) ctx.getEnvironment().get(Context.SECURITY_CREDENTIALS);
                qcon = qconFactory.createQueueConnection(user,password);
            }
            catch (Exception e)
            {
                LOG.info("Connection sans authentification");
                qcon = qconFactory.createQueueConnection();
            }            
            qsession = qcon.createQueueSession(false, modeAcquittement);
            queue = (Queue) ctx.lookup(queueName);
            if (queue == null)
            {
                throw new JMSException(LixboxResources.getString("ERROR.JMS.JNDI.QUEUE.INDISPONIBLE.02", queueName));
            }
            if (!StringUtil.isEmpty(replyQueueName))
            {
                replyQueue = (Queue) ctx.lookup(replyQueueName);
                if (replyQueue == null)
                {
                    throw new JMSException(LixboxResources.getString("ERROR.JMS.JNDI.QUEUE.INDISPONIBLE.02", replyQueueName));
                }
            }
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode ouvre la connexion sur la queue
     * 
     * @throws ProcessusException
     */
    protected void open() throws ProcessusException
    {
        try
        {
            qsender = qsession.createSender(queue);
            try
            {
                qreceiver = qsession.createReceiver(queue);
            }
            catch (Exception e)
            {
                LOG.info(e.getMessage());
            }
            textMsg = qsession.createTextMessage();
            if (replyQueue != null)
            {
                textMsg.setJMSReplyTo(replyQueue);
            }
            qcon.start();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode emet un message JMS de type TextMessage. Afin d'eviter de
     * maintenir les sockets ouvertes, nous executons la communication dans un
     * thread
     * 
     * @param message
     * 
     * @throws ProcessusException
     */
    public void sendTextMsg(String message) throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] { String.class };
        final Object[] params = new Object[] { message };
        GenericThread<JmsUtil> localThread = new GenericThread<JmsUtil>(this, "sendindTextMsg", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        localThread = null;
    }



    /**
     * Cette methode emet un message JMS de type TextMessage
     * 
     * @param message
     * 
     * @throws ProcessusException
     */
    public void sendindTextMsg(String message) throws ProcessusException
    {
        try
        {
            init();
            open();
            textMsg = qsession.createTextMessage(message);
            textMsg.setJMSTimestamp(Calendar.getInstance().getTimeInMillis());
            qsender.send(textMsg);
            LOG.debug("Queue: " + queueName + "\tMSG ECRIS\n" + message);
            close();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode emet des messages JMS de type TextMessage
     * 
     * @param messages
     * 
     * @throws ProcessusException
     */
    public void sendindTextMsgs(List<String> messages) throws ProcessusException
    {
        int nbreMsgSended = 0;
        try
        {
            init();
            open();
            for (String message : messages)
            {
                textMsg = qsession.createTextMessage();
                textMsg.setJMSTimestamp(Calendar.getInstance().getTimeInMillis());
                textMsg.setText(message);
                qsender.send(textMsg);
                LOG.debug(textMsg);
                nbreMsgSended++;
            }
            LOG.debug("Queue: " + queueName + "\tNbre Msg Transmis :" + nbreMsgSended + " / " + messages.size());
            close();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode emet un message JMS de type JmsMessage
     * 
     * @param messages
     * 
     * @throws ProcessusException
     */
    public void sendTextMessages(List<String> messages) throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] { List.class };
        final Object[] params = new Object[] { messages };
        GenericThread<JmsUtil> localThread = new GenericThread<JmsUtil>(this, "sendindTextMsgs", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        localThread = null;
    }



    /**
     * Cette methode emet un message JMS de type JmsMessage
     * 
     * @param message
     * 
     * @throws ProcessusException
     */
    public void sendJmsMsg(Message message) throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] { Message.class };
        final Object[] params = new Object[] { message };
        GenericThread<JmsUtil> localThread = new GenericThread<JmsUtil>(this, "sendingJmsMsg", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        localThread = null;
    }



    /**
     * Cette methode emet un message JMS de type JmsMessage
     * 
     * @param message
     * 
     * @throws ProcessusException
     */
    public void sendingJmsMsg(Message message) throws ProcessusException
    {
        try
        {
            init();
            open();
            qsender.send(message);
            LOG.debug("Queue: " + queueName + "\tMSG ECRIS\n" + message);
            close();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode emet un message JMS de type JmsMessage
     * 
     * @param messages
     * 
     * @throws ProcessusException
     */
    public void sendTextMsgs(List<TextMessage> messages) throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] { List.class };
        final Object[] params = new Object[] { messages };
        GenericThread<JmsUtil> localThread = new GenericThread<JmsUtil>(this, "sendingTextMsgs", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        localThread = null;
    }



    /**
     * Cette methode emet un message JMS de type JmsMessage
     * 
     * @param messages
     * 
     * @throws ProcessusException
     */
    public void sendingTextMsgs(List<TextMessage> messages) throws ProcessusException
    {
        int nbreMsgSended = 0;
        try
        {
            init();
            open();
            for (TextMessage msg : messages)
            {
                qsender.send(msg);
                LOG.debug(msg);
                nbreMsgSended++;
            }
            close();
            LOG.debug("Nbre Msg Transmis :" + nbreMsgSended + " / " + messages.size());
        }
        catch (ProcessusException e)
        {
            close();
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            close();
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode recoit des messages JMS de type TextMessage
     * 
     * @throws ProcessusException
     */
    public String receiveTextMsg() throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] {};
        final Object[] params = new Object[] {};
        final GenericThread<String> localThread = new GenericThread<String>(this, "receivingTextMsg", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        return localThread.getResultatThread();
    }



    /**
     * Cette methode recoit des messages JMS de type TextMessage (methode
     * genericthread)
     * 
     * @throws ProcessusException
     */
    public String receivingTextMsg() throws ProcessusException
    {
        final StringBuffer sbf = new StringBuffer(32);
        try
        {
            init();
            open();
            boolean messagePresent = true;
            while (messagePresent)
            {
                Message msg = qreceiver.receive(2000);
                if (msg == null)
                {
                    messagePresent = false;
                }
                else
                {
                    TextMessage tMsg = null;
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(msg.getJMSTimestamp());
                    if (msg instanceof TextMessage)
                    {
                        tMsg = (TextMessage) msg;
                        sbf.append(LixboxResources.getString("INFO.JMS.DETAIL.MSG", new String[]
                                { tMsg.getJMSMessageID(), DateUtil.format(cal, "dd/MM/yyyy hh:mm:ss"),
                                        tMsg.getText() }));
                    }
                }
            }
            close();
            return sbf.toString();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode recoit des messages JMS de type TextMessage
     * 
     * @throws ProcessusException
     */
    public List<TextMessage> receiveTextMsgs() throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] {};
        final Object[] params = new Object[] {};
        final GenericThread<List<TextMessage>> localThread = new GenericThread<List<TextMessage>>(this, "receivingTextMsgs",
                typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        return localThread.getResultatThread();
    }



    /**
     * Cette methode recoit des messages JMS de type TextMessage
     * 
     * @throws ProcessusException
     */
    public List<TextMessage> receivingTextMsgs() throws ProcessusException
    {
        final List<TextMessage> liste = new ArrayList<TextMessage>();
        try
        {
            init();
            open();
            boolean messagePresent = true;
            while (messagePresent)
            {
                Message msg = qreceiver.receive(2000);
                if (msg == null)
                {
                    messagePresent = false;
                }
                else
                {
                    if (msg instanceof TextMessage)
                    {
                        liste.add((TextMessage) msg);
                    }
                }
            }
            close();
            LOG.debug("Queue: " + queueName + "\tNbre Msg Recus: " + liste.size());
            return liste;
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode acquitte les messages de la liste passee en parametre
     * 
     * @param liste
     * 
     * @throws ProcessusException
     */
    public void acknowledgeTextMsgs(List<TextMessage> liste) throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] { List.class };
        final Object[] params = new Object[] { liste };
        final GenericThread<List<TextMessage>> localThread = new GenericThread<List<TextMessage>>(this, "acknowledgingTextMsgs",
                typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
    }



    /**
     * Cette methode acquitte les messages de la liste passee en parametre
     * 
     * @param liste
     * 
     * @throws ProcessusException
     */
    public void acknowledgingTextMsgs(List<TextMessage> liste) throws ProcessusException
    {
        int nbreMsgAcquit = 0;
        final List<TextMessage> listeTmp = new ArrayList<TextMessage>();
        try
        {
            modeAcquittement = CLIENT_ACKNOWLEDGE; // force le mode
            init();
            open();
            boolean messagePresent = true;
            while (messagePresent)
            {
                Message msg = qreceiver.receive(2000);
                if (msg == null)
                {
                    messagePresent = false;
                }
                else
                {
                    if (msg instanceof TextMessage)
                    {
                        listeTmp.add((TextMessage) msg);
                    }
                }
            }
            for (TextMessage msgAck : liste)
            {
                for (TextMessage msgtmp : listeTmp)
                {
                    if (msgAck.getText().equals(msgtmp.getText()))
                    {
                        msgtmp.acknowledge();
                        nbreMsgAcquit++;
                        break;
                    }
                }
            }
            close();
            LOG.debug("Queue: " + queueName + "\tNBRE MSG ACQUITTE: " + nbreMsgAcquit + " / " + liste.size()
                    + "\t nbreMsgQueue:" + listeTmp.size());
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode acquitte les messages de la liste passee en parametre
     * 
     * @param liste
     * 
     * @throws ProcessusException
     */
    public void acknowledgeTextMsgsByJmsMsgId(List<String> liste) throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] { List.class };
        final Object[] params = new Object[] { liste };
        final GenericThread<List<TextMessage>> localThread = new GenericThread<List<TextMessage>>(this,
                "acknowledgingTextMsgsByJmsMsgId", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
    }



    /**
     * Cette methode acquitte les messages de la liste passee en parametre
     * 
     * @param liste
     * 
     * @throws ProcessusException
     */
    public void acknowledgingTextMsgsByJmsMsgId(List<String> liste) throws ProcessusException
    {
        int nbreMsgAcquit = 0;
        final Map<String,TextMessage> listeTmp = new HashMap<String,TextMessage>(32, 0.75f);
        try
        {
            init();
            open();
            boolean messagePresent = true;
            while (messagePresent)
            {
                Message msg = qreceiver.receive(2000);
                if (msg == null)
                {
                    messagePresent = false;
                }
                else
                {
                    if (msg instanceof TextMessage)
                    {
                        listeTmp.put(msg.getJMSMessageID(), (TextMessage) msg);
                    }
                }
            }
            for (String msgId : liste)
            {
                if (listeTmp.containsKey(msgId))
                {
                    listeTmp.get(msgId).acknowledge();
                    nbreMsgAcquit++;
                    listeTmp.remove(msgId);
                }
            }
            for (TextMessage txtMsgTmp : listeTmp.values())
            {
                qsender.send(txtMsgTmp);
            }
            close();
            LOG.debug("Queue: " + queueName + "\tNBRE MSG ACQUITTE: " + nbreMsgAcquit + " / " + liste.size()
                    + "\t nbreMsgQueue:" + listeTmp.size());
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode genere un textmessage pour la queue configuree
     * 
     * @return un textMessage
     */
    public TextMessage initTextMessage()
    {
        final Class<?>[] typeParams = new Class<?>[] {};
        final Object[] params = new Object[] {};
        final GenericThread<TextMessage> localThread = new GenericThread<TextMessage>(this, "initiatingTextMessage", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        return localThread.getResultatThread();
    }



    /**
     * Cette methode genere un textmessage pour la queue configuree
     * 
     * @return un textMessage
     */
    public TextMessage initiatingTextMessage()
    {
        try
        {
            init();
            open();
            final TextMessage txtMsg = qsession.createTextMessage();
            close();
            return txtMsg;
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }



    /**
     * Cette methode emet un message JMS de type TextMessage. Afin d'eviter de
     * maintenir les sockets ouvertes, nous executons la communication dans un
     * thread
     * 
     * @param message
     * 
     * @throws ProcessusException
     */
    public void sendObjectMsg(Serializable message) throws ProcessusException
    {
        final Class<?>[] typeParams = new Class<?>[] { Serializable.class };
        final Object[] params = new Object[] { message };
        GenericThread<JmsUtil> localThread = new GenericThread<JmsUtil>(this, "sendindObjectMsg", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        localThread = null;
    }



    /**
     * Cette methode emet un message JMS de type TextMessage
     * 
     * @param message
     * 
     * @throws ProcessusException
     */
    public void sendindObjectMsg(Serializable message) throws ProcessusException
    {
        try
        {
            init();
            open();
            objectMsg = qsession.createObjectMessage(message);
            objectMsg.setJMSTimestamp(Calendar.getInstance().getTimeInMillis());
            qsender.send(objectMsg);
            LOG.debug("Queue: " + queueName + "\tMSG ECRIS\n" + message);
            close();
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }
    


    /**
     * Cette methode genere un textmessage pour la queue configuree
     * 
     * @return un textMessage
     */
    public ObjectMessage initObjectMessage()
    {
        final Class<?>[] typeParams = new Class<?>[] {};
        final Object[] params = new Object[] {};
        final GenericThread<ObjectMessage> localThread = new GenericThread<ObjectMessage>(this, "initiatingObjectMessage", typeParams, params);
        localThread.start();
        while (localThread.isAlive())
        {
            try
            {
                wait(500);
            }
            catch (Exception e)
            {
                LOG.trace(e);
            }
        }
        return localThread.getResultatThread();
    }



    /**
     * Cette methode genere un textmessage pour la queue configuree
     * 
     * @return un textMessage
     */
    public ObjectMessage initiatingObjectMessage()
    {
        try
        {
            init();
            open();
            final ObjectMessage txtMsg = qsession.createObjectMessage();
            close();
            return txtMsg;
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }
    
    

    /**
     * Cette methode termine la session JMS de cette instance de
     * l'utilitaire JMS.
     * 
     * @throws ProcessusException
     */
    protected void close() throws ProcessusException
    {
        try
        {
            if (qreceiver != null)
            {
                qreceiver.close();
            }
            if (qsender != null)
            {
                qsender.close();
            }
            if (qsession != null)
            {
                qsession.close();
            }
            if (qcon != null)
            {
                qcon.close();
            }
            qreceiver = null;
            qsender = null;
            qsession = null;
            qcon = null;
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }
    }
}
