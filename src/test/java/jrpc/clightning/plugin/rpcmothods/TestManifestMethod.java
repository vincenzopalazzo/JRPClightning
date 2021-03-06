package jrpc.clightning.plugin.rpcmothods;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import jrpc.clightning.plugins.rpcmethods.AbstractRPCMethod;
import jrpc.clightning.plugins.rpcmethods.ICLightningRPCMethod;
import jrpc.clightning.plugins.rpcmethods.manifest.ManifestMethod;
import jrpc.mock.RPCMockMethod;
import jrpc.service.CLightningLogger;
import jrpc.service.converters.JsonConverter;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author https://github.com/vincenzopalazzo */
public class TestManifestMethod {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestManifestMethod.class);

  private ICLightningRPCMethod manifest;

  @Before
  public void init() {
    manifest = new ManifestMethod();
  }

  @Test
  public void testStringMethodEmptyOne() {
    ManifestMethod method = (ManifestMethod) manifest;
    String jsonResult = method.toString();
    LOGGER.debug("\n" + jsonResult);
    TestCase.assertFalse(jsonResult.contains("usage"));
  }

  @Test
  public void testStringMethodEmptyTwo() {
    ManifestMethod method = (ManifestMethod) manifest;
    String jsonResult = method.toString();
    LOGGER.debug("\n" + jsonResult);
    TestCase.assertFalse(jsonResult.contains("name"));
  }

  @Test
  public void testFilterRPCMethodOne() {
    ManifestMethod method = (ManifestMethod) manifest;
    List<AbstractRPCMethod> methodList = new ArrayList<>();
    methodList.add(method);
    methodList.add(method);
    method.addMethods(methodList);
    TestCase.assertTrue(method.getRpcMethods().isEmpty());
  }

  @Test
  public void testFilterRPCMethodTwo() {
    ManifestMethod method = (ManifestMethod) manifest;
    List<AbstractRPCMethod> methodList = new ArrayList<>();
    methodList.add(method);
    methodList.add(new RPCMockMethod("mock", "mock", "mock"));
    method.addMethods(methodList);
    TestCase.assertFalse(method.getRpcMethods().isEmpty());
    TestCase.assertEquals(1, method.getRpcMethods().size());
  }

  @Test
  public void testAllPropriety() {
    ManifestMethod method = (ManifestMethod) manifest;
    JsonConverter converter = new JsonConverter();
    String jsonFormat = converter.serialization(method);
    TestCase.assertTrue(jsonFormat.contains("options"));
    TestCase.assertTrue(jsonFormat.contains("rpcmethods"));
    TestCase.assertTrue(jsonFormat.contains("subscriptions"));
    TestCase.assertTrue(jsonFormat.contains("hooks"));
    TestCase.assertTrue(jsonFormat.contains("features"));
    TestCase.assertTrue(jsonFormat.contains("dynamic"));
  }

  @Test
  public void testInsertHook() {
    ManifestMethod method = (ManifestMethod) manifest;
    method.addHook("random_hook");
    JsonConverter converter = new JsonConverter();
    String jsonFormat = converter.serialization(method);
    CLightningLogger.getInstance().debug(getClass(), jsonFormat);
    JsonObject jsonObject = (JsonObject) converter.deserialization(jsonFormat, JsonObject.class);
    TestCase.assertEquals(1, jsonObject.getAsJsonArray("hooks").size());
  }
}
