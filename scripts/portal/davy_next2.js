function enter(pi) {
    if (pi.getMap().getAllMonstersThreadsafe().size() == 0) {
		  if (pi.isLeader()) {
		pi.givePartyExp(400000);
		  pi.warpParty(925100500); //next
        } else {
            pi.playerMessage(5, "not.");
		  }
    } else {
        pi.playerMessage(5, "The portal is not opened yet.");
    }
}