package jrpc.mock;

import jrpc.clightning.plugins.ICLightningPlugin;
import jrpc.clightning.plugins.rpcmethods.AbstractRPCMethod;
import jrpc.service.CLightningLogger;
import jrpc.service.converters.jsonwrapper.CLightningJsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author https://github.com/vincenzopalazzo */
public class RPCMockMethod extends AbstractRPCMethod {

  private static final Logger LOGGER = LoggerFactory.getLogger(RPCMockMethod.class);

  public RPCMockMethod(String name, String usage, String description) {
    super(name, usage, description);
  }

  public RPCMockMethod(String name, String usage, String description, String longDescription) {
    super(name, usage, description, longDescription);
  }

  @Override
  public void doRun(
      ICLightningPlugin plugin, CLightningJsonObject request, CLightningJsonObject response) {
    CLightningLogger.getInstance().error(this.getClass(), "******* MOCK method run *******");
  }
}
