/**
 * Copyright 2019-2020 Vincenzo Palazzo vincenzo.palazzo@protonmail.com
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jrpc.service.converters;

import java.io.InputStream;
import java.lang.reflect.Type;
import jrpc.exceptions.ServiceException;

/** @author https://github.com/vincenzopalazzo */
public interface IConverter {

  String serialization(Object o);

  Object deserialization(InputStream inputStream, Type type) throws ServiceException;

  Object deserialization(String jsonForm, Type type) throws ServiceException;
}
