package com.hinge.challenge.largephoto.util;

import io.reactivex.observers.DisposableObserver;

public class TestDisposableObserver<T> extends DisposableObserver<T>
{
    private int valueCount = 0;
    private boolean isComplete = false;
    private boolean hasReturned = false;

    public int getValueCount()
    {
        return valueCount;
    }

    public boolean isComplete()
    {
        return this.isComplete;
    }

    public boolean hasReturned()
    {
        return this.hasReturned;
    }

    @Override
    public void onNext(T value)
    {
        valueCount++;
        this.hasReturned = true;
    }

    @Override
    public void onError(Throwable e)
    {
        System.out.print(e.getMessage());
        this.hasReturned = true;
    }

    @Override
    public void onComplete()
    {
        this.isComplete = true;
        this.hasReturned = true;
    }
}
