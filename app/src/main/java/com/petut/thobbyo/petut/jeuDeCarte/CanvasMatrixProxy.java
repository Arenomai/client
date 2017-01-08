package com.petut.thobbyo.petut.jeuDeCarte;

import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayDeque;
import java.util.Deque;

public class CanvasMatrixProxy {
    private final Canvas mCanvas;
    private final Deque<Matrix> mStack;

    public CanvasMatrixProxy(Canvas canvas) {
        mCanvas = canvas;
        mStack = new ArrayDeque<>();
        mStack.push(new Matrix());
        mCanvas.setMatrix(mStack.peek());
    }

    public Deque<Matrix> getStack() {
        return mStack;
    }

    public Matrix getMatrix() {
        return new Matrix(mStack.peek());
    }

    public void save() {
        mCanvas.save();
        mStack.push(new Matrix(mStack.peek()));
    }

    public void concat(Matrix mtx) {
        final Matrix useMtx = mStack.peek();
        useMtx.preConcat(mtx);
        mCanvas.setMatrix(useMtx);
    }

    public void setMatrix(Matrix mtx) {
        mStack.peek().set(mtx);
        mCanvas.setMatrix(mtx);
    }

    public void restore() {
        mStack.pop();
        mCanvas.restore();
    }
}
