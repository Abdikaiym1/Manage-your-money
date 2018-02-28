package com.example.asusx555l.projecttoolbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObservableArrayList<E> extends ArrayList<E> {

    public interface OnChangeListener<E> {
        void onItemRemoved(E item, int position);

        void onItemAdded(E item, int position);

        void onItemChanged(E item, E oldItem, int position);
    }

    private List<OnChangeListener<E>> listeners;

    public ObservableArrayList() {
        listeners = new ArrayList<>();
    }

    public void addListener(OnChangeListener<E> listener) {
        if (listener != null)
            listeners.add(listener);
    }

    public void removeListener(OnChangeListener<E> listener) {
        if (listener != null)
            listeners.remove(listener);
    }

    @Override
    public E set(int index, E element) {
        E e = super.set(index, element);
        for (OnChangeListener<E> listener : listeners) {
            listener.onItemChanged(element, e, index);
        }
        return e;
    }

    @Override
    public boolean add(E e) {
        boolean added = super.add(e);
        if (added) {
            for (OnChangeListener<E> listener : listeners) {
                listener.onItemAdded(e, indexOf(e));
            }
        }
        return added;
    }

    @Override
    public void add(int index, E element) {
        super.add(index, element);
        for (OnChangeListener<E> listener : listeners) {
            listener.onItemAdded(element, index);
        }
    }

    @Override
    public E remove(int index) {
        E e = super.remove(index);
        for (OnChangeListener<E> listener : listeners) {
            listener.onItemRemoved(e, index);
        }
        return e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        boolean removed = super.remove(o);
        if (removed) {
            for (OnChangeListener<E> listener : listeners) {
                listener.onItemRemoved((E) o, indexOf(o));
            }
        }
        return removed;
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return super.addAll(index, c);
    }
}
