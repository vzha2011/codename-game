package Main;

public class Person {
	private String _person;
	private String _identity;
	private boolean _b;

	public Person(String person, String id, boolean b) {
		_person = person;
		_identity = id;
		_b = b;

	}

	public String getPersonName() {
		return _person;
	}

	public String getIdentity() {
		return _identity;
	}

	public boolean CheckIfItIsRevealed() {
		return _b;
	}

	public void SetPersonRevealed() {
		_b = true;
	}
}
