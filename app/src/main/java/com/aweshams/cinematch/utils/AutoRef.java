package com.aweshams.cinematch.utils;

/**
 * Created by irteza on 2018-05-23.
 */

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Represents an AutoCloseable that contains a single Strong Reference until close() is invoked.
 * Note: To be used with a Try-with-Resources block.
 *
 * @param <T> Value type for strong reference
 * @author AFryer
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class AutoRef<T> implements AutoCloseable {
    // region Instance Variables
    private T ref;
    private StackTraceElement[] stackTrace;
    // endregion


    // region Properties

    /**
     * Retrieves the strong referenced instance
     *
     * @return The strong reference
     */
    @NonNull
    public T get() {
        if (ref == null) {
            throw new IllegalStateException("Attempting to use internal reference of a closed AutoRef");
        }
        return ref;
    }

    /**
     * Returns {@code true} if {@code close()} has been called on this {@link AutoRef}; otherwise {@code false}.
     *
     * @return {@code true} if {@code close()} was called; otherwise {@code false}.
     */
    public boolean isClosed() {
        return ref == null;
    }
    // endregion


    // region Constructors

    /**
     * Constructor of {@link AutoRef} for a reference value
     *
     * @param referent strong reference
     * @throws ReferenceNullException Thrown if the {@param referent} is {@code null}
     */
    private AutoRef(T referent) throws ReferenceNullException {
        this(referent, Thread.currentThread().getStackTrace());
    }

    /**
     * Constructor of {@link AutoRef} for a reference value
     *
     * @param referent   strong reference
     * @param stackTrace Stack trace at point of creation
     * @throws ReferenceNullException Thrown if the {@param referent} is {@code null}
     */
    private AutoRef(T referent, @Nullable StackTraceElement[] stackTrace) throws ReferenceNullException {
        if (referent == null) {
            throw new ReferenceNullException("referent must not be null");
        }
        ref = referent;
        this.stackTrace = stackTrace;
    }

    @Override
    protected void finalize() throws Throwable {
        if (ref != null) {
            close();
            Exception inner = new Exception("Creation Point");
            inner.setStackTrace(this.stackTrace);
            throw new RuntimeException("TempRef.Close was not called before finalization", inner);
        }
        super.finalize();
    }
    // endregion


    // region Public Methods

    /**
     * Helper for creating a {@link WeakReference} from this {@link AutoRef}
     */
    public WeakReference<T> weak() {
        return new WeakReference<>(ref);
    }

    /**
     * Helper for creating a {@link SoftReference} from this {@link AutoRef}
     */
    public SoftReference<T> soft() {
        return new SoftReference<>(ref);
    }

    /**
     * Clears the strong reference held by this {@link AutoRef}
     */
    @Override
    public void close() {
        ref = null;
    }
    // endregion


    // region Static Factory Methods

    /**
     * Creates an instance of the {@link AutoRef} from a reference value
     *
     * @param referent strong reference
     * @param <T>      type of the strong reference
     * @return The new {@link AutoRef}
     * @throws ReferenceNullException Thrown if the {@param referent} is {@code null}
     */
    @NonNull
    public static <T> AutoRef<T> of(@Nullable T referent) throws ReferenceNullException {
        return new AutoRef<>(referent, Thread.currentThread().getStackTrace());
    }

    /**
     * Creates an instance of the {@link AutoRef} from a reference value
     *
     * @param referent   strong reference
     * @param <T>        type of the strong reference
     * @param stackTrace Stack trace at point of creation
     * @return The new {@link AutoRef}
     * @throws ReferenceNullException Thrown if the {@param referent} is {@code null}
     */
    @NonNull
    public static <T> AutoRef<T> of(@Nullable T referent, @Nullable StackTraceElement[] stackTrace) throws ReferenceNullException {
        return new AutoRef<>(referent, stackTrace);
    }

    /**
     * Creates an instance of the {@link AutoRef} from a reference value
     *
     * @param reference {@link WeakReference} object
     * @param <T>       type of the strong reference
     * @return The new {@link AutoRef}
     * @throws ReferenceNullException Thrown if the internal value of {@param reference} is {@code null}
     */
    @NonNull
    public static <T> AutoRef<T> of(@NonNull WeakReference<T> reference) throws ReferenceNullException {
        return new AutoRef<>(reference.get(), Thread.currentThread().getStackTrace());
    }

    /**
     * Creates an instance of the {@link AutoRef} from a reference value
     *
     * @param reference  {@link WeakReference} object
     * @param <T>        type of the strong reference
     * @param stackTrace Stack trace at point of creation
     * @return The new {@link AutoRef}
     * @throws ReferenceNullException Thrown if the internal value of {@param reference} is {@code null}
     */
    @NonNull
    public static <T> AutoRef<T> of(@NonNull WeakReference<T> reference, @Nullable StackTraceElement[] stackTrace) throws ReferenceNullException {
        return new AutoRef<>(reference.get(), stackTrace);
    }

    /**
     * Creates an instance of the {@link AutoRef} from a reference value
     *
     * @param reference {@link SoftReference} object
     * @param <T>       type of the strong reference
     * @return The new {@link AutoRef}
     * @throws ReferenceNullException Thrown if the internal value of {@param reference} is {@code null}
     */
    @NonNull
    public static <T> AutoRef<T> of(@NonNull SoftReference<T> reference) throws ReferenceNullException {
        return new AutoRef<>(reference.get(), Thread.currentThread().getStackTrace());
    }

    /**
     * Creates an instance of the {@link AutoRef} from a reference value
     *
     * @param reference  {@link SoftReference} object
     * @param <T>        type of the strong reference
     * @param stackTrace Stack trace at point of creation
     * @return The new {@link AutoRef}
     * @throws ReferenceNullException Thrown if the internal value of {@param reference} is {@code null}
     */
    @NonNull
    public static <T> AutoRef<T> of(@NonNull SoftReference<T> reference, @Nullable StackTraceElement[] stackTrace) throws ReferenceNullException {
        return new AutoRef<>(reference.get(), stackTrace);
    }

    /**
     * Creates an instance of the {@link AutoRef} from a reference value
     *
     * @param reference {@link StrongReference} object
     * @param <T>       type of the strong reference
     * @return The new {@link AutoRef}
     * @throws ReferenceNullException Thrown if the internal value of {@param reference} is {@code null}
     */
    @NonNull
    public static <T> AutoRef<T> of(@NonNull StrongReference<T> reference) throws ReferenceNullException {
        return new AutoRef<>(reference.value, Thread.currentThread().getStackTrace());
    }

    /**
     * Creates an instance of the {@link AutoRef} from a reference value
     *
     * @param reference  {@link StrongReference} object
     * @param <T>        type of the strong reference
     * @param stackTrace Stack trace at point of creation
     * @return The new {@link AutoRef}
     * @throws ReferenceNullException Thrown if the internal value of {@param reference} is {@code null}
     */
    @NonNull
    public static <T> AutoRef<T> of(@NonNull StrongReference<T> reference, @Nullable StackTraceElement[] stackTrace) throws ReferenceNullException {
        return new AutoRef<>(reference.value, stackTrace);
    }
    // endregion
}
