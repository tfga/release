package br.gov.mpdft.tools.release.confirm;

import br.gov.mpdft.tools.release.exceptions.CancelledByUserException;

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
