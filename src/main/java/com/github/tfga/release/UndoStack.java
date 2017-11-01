package com.github.tfga.release;

import java.util.ArrayDeque;

import br.gov.mpdft.util.Callback0;

public class UndoStack
{
    ArrayDeque<Callback0> actionList = new ArrayDeque<Callback0>();
    
    public void push(Callback0 undoAction)
    {
        actionList.push(undoAction);
    }
    
    public void undoAll()
    {
        for (Callback0 action : actionList)
        {
            action.execute();
        }
    }

    public boolean isEmpty()
    {
        return actionList.isEmpty();
    }
}
