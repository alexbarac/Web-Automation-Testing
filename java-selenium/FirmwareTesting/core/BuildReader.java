
package com.core;

import java.io.*;

import java.util.StringTokenizer;



public class BuildReader
{
  private static final String defaultBuildFolderRDL = "\\\\172.25.100.220\\RDL3000_UPG\\Redline\\PMP";
  private static final String defaultBuildFolderAN80 = "\\\\172.25.100.220\\AN80_UPG\\PMP";

  public BuildReader()
  {
    // default empty param constructor
  }

  /**
   * filters out all the files that do not end with the following:
   * .bin
   * .sbin
   */
  static FilenameFilter filter = new FilenameFilter()
  {
    public boolean accept(final File dir, final String name)
    {
      return /*(name.indexOf(".bin") > -1) || */ (name.indexOf(".sbin") > -1);
    }
  };

  public static void main(final String[] args)
  {
    buildRDL();
    buildAN80();
  }

  private static void buildRDL()
  {
    int objCount = 0;
    File buildDir = new File(defaultBuildFolderRDL);
    File[] folders = buildDir.listFiles();

    try
    {
      FileWriter fstream = new FileWriter("buildCount.txt");
      BufferedWriter out = new BufferedWriter(fstream);

      FileOutputStream fos = new FileOutputStream("builds.dat");
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      for(final File folder : folders)
      {
        String[] children = folder.list(filter);

        System.out.println(folder.getName());

        if(children != null)
        {
          for(final String child : children)
          {
            //           \\nas\RDL3000_UPG\Redline\PMP
            StringTokenizer st1 = new StringTokenizer(child, ".");
            String name = st1.nextToken();
            Build build = new Build();

            build.setFileName(child);

            StringTokenizer st = new StringTokenizer(name, "_");

            build.setDevice(st.nextToken());
            build.setVer(Integer.valueOf(st.nextToken()));
            build.setBuildNum(Integer.valueOf(st.nextToken()));
            oos.writeObject(build);
            objCount++;

            //Copy builds to our directory
            File fromFile = new File(defaultBuildFolderRDL + "\\" + folder.getName() + "\\" +
                                     child);
            File toFile = new File("C:\\IxResources\\InternalResources\\VerControl\\Builds\\RDL");

            if(!fromFile.exists())
            {
              throw new IOException("FileCopy: " + "no such source file: " + child);
            }

            if(!fromFile.isFile())
            {
              throw new IOException("FileCopy: " + "can't copy directory: " + child);
            }

            if(!fromFile.canRead())
            {
              throw new IOException("FileCopy: " + "source file is unreadable: " + child);
            }

            if(toFile.isDirectory())
            {
              toFile = new File(toFile, fromFile.getName());
            }

            FileInputStream from = null;
            FileOutputStream to = null;

            try
            {
              from = new FileInputStream(fromFile);
              to = new FileOutputStream(toFile);

              byte[] buffer = new byte[4096];
              int bytesRead;

              while((bytesRead = from.read(buffer)) != -1)
              {
                to.write(buffer, 0, bytesRead); // write
              }
            }
            finally
            {
              if(from != null)
              {
                try
                {
                  from.close();
                }
                catch(final IOException e)
                {
                  ;
                }
              }

              if(to != null)
              {
                try
                {
                  to.close();
                }
                catch(final IOException e)
                {
                  ;
                }
              }
            }
          }
        }
      }

      oos.flush();
      oos.close();
      out.write(String.valueOf(objCount));
      //Close the output stream
      out.close();

      FileInputStream fis = new FileInputStream("builds.dat");
      ObjectInputStream ois = new ObjectInputStream(fis);

      for(int i = 0; i < objCount; i++)
      {
        Build build = (Build)ois.readObject();

        System.out.println(build.toString());
      }

      ois.close();
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

  private static void buildAN80()
  {
    int objCount = 0;
    File buildDir = new File(defaultBuildFolderAN80);
    File[] folders = buildDir.listFiles();

    try
    {
      FileWriter fstream = new FileWriter("buildCountAn80.txt");
      BufferedWriter out = new BufferedWriter(fstream);

      FileOutputStream fos = new FileOutputStream("buildsAn80.dat");
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      for(final File folder : folders)
      {
        String[] children = folder.list(filter);

        System.out.println(folder.getName());

        if(children != null)
        {
          for(final String child : children)
          {
            //           \\nas\RDL3000_UPG\Redline\PMP
            StringTokenizer st1 = new StringTokenizer(child, ".");
            String name = st1.nextToken();
            Build build = new Build();

            build.setFileName(child);

            StringTokenizer st = new StringTokenizer(name, "_");

            build.setDevice(st.nextToken());
            build.setVer(Integer.valueOf(st.nextToken()));
            build.setBuildNum(Integer.valueOf(st.nextToken()));
            oos.writeObject(build);
            objCount++;

            //Copy builds to our directory
            File fromFile = new File(defaultBuildFolderAN80 + "\\" + folder.getName() + "\\" +
                                     child);
            File toFile = new File("C:\\IxResources\\InternalResources\\VerControl\\Builds\\AN80");

            if(!fromFile.exists())
            {
              throw new IOException("FileCopy: " + "no such source file: " + child);
            }

            if(!fromFile.isFile())
            {
              throw new IOException("FileCopy: " + "can't copy directory: " + child);
            }

            if(!fromFile.canRead())
            {
              throw new IOException("FileCopy: " + "source file is unreadable: " + child);
            }

            if(toFile.isDirectory())
            {
              toFile = new File(toFile, fromFile.getName());
            }

            FileInputStream from = null;
            FileOutputStream to = null;

            try
            {
              from = new FileInputStream(fromFile);
              to = new FileOutputStream(toFile);

              byte[] buffer = new byte[4096];
              int bytesRead;

              while((bytesRead = from.read(buffer)) != -1)
              {
                to.write(buffer, 0, bytesRead); // write
              }
            }
            finally
            {
              if(from != null)
              {
                try
                {
                  from.close();
                }
                catch(final IOException e)
                {
                  ;
                }
              }

              if(to != null)
              {
                try
                {
                  to.close();
                }
                catch(final IOException e)
                {
                  ;
                }
              }
            }
          }
        }
      }

      oos.flush();
      oos.close();
      out.write(String.valueOf(objCount));
      //Close the output stream
      out.close();

      FileInputStream fis = new FileInputStream("buildsAN80.dat");
      ObjectInputStream ois = new ObjectInputStream(fis);

      for(int i = 0; i < objCount; i++)
      {
        Build build = (Build)ois.readObject();

        System.out.println(build.toString());
      }

      ois.close();
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