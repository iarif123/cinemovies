package com.aweshams.cinematch.viewmodels;

/**
 * Created by irteza on 2018-05-23.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.aweshams.cinematch.utils.AutoRef;
import com.aweshams.cinematch.utils.ReferenceNullException;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Represents a ViewModel which manages a given ViewDataBinding
 *
 * @param <TBinding> The type of ViewDataBinding class to be managed
 */
public abstract class ViewModel<TBinding extends ViewDataBinding> {
    // region Instance Variables
    @NonNull
    private WeakReference<TBinding> _binding = new WeakReference<>(null);
    // endregion


    // region Properties

    /**
     * Sets the current ViewDataBinding instance to be managed
     *
     * @param binding The new ViewDataBinding instance
     */
    public final void setBinding(@Nullable TBinding binding) {
        // if we already had a binding
        try (AutoRef<TBinding> oldBinding = AutoRef.of(_binding);
             AutoRef<Context> context = AutoRef.of(oldBinding.get().getRoot().getContext())) {
            // notify the derived class that the current binding is being unset
            onBindingUnset(context, oldBinding);
        } catch (ReferenceNullException ignored) { }
        // set the new binding
        _binding = new WeakReference<>(binding);
        // if the new binding is not null
        try (AutoRef<TBinding> newBinding = AutoRef.of(_binding);
             AutoRef<Context> context = AutoRef.of(newBinding.get().getRoot().getContext())) {
            // set the values contained within the value repository
            for (int key : _values.keySet()) {
                newBinding.get().setVariable(key, _values.get(key));
            }
            // notify the derived class of the new binding being set
            onBindingSet(context, newBinding);
            // execute the binding's pending changes
            _binding.get().executePendingBindings();
        } catch (ReferenceNullException ignored) {}
    }

    /**
     * Retrieves the current ViewDataBinding
     *
     * @return the current ViewDataBinding instance being managed
     */
    @NonNull
    public final AutoRef<TBinding> getBinding() throws ReferenceNullException {
        return AutoRef.of(_binding);
    }

    /**
     * Retrieves the root view of the managed binding, or null if the binding has not been set
     *
     * @return The root view or null
     */
    @NonNull
    public final AutoRef<View> getView() throws IllegalStateException, ReferenceNullException {
        return AutoRef.of(getBinding().get().getRoot());
    }

    /**
     * Retrieves the current context used to create the data binding
     *
     * @return The context or null
     */
    @NonNull
    public final AutoRef<Context> getContext() throws IllegalStateException, ReferenceNullException {
        return AutoRef.of(getView().get().getContext());
    }
    // endregion


    // region Property Notifiers

    /**
     * Invoked when a ViewDataBinding instance has been set. This is where the ViewModel should
     * initialize the Component UI via the ViewDataBinding.
     *
     * @param binding The new ViewDataBinding instance that has been bound
     */
    protected void onBindingSet(@NonNull AutoRef<Context> context, @NonNull AutoRef<TBinding> binding) {
    }

    /**
     * Invoked when the current ViewDataBinding instance has been unset from this ViewModel. This
     * should be where the derived ViewModel class should perform any cleanup necessary to be
     * prepared for a new ViewDataBinding to be bound.
     *
     * @param oldBinding The ViewDataBinding being removed
     */
    protected void onBindingUnset(@NonNull AutoRef<Context> context, @NonNull AutoRef<TBinding> oldBinding) {
    }
    // endregion


    // region Value Setter/Getter
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Object> _values = new HashMap<>();

    @SuppressWarnings("UnusedReturnValue")
    protected boolean setValue(int id, @Nullable Object value) {
        if (_values.containsKey(id) && value == _values.get(id)) {
            return false;
        }
        _values.put(id, value);
        try (AutoRef<TBinding> binding = getBinding()) {
            binding.get().setVariable(id, value);
        } catch (ReferenceNullException ignored) {}
        return true;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    protected <T> T getValue(int id, @NonNull Class<T> clazz) {
        if (!_values.containsKey(id)) {
            return null;
        }
        Object value = _values.get(id);
        if (value != null && clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }
}