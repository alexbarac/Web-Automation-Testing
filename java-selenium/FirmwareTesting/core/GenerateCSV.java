
package com.core;

import java.io.*;

import java.util.StringTokenizer;

public class GenerateCSV
{
  public GenerateCSV()
  {
  }

  public static void main(final String[] args)
  {
    try
    {
      FileWriter csvFile = new FileWriter("builds.csv");
      BufferedWriter out = new BufferedWriter(csvFile);

      FileReader fstream = new FileReader("buildCount.txt");
      BufferedReader in = new BufferedReader(fstream);
      int objCount = Integer.valueOf(in.readLine());

      FileInputStream fis = new FileInputStream("builds.dat");
      ObjectInputStream ois = new ObjectInputStream(fis);

      FileReader fstreamAN80 = new FileReader("buildCountAN80.txt");
      BufferedReader inAN80 = new BufferedReader(fstreamAN80);
      int objCountAN80 = Integer.valueOf(inAN80.readLine());

      FileInputStream fisAN80 = new FileInputStream("buildsAN80.dat");
      ObjectInputStream oisAN80 = new ObjectInputStream(fisAN80);

      out.write("index, ver, build, device, name");

      int i1 = 0;

      for(int i = 0; i < objCount; i++)
      {
        out.newLine();

        com.core.Build build = (Build)ois.readObject();

        i1++;

        out.write(i1 + ", " + build.toCsvFormat());
        System.out.println(build.toString());
      }

      for(int i = 0; i < objCountAN80; i++)
      {
        out.newLine();

        com.core.Build build = (Build)oisAN80.readObject();

        i1++;
        out.write(i1 + ", " + build.toCsvFormat());
        System.out.println(build.toString());
      }

      ois.close();
      oisAN80.close();
      out.close();
    }
    catch(final IOException e)
    {
      e.printStackTrace();
    }
    catch(final ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }
}