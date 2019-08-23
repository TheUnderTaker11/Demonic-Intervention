package com.theundertaker11.demonicintervention.items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.util.ModUtils;
import com.theundertaker11.demonicintervention.util.NBTKeys;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEssenceCollector extends BaseItem{

	public ItemEssenceCollector(String name) {
		super(name);
		this.setMaxDamage(0);
		this.setMaxStackSize(8);
		this.setHasSubtypes(true);
	}
	
	public static EntityPlayer getPlayerOffItem(ItemStack stack, World world) {
		NBTTagCompound tag = stack.getTagCompound();
		if(world.isRemote || tag == null) return null;
		EntityPlayer player = null;
		try{
			player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(UsernameCache.getLastKnownUsername(tag.getUniqueId(NBTKeys.TARGET_UUID)));
		}catch(NullPointerException e) {}
		return player;
	}

	public static EntityLivingBase getEntityLivingOffItem(ItemStack stack, World world) {
		Entity ent = getEntityOffItem(stack, world);
		if(ent instanceof EntityLivingBase)
			return (EntityLivingBase)ent;
		else return null;
	}
	
	public static Entity getEntityOffItem(ItemStack stack, World world) {
		NBTTagCompound tag = stack.getTagCompound();
		if(world.isRemote || tag == null) return null;
		Entity ent = null;
		try{
			ent = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityFromUuid(tag.getUniqueId(NBTKeys.TARGET_UUID));
		}catch(NullPointerException e) {}
		if(ent == null) {
			try{
				ent = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(tag.getUniqueId(NBTKeys.TARGET_UUID));
			}catch(NullPointerException e) {}
		}
		if(ent == null) {
			try{
				ent = world.getMinecraftServer().getEntityFromUuid(tag.getUniqueId(NBTKeys.TARGET_UUID));
			}catch(NullPointerException e) {}
		}
		return ent;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		if(stack.getItemDamage()==0) {
			
		}
		else {
			NBTTagCompound tag = ModUtils.getTagCompound(stack);
			tooltip.add("Essence of " + tag.getString(NBTKeys.TARGET_NAME));
		}
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		if(!worldIn.isRemote && playerIn.isSneaking() && hand == EnumHand.MAIN_HAND) {
			ItemStack newStack = playerIn.getHeldItemMainhand().copy();
			playerIn.getHeldItemMainhand().setCount(playerIn.getHeldItemMainhand().getCount()-1);
			newStack.setCount(1);
			newStack.setItemDamage(1);
			newStack.setTagCompound(new NBTTagCompound());
			newStack.getTagCompound().setUniqueId(NBTKeys.TARGET_UUID, playerIn.getPersistentID());
			newStack.getTagCompound().setString(NBTKeys.TARGET_NAME, playerIn.getName());
			if(!playerIn.addItemStackToInventory(newStack))
				playerIn.getEntityWorld().spawnEntity(new EntityItem(playerIn.getEntityWorld(), playerIn.posX, playerIn.posY, playerIn.posZ, newStack));
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(hand));
    }
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if(player.getEntityWorld().isRemote) return false;
		if(stack.getItemDamage()!=0) return false;
		final Random random = new Random();
		if(random.nextBoolean()) {
			ItemStack newStack = stack.copy();
			stack.setCount(stack.getCount()-1);
			newStack.setCount(1);
			newStack.setItemDamage(1);
			newStack.setTagCompound(new NBTTagCompound());
			newStack.getTagCompound().setUniqueId(NBTKeys.TARGET_UUID, entity.getPersistentID());
			newStack.getTagCompound().setString(NBTKeys.TARGET_NAME, entity.getName());
			if(!player.addItemStackToInventory(newStack))
				player.getEntityWorld().spawnEntity(new EntityItem(player.getEntityWorld(), player.posX, player.posY, player.posZ, newStack));
			
			return true;
		}
		else {
			player.sendMessage(new TextComponentString(I18n.format("entitymessage.failedessencegrab.name")));
			entity.sendMessage(new TextComponentString(I18n.format("entitymessage.failedessencegrabvictim.name")));
		}
        return false;
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
    {
        if(stack.getItemDamage()==0) {
        	return "item.essence";
        }
        else {
        	return "item.essence2";
        }
    }
}
