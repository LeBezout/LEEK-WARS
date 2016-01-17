package com.leekwars.utils.fastgarden.impl;

import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.fastgarden.FastGardenVisitor;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.wrappers.GardenStatsWrapper;
import com.leekwars.utils.wrappers.MessageWrapper;

/**
 * Implementation de FastGardenVisitor qui ne fait RIEN.
 * @author Bezout
 */
public class NullFastGardenVisitor implements FastGardenVisitor {

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onInit(com.leekwars.utils.model.Farmer)
	 */
	@Override
	public void onInit(Farmer pFarmer) {
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onMessage(com.leekwars.utils.wrappers.MessageWrapper)
	 */
	@Override
	public void onMessage(final MessageWrapper pMessage) {
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onEntityChange(com.leekwars.utils.enums.EntityType, java.lang.String)
	 */
	@Override
	public void onEntityChange(EntityType pEntityType, String pEntityName) {
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onResult(com.leekwars.utils.model.Fight, com.leekwars.utils.enums.FightResult)
	 */
	@Override
	public void onResult(final Fight pFight, final FightResult pResult) {
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onBeforeStat()
	 */
	@Override
	public void onBeforeStat() {
	}
	
	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onStat(com.leekwars.utils.wrappers.GardenStatsWrapper)
	 */
	@Override
	public void onStat(GardenStatsWrapper pStat) {
	}
	
	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onEnd()
	 */
	@Override
	public void onEnd() {
	}
}
