package com.theundertaker11.demonicintervention.blocks.ritualmainblock;

import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.api.pedestalcrafting.PedestalEssenceRecipe;
import com.theundertaker11.demonicintervention.api.pedestalcrafting.PedestalRecipe;
import com.theundertaker11.demonicintervention.api.pedestalcrafting.PedestalUtils;
import com.theundertaker11.demonicintervention.blocks.pedestal.TilePedestal;
import com.theundertaker11.demonicintervention.init.BlockRegistry;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.items.ItemEssenceCollector;
import com.theundertaker11.demonicintervention.tile.ItemStoringTileBase;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.block.BlockQuartz;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileRitualMain extends ItemStoringTileBase implements ITickable{

	private int tier;
	private int ticksWorking;
	protected UUID lastPlayer;
	//private int ticksNeeded;
	
	public TileRitualMain() {super();}
	
	public ItemStack getStoredStack()
	{
		return itemStackHandler.getStackInSlot(0);
	}
	/**
	 * All but normal crafting needs manually updated to client with packets..... GROSS EWWWW
	 */
	@Override
	public void update() {
		short canCraft = canCraft();
		if(canCraft == 1) { // Is normal pedestal crafting
			if(ticksWorking < 0)
				ticksWorking = 0;
			PedestalRecipe recipe = PedestalUtils.getRecipe(getStoredStack());
			if(ticksWorking >= recipe.getTimeNeeded()) {
				ticksWorking = 0;
				clearPedestals();
				itemStackHandler.setStackInSlot(0, recipe.getResultingStack());
				this.markDirty();
				EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), true);
				world.spawnEntity(lightning);
				recipe.onFinish(pos, world);
			}else {
				ticksWorking++;
				doLightningLogic(recipe.getTimeNeeded());
			}
		}
		else if(canCraft == 2) { //Is taglock ritual
			if(ticksWorking < 0)
				ticksWorking = 0;
			PedestalEssenceRecipe ritual = getEssenceRitual();
			if(ritual == null) return;
			doLightningLogic(ritual.getTimeNeeded());
			if(ticksWorking >= ritual.getTimeNeeded()) {
				EntityPlayer caster = world.getMinecraftServer().getPlayerList().getPlayerByUUID(lastPlayer);
				if(caster == null) return;
				EntityLivingBase target = ItemEssenceCollector.getEntityLivingOffItem(this.getStoredStack(), world);
				ticksWorking = 0;
				clearPedestals();
				itemStackHandler.setStackInSlot(0, ritual.getResultingStack());
				this.markDirty();
				ritual.onFinish(this.getPos(), caster, target);
				EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), true);
				world.spawnEntity(lightning);
			}else {
				ticksWorking++;
			}
		}
		else {
			ticksWorking = 0;
		}
	}
	/**
	 * 
	 * @return 0 for can't, 1 for normal recipe, 2 for Taglock recipe
	 */
	private short canCraft() {
		if(getStoredStack().isEmpty())
			return 0;
		this.updateTier();
		this.updateAllTiles();
		if(getStoredStack().getItem() == ItemRegistry.essence && this.lastPlayer != null){
			return canCraftSpecial();
		}
		PedestalRecipe recipe = PedestalUtils.getRecipe(getStoredStack());
		if(recipe != null){
			if( (recipe.karmaType() < 0 && recipe.getTier() >= this.getTierNoUpdate()) 
					|| (recipe.karmaType() > 0 && recipe.getTier() <= this.getTierNoUpdate())
					|| (recipe.karmaType() == 0 && Math.abs(recipe.getTier()) <= Math.abs(this.getTierNoUpdate()))) {
				NonNullList<ItemStack> copyList = ModUtils.createCopyItemList(recipe.getItemList());
				switch(recipe.getTier()) {
				case 1:
					return t1CheckIfAllItemsThere(copyList);
				case -1:
					return t1CheckIfAllItemsThere(copyList);
				default:
					return 0;
				}
			}else return 0;
		}
		return 0;
	}
	/**
	 * Currently just for when a taglock is the main ingredient.
	 * @return
	 */
	private short canCraftSpecial() {
		EntityLivingBase entityLiving = ItemEssenceCollector.getEntityLivingOffItem(this.getStoredStack(), world);
		if(entityLiving == null) {
			entityLiving = ItemEssenceCollector.getPlayerOffItem(this.getStoredStack(), world);
		}
		if(entityLiving != null) {
			NonNullList<ItemStack> list = NonNullList.create();
			for(TilePedestal tile : this.getT1Pedestals()) {
				list.add(tile.getStoredStack());
			}
			PedestalRecipe ritual = PedestalUtils.getRitual(list);
			if(ritual != null) {
				System.out.println("OOPSY DAISY");
				if(ritual instanceof PedestalEssenceRecipe) {
					if(((PedestalEssenceRecipe) ritual).isOnlyPlayers() && !(entityLiving instanceof EntityPlayer))
						return 0;
					else 
						return 2;
				}else {
					return 0;
				}
			}else return 0;
		}
		return 0;
	}
	/**
	 * Looks if all the items in the copyList are on the T1 pedestals
	 * @param copyList
	 * @return
	 */
	private short t1CheckIfAllItemsThere(NonNullList<ItemStack> copyList) {
		if(this.getTierNoUpdate() == 0)
			return 0;
		for(TilePedestal tile : this.getT1Pedestals()) {
			for(int i=0 ; i < copyList.size(); i++) {
				ItemStack copyStack = copyList.get(i);
				if(ModUtils.compareItems(copyStack, tile.getStoredStack())) {
					copyList.set(i, ItemStack.EMPTY);
					break;
				}
			}
		}
		for(ItemStack copyStack : copyList)
		{
			if(!copyStack.isEmpty())
				return 0;
		}
		
		return 1;
	}
	/**
	 * Uses current stored items to determine ritual when essence is center item
	 * @return
	 */
	private PedestalEssenceRecipe getEssenceRitual() {
		if(this.getStoredStack().getItem() != ItemRegistry.essence) return null;
		
		NonNullList<ItemStack> list = NonNullList.create();
		for(TilePedestal tile : this.getT1Pedestals()) {
			list.add(tile.getStoredStack());
		}
		return PedestalUtils.getEssenceRitual(list);
	}
	private void clearPedestals() {
		if(this.getTierNoUpdate() == 1 || this.getTierNoUpdate() == -1) {
			for(TilePedestal tile : this.getT1Pedestals()) {
				tile.setStoredStack(ItemStack.EMPTY);
			}
		}
		
	}
	
	private void doLightningLogic(int time) {
		if(ticksWorking == (int)((double)time * (0.2D))) {
			EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX()+3, pos.getY()+2, pos.getZ(), true);
			world.spawnEntity(lightning);
		}
		if(ticksWorking == (int)((double)time * (0.4D))) {
			EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX()-3, pos.getY()+2, pos.getZ(), true);
			world.spawnEntity(lightning);
		}
		if(ticksWorking == (int)((double)time * (0.6D))) {
			EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX(), pos.getY()+2, pos.getZ()+3, true);
			world.spawnEntity(lightning);
		}
		if(ticksWorking == (int)((double)time * (0.8D))) {
			EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX(), pos.getY()+2, pos.getZ()-3, true);
			world.spawnEntity(lightning);
		}
	}
	//If you overwrite this make sure to call the super
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("inputitem", itemStackHandler.serializeNBT());
		if(this.lastPlayer != null) compound.setUniqueId("lastPlayerUUID", this.lastPlayer);
		return super.writeToNBT(compound);
	}
		
		//If you overwrite this make sure to call the super
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("inputitem"))
		{
			itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("inputitem"));
		}
		if(compound.hasKey("lastPlayerUUID")) {
			this.lastPlayer = compound.getUniqueId("lastPlayerUUID");
		}
	}
	/**
	 * Set this to a variable, cause it does the whole multiblock check each time this is called.
	 * 
	 * @return Negative if evil, positive if holy
	 */
	public int getTier() {
		this.updateTier();
		return this.tier;
	}
	/**
	 * Just gets what tier was last time the multiblock was updated.
	 * @return
	 */
	public int getTierNoUpdate() {
		return this.tier;
	}
	private void updateTier() {
		tier = 0;
		if(this.isTier1(true)) tier = -1;
		if(this.isTier1(false)) tier = 1;
		if(this.isTier2(true)) tier = -2;
		if(this.isTier2(false)) tier = 2;
		
	}
	public boolean isTier1(boolean isEvil) {
		if(!isEvil && checkTier1Good())
				return true;
		if(isEvil && checkTier1Evil())
				return true;

		return false;
	}
	public boolean isTier2(boolean isEvil) {
		
		return false;
	}
	
	private boolean checkTier1Good() {
		if(checkLava() && checkRingTier1Good() && checkPedestalsT1()) {
			return true;
		}
		return false;
	}
	private boolean checkTier1Evil() {
		if(checkLava() && checkRingTier1Evil() && checkPedestalsT1()) {
			return true;
		}
		return false;
	}
	private boolean checkLava() {
		if(world.getBlockState(pos.up()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().north()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().south()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().east()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().west()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().north().east()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().north().west()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().south().east()).getBlock() == Blocks.LAVA
				&& world.getBlockState(pos.up().south().west()).getBlock() == Blocks.LAVA
				) {
			return true;
		}
		return false;
	}
	//How to find quartz pillar- world.getBlockState(pos.up().north(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(2)).getValue(BlockQuartz.VARIANT).getName().equals("lines_y")
	
	/**
	 * By ring I mean ring around the blocks of lava
	 * @return
	 */
	private boolean checkRingTier1Good() {
		if(world.getBlockState(pos.up().north(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().north(3)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(3)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().east(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().east(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().east(3)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().east(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().west(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().west(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().west(3)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().west(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")

				&& world.getBlockState(pos.up().north(2).east()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2).east()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().north(2).east(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2).east(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				
				&& world.getBlockState(pos.up().north(2).west()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2).west()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().north(2).west(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().north(3)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().south(2).west(2)).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().south(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
					
				&& world.getBlockState(pos.up().east(2).south()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().east(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().west(2).north()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().west(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().east(2).north()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().east(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")
				&& world.getBlockState(pos.up().west(2).south()).getBlock() == Blocks.QUARTZ_BLOCK && world.getBlockState(pos.up().west(2)).getValue(BlockQuartz.VARIANT).getName().equals("default")

				){
			return true;
		}
		return false;
	}
	/**
	 * By ring I mean ring around the blocks of lava
	 * @return
	 */
	private boolean checkRingTier1Evil() {
		if(world.getBlockState(pos.up().north(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().north(3)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(3)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().west(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().west(3)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().east(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().east(3)).getBlock() == Blocks.RED_NETHER_BRICK
				
				&& world.getBlockState(pos.up().north(2).east()).getBlock() == Blocks.RED_NETHER_BRICK 
				&& world.getBlockState(pos.up().south(2).east()).getBlock() == Blocks.RED_NETHER_BRICK 
				&& world.getBlockState(pos.up().north(2).east(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(2).east(2)).getBlock() == Blocks.RED_NETHER_BRICK
				
				&& world.getBlockState(pos.up().north(2).west()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(2).west()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().north(2).west(2)).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().south(2).west(2)).getBlock() == Blocks.RED_NETHER_BRICK
					
				&& world.getBlockState(pos.up().east(2).south()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().west(2).north()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().east(2).north()).getBlock() == Blocks.RED_NETHER_BRICK
				&& world.getBlockState(pos.up().west(2).south()).getBlock() == Blocks.RED_NETHER_BRICK

				) {
			return true;
		}
		return false;
	}
	
	private boolean checkPedestalsT1() {
		if(world.getBlockState(pos.up().north(3).up()).getBlock() == BlockRegistry.pedestal
			&& world.getBlockState(pos.up().east(3).up()).getBlock() == BlockRegistry.pedestal	
			&& world.getBlockState(pos.up().south(3).up()).getBlock() == BlockRegistry.pedestal
			&& world.getBlockState(pos.up().west(3).up()).getBlock() == BlockRegistry.pedestal
				) {
			return true;
		}
		return false;
	}
	private ArrayList<TilePedestal> getT1Pedestals(){
		ArrayList<TilePedestal> list = new ArrayList<>();
		list.add((TilePedestal)world.getTileEntity(pos.up().north(3).up()));
		list.add((TilePedestal)world.getTileEntity(pos.up().east(3).up()));
		list.add((TilePedestal)world.getTileEntity(pos.up().south(3).up()));
		list.add((TilePedestal)world.getTileEntity(pos.up().west(3).up()));
		return list;
	}
	
	@SideOnly(Side.CLIENT)
	public void setTicksWorking(int ticks) {
		this.ticksWorking = ticks;
	}
	
	private void updateAllTiles() {
		//This tile
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
		//T1 Tiles
		world.notifyBlockUpdate(pos.up().north(3).up(), world.getBlockState(pos.up().north(3).up()), world.getBlockState(pos.up().north(3).up()), 2);
		world.notifyBlockUpdate(pos.up().east(3).up(), world.getBlockState(pos.up().east(3).up()), world.getBlockState(pos.up().east(3).up()), 2);
		world.notifyBlockUpdate(pos.up().south(3).up(), world.getBlockState(pos.up().south(3).up()), world.getBlockState(pos.up().south(3).up()), 2);
		world.notifyBlockUpdate(pos.up().west(3).up(), world.getBlockState(pos.up().west(3).up()), world.getBlockState(pos.up().west(3).up()), 2);
	}
}
