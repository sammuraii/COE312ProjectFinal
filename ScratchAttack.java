public class ScratchAttack extends AttackType
{

	public ScratchAttack(Object s) {
		super(s);
		//TODO Auto-generated constructor stub
	}

	
	void Attack() {
		Animal attackingAnimal = (Animal) attacker;
		UI.print(attackingAnimal.name + " scratched you!");
        Message m = new Message(attackingAnimal,"attack","5");
		attackingAnimal.publishMessage(m);
	}
    
}