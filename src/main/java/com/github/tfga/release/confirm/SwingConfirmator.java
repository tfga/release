package com.github.tfga.release.confirm;

import com.github.tfga.release.exceptions.CancelledByUserException;

public class SwingConfirmator implements Confirmator
{
    @Override
    public String confirm(String tagName, String nextVersion)
    {
        RenameDialog dialog = new RenameDialog(tagName, nextVersion);
        
        dialog.setVisible(true); // vai travar aqui
        
        if (dialog.cancelled())
            throw new CancelledByUserException();

        return dialog.getFilename();
    }
}
