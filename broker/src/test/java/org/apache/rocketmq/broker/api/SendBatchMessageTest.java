/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * $Id: SendMessageTest.java 1831 2013-05-16 01:39:51Z shijia.wxr $
 */
package org.apache.rocketmq.broker.api;


import org.apache.rocketmq.broker.BrokerTestHarness;
import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.hook.SendMessageContext;
import org.apache.rocketmq.client.impl.CommunicationMode;
import org.apache.rocketmq.client.impl.MQClientAPIImpl;
import org.apache.rocketmq.client.impl.factory.MQClientInstance;
import org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageBatch;
import org.apache.rocketmq.common.message.MessageClientIDSetter;
import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.common.protocol.header.SendMessageRequestHeader;
import org.apache.rocketmq.remoting.netty.NettyClientConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shijia.wxr
 */
public class SendBatchMessageTest extends BrokerTestHarness {


    MQClientAPIImpl client = new MQClientAPIImpl(new NettyClientConfig(), null, null, new ClientConfig());
    String topic = "UnitTestTopic";

    @Before
    @Override
    public void startup() throws Exception {
        super.startup();
        client.start();

    }

    @After
    @Override
    public void shutdown() throws Exception {
        client.shutdown();
        super.shutdown();
    }

    @Test
    public void testSendBatch() throws Exception {
        MessageBatch messageBatch = new MessageBatch();
        messageBatch.setTopic("topic");
        for (int i = 0; i < 10; i++) {
            Message message = new Message("topic", ("body" + i).getBytes());
            message.setFlag(i);
            message.putUserProperty("key", "value" + i);
            messageBatch.addMessage(message);
        }
        messageBatch.setBody(messageBatch.encode());
        for (Message message : messageBatch) {
            MessageClientIDSetter.setUniqID(message);
        }
        SendMessageRequestHeader requestHeader = new SendMessageRequestHeader();
        requestHeader.setProducerGroup("abc");
        requestHeader.setTopic(messageBatch.getTopic());
        requestHeader.setDefaultTopic(MixAll.DEFAULT_TOPIC);
        requestHeader.setDefaultTopicQueueNums(4);
        requestHeader.setQueueId(0);
        requestHeader.setSysFlag(0);
        requestHeader.setBornTimestamp(System.currentTimeMillis());
        requestHeader.setFlag(messageBatch.getFlag());
        requestHeader.setProperties(MessageDecoder.messageProperties2String(messageBatch.getProperties()));
        requestHeader.setBatch(true);

        SendResult result = client.sendMessage(brokerAddr, BROKER_NAME, messageBatch, requestHeader, 1000 * 5,
                CommunicationMode.SYNC, new SendMessageContext(), null);
        Assert.assertEquals(SendStatus.SEND_OK, result.getSendStatus());
        final String[] msgIds = result.getMsgId().split(",");
        int idx = 0;
        for (Message message : messageBatch) {
            Assert.assertEquals(MessageClientIDSetter.getUniqID(message), msgIds[idx++]);
        }
        Assert.assertEquals(msgIds.length, result.getOffsetMsgId().split(",").length);
        System.out.println(result);
    }
    @Test
    public void testSendSingle() throws Exception {
        Message msg = new Message(topic, "TAG1 TAG2", "100200300", "body".getBytes());
        SendMessageRequestHeader requestHeader = new SendMessageRequestHeader();
        requestHeader.setProducerGroup("abc");
        requestHeader.setTopic(msg.getTopic());
        requestHeader.setDefaultTopic(MixAll.DEFAULT_TOPIC);
        requestHeader.setDefaultTopicQueueNums(4);
        requestHeader.setQueueId(0);
        requestHeader.setSysFlag(0);
        requestHeader.setBornTimestamp(System.currentTimeMillis());
        requestHeader.setFlag(msg.getFlag());
        requestHeader.setProperties(MessageDecoder.messageProperties2String(msg.getProperties()));

        SendResult result = client.sendMessage(brokerAddr, BROKER_NAME, msg, requestHeader, 1000 * 5,
                CommunicationMode.SYNC, new SendMessageContext(), null);
        Assert.assertEquals(SendStatus.SEND_OK, result.getSendStatus());
    }
}
