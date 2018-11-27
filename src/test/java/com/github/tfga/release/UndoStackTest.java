package com.github.tfga.release;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.github.tfga.release.util.Callback0;



public class UndoStackTest
{
    @Test
    public void undoAll()
    {
        final ArrayList<Integer> log = new ArrayList<Integer>();
        
        Callback0 undoAction1 = () -> log.add(1);
        
        Callback0 undoAction2 = () -> log.add(2);
        
        UndoStack undoStack = new UndoStack();
        undoStack.push(undoAction1);
        undoStack.push(undoAction2);

        undoStack.undoAll();
        
        assertEquals(Arrays.asList(2, 1), log);
    }
}
