/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.entity.findings;

import java.io.Serializable;

/**
 *
 * @author shogo_saito
 */
public class TranspFindings extends Findings implements Serializable {

    public TranspFindings() {
        super();
    }

    public TranspFindings(String message) {
        super(message);
    }

    public TranspFindings(String message, int idx) {
        this(String.format(message, idx + 1));
    }

    public TranspFindings(String message, String value) {
        this(String.format(message, value));
    }

    public TranspFindings(String message, int idx, String value) {
        this(String.format(message, idx + 1, value));
    }

    @Deprecated
    public TranspFindings(String message, Object... values) {
        this(String.format(message, values));
    }
}
