package jp.co.sac.routineTaskSystem.main;

import jp.co.sac.routineTaskSystem.flow.DocumentCheckFlow;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class TaskSystem {

    private static Logger log = Logger.getLogger("root");
    private static int exitStatus = 0;

    public static void main(String[] args) throws Exception {
        DocumentCheckFlow flow = new DocumentCheckFlow();
        exitStatus = flow.onCheck(args);
        System.exit(exitStatus);
    }
}
