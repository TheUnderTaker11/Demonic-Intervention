package com.theundertaker11.demonicintervention.items;

import com.theundertaker11.demonicintervention.DemonicInterventionMain;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemVampireBook extends ItemWrittenBook{

	public ItemVampireBook(String name) {
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setHasSubtypes(true);
		this.setCreativeTab(DemonicInterventionMain.DICreativeTab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (tab == DemonicInterventionMain.DICreativeTab)
        {
        	ItemStack stackReal = new ItemStack(Items.WRITTEN_BOOK);
        	ItemStack stackFake = new ItemStack(Items.WRITTEN_BOOK);
        	NBTTagCompound tagReal = new NBTTagCompound();
        	NBTTagCompound tagFake = new NBTTagCompound();
        	setRealTag(tagReal);
        	setFakeTag(tagFake);
        	stackReal.setTagCompound(tagReal);
        	stackFake.setTagCompound(tagFake);
            items.add(stackReal);
            items.add(stackFake);
        }
    }
	
	public static void setRealTag(NBTTagCompound tag) {
		tag.setString("title", "The Fallen Man");
    	tag.setString("author", "Laer");
    	NBTTagList bookpages = new NBTTagList();
    	bookpages.appendTag(new NBTTagString("'Journal Entry #1 \nI recently discovered a portal to the hell world. Unlike what I had heard before, I found some strange flowers there. It is almost as if one of them whispered to me, telling me to take it. Would have felt wrong if I didnt.'"));
    	bookpages.appendTag(new NBTTagString("'Journal Entry #2 \nThe flower speaks to me. Tells me about the great power that can be obtained if I trust it. Do I trust it?'"));
    	bookpages.appendTag(new NBTTagString("'Journal Entry #3 \nA leap. I must leap. Death will only pass me by, the flower promises. ja. No need for Afterlife. Sunset nevermore. afd.'"));
    	bookpages.appendTag(new NBTTagString("'Journal Entry #4 \nMy last entry in this short journal. I jumped, I lived. The flower does not lead me astray. I thought myself crazy, but I just heard the truth. There is no turning back now, the flower says I must take the life of another. I took the jump, now I must take the leap of faith.'"));
    	bookpages.appendTag(new NBTTagString("'I must kill another person, using the shining heart of a wither. Then the power will be mine. The Golden Chalice will save me.'"));
    	tag.setTag("pages", bookpages);
	}
	
	public static void setFakeTag(NBTTagCompound tag) {
		tag.setString("title", "The Drowned Man");
    	tag.setString("author", "Ekaf");
    	NBTTagList bookpages = new NBTTagList();
    	bookpages.appendTag(new NBTTagString("'Dear Faith \nMy sweet sister, I write to you with news from the town! Lord Vlad has died, but that is not all! There have been rumors that somehow he has been brought back. The townspeople are saying he went through the obsidian portal, and came back with a flower.'"));
    	bookpages.appendTag(new NBTTagString("'Within the next day they found him drowned in the well, but before they could bury him the body was gone! They are saying he became undead, because since then people around the town have gone missing, and some have even be found with all their blood drained! I warn you'"));
    	bookpages.appendTag(new NBTTagString("'dear sister, do not return until the town is safe again, no one knows how long this reign of terror will last.\nLove, Ekaf'"));
    	tag.setTag("pages", bookpages);
	}

}
