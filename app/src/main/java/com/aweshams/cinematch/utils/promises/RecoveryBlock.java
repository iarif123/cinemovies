package com.aweshams.cinematch.utils.promises;

/**
 * Created by irteza on 2018-01-04.
 */
public interface RecoveryBlock {
    /**
     * Execute.
     *
     * @param error    the error
     * @param recovery the recovery
     */
    void execute(Exception error, Recovery recovery) throws Exception;
}
