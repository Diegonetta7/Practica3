package es.uv.prnr.p2;

import javax.persistence.*;

@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "emp_no")
public class Manager extends Employee {
	@Column(name = "bonus", nullable = false)
	private Long bonus;

	public Manager() {

	}

	public Manager(Employee e, Long bonus) {
		super(e.getId(), e.getFirstName(), e.getLastName(), e.getBirthDate(), e.getHireDate(), e.getGender());
		this.bonus = bonus;
	}

	public Long getBonus() {
		return this.bonus;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}
}