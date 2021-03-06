package jrpc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import jrpc.clightning.model.CLightningGetInfo;
import jrpc.clightning.service.CLightningConfigurator;
import jrpc.service.converters.JsonConverter;

public class MocksUtils {

  private static String getSandBoxPath() {
    String url = CLightningConfigurator.getInstance().getUrl();
    StringTokenizer tokens = new StringTokenizer(url, "/");
    if (tokens.countTokens() > 1) {
      StringBuilder builder = new StringBuilder("/");
      while (tokens.hasMoreElements()) {
        String token = tokens.nextToken();
        if (token.contains("lightning_dir")) {
          break;
        }
        builder.append(token).append("/");
      }
      return builder.toString();
    }
    return "";
  }

  public static CLightningGetInfo getInfoFirstNode() {

    String sandBoxPath = MocksUtils.getSandBoxPath();
    if (sandBoxPath.isEmpty()) return null;
    sandBoxPath += "node_one.info";

    byte[] encoded = new byte[0];
    try {
      encoded = Files.readAllBytes(Paths.get(sandBoxPath));
    } catch (IOException e) {
      e.printStackTrace();
    }
    String getInfo = new String(encoded);
    JsonConverter converter = new JsonConverter();
    return (CLightningGetInfo) converter.deserialization(getInfo, CLightningGetInfo.class);
  }

  public static void stopBitcoin() {
    runProcess("stop-bitcoin.sh");
  }

  public static void generateBlockBitcoin() {
    runProcess("generate-block-bitcoin.sh");
  }

  public static void fundCLightningNodeTwo() {
    runProcess("fund_node_two.sh");
  }

  private static void runProcess(String cmd) {
    try {
      String sandBoxPath = MocksUtils.getSandBoxPath();
      if (sandBoxPath.isEmpty()) return;

      ProcessBuilder pb = new ProcessBuilder("bash", new File(cmd).toString());
      pb.inheritIO();
      pb.directory(new File(sandBoxPath));
      Process process = pb.start();
      process.waitFor();
    } catch (IOException | InterruptedException ioException) {
      ioException.printStackTrace();
    }
  }
}
