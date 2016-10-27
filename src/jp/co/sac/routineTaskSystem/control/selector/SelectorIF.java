package jp.co.sac.routineTaskSystem.control.selector;

/**
 *
 * @author shogo_saito
 */
public interface SelectorIF<T, U> {
    public interface SelectTarget<T> {
        public T newInstance();
    }
    public T select(U u);
}
