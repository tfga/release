package br.gov.mpdft.tools.release;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import br.gov.mpdft.tools.release.UndoStack;
import br.gov.mpdft.util.Callback0;



public class UndoStackTest
{
    @Test
    public void undoAll()
    {
        final ArrayList<Integer> log = new ArrayList<Integer>();
        
        Callback0 undoAction1 = new Callback0()
        {
            @Override
            public void execute()
            {
                log.add(1);
            }
        };
        
        Callback0 undoAction2 = new Callback0()
        {
            @Override
            public void execute()
            {
                log.add(2);
            }
        };
        
        UndoStack undoStack = new UndoStack();
        undoStack.push(undoAction1);
        undoStack.push(undoAction2);

        undoStack.undoAll();
        
        assertEquals(Arrays.asList(2, 1), log);
    }
}
