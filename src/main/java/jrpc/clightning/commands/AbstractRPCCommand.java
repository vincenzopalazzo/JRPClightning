/**
 * Copyright 2019 https://github.com/vincenzopalazzo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jrpc.clightning.commands;

import jrpc.clightning.exceptions.CommandException;
import jrpc.clightning.service.socket.CLightningSocket;
import jrpc.exceptions.ServiceException;

import java.util.HashMap;

/**
 * @author https://github.com/vincenzopalazzo
 */
public abstract class AbstractRPCCommand<T> implements IRPCCommand<T>{

    protected String commandName;

    public AbstractRPCCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public T doRPCCommand(CLightningSocket socket, HashMap<String, String> payload) throws ServiceException, CommandException {
        if(socket == null || payload == null){
            throw new IllegalArgumentException("Methods is/are null");
        }
        return null;
    }
}