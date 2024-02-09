
package com.core;

import java.io.Serializable;


public class Build
  implements Serializable
{
  private int ver;
  private int buildNum;
  private String device;
  private String fileName;

  public Build()
  {
  }

  public Build(final int ver, final int buildNum, final String device, final String fileName)
  {
    this.ver = ver;
    this.buildNum = buildNum;
    this.device = device;
    this.fileName = fileName;
  }

  public int getVer()
  {
    return ver;
  }

  public void setVer(final int ver)
  {
    this.ver = ver;
  }

  public int getBuildNum()
  {
    return buildNum;
  }

  public void setBuildNum(final int buildNum)
  {
    this.buildNum = buildNum;
  }

  public String getDevice()
  {
    return device;
  }

  public void setDevice(final String device)
  {
    this.device = device;
  }

  public String getFileName()
  {
    return fileName;
  }

  public void setFileName(final String fileName)
  {
    this.fileName = fileName;
  }

  @Override
  public String toString()
  {
    return "Build: " + ver + ", " + buildNum + ", " + device + ", " + fileName;
  }

  public String toCsvFormat()
  {
      return ver  + ", " + buildNum + ", " + device + ", " + fileName;
  }
}