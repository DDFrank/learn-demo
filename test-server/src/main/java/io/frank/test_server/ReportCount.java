package io.frank.test_server;

import java.time.LocalDateTime;

/**
 * @author jinjunliang
 **/
public class ReportCount {
  private int totalCount;
  private int bind402Count;
  private int userSyncCount;
  private int otherCount;
  private int chcekinCount;
  private int backCount;

  public void updateTotalCount() {
    totalCount++;
  }

  public void updateBindCount() {
    bind402Count++;
  }

  public void updateUserSyncCount() {
    userSyncCount++;
  }

  public void updateOtherCount() {
    otherCount++;
  }

  public void updateCheckinCount() {
    chcekinCount++;
  }

  public void updateBackCount() {
    backCount++;
  }

  public void report() {
    System.out.println(LocalDateTime.now().toString());
    System.out.println("总共收到:" + totalCount + "条消息");
    System.out.println("收到:" + bind402Count + "条402消息");
    System.out.println("总共收到:" + userSyncCount + "条人员同步消息");
    System.out.println("总共收到:" + chcekinCount + "条打卡消息");
    System.out.println("总共收到:" + backCount + "条后门消息");
    System.out.println("总共收到:" + otherCount + "条其它消息");
  }
}
