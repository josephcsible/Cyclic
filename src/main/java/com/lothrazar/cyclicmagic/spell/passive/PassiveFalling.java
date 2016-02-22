package com.lothrazar.cyclicmagic.spell.passive;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import com.lothrazar.cyclicmagic.Const;
import com.lothrazar.cyclicmagic.PotionRegistry;

public class PassiveFalling implements IPassiveSpell{

	private final static float FALLDISTANCE = 3;
	private final static int SECONDS = 30;

	@Override
	public boolean canTrigger(EntityPlayer entity){

		return true;// there are multiple tests,so leave it up to .trigger
	}

	@Override
	public void trigger(EntityPlayer entity){

		if(entity.fallDistance >= FALLDISTANCE){

			if(entity.isPotionActive(PotionRegistry.slowfall) == false){
				PotionRegistry.addOrMergePotionEffect(entity, new PotionEffect(PotionRegistry.slowfall.id, SECONDS * Const.TICKS_PER_SEC));
			}
		}

		if(entity.getPosition().getY() < -10){
			entity.setPositionAndUpdate(entity.getPosition().getX(), entity.getPosition().getY() + 256, entity.getPosition().getZ());;
		}

		if(entity.ridingEntity != null && entity.ridingEntity.fallDistance >= FALLDISTANCE && entity.ridingEntity instanceof EntityLivingBase){
			EntityLivingBase maybeHorse = (EntityLivingBase) entity.ridingEntity;

			if(maybeHorse.isPotionActive(PotionRegistry.slowfall) == false){

				PotionRegistry.addOrMergePotionEffect(maybeHorse, new PotionEffect(PotionRegistry.slowfall.id, SECONDS * Const.TICKS_PER_SEC));
			}
		}
	}

	@Override
	public String getName(){

		return StatCollector.translateToLocal("spellpassive.falling.name");
	}

	@Override
	public String getInfo(){

		return StatCollector.translateToLocal("spellpassive.falling.info");
	}

	@Override
	public int getID(){

		return 3;
	}
}