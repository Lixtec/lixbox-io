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
package fr.lixbox.io.jms.listener;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.ExceptionListener;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.Queue;
import jakarta.jms.Session;

public abstract class MessageQueueListener implements MessageListener, ExceptionListener
{    
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(MessageQueueListener.class);
    
    private String hostUri;
    private String queueUri;
    
    private Connection dConnection;
    private Session dSession;
    private MessageConsumer dSubscriber;
    

    
    // ----------- Methode -----------
    public MessageQueueListener(String hostUri, String queueUri)
    {
        this.hostUri = hostUri;
        this.queueUri = queueUri;
    }
    
    
    
    public void initialize()
    {
        try
        {
            startConnection();
            LOG.info("CONNECTION A LA QUEUE INITIALISEE");
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
    }

    

    @Override
    public abstract void onMessage(Message msg);



    @SuppressWarnings("deprecation")
    @Override
    public void onException(JMSException exception)
    {
        LOG.error("PERTE DE CONNEXION AVEC LE SERVEUR DISTANT");
        LOG.error("ARRET DES CONNECTIONS JMS");
        try
        {       
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        dSubscriber.close();
                    }
                    catch (JMSException e)
                    {
                        LOG.error("IMPOSSIBLE DE TERMINER dSubscriber");
                    }
                }
            });
            thread.start();
            long endTimeMillis = System.currentTimeMillis() + 2000;
            while (thread.isAlive()) {
                if (System.currentTimeMillis() > endTimeMillis) 
                {
                    thread.stop();
                    break;
                }
                else
                {
                    try 
                    {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException t) {}
                }
            }
        }
        catch (Exception e)
        {
            LOG.error("IMPOSSIBLE DE TERMINER dSubscriber");
        }  
        dSubscriber = null;
        try
        {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        dSession.close();
                    }
                    catch (JMSException e)
                    {
                        LOG.error("IMPOSSIBLE DE TERMINER dSession");
                    }
                }
            });
            thread.start();
            long endTimeMillis = System.currentTimeMillis() + 2000;
            while (thread.isAlive()) {
                if (System.currentTimeMillis() > endTimeMillis) 
                {
                    thread.stop();
                    break;
                }
                else
                {
                    try 
                    {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException t) {}
                }
            }
        }
        catch (Exception e)
        {
            LOG.error("IMPOSSIBLE DE TERMINER dSession");
        }  
        dSession = null;
        try
        {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        dConnection.close();
                    }
                    catch (JMSException e)
                    {
                        LOG.error("IMPOSSIBLE DE TERMINER dConnection");
                    }
                }
            });
            thread.start();
            long endTimeMillis = System.currentTimeMillis() + 2000;
            while (thread.isAlive()) {
                if (System.currentTimeMillis() > endTimeMillis) 
                {
                    thread.stop();
                    break;
                }
                else
                {
                    try 
                    {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException t) {}
                }
            }
        }
        catch (Exception e)
        {
            LOG.error("IMPOSSIBLE DE TERMINER dConnection");
        } 
        dConnection = null;
        LOG.error("REDEMARRAGE DES CONNECTIONS");
        
        do
        {
            LOG.error("TENTATIVE DE CONNECTION AU SERVEUR DISTANT");
            try
            {
                Thread.sleep(3000);
                startConnection();
            }
            catch (Exception e)
            {
                LOG.fatal(e);
            }
        }
        while (dSubscriber == null || dSession==null || dConnection == null);
            
        LOG.error("RECONNECTION AU SERVEUR DISTANT TERMINEE");
    }
    
    
    
    private void startConnection() throws NamingException, JMSException
    {
        Properties dProp = new Properties();
        dProp.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        dProp.put(Context.PROVIDER_URL, this.hostUri);
        dProp.put(Context.URL_PKG_PREFIXES, "org.jboss.naming.remote.client");
        Context dContext = new InitialContext(dProp);
        ConnectionFactory connFactory = (ConnectionFactory) dContext.lookup("jms/RemoteConnectionFactory");
        dConnection = connFactory.createConnection();
        dConnection.setExceptionListener(this);
        dSession = dConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = (Queue) dContext.lookup(this.queueUri);
        dSubscriber = dSession.createConsumer(queue);
        dSubscriber.setMessageListener(this);
        dConnection.start();
    }
}
