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
package org.apache.rocketmq.common.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageBatch extends Message implements Iterable<Message> {

    private static final long serialVersionUID = 621335151046335557L;
    private final List<Message> messages;

    public MessageBatch() {
        this(32);
    }

    public MessageBatch(int msgNum) {
        messages = new ArrayList<>(msgNum);
    }

    public byte[] encode() throws Exception {
        return MessageDecoder.encodeMessages(messages);
    }


    public void addMessage(Message message) {
        assert message != null;
        if (message.getDelayTimeLevel() > 0) {
            throw new UnsupportedOperationException("You should use setDelayTimeLevel in MessageBatch");
        }
        messages.add(message);
    }

    public Iterator<Message> iterator() {
        return messages.iterator();
    }
}
