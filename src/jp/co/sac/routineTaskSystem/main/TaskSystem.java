package jp.co.sac.routineTaskSystem.main;

import java.util.Arrays;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.flow.DocumentCheckFlow;
import jp.co.sac.routineTaskSystem.flow.IMUpdateRosterFlow;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class TaskSystem {

    private static Logger log = Logger.getLogger("root");
    private static int exitStatus = 0;

    public static void main(String[] args) throws Exception {
        String mode = "";
        String[] filePaths = new String[0];
        if (args != null && args.length > 0) {
            mode = args[0];
            filePaths = Arrays.copyOfRange(args, 1, args.length);
        }
        
        switch (mode) {
            case "AutoIMCsv": {
                IMUpdateRosterFlow flow = new IMUpdateRosterFlow();
                exitStatus = flow.onCheck(filePaths);
                break;
            }
            case "DocCheck":
            default: {
                DocumentCheckFlow flow = new DocumentCheckFlow();
                exitStatus = flow.onCheck(filePaths);
                break;
            }
        }
        System.exit(exitStatus);
    }
}
