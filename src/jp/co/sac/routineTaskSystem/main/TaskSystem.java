package jp.co.sac.routineTaskSystem.main;

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
        String mode = (args != null && args.length > 0) ? args[0] : null;
        switch (mode) {
            case "AutoIMCsv": {
                IMUpdateRosterFlow flow = new IMUpdateRosterFlow();
                exitStatus = flow.onCheck(args);
                break;
            }
            default: {
                DocumentCheckFlow flow = new DocumentCheckFlow();
                exitStatus = flow.onCheck(args);
                break;
            }
        }
        System.exit(exitStatus);
    }
}
