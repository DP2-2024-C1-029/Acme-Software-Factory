
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
	@Index(columnList = "code", unique = true), //
	@Index(columnList = "code,id", unique = true), //
	@Index(columnList = "sponsorship_id,isPublished")
})
public class Invoice extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationTime;

	// TODO - 1 month later to registrationTime in servicies
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				dueDate;

	// TODO - comprobar positivo sin 0 en servicios
	@NotNull
	@Valid
	private Money				quantity;

	@Min(0)
	@Digits(integer = 3, fraction = 2)
	@Range(min = 0, max = 100)
	private double				tax;

	@URL
	@Length(max = 255)
	private String				link;

	// For deriverable 03
	private boolean				isPublished;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Money totalAmount() {
		if (this.quantity != null) {
			Money m = new Money();
			m.setAmount(this.quantity.getAmount() * (1 + this.tax / 100));
			m.setCurrency(this.quantity.getCurrency());
			return m;
		} else
			return null;
	}


	// Relationships ----------------------------------------------------------
	@Valid
	@NotNull
	@ManyToOne
	private Sponsorship sponsorship;
}
