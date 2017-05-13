package br.gov.mpdft.tools.release.confirm;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class RenameDialogTest
{
    DialogFixture fixture;
    
    static final String NEXT_VERSION = "1.24.0.1";

    @BeforeClass
    public static void setUpOnce()
    {
        FailOnThreadViolationRepaintManager.install();
    }
    
    @Before
    public void setUp()
    {
        RenameDialog dialog = GuiActionRunner.execute(new GuiQuery<RenameDialog>()
        {
            protected RenameDialog executeInEDT()
            {
                return new RenameDialog("Temis-1.24.0.0", NEXT_VERSION);  
            }
        });
        
        fixture = new DialogFixture(dialog);
        fixture.show(); // shows the frame to test
    }
    
    @After
    public void tearDown()
    {
        fixture.cleanUp();
    }
    
//    @Test
//    /* 
//     * TODO: use Fest Assertions
//     * 
//     * Esc   => CancelledByUserException
//     * Enter => tagUrl == "tagUrl"
//     * 
//     */
//    public void confirm()
//    {
//        SwingConfirmator confirmator = new SwingConfirmator();
//        
//        String tagUrl = confirmator.confirm("Temis-1.24.0.0", "1.24.0.1");
//        
//        System.out.printf("-%s-", tagUrl);
//    }
    
    @Test
    public void windowCloseShouldCancel()
    {
        fixture.close();
        
        assertTrue(target().cancelled());
    }
    
    @Test
    public void EscShouldCancel()
    {
        fixture.textBox().pressAndReleaseKeys(KeyEvent.VK_ESCAPE);
        
        assertTrue(target().cancelled());
    }
    
    @Test
    public void EnterShouldConfirm()
    {
        fixture.textBox().pressAndReleaseKeys(KeyEvent.VK_ENTER);
        
        assertFalse(target().cancelled());
        
        assertEquals(NEXT_VERSION, target().getFilename());
    }

    RenameDialog target()
    {
        return fixture.targetCastedTo(RenameDialog.class);
    }
}
