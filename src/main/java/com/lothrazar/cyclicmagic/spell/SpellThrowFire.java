package com.lothrazar.cyclicmagic.spell;

import com.lothrazar.cyclicmagic.projectile.EntityBlazeBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SpellThrowFire extends BaseSpell implements ISpell {

	public SpellThrowFire(int id,String name){
		super(id,name);
		this.cooldown = 30;
		this.experience = 50;
	}

	@Override
	public boolean cast(World world, EntityPlayer player, BlockPos pos, EnumFacing side ) {

		if(world.isRemote == false){
			 world.spawnEntityInWorld(new EntityBlazeBolt(world, player));
		}
		return true;
	}
}
