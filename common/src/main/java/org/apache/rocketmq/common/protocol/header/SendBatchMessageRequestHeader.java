/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.annotation.CFNullable;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

/**
 * Use short variable name to speed up FastJson deserialization process.
 */
public class SendBatchMessageRequestHeader implements CommandCustomHeader {
    @CFNotNull
    private String a; // producerGroup;
    @CFNotNull
    private String b; // topic;
    @CFNotNull
    private String c; // defaultTopic;
    @CFNotNull
    private Integer d; // defaultTopicQueueNums;
    @CFNotNull
    private Integer e; // queueId;
    @CFNotNull
    private Integer f; // sysFlag;
    @CFNotNull
    private Long g; // bornTimestamp;
    @CFNotNull
    private Integer h; // flag;
    @CFNullable
    private String i; // properties;
    @CFNullable
    private Integer j; // reconsumeTimes;
    @CFNullable
    private boolean k; // unitMode = false;

    private Integer l; // consumeRetryTimes

    public static SendMessageRequestHeader createSendMessageRequestHeader(final SendBatchMessageRequestHeader batch) {
        SendMessageRequestHeader v1 = new SendMessageRequestHeader();
        v1.setProducerGroup(batch.a);
        v1.setTopic(batch.b);
        v1.setDefaultTopic(batch.c);
        v1.setDefaultTopicQueueNums(batch.d);
        v1.setQueueId(batch.e);
        v1.setSysFlag(batch.f);
        v1.setBornTimestamp(batch.g);
        v1.setFlag(batch.h);
        v1.setProperties(batch.i);
        v1.setReconsumeTimes(batch.j);
        v1.setUnitMode(batch.k);
        v1.setMaxReconsumeTimes(batch.l);
        return v1;
    }

    public static SendBatchMessageRequestHeader createSendBatchMessageRequestHeader(final SendMessageRequestHeader v1) {
        SendBatchMessageRequestHeader batch = new SendBatchMessageRequestHeader();
        batch.a = v1.getProducerGroup();
        batch.b = v1.getTopic();
        batch.c = v1.getDefaultTopic();
        batch.d = v1.getDefaultTopicQueueNums();
        batch.e = v1.getQueueId();
        batch.f = v1.getSysFlag();
        batch.g = v1.getBornTimestamp();
        batch.h = v1.getFlag();
        batch.i = v1.getProperties();
        batch.j = v1.getReconsumeTimes();
        batch.k = v1.isUnitMode();
        batch.l = v1.getMaxReconsumeTimes();
        return batch;
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public Integer getF() {
        return f;
    }

    public void setF(Integer f) {
        this.f = f;
    }

    public Long getG() {
        return g;
    }

    public void setG(Long g) {
        this.g = g;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public boolean isK() {
        return k;
    }

    public void setK(boolean k) {
        this.k = k;
    }

    public Integer getL() {
        return l;
    }

    public void setL(final Integer l) {
        this.l = l;
    }
}