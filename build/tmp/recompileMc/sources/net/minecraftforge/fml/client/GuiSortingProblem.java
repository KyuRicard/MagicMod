package net.minecraftforge.fml.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.toposort.ModSortingException;
import net.minecraftforge.fml.common.toposort.ModSortingException.SortingExceptionData;

public class GuiSortingProblem extends GuiScreen {
    private SortingExceptionData<ModContainer> failedList;

    public GuiSortingProblem(ModSortingException modSorting)
    {
        this.failedList = modSorting.getExceptionData();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui()
    {
        super.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     *  
     * @param mouseX Mouse x coordinate
     * @param mouseY Mouse y coordinate
     * @param partialTicks How far into the current tick (1/20th of a second) the game is
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        int offset = Math.max(85 - (failedList.getVisitedNodes().size() + 3) * 10, 10);
        this.drawCenteredString(this.fontRendererObj, "Forge Mod Loader has found a problem with your minecraft installation", this.width / 2, offset, 0xFFFFFF);
        offset+=10;
        this.drawCenteredString(this.fontRendererObj, "A mod sorting cycle was detected and loading cannot continue", this.width / 2, offset, 0xFFFFFF);
        offset+=10;
        this.drawCenteredString(this.fontRendererObj, String.format("The first mod in the cycle is %s", failedList.getFirstBadNode()), this.width / 2, offset, 0xFFFFFF);
        offset+=10;
        this.drawCenteredString(this.fontRendererObj, "The remainder of the cycle involves these mods", this.width / 2, offset, 0xFFFFFF);
        offset+=5;
        for (ModContainer mc : failedList.getVisitedNodes())
        {
            offset+=10;
            this.drawCenteredString(this.fontRendererObj, String.format("%s : before: %s, after: %s", mc.toString(), mc.getDependants(), mc.getDependencies()), this.width / 2, offset, 0xEEEEEE);
        }
        offset+=20;
        this.drawCenteredString(this.fontRendererObj, "The file 'ForgeModLoader-client-0.log' contains more information", this.width / 2, offset, 0xFFFFFF);
    }

}