public class AttackSword extends AttackType{

	public AttackSword(Object s) {
		super(s);
	}

	void Attack() {
		
		Character attackingCharacter = (Character) attacker;
		UI.print(attackingCharacter.name + " attacked with an Axe!");
        Message m = new Message(attackingCharacter,"attack","25");
		attackingCharacter.publishMessage(m);
	}

}