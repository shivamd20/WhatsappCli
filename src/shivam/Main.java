package shivam;


import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {


// SELECT  data, key_remote_jid,  timestamp FROM `messages` where key_from_me=0  and data not null ORDER BY `timestamp` DESC LIMIT 0, 50 ;

    public static void command(String ...args) throws  Exception{

        if(args.length == 0) throw  new IllegalArgumentException("command not supplied");

      switch (args[0]){


          case "send": sendMessege(args[1],args[2]); break;

          case "init" : initialise();

           default: throw new IllegalArgumentException("illegal command");


      }

    }





    public static void initialise() throws Exception{

      Process p1 =  Runtime.getRuntime().exec("adb push app-debug.apk /data/local/tmp/com.example.shivamdwivedi.automator");

      printInputStream(p1.getInputStream());

        p1.waitFor();

        Process p2 = Runtime.getRuntime().exec("adb shell pm install -t -r \"/data/local/tmp/com.example.shivamdwivedi.automator\"");

        printInputStream(p2.getInputStream());

        p2.waitFor();

        Process p3 =  Runtime.getRuntime().exec("adb push app-debug-androidTest.apk /data/local/tmp/com.example.shivamdwivedi.automator.test");

        printInputStream(p3.getInputStream());

        p3.waitFor();

        Process p4 = Runtime.getRuntime().exec("adb shell pm install -t -r \"/data/local/tmp/com.example.shivamdwivedi.automator.test\"");

        printInputStream(p4.getInputStream());

        p4.waitFor();





    }

    static void printInputStream(InputStream ip){

              InputStream inputStream =  ip;

              System.out.println("");

      Scanner sc = new Scanner(inputStream);

       while (sc.hasNext()){

          System.out.print(sc.nextLine());
           System.out.println("");
     }

        System.out.println("");





    }


    public static void main(String[] args)   {




        try{


         Process p =   Runtime.getRuntime().exec("emulator -avd Pixel_2_XL_API_28");

        p.waitFor(20,TimeUnit.SECONDS);






            initialise();

            command(new String[]{

                    "send",
                    "917389630407",
                    "how are you"

            });

        }


        catch (Exception e){


            System.err.print(e.getMessage());

        }

    }


    public static void validateMobileNumber(String num) {


        if (num == null) throw new NullPointerException("number not supplied");
        if (num.length() != 12) {
            throw new IllegalArgumentException("Invalid Number");

        }
        Long.parseLong(num);
    }

    public static void validateMSG(String msg) {

//        if(msg.contains("/n")) throw new IllegalArgumentException("contains /");

        if (msg.trim().length() < 1) throw new IllegalArgumentException("msg too short");

        if (msg == null) throw new NullPointerException("msg not supplied");


    }


    public static void sendMessege(String mobileNumber, String msg) throws  Exception {

       msg =  msg.replace(" ","@@@");

        msg = msg.replaceAll("\\r\\n|\\r|\\n", "@@@");

       msg = msg.replace('\\', '!');


        validateMobileNumber(mobileNumber);
        validateMSG(msg);

        long mobile = Long.parseLong(mobileNumber);


       Process process = Runtime.getRuntime().exec("adb shell am instrument -w -r   -e debug false -e msg "+ msg  +" -e mobile "+mobile+"  -e class 'com.example.shivamdwivedi.automator.ChangeTextBehaviorTest' com.example.shivamdwivedi.automator.test/android.support.test.runner.AndroidJUnitRunner");


       System.out.println("sending ");



      process.waitFor(1, TimeUnit.MINUTES);

        System.out.print( process.exitValue());




    }
}
