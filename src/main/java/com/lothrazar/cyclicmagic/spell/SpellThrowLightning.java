package com.lothrazar.cyclicmagic.spell;

import com.lothrazar.cyclicmagic.projectile.EntityLightningballBolt; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SpellThrowLightning extends BaseSpell implements ISpell {

	public SpellThrowLightning(int id,String name){
		super(id,name);
		this.cooldown = 200;
		this.experience = 200;
		this.durability = 500;
	}
 
	@Override
	public boolean cast(World world, EntityPlayer player, BlockPos pos, EnumFacing side) {

		if(world.isRemote == false){
			world.spawnEntityInWorld(new EntityLightningballBolt(world, player));
		}
		return true;
	}
}
