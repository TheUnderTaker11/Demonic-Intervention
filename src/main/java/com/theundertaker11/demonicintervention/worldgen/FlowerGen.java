package com.theundertaker11.demonicintervention.worldgen;

import java.util.Random;

import com.theundertaker11.demonicintervention.init.BlockRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class FlowerGen implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		if (world.provider.getDimension() == -1 && rand.nextInt(12) == 0) {
			//System.out.println("Is it even called?");
			int x = (chunkX<<4) + rand.nextInt(15) - 7;
            int z = (chunkZ<<4) + rand.nextInt(15) - 7;
            int y = 128;
            for(int i=0; i<(y-3);i++) {
            	
            	BlockPos pos = new BlockPos(x, y - i, z);
            	if(world.getBlockState(pos).getBlock() == Blocks.AIR && world.getBlockState(pos.down()).getBlock() == Blocks.NETHERRACK) {
            		if(rand.nextInt(3) < 2) {
            			IBlockState state = BlockRegistry.netherFlower.getDefaultState();
            			world.setBlockState(pos, state);
            			break;
            		}
            	}
            }
		}
		
	}
}
