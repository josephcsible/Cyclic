package com.lothrazar.cyclicmagic.spell;

import com.lothrazar.cyclicmagic.projectile.EntityShearingBolt; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SpellThrowShear extends BaseSpellThrown implements ISpell {

	public SpellThrowShear(int id,String name){
		super.init(id,name);
		this.cost = 1;
	}
 
	@Override
	public boolean cast(World world, EntityPlayer player, BlockPos pos, EnumFacing side) {

		if(world.isRemote == false){
			world.spawnEntityInWorld(new EntityShearingBolt(world, player));
		}
		return true;
	}
}
